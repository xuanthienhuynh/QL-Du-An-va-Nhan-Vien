package DAO;

import DTO.DuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DuAnDAO {
    
    // === READ: Lấy tất cả dự án ===
    public ArrayList<DuAn_DTO> layDanhSachDuAn() {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT MaDA, TenDA, KinhPhi, NgayBatDau, NgayKetThuc, TrangThai, MaCN FROM DuAn ORDER BY MaDA";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));
                da.setMaCN(rs.getString("MaCN"));
                list.add(da);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    // === READ: Lấy thông tin 1 dự án theo mã ===
    public DuAn_DTO getDuAnByMa(String maDA) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DuAn_DTO da = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM DuAn WHERE MaDA = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));
                da.setMaCN(rs.getString("MaCN"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return da;
    }
    
    // === CREATE: Thêm dự án mới ===
    public boolean themDuAn(DuAn_DTO da) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "INSERT INTO DuAn (MaDA, TenDA, KinhPhi, NgayBatDau, NgayKetThuc, TrangThai, MaCN) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, da.getMaDA());
            ps.setString(2, da.getTenDA());
            ps.setDouble(3, da.getKinhPhi());
            ps.setDate(4, new java.sql.Date(da.getNgayBatDau().getTime()));
            ps.setDate(5, new java.sql.Date(da.getNgayKetThuc().getTime()));
            ps.setString(6, da.getTrangThai());
            ps.setString(7, da.getMaCN());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // === UPDATE: Cập nhật dự án ===
    public boolean suaDuAn(DuAn_DTO da) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "UPDATE DuAn SET TenDA=?, KinhPhi=?, NgayBatDau=?, NgayKetThuc=?, TrangThai=?, MaCN=? " +
                        "WHERE MaDA=?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, da.getTenDA());
            ps.setDouble(2, da.getKinhPhi());
            ps.setDate(3, new java.sql.Date(da.getNgayBatDau().getTime()));
            ps.setDate(4, new java.sql.Date(da.getNgayKetThuc().getTime()));
            ps.setString(5, da.getTrangThai());
            ps.setString(6, da.getMaCN());
            ps.setString(7, da.getMaDA()); // WHERE condition
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // === DELETE: Xóa dự án ===
    public boolean xoaDuAn(String maDA) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "DELETE FROM DuAn WHERE MaDA = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // === Tìm kiếm dự án ===
    public ArrayList<DuAn_DTO> timKiemDuAn(String tuKhoa) {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM DuAn WHERE MaDA LIKE ? OR TenDA LIKE ? OR TrangThai LIKE ? ORDER BY MaDA";
            ps = conn.prepareStatement(sql);
            
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));
                da.setMaCN(rs.getString("MaCN"));
                list.add(da);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) database.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}