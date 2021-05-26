package com.hx.module.system.domain.query;

import com.hx.annotation.Query;
import lombok.Data;

@Data
public class DictDetailQuery {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name",joinName = "dict")
    private String dictName;
}
