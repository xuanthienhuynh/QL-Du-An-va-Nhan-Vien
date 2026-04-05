USE master;
GO


IF DB_ID('QL_DuAn_CongTy') IS NOT NULL
BEGIN
    ALTER DATABASE QL_DuAn_CongTy SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QL_DuAn_CongTy;
END
GO


CREATE DATABASE QL_DuAn_CongTy;
GO
USE QL_DuAn_CongTy;
GO



CREATE TABLE ChiNhanh (
    MaCN CHAR(10) PRIMARY KEY,
    TenCN NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(200)
);

CREATE TABLE PhongBan (
    MaPB CHAR(10) PRIMARY KEY,
    TenPB NVARCHAR(100) NOT NULL,
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN) 
);

CREATE TABLE NhanVien (
    MaNV CHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    DcTinh NVARCHAR(50), 
    DcPhuong NVARCHAR(50),
    DcSoNha NVARCHAR(50),
    NgaySinh DATE,
    Luong DECIMAL(18,2),
    TinhTrang NVARCHAR(20), -- Dùng '1' (Đang làm), '0' (Đã nghỉ) như ý bạn
    VaiTro NVARCHAR(20),    -- 'NhanVien', 'QuanLy', 'Sep'
    MatKhau VARCHAR(50),    
    MaPB CHAR(10) REFERENCES PhongBan(MaPB), 
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN)  
);

CREATE TABLE DuAn (
    MaDA CHAR(10) PRIMARY KEY,
    TenDA NVARCHAR(100) NOT NULL,
    KinhPhi DECIMAL(18,2),
    DoanhThu DECIMAL(18,2) DEFAULT 0, -- Tích hợp sẵn Doanh Thu
    NgayBatDau DATE,
    NgayKetThuc DATE, 
    TrangThai NVARCHAR(50), -- 'DangChay', 'KetThuc'
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN) 
);

CREATE TABLE PhanCong (
    MaNV CHAR(10) REFERENCES NhanVien(MaNV),
    MaDA CHAR(10) REFERENCES DuAn(MaDA),
    NgayThamGia DATE DEFAULT GETDATE(),
    NgayKetThuc DATE, 
    VaiTroDuAn NVARCHAR(50),
    DanhGia INT,
    PRIMARY KEY (MaNV, MaDA)
);
GO

-- 1. DỮ LIỆU CHI NHÁNH (Chuẩn 3 miền)
INSERT INTO ChiNhanh (MaCN, TenCN, DiaChi) VALUES
('CN_HN',  N'Chi Nhánh Hà Nội',  N'123 Cầu Giấy, Hà Nội'),
('CN_HCM', N'Chi Nhánh TP.HCM',  N'456 Nguyễn Huệ, Quận 1, TP.HCM'),
('CN_DN',  N'Chi Nhánh Đà Nẵng', N'789 Sông Hàn, Đà Nẵng');


INSERT INTO PhongBan (MaPB, TenPB, MaCN) VALUES

('PB_IT_HN',   N'Phòng Kỹ Thuật HN', 'CN_HN'),
('PB_HR_HN',   N'Phòng Nhân Sự HN',  'CN_HN'),
('PB_KT_HN',   N'Phòng Kế Toán HN',  'CN_HN'),

('PB_IT_HCM',  N'Phòng Kỹ Thuật HCM','CN_HCM'),
('PB_HR_HCM',  N'Phòng Nhân Sự HCM', 'CN_HCM'),
('PB_MKT_HCM', N'Phòng Marketing HCM','CN_HCM'),

('PB_IT_DN',   N'Phòng Kỹ Thuật ĐN', 'CN_DN'),
('PB_SALE_DN', N'Phòng Kinh Doanh ĐN','CN_DN');


INSERT INTO NhanVien (MaNV, HoTen, GioiTinh, DcTinh, DcPhuong, DcSoNha, NgaySinh, Luong, TinhTrang, VaiTro, MatKhau, MaPB, MaCN) VALUES

('NV01', N'Nguyễn Sếp Tổng',N'Nam', N'Hà Nội', N'Dịch Vọng',  N'Số 1',  '1980-01-01', 50000000, '1', 'Sep',      '123', 'PB_IT_HN',   'CN_HN'),

