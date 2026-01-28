
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
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN) -- FK
);


CREATE TABLE NhanVien (
    MaNV CHAR(10) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    -- Tách 3 cột địa chỉ theo đúng yêu cầu
    DcTinh NVARCHAR(50), 
    DcPhuong NVARCHAR(50),
    DcSoNha NVARCHAR(50),
    NgaySinh DATE,
    Luong DECIMAL(18,2),
    TinhTrang NVARCHAR(20), -- 'DangLamViec', 'DaNghiViec'
    VaiTro NVARCHAR(20),    -- 'NhanVien', 'QuanLy', 'Sep'
    MatKhau VARCHAR(50),    -- Đơn giản hóa, thực tế nên mã hóa
    MaPB CHAR(10) REFERENCES PhongBan(MaPB), -- FK 1
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN)  -- FK 2 (Giữ lại để phân mảnh dễ hơn)
);

-- 5. Tạo bảng Dự Án
CREATE TABLE DuAn (
    MaDA CHAR(10) PRIMARY KEY,
    TenDA NVARCHAR(100) NOT NULL,
    KinhPhi DECIMAL(18,2),
    NgayBatDau DATE,
    NgayKetThuc DATE, -- Deadline dự kiến
    TrangThai NVARCHAR(50), -- 'DangChay', 'KetThuc'
    MaCN CHAR(10) REFERENCES ChiNhanh(MaCN) -- FK
);

-- 6. Tạo bảng Phân Công (Bảng trung gian N-N)
CREATE TABLE PhanCong (
    MaNV CHAR(10) REFERENCES NhanVien(MaNV),
    MaDA CHAR(10) REFERENCES DuAn(MaDA),
    NgayThamGia DATE DEFAULT GETDATE(),
    NgayKetThuc DATE, -- NULL = Đang làm, Có ngày = Đã xong
    VaiTroDuAn NVARCHAR(50),
    DanhGia INT,
    PRIMARY KEY (MaNV, MaDA)
);

-- --- DỮ LIỆU MẪU (SEED DATA) ---
INSERT INTO ChiNhanh VALUES ('CN_HN', N'Chi Nhánh Hà Nội', N'123 Cầu Giấy');
INSERT INTO PhongBan VALUES ('PB_IT_HN', N'Phòng IT Hà Nội', 'CN_HN');
-- Pass mặc định là '123'
INSERT INTO NhanVien VALUES ('NV01', N'Nguyễn Văn A', N'Nam', N'Hà Nội', N'Dịch Vọng', N'Số 10', '2000-01-01', 15000000, N'DangLamViec', N'NhanVien', '123', 'PB_IT_HN', 'CN_HN');
delete from NhanVien
USE QL_DuAn_CongTy;
GO

-- =============================================
-- 1. DỮ LIỆU BẢNG CHI NHÁNH (10 Chi nhánh rải rác 3 miền)
-- =============================================
INSERT INTO ChiNhanh (MaCN, TenCN, DiaChi) VALUES

('CN_HCM', N'Chi Nhánh TP.HCM',      N'456 Nguyễn Huệ, Quận 1, TP.HCM'),
('CN_DN',  N'Chi Nhánh Đà Nẵng',     N'789 Sông Hàn, Đà Nẵng'),
('CN_HP',  N'Chi Nhánh Hải Phòng',   N'12 Lạch Tray, Hải Phòng'),
('CN_CT',  N'Chi Nhánh Cần Thơ',     N'34 Ninh Kiều, Cần Thơ'),
('CN_VT',  N'Chi Nhánh Vũng Tàu',    N'56 Bãi Sau, Vũng Tàu'),
('CN_BD',  N'Chi Nhánh Bình Dương',  N'78 Thủ Dầu Một, Bình Dương'),
('CN_NA',  N'Chi Nhánh Nghệ An',     N'90 TP Vinh, Nghệ An'),
('CN_TH',  N'Chi Nhánh Thanh Hóa',   N'11 Lê Lợi, Thanh Hóa'),
('CN_DL',  N'Chi Nhánh Đà Lạt',      N'22 Hồ Xuân Hương, Lâm Đồng');

