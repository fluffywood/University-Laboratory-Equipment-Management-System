package com.asset.service;

import com.asset.entity.EquipmentRegister;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 设备入账未审表Service接口
 */
public interface EquipmentRegisterService extends IService<EquipmentRegister> {

    /**
     * 分页查询设备登记信息
     */
    IPage<EquipmentRegister> pageEquipmentRegister(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 提交设备卡片进行审核
     */
    boolean submitForApproval(EquipmentRegister equipmentRegister);

    /**
     * 初审
     */
    boolean firstReview(String ywdh, String shyj, String shr);

    /**
     * 终审
     */
    boolean finalReview(String ywdh, String shyj, String shr);

    /**
     * 驳回
     */
    boolean reject(String ywdh, String shyj, String shr);

    /**
     * 中审
     */
    boolean middleReview(String ywdh, String shyj, String shr);

    /**
     * 生成业务单号
     */
    String generateYwdh();

    /**
     * 导出设备列表
     */
    List<EquipmentRegister> listForExport(String keyword);
} 