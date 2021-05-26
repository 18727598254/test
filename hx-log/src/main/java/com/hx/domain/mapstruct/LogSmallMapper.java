package com.hx.domain.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.domain.entity.Log;
import com.hx.domain.dto.LogSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends BaseMapper<LogSmallDTO, Log> {

}
