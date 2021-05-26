
package com.hx.module.system.domain.mapstruct;


import com.hx.base.BaseMapper;
import com.hx.module.system.domain.dto.DeptDTO;
import com.hx.module.system.domain.entity.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptDTO, Dept> {
}