('NV02', N'Trần Quản Lý HN',N'Nữ',  N'Hà Nội', N'Mai Dịch',   N'Số 22', '1985-05-15', 25000000, '1', 'QuanLy',   '123', 'PB_IT_HN',   'CN_HN'),
('NV03', N'Lê Văn HN_1',    N'Nam', N'Hà Nội', N'Mỹ Đình',    N'Số 33', '1995-08-20', 15000000, '1', 'NhanVien', '123', 'PB_IT_HN',   'CN_HN'),
('NV04', N'Phạm Thị HN_2',  N'Nữ',  N'Hà Nội', N'Cầu Giấy',   N'Số 44', '1998-12-12', 12000000, '1', 'NhanVien', '123', 'PB_HR_HN',   'CN_HN'),
('NV05', N'Hoàng Văn HN_3', N'Nam', N'Hà Nội', N'Thanh Xuân', N'Số 55', '1993-03-10', 14000000, '1', 'NhanVien', '123', 'PB_KT_HN',   'CN_HN'),
('NV06', N'Đỗ Thị HN_4',    N'Nữ',  N'Hà Nội', N'Đống Đa',    N'Số 66', '1997-07-07', 11000000, '0', 'NhanVien', '123', 'PB_IT_HN',   'CN_HN'), -- Nghỉ việc
('NV07', N'Vũ Văn HN_5',    N'Nam', N'Hà Nội', N'Ba Đình',    N'Số 77', '1999-09-09', 10000000, '1', 'NhanVien', '123', 'PB_IT_HN',   'CN_HN'),
('NV08', N'Ngô Thị HN_6',   N'Nữ',  N'Hà Nội', N'Tây Hồ',     N'Số 88', '1996-06-06', 13000000, '1', 'NhanVien', '123', 'PB_HR_HN',   'CN_HN'),
('NV09', N'Bùi Văn HN_7',   N'Nam', N'Hà Nội', N'Hoàng Mai',  N'Số 99', '1994-11-11', 16000000, '1', 'NhanVien', '123', 'PB_IT_HN',   'CN_HN'),
('NV10', N'Đặng Thị HN_8',  N'Nữ',  N'Hà Nội', N'Hai Bà Trưng',N'Số 10', '1992-02-02', 12500000, '1', 'NhanVien', '123', 'PB_KT_HN',   'CN_HN'),

-- Nhân sự TP.HCM
('NV11', N'Trương Quản SG', N'Nam', N'TP.HCM', N'Bến Nghé',   N'Số 11', '1986-04-04', 28000000, '1', 'QuanLy',   '123', 'PB_IT_HCM',  'CN_HCM'),
('NV12', N'Lâm Văn SG_1',   N'Nam', N'TP.HCM', N'Thảo Điền',  N'Số 12', '1993-10-10', 16000000, '1', 'NhanVien', '123', 'PB_IT_HCM',  'CN_HCM'),
('NV13', N'Ngô Thị SG_2',   N'Nữ',  N'TP.HCM', N'Tân Định',   N'Số 13', '1997-01-01', 14000000, '1', 'NhanVien', '123', 'PB_MKT_HCM', 'CN_HCM'),
('NV14', N'Phan Văn SG_3',  N'Nam', N'TP.HCM', N'Phú Mỹ Hưng',N'Số 14', '1995-05-05', 18000000, '1', 'NhanVien', '123', 'PB_IT_HCM',  'CN_HCM'),
('NV15', N'Võ Thị SG_4',    N'Nữ',  N'TP.HCM', N'Bình Thạnh', N'Số 15', '1998-08-08', 12000000, '1', 'NhanVien', '123', 'PB_HR_HCM',  'CN_HCM'),
('NV16', N'Đinh Văn SG_5',  N'Nam', N'TP.HCM', N'Gò Vấp',     N'Số 16', '1994-04-14', 15000000, '0', 'NhanVien', '123', 'PB_IT_HCM',  'CN_HCM'), -- Nghỉ việc
('NV17', N'Hồ Thị SG_6',    N'Nữ',  N'TP.HCM', N'Thủ Đức',    N'Số 17', '1996-06-16', 13500000, '1', 'NhanVien', '123', 'PB_MKT_HCM', 'CN_HCM'),
('NV18', N'Lý Văn SG_7',    N'Nam', N'TP.HCM', N'Phú Nhuận',  N'Số 18', '1992-02-22', 17000000, '1', 'NhanVien', '123', 'PB_IT_HCM',  'CN_HCM'),
('NV19', N'Trịnh Thị SG_8', N'Nữ',  N'TP.HCM', N'Tân Bình',   N'Số 19', '1999-09-19', 11000000, '1', 'NhanVien', '123', 'PB_HR_HCM',  'CN_HCM'),
('NV20', N'Đoàn Văn SG_9',  N'Nam', N'TP.HCM', N'Quận 4',     N'Số 20', '1991-11-21', 19000000, '1', 'NhanVien', '123', 'PB_IT_HCM',  'CN_HCM'),

