package com.hx.module.system.service;

import com.hx.module.system.domain.dto.DeptDTO;
import com.hx.module.system.domain.entity.Dept;
import com.hx.module.system.domain.query.DeptQuery;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


public interface DeptService {

    /**
     * 查询所有数据
     *
     * @param criteria 条件
     * @param isQuery  /
     * @return /
     * @throws Exception /
     */
    List<DeptDTO> queryAll(DeptQuery criteria, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    DeptDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     */
    void create(Dept resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Dept resources);

    /**
     * 删除
     *
     * @param deptDtos /
     */
    void delete(Set<DeptDTO> deptDtos);

    /**
     * 根据PID查询
     *
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);

    /**
     * 根据角色ID查询
     *
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DeptDTO> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     *
     * @param deptList /
     * @param deptDtos /
     * @return /
     */
    Set<DeptDTO> getDeleteDepts(List<Dept> deptList, Set<DeptDTO> deptDtos);

    /**
     * 根据ID获取同级与上级数据
     *
     * @param deptDto /
     * @param depts   /
     * @return /
     */
    List<DeptDTO> getSuperior(DeptDTO deptDto, List<Dept> depts);

    /**
     * 构建树形数据
     *
     * @param deptDtos /
     * @return /
     */
    Object buildTree(List<DeptDTO> deptDtos);

    /**
     * 获取
     *
     * @param deptId
     * @param deptList
     * @return
     */
    List<Long> getDeptChildren(Long deptId, List<Dept> deptList);

    /**
     * 验证是否被角色或用户关联
     *
     * @param deptDtos /
     */
    void verification(Set<DeptDTO> deptDtos);
}
