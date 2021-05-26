package com.hx.module.system.domain.query;

import com.hx.annotation.Query;
import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class DictQuery {

    @Query(blurry = "name,description")
    private String blurry;
}
