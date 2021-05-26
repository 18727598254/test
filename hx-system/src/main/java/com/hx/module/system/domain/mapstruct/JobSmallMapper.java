
package com.hx.module.system.domain.mapstruct;

import com.hx.base.BaseMapper;
import com.hx.module.system.domain.dto.JobSmallDTO;
import com.hx.module.system.domain.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends BaseMapper<JobSmallDTO, Job> {

}