-- Nhân sự Đà Nẵng
('NV21', N'Tạ Quản Lý ĐN',  N'Nữ',  N'Đà Nẵng',N'Hải Châu 1', N'Số 21', '1987-07-27', 24000000, '1', 'QuanLy',   '123', 'PB_IT_DN',   'CN_DN'),
('NV22', N'Tống Văn ĐN_1',  N'Nam', N'Đà Nẵng',N'Thạch Thang',N'Số 22', '1994-04-24', 14500000, '1', 'NhanVien', '123', 'PB_IT_DN',   'CN_DN'),
('NV23', N'Dương Thị ĐN_2', N'Nữ',  N'Đà Nẵng',N'Thuận Phước',N'Số 23', '1998-08-28', 11500000, '1', 'NhanVien', '123', 'PB_SALE_DN', 'CN_DN'),
('NV24', N'Vương Văn ĐN_3', N'Nam', N'Đà Nẵng',N'Sơn Trà',    N'Số 24', '1995-05-25', 15500000, '1', 'NhanVien', '123', 'PB_IT_DN',   'CN_DN'),
('NV25', N'Mai Thị ĐN_4',   N'Nữ',  N'Đà Nẵng',N'Ngũ Hành Sơn',N'Số 25','1997-07-07', 12500000, '1', 'NhanVien', '123', 'PB_SALE_DN', 'CN_DN'),
('NV26', N'Tôn Văn ĐN_5',   N'Nam', N'Đà Nẵng',N'Cẩm Lệ',     N'Số 26', '1993-03-23', 13500000, '0', 'NhanVien', '123', 'PB_IT_DN',   'CN_DN'), -- Nghỉ việc
('NV27', N'Kiều Thị ĐN_6',  N'Nữ',  N'Đà Nẵng',N'Liên Chiểu', N'Số 27', '1999-09-29', 10500000, '1', 'NhanVien', '123', 'PB_SALE_DN', 'CN_DN'),
('NV28', N'Bạch Văn ĐN_7',  N'Nam', N'Đà Nẵng',N'Hòa Vang',   N'Số 28', '1992-12-12', 16500000, '1', 'NhanVien', '123', 'PB_IT_DN',   'CN_DN'),
('NV29', N'Châu Thị ĐN_8',  N'Nữ',  N'Đà Nẵng',N'Hoàng Sa',   N'Số 29', '1996-06-26', 11000000, '1', 'NhanVien', '123', 'PB_SALE_DN', 'CN_DN'),
('NV30', N'Thiệp Văn ĐN_9', N'Nam', N'Đà Nẵng',N'Trường Sa',  N'Số 30', '1991-01-31', 17500000, '1', 'NhanVien', '123', 'PB_IT_DN',   'CN_DN');

-- 4. DỮ LIỆU DỰ ÁN (15 Dự án - Đã cộng lãi vào Doanh Thu)
INSERT INTO DuAn (MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, TrangThai, MaCN) VALUES
-- Dự án tại Hà Nội
('DA01', N'Hệ thống ERP Thủ Đô',        5000000000, 6500000000, '2023-01-01', '2025-12-31', N'DangChay', 'CN_HN'),
('DA02', N'App Quản lý nhân sự HR-Pro', 200000000,  300000000,  '2023-06-01', '2024-06-01', N'DangChay', 'CN_HN'),
('DA03', N'Phần mềm Kế Toán Fast',      300000000,  450000000,  '2023-03-01', '2023-12-31', N'KetThuc',  'CN_HN'),
('DA04', N'Web Cổng thông tin Bộ Y Tế', 1500000000, 2000000000, '2024-01-01', '2025-06-01', N'DangChay', 'CN_HN'),
('DA05', N'Bảo trì máy chủ Core HN',    100000000,  120000000,  '2023-01-01', '2023-12-31', N'KetThuc',  'CN_HN'),

