package com.hx.module.system.dao;

import com.hx.base.BaseDao;
import com.hx.module.system.domain.entity.Dict;

import java.util.List;
import java.util.Set;


public interface DictDao extends BaseDao<Dict, Long> {

    /**
     * 删除
     * @param ids /
     */
    void deleteByIdIn(Set<Long> ids);

    /**
     * 查询
     * @param ids /
     * @return /
     */
    List<Dict> findByIdIn(Set<Long> ids);
}
