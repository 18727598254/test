package com.hx.module.system.domain.dto;

import com.hx.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
public class DictDetailDTO extends BaseDTO implements Serializable {

    private Long id;

    private DictSmallDTO dict;

    private String label;

    private String value;

    private Integer dictSort;
}
