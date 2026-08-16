package com.asset.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设备入账未审表实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("equipment_register")
public class EquipmentRegister {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ExcelProperty("业务单号")
    private String ywdh;
    @ExcelProperty("设备名称")
    private String zcmc;
    @ExcelProperty("使用单位号")
    private String lydwh;
    @ExcelProperty("使用单位名")
    private String lydwm;
    @ExcelProperty("分类号")
    private String zcflh;
    @ExcelProperty("品牌型号")
    private String ppxh;
    @ExcelProperty("规格")
    private String gg;
    @ExcelProperty("数量")
    private Integer sl;
    @ExcelProperty("单价")
    private BigDecimal dj;
    @ExcelProperty("金额")
    private BigDecimal je;
    @ExcelProperty("计量单位")
    private String jldw;
    @ExcelProperty("厂家")
    private String cj;
    @ExcelProperty("购置日期")
    private LocalDate ggrq;
    @ExcelProperty("现状")
    private String xz;
    @ExcelProperty("经费科目")
    private String jfkm;
    @ExcelProperty("发票号")
    private String fph;
    @ExcelProperty("供货商")
    private String ghs;
    @ExcelProperty("存放地编号")
    private String cfdbh;
    @ExcelProperty("存放地名称")
    private String cfdmc;
    @ExcelProperty("使用人编号")
    private String syrbh;
    @ExcelProperty("使用人")
    private String syr;
    @ExcelProperty("经手人")
    private String jsr;
    @ExcelProperty("审核状态")
    private String shzt;
    @ExcelProperty("记帐人")
    private String jzr;
    @ExcelProperty("入账时间")
    private LocalDate rzrq;
    @ExcelProperty("备注")
    private String bz;
    @ExcelProperty("输入人")
    private String srr;
    @ExcelProperty("输入日期")
    private LocalDateTime srrq;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
} 