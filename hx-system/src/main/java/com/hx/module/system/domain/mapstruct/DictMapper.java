
package com.hx.module.system.domain.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.module.system.domain.dto.DictDTO;
import com.hx.module.system.domain.entity.Dict;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictMapper extends BaseMapper<DictDTO, Dict> {

}