-- Dự án tại TP.HCM
('DA06', N'Website E-commerce Sài Gòn', 800000000,  1200000000, '2023-05-01', '2024-05-01', N'DangChay', 'CN_HCM'),
('DA07', N'Hệ thống AI Camera Giao thông',4000000000, 5500000000, '2024-01-01', '2026-01-01', N'DangChay', 'CN_HCM'),
('DA08', N'Ứng dụng đặt xe nội bộ',     150000000,  200000000,  '2023-08-01', '2024-02-01', N'KetThuc',  'CN_HCM'),
('DA09', N'Game Mobile Giải Trí HCM',   600000000,  1500000000, '2024-03-01', '2025-03-01', N'DangChay', 'CN_HCM'),
('DA10', N'Phần mềm Logistic Cảng Cát Lái',2500000000, 3200000000, '2023-10-01', '2025-10-01', N'DangChay', 'CN_HCM'),

-- Dự án tại Đà Nẵng
('DA11', N'Du lịch thông minh Đà Nẵng', 1200000000, 1800000000, '2023-02-01', '2025-02-01', N'DangChay', 'CN_DN'),
('DA12', N'App Quản lý Resort Biển ĐN', 400000000,  600000000,  '2023-09-01', '2024-09-01', N'DangChay', 'CN_DN'),
('DA13', N'Phần mềm Quản lý bãi xe',    200000000,  300000000,  '2023-04-01', '2023-11-01', N'KetThuc',  'CN_DN'),
('DA14', N'Web giới thiệu Đặc sản Miền Trung',50000000, 80000000, '2024-01-01', '2024-04-01', N'DangChay', 'CN_DN'),
('DA15', N'Hệ thống cảnh báo bão',      2000000000, 2500000000, '2023-06-01', '2025-06-01', N'DangChay', 'CN_DN');

-- 5. DỮ LIỆU PHÂN CÔNG (Gán nhân viên vào đúng dự án ở Chi nhánh của họ)
INSERT INTO PhanCong (MaNV, MaDA, NgayThamGia, NgayKetThuc, VaiTroDuAn, DanhGia) VALUES
-- Team Hà Nội
('NV02', 'DA01', '2023-01-05', NULL,         N'PM',          NULL),
('NV03', 'DA01', '2023-01-10', NULL,         N'Developer',   NULL),
('NV05', 'DA01', '2023-01-15', NULL,         N'Tester',      NULL),
('NV07', 'DA02', '2023-06-05', NULL,         N'Developer',   NULL),
('NV09', 'DA03', '2023-03-05', '2023-12-31', N'Team Leader', 9),
('NV04', 'DA03', '2023-03-10', '2023-12-31', N'Tester',      8),

-- Team TP.HCM (NV12 cực kỳ năng suất, làm nhiều dự án để test Top Dự Án)
('NV11', 'DA06', '2023-05-05', NULL,         N'PM',          NULL),
('NV12', 'DA06', '2023-05-10', NULL,         N'Fullstack',   NULL),
('NV12', 'DA07', '2024-01-10', NULL,         N'AI Engineer', NULL),
('NV12', 'DA08', '2023-08-05', '2024-02-01', N'Backend',     10),
('NV14', 'DA07', '2024-01-05', NULL,         N'Developer',   NULL),
('NV18', 'DA10', '2023-10-05', NULL,         N'Data Engineer',NULL),
('NV20', 'DA10', '2023-10-10', NULL,         N'DevOps',      NULL),

-- Team Đà Nẵng
('NV21', 'DA11', '2023-02-05', NULL,         N'PM',          NULL),
('NV22', 'DA11', '2023-02-10', NULL,         N'Developer',   NULL),
('NV24', 'DA12', '2023-09-05', NULL,         N'Frontend',    NULL),
('NV28', 'DA15', '2023-06-05', NULL,         N'System Arch', NULL),
('NV30', 'DA15', '2023-06-10', NULL,         N'Developer',   NULL);

