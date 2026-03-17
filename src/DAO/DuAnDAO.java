package DAO;

import DTO.DuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DuAnDAO {
    public ArrayList<DuAn_DTO> layDanhSachDuAn() {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        // Lấy đầy đủ các cột theo DTO
        String sql = "SELECT MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, TrangThai, MaCN FROM DuAn";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setDoanhThu(rs.getDouble("DoanhThu"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));
                da.setMaCN(rs.getString("MaCN"));
                list.add(da);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themDuAn(DuAn_DTO da) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                // THÊM TrangThai vào câu SQL (dấu ? thứ 7) và đẩy MaCN sang dấu ? thứ 8
                String sql = "INSERT INTO DuAn (MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, TrangThai, MaCN) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, da.getMaDA());
                ps.setString(2, da.getTenDA());
                ps.setDouble(3, da.getKinhPhi());
                ps.setDouble(4, da.getDoanhThu());
                ps.setDate(5, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                ps.setDate(6, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);
                ps.setString(7, da.getTrangThai()); // THÊM DÒNG NÀY
                ps.setString(8, da.getMaCN()); // Đổi thành số 8

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.closeConnection(conn);
            }
        }
        return false;
    }

    // ========================================================
    // 3. SỬA DỮ LIỆU (UPDATE) - Cập nhật dự án
    // ========================================================
    public boolean capNhatDuAn(DuAn_DTO da) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                // THÊM TrangThai=? vào câu lệnh UPDATE
                String sql = "UPDATE DuAn SET TenDA=?, KinhPhi=?, DoanhThu=?, NgayBatDau=?, NgayKetThuc=?, TrangThai=?, MaCN=? WHERE MaDA=?";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, da.getTenDA());
                ps.setDouble(2, da.getKinhPhi());
                ps.setDouble(3, da.getDoanhThu());
                ps.setDate(4, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                ps.setDate(5, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);
                ps.setString(6, da.getTrangThai()); // THÊM DÒNG NÀY
                ps.setString(7, da.getMaCN()); // Đổi thành số 7
                ps.setString(8, da.getMaDA()); // Đổi thành số 8 (WHERE)

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.closeConnection(conn);
            }
        }
        return false;
    }

    // ========================================================
    // 4. XÓA DỮ LIỆU (DELETE) - Hủy/Xóa dự án
    // ========================================================
    public boolean xoaDuAn(String maDA) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                // Tắt auto-commit để thực hiện xóa nhiều bảng cùng lúc (Transaction)
                conn.setAutoCommit(false);

                // BƯỚC 1: Xóa các ràng buộc ở bảng con (bảng PhanCong) trước
                String sqlPhanCong = "DELETE FROM PhanCong WHERE MaDA = ?";
                PreparedStatement psPhanCong = conn.prepareStatement(sqlPhanCong);
                psPhanCong.setString(1, maDA);
                psPhanCong.executeUpdate(); // Có thể có hoặc không có nhân viên nào bị xóa

                // BƯỚC 2: Sau khi dọn sạch bảng con, tiến hành xóa Dự án ở bảng cha
                String sqlDuAn = "DELETE FROM DuAn WHERE MaDA = ?";
                PreparedStatement psDuAn = conn.prepareStatement(sqlDuAn);
                psDuAn.setString(1, maDA);
                int rowsAffected = psDuAn.executeUpdate();

                // Xác nhận thực thi lệnh xóa cả 2 bảng
                conn.commit();

                return rowsAffected > 0;

            } catch (Exception e) {
                try {
                    // Nếu quá trình xóa có lỗi (vd đứt cáp mạng), hoàn tác lại như chưa có gì xảy
                    // ra
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }

                System.err.println("Lỗi xóa: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Bật lại auto-commit và đóng kết nối
                try {
                    conn.setAutoCommit(true);
                } catch (Exception ex) {
                }

                database.closeConnection(conn);
            }
        }
        return false;
    }

    public DTO.DuAn_DTO layThongTinDuAn(String maDA) {
        DTO.DuAn_DTO da = null;
        String sql = "SELECT * FROM DuAn WHERE MaDA = ?";

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                da = new DTO.DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setDoanhThu(rs.getDouble("DoanhThu"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));
                da.setMaCN(rs.getString("MaCN"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return da;
    }

    public boolean capNhatTrangThaiDuAn(DTO.DuAn_DTO da) {
        java.sql.Connection conn = database.createConnection();
        if (conn != null) {
            try {
                // Lệnh SQL chỉ cập nhật duy nhất cột TrangThai
                String sql = "UPDATE DuAn SET TrangThai = ? WHERE MaDA = ?";
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);

                // Lấy trạng thái đã được set bên trong đối tượng DTO
                ps.setString(1, da.getTrangThai());
                ps.setString(2, da.getMaDA());

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.closeConnection(conn);
            }
        }
        return false;
    }
}