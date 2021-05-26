package com.hx.module.system.dao;

import com.hx.base.BaseDao;
import com.hx.module.system.domain.entity.DictDetail;

import java.util.List;


public interface DictDetailDao extends BaseDao<DictDetail, Long> {

    /**
     * 根据字典名称查询
     * @param name /
     * @return /
     */
    List<DictDetail> findByDictName(String name);
}
