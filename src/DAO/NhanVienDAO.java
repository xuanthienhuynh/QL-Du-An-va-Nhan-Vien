package DAO;

import DTO.NhanVien_DTO; // Nhớ kiểm tra kỹ tên file DTO này nhé
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// KHÔNG CẦN import database; vì nó nằm cùng gói DAO rồi

public class NhanVienDAO {

    // xuanthien
    public NhanVien_DTO checkLogin(String user, String pass) {
        NhanVien_DTO nv = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = database.createConnection();
            if (conn == null) {
                return null;
            }

            String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ? AND TinhTrang = 1";

            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                nv = new NhanVien_DTO();
                // Lấy các thông tin cơ bản (Có trim() để tránh lỗi khoảng trắng)
                nv.setMaNV(rs.getString("MaNV").trim());
                nv.setHoTen(rs.getString("HoTen").trim());
                nv.setVaiTro(rs.getString("VaiTro") != null ? rs.getString("VaiTro").trim() : "");
                nv.setTinhTrang(rs.getBoolean("TinhTrang"));

                // --- BỔ SUNG LẤY FULL THÔNG TIN ĐỂ HIỂN THỊ TRÊN PROFILE ---
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setLuong(rs.getDouble("Luong"));

                String maPB = rs.getString("MaPB");
                nv.setMaPB(maPB != null ? maPB.trim() : "");

                String maCN = rs.getString("MaCN");
                nv.setMaCN(maCN != null ? maCN.trim() : "");

                // Lấy địa chỉ
                nv.setDcSoNha(rs.getString("DcSoNha"));
                nv.setDcPhuong(rs.getString("DcPhuong"));
                nv.setDcTinh(rs.getString("DcTinh"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return nv;
    }

    // Hàm lấy danh sách nhân viên (Trả về ArrayList)
    public java.util.ArrayList<DTO.NhanVien_DTO> layDanhSachNhanVien() {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT MaNV, HoTen, VaiTro, TinhTrang, MaCN FROM NhanVien";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();

                // Xử lý an toàn: Kiểm tra NULL trước khi trim() để tránh sập vòng lặp
                String maNV = rs.getString("MaNV");
                nv.setMaNV(maNV != null ? maNV.trim() : "");

                String hoTen = rs.getString("HoTen");
                nv.setHoTen(hoTen != null ? hoTen.trim() : "");

                String vaiTro = rs.getString("VaiTro");
                nv.setVaiTro(vaiTro != null ? vaiTro.trim() : "");

                nv.setTinhTrang(rs.getBoolean("TinhTrang"));

                String maCN = rs.getString("MaCN");
                nv.setMaCN(maCN != null ? maCN.trim() : "");

                list.add(nv);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Lỗi sập vòng lặp Load Danh Sách: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // Thêm vào file DAO.NhanVienDAO.java
    public java.util.ArrayList<DTO.NhanVien_DTO> timKiemNhanVien(String tuKhoa) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        // FIX LỖI: Thêm MaCN vào câu SELECT để BUS có dữ liệu mà lọc
        String sql = "SELECT MaNV, HoTen, VaiTro, MaCN FROM NhanVien WHERE MaNV LIKE ? OR HoTen LIKE ? OR VaiTro LIKE ?";

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);

            // Bỏ từ khóa vào định dạng %tuKhoa% của SQL
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setVaiTro(rs.getString("VaiTro"));
                // FIX LỖI: Nạp MaCN vào object
                nv.setMaCN(rs.getString("MaCN"));
                list.add(nv);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 1. Lấy danh sách Mã Phòng Ban để đổ vào ComboBox
    public java.util.ArrayList<String> layDanhSachMaPB(String maCN) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();

        // Chỉ lấy PB thuộc chi nhánh được chọn
        String sql = "SELECT MaPB FROM PhongBan WHERE MaCN = ?";

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCN); // Truyền mã chi nhánh vào

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("MaPB"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy danh sách Mã Chi Nhánh
    public java.util.ArrayList<String> layDanhSachMaCN() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        String sql = "SELECT MaCN FROM ChiNhanh";
        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("MaCN"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy Mã Nhân Viên cuối cùng (Ví dụ: NV09)
    public String layMaNVCuoiCung() {
        String ma = "";
        // SỬA CÂU SQL: Ưu tiên sắp xếp theo độ dài (LEN) trước
        String sql = "SELECT TOP 1 MaNV FROM NhanVien ORDER BY LEN(MaNV) DESC, MaNV DESC";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ma = rs.getString("MaNV");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ma;
    }

    // 4. Hàm Thêm Nhân Viên
    public boolean themNhanVien(DTO.NhanVien_DTO nv) {
        String sql = "INSERT INTO NhanVien(MaNV, HoTen, GioiTinh, NgaySinh, Luong, MaPB, MaCN, VaiTro, MatKhau, TinhTrang) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(5, nv.getLuong());
            ps.setString(6, nv.getMaPB());
            ps.setString(7, nv.getMaCN());
            ps.setString(8, "NhanVien"); // Mặc định là nhân viên thường
            ps.setString(9, "123"); // Mật khẩu mặc định
            ps.setBoolean(10, nv.getTinhTrang());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public DTO.NhanVien_DTO layThongTinNhanVien(String maNV) {
        DTO.NhanVien_DTO nv = null;
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setMaCN(rs.getString("MaCN"));
                nv.setTinhTrang(rs.getBoolean("TinhTrang"));
                nv.setVaiTro(rs.getString("VaiTro"));
                nv.setDcSoNha(rs.getString("DcSoNha"));
                nv.setDcPhuong(rs.getString("DcPhuong"));
                nv.setDcTinh(rs.getString("DcTinh"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    // 2. Cập nhật thông tin nhân viên
    public boolean capNhatNhanVien(DTO.NhanVien_DTO nv) {
        String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, NgaySinh=?, Luong=?, MaPB=?, MaCN=? WHERE MaNV=?";
        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(4, nv.getLuong());
            ps.setString(5, nv.getMaPB());
            ps.setString(6, nv.getMaCN());
            ps.setString(7, nv.getMaNV()); // Điều kiện WHERE

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Cho nghỉ việc (Đổi tình trạng thành False/0)
    public boolean choNghiViec(String maNV) {
        String sql = "UPDATE NhanVien SET TinhTrang = 0 WHERE MaNV = ?";
        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // thong ke
    public java.util.ArrayList<DTO.NhanVien_DTO> thongKeLuongNhanVien(String maCN, String maPB, String sapXep) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();

        // Bắt đầu câu SQL
        String sql = "SELECT TOP 10 MaNV, HoTen, MaPB, Luong FROM NhanVien WHERE TinhTrang = 1 ";

        // CHỈ GIỮ LẠI 1 ĐIỀU KIỆN IF CHO CHI NHÁNH (Đã xóa cái cũ)
        if (maCN != null && !maCN.equals("Chi Nhánh") && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            sql += " AND NhanVien.MaCN = '" + maCN + "' ";
        }

        // CHỈ GIỮ LẠI 1 ĐIỀU KIỆN IF CHO PHÒNG BAN (Đã xóa cái cũ)
        if (maPB != null && !maPB.equals("Phòng ban") && !maPB.equals("Tất cả Phòng Ban") && !maPB.isEmpty()) {
            sql += " AND NhanVien.MaPB = '" + maPB + "' ";
        }

        // XỬ LÝ SẮP XẾP
        if (sapXep != null && sapXep.equals("Sắp xếp giảm ")) {
            sql += " ORDER BY Luong DESC";
        } else {
            sql += " ORDER BY Luong ASC"; // Mặc định là tăng dần
        }

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setLuong(rs.getDouble("Luong"));
                list.add(nv);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thống kê dự án
    public java.util.ArrayList<DTO.NhanVien_DTO> thongKeTopDuAn(String maCN, String maPB, String sapXep) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();

        String sql = "SELECT TOP 10 NhanVien.MaNV, NhanVien.HoTen, NhanVien.MaPB, COUNT(PhanCong.MaDA) AS SoLuongDuAn "
                + "FROM NhanVien LEFT JOIN PhanCong ON NhanVien.MaNV = PhanCong.MaNV " +
                "WHERE NhanVien.TinhTrang = 1 ";

        // CHỈ GIỮ LẠI 1 ĐIỀU KIỆN IF CHO CHI NHÁNH
        if (maCN != null && !maCN.equals("Chi Nhánh") && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            sql += " AND NhanVien.MaCN = '" + maCN + "' ";
        }

        // CHỈ GIỮ LẠI 1 ĐIỀU KIỆN IF CHO PHÒNG BAN
        if (maPB != null && !maPB.equals("Phòng ban") && !maPB.equals("Tất cả Phòng Ban") && !maPB.isEmpty()) {
            sql += " AND NhanVien.MaPB = '" + maPB + "' ";
        }

        sql += " GROUP BY NhanVien.MaNV, NhanVien.HoTen, NhanVien.MaPB ";

        if (sapXep != null && sapXep.equals("Sắp xếp giảm ")) {
            sql += " ORDER BY SoLuongDuAn DESC";
        } else {
            sql += " ORDER BY SoLuongDuAn ASC";
        }

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setLuong(rs.getDouble("SoLuongDuAn"));
                list.add(nv);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách dự án (Mã + Tên) mà nhân viên tham gia
    public java.util.ArrayList<String> layDuAnCuaNhanVien(String maNV) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        // Kết hợp bảng PhanCong và DuAn để lấy tên dự án
        String sql = "SELECT DuAn.MaDA, DuAn.TenDA FROM PhanCong JOIN DuAn ON PhanCong.MaDA = DuAn.MaDA WHERE PhanCong.MaNV = ?";
        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            java.sql.ResultSet rs = ps.executeQuery();

            int stt = 1;
            while (rs.next()) {
                list.add(stt + ") " + rs.getString("MaDA") + " - " + rs.getString("TenDA"));
                stt++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // xuan thien
}