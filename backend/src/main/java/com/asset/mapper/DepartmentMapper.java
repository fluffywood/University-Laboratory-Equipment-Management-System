package com.asset.mapper;

import com.asset.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 单位表Mapper接口
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
} 