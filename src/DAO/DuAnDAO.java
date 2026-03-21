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

    public java.util.ArrayList<DTO.DuAn_DTO> thongKeDacBietDuAn(String kieuThongKe, String maCN, java.util.Date ngayBD,
            java.util.Date ngayKT, String sapXep) {
        java.util.ArrayList<DTO.DuAn_DTO> list = new java.util.ArrayList<>();

        String select = "";
        String join = " FROM DuAn DA ";
        String where = " WHERE 1=1 ";
        String group = "";
        String order = "";

        // Lọc theo ngày tháng (nếu người dùng có chọn)
        if (ngayBD != null) {
            where += " AND DA.NgayBatDau >= ? ";
        }
        if (ngayKT != null) {
            where += " AND DA.NgayKetThuc <= ? ";
        }

        // 1. XỬ LÝ 5 KIỂU THỐNG KÊ
        if (kieuThongKe.contains("doanh thu")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DA.DoanhThu AS ChiSo ";
        } else if (kieuThongKe.contains("kinh phí")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DA.KinhPhi AS ChiSo ";
        } else if (kieuThongKe.contains("lâu nhất")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DATEDIFF(day, DA.NgayBatDau, DA.NgayKetThuc) AS ChiSo ";
        } else if (kieuThongKe.contains("Deadline")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DATEDIFF(day, GETDATE(), DA.NgayKetThuc) AS ChiSo ";
            where += " AND DA.NgayKetThuc >= GETDATE() "; // Chỉ lấy dự án chưa tới hạn
        } else if (kieuThongKe.contains("quy mô")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, COUNT(PC.MaNV) AS ChiSo ";
            join += " LEFT JOIN PhanCong PC ON DA.MaDA = PC.MaDA ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.MaCN ";
        }

        // 2. XỬ LÝ LỌC THEO CHI NHÁNH
        if (maCN != null && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            where += " AND DA.MaCN = '" + maCN + "' ";
        }

        // 3. XỬ LÝ SẮP XẾP
        if (sapXep != null && sapXep.contains("giảm")) {
            order = " ORDER BY ChiSo DESC ";
        } else {
            order = " ORDER BY ChiSo ASC ";
        }

        String sql = select + join + where + group + order;

        try {
            java.sql.Connection conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);

            // Nạp tham số ngày tháng vào các dấu ? trong SQL
            int paramIndex = 1;
            if (ngayBD != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayBD.getTime()));
            }
            if (ngayKT != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayKT.getTime()));
            }

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DTO.DuAn_DTO da = new DTO.DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setMaCN(rs.getString("MaCN")); // Hiện mã chi nhánh
                da.setDoanhThu(rs.getDouble("ChiSo")); // Mượn tạm biến DoanhThu của DTO để lưu chỉ số trả về
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
                String sql = "INSERT INTO DuAn (MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, MaCN) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);

                // Truyền tham số an toàn vào dấu ?
                ps.setString(1, da.getMaDA());
                ps.setString(2, da.getTenDA());
                ps.setDouble(3, da.getKinhPhi());
                ps.setDouble(4, da.getDoanhThu());

                // Xử lý chuyển đổi java.util.Date sang java.sql.Date
                ps.setDate(5, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                ps.setDate(6, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);

                ps.setString(7, da.getMaCN());

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu thêm thành công

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
                String sql = "UPDATE DuAn SET TenDA=?, KinhPhi=?, DoanhThu=?, NgayBatDau=?, NgayKetThuc=?, MaCN=? WHERE MaDA=?";
                PreparedStatement ps = conn.prepareStatement(sql);

                // Gán dữ liệu thay đổi
                ps.setString(1, da.getTenDA());
                ps.setDouble(2, da.getKinhPhi());
                ps.setDouble(3, da.getDoanhThu());
                ps.setDate(4, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                ps.setDate(5, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);
                ps.setString(6, da.getMaCN());

                // Điều kiện WHERE (Mã DA nằm ở dấu ? thứ 7)
                ps.setString(7, da.getMaDA());

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