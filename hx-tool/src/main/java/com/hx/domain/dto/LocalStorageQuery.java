package com.hx.domain.dto;

import com.hx.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
public class LocalStorageQuery {

    @Query(blurry = "name,suffix,type,createBy,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
