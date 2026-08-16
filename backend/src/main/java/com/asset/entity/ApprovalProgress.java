package com.asset.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 审核进度表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("approval_progress")
public class ApprovalProgress {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String ywdh;

    private Integer xh;

    private String ywlx;

    private String shrbh;
    @TableField(value = "shr")
    private String checker;

    private LocalDate shrq;

    private String shyj;

    private String shzt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 