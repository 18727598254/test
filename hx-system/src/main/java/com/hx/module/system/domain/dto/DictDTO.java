package com.hx.module.system.domain.dto;

import com.hx.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
public class DictDTO extends BaseDTO implements Serializable {

    private Long id;

    private List<DictDetailDTO> dictDetails;

    private String name;

    private String description;
}
