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

    public static void main(String[] args) {
        NhanVienDAO dao = new NhanVienDAO();

        System.out.println("--- ĐANG TEST VỚI CLASS 'database' TRONG GÓI DAO ---");

        // Thay user/pass bằng dữ liệu thật của bạn

    }
}