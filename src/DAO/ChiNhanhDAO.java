package DAO;

import DTO.ChiNhanh_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiNhanhDAO {

    // 1. LẤY DANH SÁCH CHI NHÁNH
    public ArrayList<ChiNhanh_DTO> layDanhSachChiNhanh() {
        ArrayList<ChiNhanh_DTO> list = new ArrayList<>();
        String sql = "SELECT MaCN, TenCN, DiaChi FROM ChiNhanh";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiNhanh_DTO cn = new ChiNhanh_DTO();
                cn.setMaCN(rs.getString("MaCN"));
                cn.setTenCN(rs.getString("TenCN"));
                cn.setDiaChi(rs.getString("DiaChi"));
                list.add(cn);
            }
            database.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. THÊM CHI NHÁNH MỚI
    public boolean themChiNhanh(ChiNhanh_DTO cn) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO ChiNhanh (MaCN, TenCN, DiaChi) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, cn.getMaCN());
                ps.setString(2, cn.getTenCN());
                ps.setString(3, cn.getDiaChi());

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

    // 3. CẬP NHẬT THÔNG TIN CHI NHÁNH
    public boolean capNhatChiNhanh(ChiNhanh_DTO cn) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE ChiNhanh SET TenCN = ?, DiaChi = ? WHERE MaCN = ?";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, cn.getTenCN());
                ps.setString(2, cn.getDiaChi());
                ps.setString(3, cn.getMaCN());

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

    // 4. XÓA CHI NHÁNH
    public boolean xoaChiNhanh(String maCN) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM ChiNhanh WHERE MaCN = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maCN);

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

    // 5. LẤY CHI TIẾT MỘT CHI NHÁNH
    public ChiNhanh_DTO layThongTinChiNhanh(String maCN) {
        ChiNhanh_DTO cn = null;
        String sql = "SELECT * FROM ChiNhanh WHERE MaCN = ?";

        try (Connection conn = database.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maCN);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cn = new ChiNhanh_DTO();
                cn.setMaCN(rs.getString("MaCN"));
                cn.setTenCN(rs.getString("TenCN"));
                cn.setDiaChi(rs.getString("DiaChi"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cn;
    }
}