-- =============================================
-- 2. DỮ LIỆU BẢNG PHÒNG BAN (10 Phòng ban thuộc các chi nhánh)
-- =============================================
INSERT INTO PhongBan (MaPB, TenPB, MaCN) VALUES

('PB_HR_HN',   N'Phòng Nhân Sự HN',      'CN_HN'),
('PB_KT_HN',   N'Phòng Kế Toán HN',      'CN_HN'),
('PB_IT_HCM',  N'Phòng Kỹ Thuật HCM',    'CN_HCM'),
('PB_HR_HCM',  N'Phòng Nhân Sự HCM',     'CN_HCM'),
('PB_MKT_HCM', N'Phòng Marketing HCM',   'CN_HCM'),
('PB_IT_DN',   N'Phòng Kỹ Thuật ĐN',     'CN_DN'),
('PB_SALE_HP', N'Phòng Kinh Doanh HP',   'CN_HP'),
('PB_LOG_CT',  N'Phòng Logistic CT',     'CN_CT'),
('PB_DEV_BD',  N'Phòng Phát Triển BD',   'CN_BD');

-- =============================================
-- 3. DỮ LIỆU BẢNG NHÂN VIÊN (10 NV, Pass mặc định 123)
-- Lưu ý: Dữ liệu này test đủ các vai trò: NhanVien, QuanLy, Sep
-- =============================================
INSERT INTO NhanVien (MaNV, HoTen, GioiTinh, DcTinh, DcPhuong, DcSoNha, NgaySinh, Luong, TinhTrang, VaiTro, MatKhau, MaPB, MaCN) VALUES
('NV01', N'Nguyễn Văn A', N'Nam',   N'Hà Nội',    N'Dịch Vọng',   N'Số 10', '1990-01-01', 15000000, N'DangLamViec', N'Sep',      '123', 'PB_IT_HN',   'CN_HN'),
('NV02', N'Trần Thị B',   N'Nữ',    N'Hà Nội',    N'Mai Dịch',    N'Số 22', '1995-05-15', 12000000, N'DangLamViec', N'QuanLy',   '123', 'PB_HR_HN',   'CN_HN'),
('NV03', N'Lê Văn C',     N'Nam',   N'TP.HCM',    N'Bến Nghé',    N'Số 33', '1992-08-20', 14000000, N'DangLamViec', N'QuanLy',   '123', 'PB_IT_HCM',  'CN_HCM'),
('NV04', N'Phạm Thị D',   N'Nữ',    N'TP.HCM',    N'Thảo Điền',   N'Số 44', '1998-12-12', 10000000, N'DangLamViec', N'NhanVien', '123', 'PB_HR_HCM',  'CN_HCM'),
('NV05', N'Hoàng Văn E',  N'Nam',   N'Đà Nẵng',   N'Hải Châu 1',  N'Số 55', '1993-03-10', 13000000, N'DangLamViec', N'NhanVien', '123', 'PB_IT_DN',   'CN_DN'),
('NV06', N'Đỗ Thị F',     N'Nữ',    N'Hải Phòng', N'Cầu Đất',     N'Số 66', '1997-07-07', 11000000, N'DangLamViec', N'NhanVien', '123', 'PB_SALE_HP', 'CN_HP'),
('NV07', N'Vũ Văn G',     N'Nam',   N'Cần Thơ',   N'An Bình',     N'Số 77', '1991-09-09', 12500000, N'DangLamViec', N'NhanVien', '123', 'PB_LOG_CT',  'CN_CT'),
('NV08', N'Ngô Thị H',    N'Nữ',    N'Bình Dương',N'Chánh Nghĩa', N'Số 88', '1996-06-06', 11500000, N'DangLamViec', N'NhanVien', '123', 'PB_DEV_BD',  'CN_BD'),
('NV09', N'Bùi Văn I',    N'Nam',   N'Hà Nội',    N'Láng Hạ',     N'Số 99', '1988-11-11', 20000000, N'DaNghiViec',  N'NhanVien', '123', 'PB_IT_HN',   'CN_HN'), -- Đã nghỉ việc
('NV10', N'Đặng Thị K',   N'Nữ',    N'TP.HCM',    N'Tân Định',    N'Số 01', '1999-02-02', 9000000,  N'DangLamViec', N'NhanVien', '123', 'PB_MKT_HCM', 'CN_HCM');

