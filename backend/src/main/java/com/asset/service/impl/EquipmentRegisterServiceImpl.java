package com.asset.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.StringUtils;
import com.asset.entity.ApprovalProgress;
import com.asset.entity.EquipmentRegister;
import com.asset.mapper.ApprovalProgressMapper;
import com.asset.mapper.EquipmentRegisterMapper;
import com.asset.service.EquipmentRegisterService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 设备入账未审表Service实现类
 */
@Service
public class EquipmentRegisterServiceImpl extends ServiceImpl<EquipmentRegisterMapper, EquipmentRegister> implements EquipmentRegisterService {

    @Autowired
    private ApprovalProgressMapper approvalProgressMapper;

    @Override
    public IPage<EquipmentRegister> pageEquipmentRegister(Integer pageNum, Integer pageSize, String keyword) {
        Page<EquipmentRegister> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(EquipmentRegister::getZcmc, keyword)
                  .or()
                  .like(EquipmentRegister::getYwdh, keyword)
                  .or()
                  .like(EquipmentRegister::getLydwm, keyword);
        }
        
        wrapper.orderByDesc(EquipmentRegister::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitForApproval(EquipmentRegister equipmentRegister) {
        // 生成业务单号
        equipmentRegister.setYwdh(generateYwdh());
        equipmentRegister.setShzt("0"); // 设置为未审状态
        equipmentRegister.setSrrq(LocalDateTime.now());
        
        // 保存设备登记信息
        boolean saved = this.save(equipmentRegister);
        
        if (saved) {
            // 创建审核进度记录
            ApprovalProgress progress = new ApprovalProgress();
            progress.setYwdh(equipmentRegister.getYwdh());
            progress.setXh(1);
            progress.setYwlx("1"); // 新增设备
            progress.setShzt("0"); // 未审核
            progress.setShrq(LocalDate.now());
            progress.setChecker("system");
            approvalProgressMapper.insert(progress);
        }
        
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean firstReview(String ywdh, String shyj, String shr) {
        // 更新设备审核状态
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentRegister::getYwdh, ywdh);
        EquipmentRegister equipment = this.getOne(wrapper);
        
        if (equipment != null) {
            equipment.setShzt("1"); // 设置为初审状态
            this.updateById(equipment);
            
            // 更新审核进度
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh);
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);
            
            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("1"); // 初审
                approvalProgressMapper.updateById(progress);
            }
            
            return true;
        }
        
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean middleReview(String ywdh, String shyj, String shr) {
        // 更新设备审核状态
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentRegister::getYwdh, ywdh);
        EquipmentRegister equipment = this.getOne(wrapper);

        if (equipment != null) {
            equipment.setShzt("2"); // 设置为中审状态
            this.updateById(equipment);

            // 更新审核进度
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh);
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);

            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("2"); // 中审
                approvalProgressMapper.updateById(progress);
            }

            return true;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean finalReview(String ywdh, String shyj, String shr) {
        // 更新设备审核状态
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentRegister::getYwdh, ywdh);
        EquipmentRegister equipment = this.getOne(wrapper);

        if (equipment != null) {
            equipment.setShzt("3"); // 设置为终审状态
            this.updateById(equipment);

            // 更新审核进度
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh);
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);

            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("3"); // 终审
                approvalProgressMapper.updateById(progress);
            }

            return true;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reject(String ywdh, String shyj, String shr) {
        // 更新设备审核状态
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentRegister::getYwdh, ywdh);
        EquipmentRegister equipment = this.getOne(wrapper);
        
        if (equipment != null) {
            equipment.setShzt("9"); // 设置为驳回状态
            this.updateById(equipment);
            
            // 更新审核进度
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh);
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);
            
            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("9"); // 驳回
                approvalProgressMapper.updateById(progress);
            }
            
            return true;
        }
        
        return false;
    }

    @Override
    public String generateYwdh() {
        // 生成业务单号：4位年份 + 6位流水号
        LocalDate localDate = LocalDate.now();
        String year = localDate.format(DateTimeFormatter.ofPattern("yyyy"));
//        String year = DateUtil.format(LocalDate.now(), "yyyy");
        
        // 查询当前年份的最大流水号
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(EquipmentRegister::getYwdh, year)
               .orderByDesc(EquipmentRegister::getYwdh)
               .last("LIMIT 1");
        
        EquipmentRegister lastRecord = this.getOne(wrapper);
        
        int sequence = 1;
        if (lastRecord != null && StrUtil.isNotBlank(lastRecord.getYwdh())) {
            String lastYwdh = lastRecord.getYwdh();
            if (lastYwdh.length() >= 10) {
                String sequenceStr = lastYwdh.substring(4);
                try {
                    sequence = Integer.parseInt(sequenceStr) + 1;
                } catch (NumberFormatException e) {
                    sequence = 1;
                }
            }
        }
        
        return year + String.format("%06d", sequence);
    }

    @Override
    public List<EquipmentRegister> listForExport(String keyword) {
        LambdaQueryWrapper<EquipmentRegister> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(EquipmentRegister::getZcmc, keyword)
                   .or().like(EquipmentRegister::getYwdh, keyword)
                   .or().like(EquipmentRegister::getLydwm, keyword);
        }
        wrapper.orderByDesc(EquipmentRegister::getCreateTime);
        return this.list(wrapper);
    }
} 