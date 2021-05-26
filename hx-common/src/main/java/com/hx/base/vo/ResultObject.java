package com.hx.base.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lb
 * 前后端交互数据标准
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class ResultObject<T> implements Serializable {


}
