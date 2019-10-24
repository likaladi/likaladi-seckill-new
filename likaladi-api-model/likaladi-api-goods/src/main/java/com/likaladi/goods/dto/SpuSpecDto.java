package com.likaladi.goods.dto;

import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
/**
 * @author likaladi
 */
@Data
@ApiModel(value = "规格")
public class SpuSpecDto {

    @ApiModelProperty(value = "规格键")
    private String key;

    @ApiModelProperty(value = "规格值")
    private List<String> value;

    @ApiModelProperty(value = "是否搜索属性：0不是；1是")
    private Boolean isSearch;
}
