package com.asset.controller;

import com.asset.entity.Zczzb;
import com.asset.service.ZczzbService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/zczzb")
@CrossOrigin(origins = "*")
public class ZczzbController {
    @Autowired
    private ZczzbService zczzbService;

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        IPage<Zczzb> page = zczzbService.pageZczzb(pageNum, pageSize, keyword);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        result.put("data", page);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitForApproval(@RequestBody Zczzb zczzb) {
        boolean success = zczzbService.submitForApproval(zczzb);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "提交成功，业务单号：" + zczzb.getYwdh());
            result.put("data", zczzb);
        } else {
            result.put("code", 500);
            result.put("message", "提交失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/first-review")
    public ResponseEntity<Map<String, Object>> firstReview(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        boolean success = zczzbService.firstReview(ywdh, shyj, shr);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "初审完成");
        } else {
            result.put("code", 500);
            result.put("message", "初审失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/final-review")
    public ResponseEntity<Map<String, Object>> finalReview(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        boolean success = zczzbService.finalReview(ywdh, shyj, shr);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "终审完成");
        } else {
            result.put("code", 500);
            result.put("message", "终审失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reject")
    public ResponseEntity<Map<String, Object>> reject(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        boolean success = zczzbService.reject(ywdh, shyj, shr);
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "驳回完成");
        } else {
            result.put("code", 500);
            result.put("message", "驳回失败");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail")
    public ResponseEntity<Map<String, Object>> detail(@RequestParam String ywdh) {
        Zczzb zczzb = zczzbService.getByYwdh(ywdh);
        Map<String, Object> result = new HashMap<>();
        if (zczzb != null) {
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", zczzb);
        } else {
            result.put("code", 404);
            result.put("message", "未找到该资产增值单");
        }
        return ResponseEntity.ok(result);
    }
} 