package com.likaladi.base;

import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class BasePage implements Serializable {

    @ApiModelProperty(value = "页码")
    @Min(value = 1, message = "页码：1-100")
    @Max(value = 100, message = "页码：1-100")
    private Integer page;

    @Min(value = 1, message = "显示条数：1-100")
    @Max(value = 100, message = "显示条数：1-100")
    @ApiModelProperty(value = "显示条数")
    private Integer rows;

    @ApiModelProperty(value = "排序字段")
    private String sortBy;

    @ApiModelProperty(value = "升序-ASC; 降序-DESC")
    @Range(value = "ASC, DESC", message = "升序-ASC; 降序-DESC")
    private String sortEnum;
}
