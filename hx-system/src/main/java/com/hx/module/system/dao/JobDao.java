package com.hx.module.system.dao;

import com.hx.base.BaseDao;
import com.hx.module.system.domain.entity.Job;

import java.util.Set;

/**
* @author lb
*/
public interface JobDao extends BaseDao<Job, Long> {

    /**
     * 根据名称查询
     * @param name 名称
     * @return /
     */
    Job findByName(String name);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);
}
