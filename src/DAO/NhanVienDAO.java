package DAO;

import DTO.NhanVien_DTO; // Nhớ kiểm tra kỹ tên file DTO này nhé
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// KHÔNG CẦN import database; vì nó nằm cùng gói DAO rồi

public class NhanVienDAO {

    public NhanVien_DTO checkLogin(String user, String pass) {
        NhanVien_DTO nv = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // GỌI TRỰC TIẾP class database (vì đang ở cùng thư mục DAO)
            conn = database.createConnection();

            if (conn == null) {
                return null;
            }

            String sql = "SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setVaiTro(rs.getString("VaiTro"));
                // nv.setVaiTro(rs.getString("VaiTro"));
                // nv.setMaCN(rs.getString("MaCN"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            database.closeConnection(conn);
        }
        return nv;
    }

    // Hàm lấy danh sách nhân viên (Trả về ArrayList)
    public java.util.ArrayList<DTO.NhanVien_DTO> layDanhSachNhanVien() {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT MaNV, HoTen, VaiTro FROM NhanVien"; // Thêm MaCN nếu có

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setVaiTro(rs.getString("VaiTro"));
                // nv.setMaCN(rs.getString("MaCN")); // Nhớ lấy thêm chi nhánh
                list.add(nv);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm vào file DAO.NhanVienDAO.java
    public java.util.ArrayList<DTO.NhanVien_DTO> timKiemNhanVien(String tuKhoa) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        // Tìm gần đúng theo MaNV hoặc HoTen
        String sql = "SELECT MaNV, HoTen, VaiTro FROM NhanVien WHERE MaNV LIKE ? OR HoTen LIKE ? OR VaiTro LIKE ?";

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
        String sql = "INSERT INTO NhanVien(MaNV, HoTen, GioiTinh, NgaySinh, Luong, MaPB, MaCN, VaiTro, MatKhau) VALUES(?,?,?,?,?,?,?,?,?)";
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

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        NhanVienDAO dao = new NhanVienDAO();

    }
}