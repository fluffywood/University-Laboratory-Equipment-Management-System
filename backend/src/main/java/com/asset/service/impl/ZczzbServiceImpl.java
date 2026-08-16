package com.asset.service.impl;

import com.asset.entity.Zczzb;
import com.asset.entity.ApprovalProgress;
import com.asset.mapper.ZczzbMapper;
import com.asset.mapper.ApprovalProgressMapper;
import com.asset.service.ZczzbService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ZczzbServiceImpl extends ServiceImpl<ZczzbMapper, Zczzb> implements ZczzbService {
    @Autowired
    private ApprovalProgressMapper approvalProgressMapper;

    @Override
    public IPage<Zczzb> pageZczzb(Integer pageNum, Integer pageSize, String keyword) {
        Page<Zczzb> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Zczzb> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Zczzb::getZcmc, keyword)
                   .or().like(Zczzb::getYwdh, keyword)
                   .or().like(Zczzb::getLydwm, keyword);
        }
        wrapper.orderByDesc(Zczzb::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean submitForApproval(Zczzb zczzb) {
        zczzb.setYwdh(generateYwdh());
        zczzb.setShzt("0");
        zczzb.setSrrq(LocalDateTime.now());
        boolean saved = this.save(zczzb);
        if (saved) {
            ApprovalProgress progress = new ApprovalProgress();
            progress.setYwdh(zczzb.getYwdh());
            progress.setXh(1);
            progress.setYwlx("5"); // 5=单价增值
            progress.setShzt("0");
            progress.setShrq(LocalDate.now());
            progress.setChecker("system");
            approvalProgressMapper.insert(progress);
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean firstReview(String ywdh, String shyj, String shr) {
        LambdaQueryWrapper<Zczzb> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Zczzb::getYwdh, ywdh);
        Zczzb zczzb = this.getOne(wrapper);
        if (zczzb != null) {
            zczzb.setShzt("1");
            this.updateById(zczzb);
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh).eq(ApprovalProgress::getYwlx, "5");
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);
            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("1");
                approvalProgressMapper.updateById(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean finalReview(String ywdh, String shyj, String shr) {
        LambdaQueryWrapper<Zczzb> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Zczzb::getYwdh, ywdh);
        Zczzb zczzb = this.getOne(wrapper);
        if (zczzb != null) {
            zczzb.setShzt("2");
            this.updateById(zczzb);
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh).eq(ApprovalProgress::getYwlx, "5");
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);
            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("2");
                approvalProgressMapper.updateById(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reject(String ywdh, String shyj, String shr) {
        LambdaQueryWrapper<Zczzb> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Zczzb::getYwdh, ywdh);
        Zczzb zczzb = this.getOne(wrapper);
        if (zczzb != null) {
            zczzb.setShzt("9");
            this.updateById(zczzb);
            LambdaQueryWrapper<ApprovalProgress> progressWrapper = new LambdaQueryWrapper<>();
            progressWrapper.eq(ApprovalProgress::getYwdh, ywdh).eq(ApprovalProgress::getYwlx, "5");
            ApprovalProgress progress = approvalProgressMapper.selectOne(progressWrapper);
            if (progress != null) {
                progress.setChecker(shr);
                progress.setShrq(LocalDate.now());
                progress.setShyj(shyj);
                progress.setShzt("9");
                approvalProgressMapper.updateById(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    public Zczzb getByYwdh(String ywdh) {
        LambdaQueryWrapper<Zczzb> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Zczzb::getYwdh, ywdh);
        return this.getOne(wrapper);
    }

    @Override
    public String generateYwdh() {
        String year = String.valueOf(LocalDate.now().getYear());
        long count = this.count();
        return year + String.format("%06d", count + 1);
    }
} 