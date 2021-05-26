package com.hx.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.hx.enumeration.DataScopeEnum;
import com.hx.exception.HxException;
import com.hx.module.system.dao.DeptDao;
import com.hx.module.system.dao.RoleDao;
import com.hx.module.system.dao.UserDao;
import com.hx.module.system.domain.entity.Dept;
import com.hx.module.system.domain.entity.User;
import com.hx.module.system.service.DeptService;
import com.hx.module.system.domain.dto.DeptDTO;
import com.hx.module.system.domain.query.DeptQuery;
import com.hx.module.system.domain.mapstruct.DeptMapper;
import com.hx.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dept")
public class DeptServiceImpl implements DeptService {

    private final DeptDao deptRepository;
    private final DeptMapper deptMapper;
    private final UserDao userRepository;
    private final RedisUtils redisUtils;
    private final RoleDao roleRepository;

    @Override
    public List<DeptDTO> queryAll(DeptQuery criteria, Boolean isQuery) throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "deptSort");

        String dataScopeType = SecurityUtils.getDataScopeType();
        if (isQuery) {
            if(dataScopeType.equals(DataScopeEnum.ALL.getValue())){
                criteria.setPidIsNull(true);
            }
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>(){{ add("pidIsNull");add("enabled");}};
            for (Field field : fields) {
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                Object val = field.get(criteria);
                if(fieldNames.contains(field.getName())){
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        List<DeptDTO> list = deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),sort));
        // 如果为空，就代表为自定义权限或者本级权限，就需要去重，不理解可以注释掉，看查询结果
        if(StringUtils.isBlank(dataScopeType)) {
            return deduplication(list);
        }
        return list;
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public DeptDTO findById(Long id) {
        Dept dept = deptRepository.findById(id).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getId(),"Dept","id",id);
        return deptMapper.toDto(dept);
    }

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Dept resources) {
        deptRepository.save(resources);
        // 计算子节点数目
        resources.setSubCount(0);
        // 清理缓存
        updateSubCnt(resources.getPid());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        // 旧的部门
        Long oldPid = findById(resources.getId()).getPid();
        Long newPid = resources.getPid();
        if(resources.getPid() != null && resources.getId().equals(resources.getPid())) {
            throw new HxException("上级不能为自己");
        }
        Dept dept = deptRepository.findById(resources.getId()).orElseGet(Dept::new);
        ValidationUtil.isNull( dept.getId(),"Dept","id",resources.getId());
        resources.setId(dept.getId());
        deptRepository.save(resources);
        // 更新父节点中子节点数目
        updateSubCnt(oldPid);
        updateSubCnt(newPid);
        // 清理缓存
        delCaches(resources.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<DeptDTO> deptDtos) {
        for (DeptDTO deptDto : deptDtos) {
            // 清理缓存
            delCaches(deptDto.getId());
            deptRepository.deleteById(deptDto.getId());
            updateSubCnt(deptDto.getPid());
        }
    }

    @Override
    public void download(List<DeptDTO> deptDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDTO deptDTO : deptDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("部门名称", deptDTO.getName());
            map.put("部门状态", deptDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", deptDTO.getCreateTime());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    public Set<DeptDTO> getDeleteDepts(List<Dept> menuList, Set<DeptDTO> deptDtos) {
        for (Dept dept : menuList) {
            deptDtos.add(deptMapper.toDto(dept));
            List<Dept> depts = deptRepository.findByPid(dept.getId());
            if(depts!=null && depts.size()!=0){
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

    @Override
    public List<Long> getDeptChildren(Long deptId, List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()){
                        List<Dept> depts = deptRepository.findByPid(dept.getId());
                        if(deptList.size() != 0){
                            list.addAll(getDeptChildren(dept.getId(), depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }

    @Override
    public List<DeptDTO> getSuperior(DeptDTO deptDto, List<Dept> depts) {
        if(deptDto.getPid() == null){
            depts.addAll(deptRepository.findByPidIsNull());
            return deptMapper.toDto(depts);
        }
        depts.addAll(deptRepository.findByPid(deptDto.getPid()));
        return getSuperior(findById(deptDto.getPid()), depts);
    }

    @Override
    public Object buildTree(List<DeptDTO> deptDtos) {
        Set<DeptDTO> trees = new LinkedHashSet<>();
        Set<DeptDTO> depts= new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDTO::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDTO deptDTO : deptDtos) {
            isChild = false;
            if (deptDTO.getPid() == null) {
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDtos) {
                if (it.getPid() != null && deptDTO.getId().equals(it.getPid())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if(isChild) {
                depts.add(deptDTO);
            } else if(deptDTO.getPid() != null &&  !deptNames.contains(findById(deptDTO.getPid()).getName())) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtil.isEmpty(trees)) {
            trees = depts;
        }
        Map<String,Object> map = new HashMap<>(2);
        map.put("totalElements",deptDtos.size());
        map.put("content",CollectionUtil.isEmpty(trees)? deptDtos :trees);
        return map;
    }

    @Override
    public void verification(Set<DeptDTO> deptDtos) {
        Set<Long> deptIds = deptDtos.stream().map(DeptDTO::getId).collect(Collectors.toSet());
        if(userRepository.countByDepts(deptIds) > 0){
            throw new HxException("所选部门存在用户关联，请解除后再试！");
        }
        if(roleRepository.countByDepts(deptIds) > 0){
            throw new HxException("所选部门存在角色关联，请解除后再试！");
        }
    }

    private void updateSubCnt(Long deptId){
        if(deptId != null){
            int count = deptRepository.countByPid(deptId);
            deptRepository.updateSubCntById(count, deptId);
        }
    }

    /**
     * 清理缓存
     */
    private void delCaches(Long id){
        List<User> users = userRepository.findByDeptRoleId(id);
        // 删除数据权限
        redisUtils.delByKeys("data::user:",users.stream().map(User::getId).collect(Collectors.toSet()));
        redisUtils.del("dept::id:" + id);
    }

    private List<DeptDTO> deduplication(List<DeptDTO> list) {
        List<DeptDTO> deptDtos = new ArrayList<>();
        for (DeptDTO deptDto : list) {
            boolean flag = true;
            for (DeptDTO dto : list) {
                if (deptDto.getPid().equals(dto.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                deptDtos.add(deptDto);
            }
        }
        return deptDtos;
    }
}
