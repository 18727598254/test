package com.hx.domain.dto;

import com.hx.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * sm.ms图床
 */
@Data
public class PictureQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String filename;

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
