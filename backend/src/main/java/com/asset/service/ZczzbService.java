package com.asset.service;

import com.asset.entity.Zczzb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ZczzbService extends IService<Zczzb> {
    IPage<Zczzb> pageZczzb(Integer pageNum, Integer pageSize, String keyword);
    boolean submitForApproval(Zczzb zczzb);
    boolean firstReview(String ywdh, String shyj, String shr);
    boolean finalReview(String ywdh, String shyj, String shr);
    boolean reject(String ywdh, String shyj, String shr);
    Zczzb getByYwdh(String ywdh);
    String generateYwdh();
} 