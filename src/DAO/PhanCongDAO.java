package DAO;

import DTO.PhanCong_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhanCongDAO {

    // 1. LẤY DANH SÁCH PHÂN CÔNG
    public ArrayList<PhanCong_DTO> layDanhSachPhanCong() {
        ArrayList<PhanCong_DTO> list = new ArrayList<>();
        String sql = "SELECT MaNV, MaDA, NgayThamGia, NgayKetThuc, VaiTroDuAn, DanhGia FROM PhanCong";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhanCong_DTO pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                pc.setDanhGia(rs.getInt("DanhGia"));
                list.add(pc);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. THÊM PHÂN CÔNG MỚI
    public boolean themPhanCong(PhanCong_DTO pc) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO PhanCong (MaNV, MaDA, NgayThamGia, NgayKetThuc, VaiTroDuAn, DanhGia) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, pc.getMaNV());
                ps.setString(2, pc.getMaDA());
                ps.setDate(3, pc.getNgayThamGia() != null ? new java.sql.Date(pc.getNgayThamGia().getTime()) : null);
                ps.setDate(4, pc.getNgayKetThuc() != null ? new java.sql.Date(pc.getNgayKetThuc().getTime()) : null);
                ps.setString(5, pc.getVaiTroDuAn());
                ps.setInt(6, pc.getDanhGia());

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

    // 3. CẬP NHẬT PHÂN CÔNG (Dựa trên MaNV và MaDA)
    public boolean capNhatPhanCong(PhanCong_DTO pc) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE PhanCong SET NgayThamGia=?, NgayKetThuc=?, VaiTroDuAn=?, DanhGia=? WHERE MaNV=? AND MaDA=?";
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setDate(1, pc.getNgayThamGia() != null ? new java.sql.Date(pc.getNgayThamGia().getTime()) : null);
                ps.setDate(2, pc.getNgayKetThuc() != null ? new java.sql.Date(pc.getNgayKetThuc().getTime()) : null);
                ps.setString(3, pc.getVaiTroDuAn());
                ps.setInt(4, pc.getDanhGia());

                // Điều kiện WHERE
                ps.setString(5, pc.getMaNV());
                ps.setString(6, pc.getMaDA());

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

    // 4. XÓA PHÂN CÔNG
    public boolean xoaPhanCong(String maNV, String maDA) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM PhanCong WHERE MaNV = ? AND MaDA = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maNV);
                ps.setString(2, maDA);

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

    // 5. LẤY CHI TIẾT MỘT BẢN GHI PHÂN CÔNG
    public PhanCong_DTO layThongTinPhanCong(String maNV, String maDA) {
        PhanCong_DTO pc = null;
        String sql = "SELECT * FROM PhanCong WHERE MaNV = ? AND MaDA = ?";

        try (Connection conn = database.createConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            ps.setString(2, maDA);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                pc.setDanhGia(rs.getInt("DanhGia"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pc;
    }
}