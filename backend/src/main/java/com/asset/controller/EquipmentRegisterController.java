package com.asset.controller;

import com.asset.entity.EquipmentRegister;
import com.asset.service.EquipmentRegisterService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.excel.EasyExcel;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.io.IOException;
import java.util.List;

/**
 * 设备入账未审表Controller
 */
@RestController
@RequestMapping("/equipment")
@CrossOrigin(origins = "*")
public class EquipmentRegisterController {

    @Autowired
    private EquipmentRegisterService equipmentRegisterService;

    /**
     * 分页查询设备登记信息
     */
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        
        IPage<EquipmentRegister> page = equipmentRegisterService.pageEquipmentRegister(pageNum, pageSize, keyword);
        
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");
        result.put("data", page);
        
        return ResponseEntity.ok(result);
    }

    /**
     * 根据ID查询设备登记信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        EquipmentRegister equipment = equipmentRegisterService.getById(id);
        
        Map<String, Object> result = new HashMap<>();
        if (equipment != null) {
            result.put("code", 200);
            result.put("message", "查询成功");
            result.put("data", equipment);
        } else {
            result.put("code", 404);
            result.put("message", "设备信息不存在");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 提交设备卡片进行审核
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitForApproval(@Valid @RequestBody EquipmentRegister equipmentRegister) {
        boolean success = equipmentRegisterService.submitForApproval(equipmentRegister);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "提交成功，业务单号：" + equipmentRegister.getYwdh());
            result.put("data", equipmentRegister);
        } else {
            result.put("code", 500);
            result.put("message", "提交失败");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 初审
     */
    @PostMapping("/first-review")
    public ResponseEntity<Map<String, Object>> firstReview(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        
        boolean success = equipmentRegisterService.firstReview(ywdh, shyj, shr);
        
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

    /**
     * 终审
     */
    @PostMapping("/final-review")
    public ResponseEntity<Map<String, Object>> finalReview(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        
        boolean success = equipmentRegisterService.finalReview(ywdh, shyj, shr);
        
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

    /**
     * 驳回
     */
    @PostMapping("/reject")
    public ResponseEntity<Map<String, Object>> reject(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {
        
        boolean success = equipmentRegisterService.reject(ywdh, shyj, shr);
        
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

    /**
     * 中审
     */
    @PostMapping("/middle-review")
    public ResponseEntity<Map<String, Object>> middleReview(
            @RequestParam String ywdh,
            @RequestParam String shyj,
            @RequestParam String shr) {

        boolean success = equipmentRegisterService.middleReview(ywdh, shyj, shr);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "中审完成");
        } else {
            result.put("code", 500);
            result.put("message", "中审失败");
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 更新设备登记信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentRegister equipmentRegister) {
        
        equipmentRegister.setId(id);
        boolean success = equipmentRegisterService.updateById(equipmentRegister);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "更新成功");
        } else {
            result.put("code", 500);
            result.put("message", "更新失败");
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * 删除设备登记信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        boolean success = equipmentRegisterService.removeById(id);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("code", 200);
            result.put("message", "删除成功");
        } else {
            result.put("code", 500);
            result.put("message", "删除失败");
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/export")
    public void exportEquipmentList(@RequestParam(required = false) String keyword, HttpServletResponse response) throws IOException {
        List<EquipmentRegister> list = equipmentRegisterService.listForExport(keyword);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("设备列表", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), EquipmentRegister.class)
                .sheet("设备列表")
                .doWrite(list);
    }
} 