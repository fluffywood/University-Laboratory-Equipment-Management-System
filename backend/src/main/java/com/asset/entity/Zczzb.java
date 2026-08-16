package com.asset.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zczzb")
public class Zczzb {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String lydwh;
    private String lydwm;
    private String zcbhqj;
    private String zcmc;
    private String ppxh;
    private BigDecimal zzqje;
    private BigDecimal zzje;
    private BigDecimal zzhje;
    private LocalDate ggrq;
    private String xz;
    private String jfkm;
    private String fph;
    private String ghs;
    private String cfdbh;
    private String cfdmc;
    private String syrbh;
    private String syr;
    private String jsr;
    private String shzt = "0";
    private String ywdh;
    private String jzr;
    private LocalDate rzrq;
    private String bz;
    private String srr;
    private LocalDateTime srrq;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 