
package com.hx.module.system.domain.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.module.system.domain.dto.RoleDTO;
import com.hx.module.system.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDTO, Role> {

}