-- =============================================
-- 4. DỮ LIỆU BẢNG DỰ ÁN (10 Dự án khác nhau)
-- =============================================
INSERT INTO DuAn (MaDA, TenDA, KinhPhi, NgayBatDau, NgayKetThuc, TrangThai, MaCN) VALUES
('DA01', N'Hệ thống Smart City Hà Nội',    5000000000, '2023-01-01', '2025-12-31', N'DangChay', 'CN_HN'),
('DA02', N'App Quản lý nhân sự HR-Pro',    200000000,  '2023-06-01', '2024-06-01', N'DangChay', 'CN_HN'),
('DA03', N'Website E-commerce Sài Gòn',    300000000,  '2023-03-01', '2023-12-31', N'KetThuc',  'CN_HCM'),
('DA04', N'Hệ thống AI Camera Giao thông', 4000000000, '2024-01-01', '2026-01-01', N'DangChay', 'CN_HCM'),
('DA05', N'Du lịch thông minh Đà Nẵng',    1500000000, '2023-05-05', '2025-05-05', N'DangChay', 'CN_DN'),
('DA06', N'Phần mềm Logistic Cảng HP',     800000000,  '2023-08-01', '2024-08-01', N'DangChay', 'CN_HP'),
('DA07', N'Nông nghiệp công nghệ cao CT',  1200000000, '2023-02-01', '2025-02-01', N'DangChay', 'CN_CT'),
('DA08', N'Quản lý kho bãi Bình Dương',    600000000,  '2023-09-01', '2024-03-01', N'KetThuc',  'CN_BD'),
('DA09', N'Hệ thống giáo dục trực tuyến',  2500000000, '2023-10-01', '2025-10-01', N'DangChay', 'CN_HN'),
('DA10', N'Ứng dụng đặt xe nội bộ',        100000000,  '2024-02-01', '2024-12-31', N'DangChay', 'CN_HCM');

-- =============================================
-- 5. DỮ LIỆU BẢNG PHÂN CÔNG (10 Lượt phân công)
-- Logic: NV01 tham gia nhiều dự án (để test logic "Tối đa 3")
-- =============================================
INSERT INTO PhanCong (MaNV, MaDA, NgayThamGia, NgayKetThuc, VaiTroDuAn, DanhGia) VALUES
('NV01', 'DA01', '2023-01-05', NULL,          N'PM - Quản lý',    NULL), -- Đang làm
('NV01', 'DA02', '2023-06-05', NULL,          N'Cố vấn kỹ thuật', NULL), -- Đang làm (NV01 đã 2 dự án)
('NV02', 'DA02', '2023-06-10', NULL,          N'Tester',          NULL),
('NV03', 'DA03', '2023-03-05', '2023-12-31',  N'Developer',       9),    -- Đã xong
('NV03', 'DA04', '2024-01-05', NULL,          N'Team Leader',     NULL),
('NV04', 'DA04', '2024-01-10', NULL,          N'HR Support',      NULL),
('NV05', 'DA05', '2023-05-10', NULL,          N'Developer',       NULL),
('NV06', 'DA06', '2023-08-05', NULL,          N'BA',              NULL),
('NV08', 'DA08', '2023-09-05', '2024-03-01',  N'Kho vận',         8),    -- Đã xong
('NV10', 'DA10', '2024-02-05', NULL,          N'Developer',       NULL);