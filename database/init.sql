-- 高校资产管理系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS asset_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE asset_management;

-- 设备入账未审表
CREATE TABLE equipment_register (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    lydwh VARCHAR(10) NOT NULL COMMENT '使用单位号',
    lydwm VARCHAR(60) COMMENT '使用单位名',
    Zcbhqj VARCHAR(30) COMMENT '设备编号区间',
    zcflh VARCHAR(8) NOT NULL COMMENT '分类号',
    zcmc VARCHAR(40) NOT NULL COMMENT '设备名称',
    ppxh VARCHAR(30) NOT NULL COMMENT '品牌型号',
    gg VARCHAR(50) NOT NULL COMMENT '规格',
    SL INT COMMENT '数量',
    DJ DECIMAL(12,2) COMMENT '单价',
    JE DECIMAL(12,2) NOT NULL COMMENT '金额',
    jldw VARCHAR(20) COMMENT '计量单位',
    Cj VARCHAR(40) NOT NULL COMMENT '厂家',
    Ggrq DATE NOT NULL COMMENT '购置日期',
    Xz VARCHAR(1) NOT NULL COMMENT '现状',
    jfkm VARCHAR(1) NOT NULL COMMENT '经费科目',
    fph VARCHAR(20) COMMENT '发票号',
    ghs VARCHAR(40) COMMENT '供货商',
    cfdbh VARCHAR(12) COMMENT '存放地编号',
    cfdmc VARCHAR(30) COMMENT '存放地名称',
    SYRBH VARCHAR(20) COMMENT '使用人编号',
    SYR VARCHAR(20) NOT NULL COMMENT '使用人',
    JSR VARCHAR(20) NOT NULL COMMENT '经手人',
    SHZT VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '审核状态：0未审 1初审 2终审',
    ywdh VARCHAR(12) COMMENT '业务单号',
    jzr VARCHAR(20) NOT NULL COMMENT '记帐人',
    Rzrq DATE NOT NULL COMMENT '入账时间',
    Bz VARCHAR(200) COMMENT '备注',
    srr VARCHAR(20) COMMENT '输入人',
    srrq DATETIME NOT NULL COMMENT '输入日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_lydwh (lydwh),
    INDEX idx_zcflh (zcflh),
    INDEX idx_JE (JE),
    INDEX idx_Ggrq (Ggrq),
    INDEX idx_SHZT (SHZT),
    INDEX idx_ywdh (ywdh)
) COMMENT '设备入账未审表';

-- 单位表
CREATE TABLE department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    DWBH VARCHAR(10) NOT NULL UNIQUE COMMENT '单位编号',
    DWMC VARCHAR(60) NOT NULL COMMENT '单位名称',
    FJDBH VARCHAR(10) COMMENT '父节点编号',
    DWXZ VARCHAR(1) COMMENT '单位性质：1教学 2科研 3教辅 4行政 5后勤 6其它',
    DWBZ VARCHAR(1) NOT NULL DEFAULT '*' COMMENT '单位标志',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_DWBH (DWBH),
    INDEX idx_FJDBH (FJDBH)
) COMMENT '单位表';

-- 存放地点表
CREATE TABLE storage_location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    XH INT NOT NULL COMMENT '序号',
    CFDBH VARCHAR(12) NOT NULL UNIQUE COMMENT '存放地编号',
    CFDMC VARCHAR(40) COMMENT '存放地名称',
    DWBH VARCHAR(10) COMMENT '单位编号',
    DWMC VARCHAR(60) COMMENT '单位名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_CFDBH (CFDBH),
    INDEX idx_DWBH (DWBH)
) COMMENT '存放地点表';

-- 人员表
CREATE TABLE personnel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    XH INT NOT NULL COMMENT '序号',
    DWBH VARCHAR(10) COMMENT '单位编号',
    DWMC VARCHAR(60) COMMENT '单位名称',
    RYBH VARCHAR(12) NOT NULL UNIQUE COMMENT '人员编号',
    RYM VARCHAR(20) NOT NULL COMMENT '人员名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_RYBH (RYBH),
    INDEX idx_DWBH (DWBH)
) COMMENT '人员表';

-- 审核进度表
CREATE TABLE approval_progress (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    ywdh VARCHAR(12) NOT NULL COMMENT '业务单号',
    Xh INT NOT NULL COMMENT '序号',
    ywlx VARCHAR(2) COMMENT '业务类型：1新增设备 2报废 3丢失 4校内调拨 5单价增值 6减值 7退库',
    shrbh VARCHAR(10) COMMENT '审核人编号',
    shr VARCHAR(40) COMMENT '审核人',
    shrq DATE COMMENT '审核日期',
    shyj VARCHAR(200) COMMENT '审核意见',
    shzt VARCHAR(1) COMMENT '审核状态：1初审 2终审 9驳回',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_ywdh (ywdh),
    INDEX idx_shrbh (shrbh)
) COMMENT '审核进度表';

