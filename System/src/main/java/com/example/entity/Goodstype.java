package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 3072 kB
 * </p>
 *
 * @author maso
 * @since 2024-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GoodsType对象", description="InnoDB free: 3072 kB")
public class Goodstype implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;


}
