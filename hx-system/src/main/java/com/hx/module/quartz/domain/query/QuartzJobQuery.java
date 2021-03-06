package com.hx.module.quartz.domain.query;

import com.hx.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class QuartzJobQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