-- 设备分类表
CREATE TABLE equipment_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    FLDM INT NOT NULL UNIQUE COMMENT '分类代码',
    FLMC VARCHAR(40) COMMENT '分类名称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_FLDM (FLDM)
) COMMENT '设备分类表';

-- 字典表
CREATE TABLE dictionary (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    ZDM VARCHAR(20) NOT NULL COMMENT '字段名',
    DM INT NOT NULL COMMENT '代码',
    MC VARCHAR(40) COMMENT '名称',
    BZ VARCHAR(10) COMMENT '标志',
    ZY VARCHAR(60) COMMENT '摘要',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_ZDM (ZDM),
    INDEX idx_DM (DM),
    UNIQUE KEY uk_zdm_dm (ZDM, DM)
) COMMENT '字典表';

-- 资产增值表
CREATE TABLE zczzb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lydwh VARCHAR(10) NOT NULL,
    lydwm VARCHAR(60),
    zcbhqj VARCHAR(30),
    zcmc VARCHAR(40) NOT NULL,
    ppxh VARCHAR(30) NOT NULL,
    zzqje DECIMAL(12,2) NOT NULL,
    zzje DECIMAL(12,2) NOT NULL,
    zzhje DECIMAL(12,2) NOT NULL,
    ggrq DATE NOT NULL,
    xz VARCHAR(1) NOT NULL,
    jfkm VARCHAR(1) NOT NULL,
    fph VARCHAR(20),
    ghs VARCHAR(40),
    cfdbh VARCHAR(12),
    cfdmc VARCHAR(30),
    syrbh VARCHAR(20),
    syr VARCHAR(20) NOT NULL,
    jsr VARCHAR(20) NOT NULL,
    shzt VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '0未审 1初审 2终审',
    ywdh VARCHAR(30),
    jzr VARCHAR(20) NOT NULL,
    rzrq DATE NOT NULL,
    bz VARCHAR(200),
    srr VARCHAR(20),
    srrq DATETIME NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- 插入初始数据

-- 插入单位数据
INSERT INTO department (DWBH, DWMC, FJDBH, DWXZ, DWBZ) VALUES
('001', '计算机学院', NULL, '1', '*'),
('002', '信息工程学院', NULL, '1', '*'),
('003', '机械工程学院', NULL, '1', '*'),
('004', '财务处', NULL, '4', '*'),
('005', '资产处', NULL, '4', '*');

-- 插入设备分类数据
INSERT INTO equipment_category (FLDM, FLMC) VALUES
(100, '计算机设备'),
(101, '服务器'),
(102, '网络设备'),
(200, '办公设备'),
(201, '打印机'),
(202, '复印机'),
(300, '实验设备'),
(301, '分析仪器'),
(302, '测量设备');

-- 插入字典数据
INSERT INTO dictionary (ZDM, DM, MC, BZ, ZY) VALUES
-- 现状字典
('XZ', 1, '在用', '1', '设备正在使用中'),
('XZ', 2, '闲置', '1', '设备暂时闲置'),
('XZ', 3, '待修', '1', '设备需要维修'),
('XZ', 4, '待报废', '1', '设备准备报废'),
('XZ', 5, '丢失', '1', '设备丢失'),
('XZ', 6, '报废', '1', '设备已报废'),
('XZ', 7, '出售', '1', '设备已出售'),
('XZ', 9, '其它', '1', '其他状态'),
('XZ', 65, '调入', '1', '设备调入'),
('XZ', 66, '转入', '1', '设备转入'),
('XZ', 67, '转出', '1', '设备转出'),
('XZ', 68, '注销', '1', '设备注销'),
('XZ', 69, '盘亏', '1', '设备盘亏'),
('XZ', 70, '调剂', '1', '设备调剂'),
('XZ', 71, '对外捐赠', '1', '设备对外捐赠'),

-- 经费科目字典
('jfkm', 1, '教学', '1', '教学经费'),
('jfkm', 2, '科研', '1', '科研经费'),
('jfkm', 3, '基建', '1', '基建经费'),
('jfkm', 4, '自筹经费', '1', '自筹经费'),
('jfkm', 5, '世界银行贷款', '1', '世界银行贷款'),
('jfkm', 6, '捐赠', '1', '捐赠经费'),
('jfkm', 9, '其它', '1', '其他经费'),
('jfkm', 65, '研究生', '1', '研究生经费'),
('jfkm', 66, '贷款配套费', '1', '贷款配套费'),
('jfkm', 67, '行政事业费', '1', '行政事业费'),
('jfkm', 68, '211经费', '1', '211工程经费'),
('jfkm', 69, '十五投资', '1', '十五投资'),
('jfkm', 70, '985经费', '1', '985工程经费'); 