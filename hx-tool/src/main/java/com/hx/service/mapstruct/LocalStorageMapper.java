package com.hx.service.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.domain.entity.LocalStorage;
import com.hx.domain.dto.LocalStorageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalStorageMapper extends BaseMapper<LocalStorageDTO, LocalStorage> {

}
