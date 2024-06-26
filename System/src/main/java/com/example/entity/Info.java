package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * InnoDB free: 3072 kB
 * </p>
 *
 * @author maso
 * @since 2024-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Info对象", description="InnoDB free: 3072 kB")
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String info;

    private Integer type;

    private LocalDateTime time;

    private Integer goods;


}
