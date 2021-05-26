
package com.hx.module.system.domain.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.module.system.domain.dto.UserDTO;
import com.hx.module.system.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", uses = {RoleMapper.class, DeptMapper.class, JobMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, User> {
}
