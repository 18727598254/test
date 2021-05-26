package com.hx.module.quartz.dao;

import com.hx.base.BaseDao;
import com.hx.module.quartz.domain.entity.QuartzJob;

import java.util.List;


public interface QuartzJobDao extends BaseDao<QuartzJob, Long> {

    /**
     * 查询启用的任务
     *
     * @return List
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
