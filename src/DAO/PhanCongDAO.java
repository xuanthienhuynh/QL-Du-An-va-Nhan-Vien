package DAO;

import DTO.PhanCong_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhanCongDAO {
    
    // === READ: Lấy tất cả phân công ===
    public ArrayList<PhanCong_DTO> getAllPhanCong() {
        ArrayList<PhanCong_DTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM PhanCong ORDER BY MaNV, MaDA";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                PhanCong_DTO pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                
                // DanhGia có thể null
                Object danhGia = rs.getObject("DanhGia");
                if (danhGia != null) {
                    pc.setDanhGia(rs.getInt("DanhGia"));
                } else {
                    pc.setDanhGia(0);
                }
                
                list.add(pc);
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
    
    // === READ: Lấy phân công theo nhân viên ===
    public ArrayList<PhanCong_DTO> getPhanCongByNhanVien(String maNV) {
        ArrayList<PhanCong_DTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM PhanCong WHERE MaNV = ? ORDER BY NgayThamGia DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                PhanCong_DTO pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                
                Object danhGia = rs.getObject("DanhGia");
                pc.setDanhGia(danhGia != null ? rs.getInt("DanhGia") : 0);
                
                list.add(pc);
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
    
    // === READ: Lấy phân công theo dự án ===
    public ArrayList<PhanCong_DTO> getPhanCongByDuAn(String maDA) {
        ArrayList<PhanCong_DTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM PhanCong WHERE MaDA = ? ORDER BY NgayThamGia";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                PhanCong_DTO pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                
                Object danhGia = rs.getObject("DanhGia");
                pc.setDanhGia(danhGia != null ? rs.getInt("DanhGia") : 0);
                
                list.add(pc);
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
    
    // === READ: Lấy 1 phân công cụ thể ===
    public PhanCong_DTO getPhanCong(String maNV, String maDA) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        PhanCong_DTO pc = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM PhanCong WHERE MaNV = ? AND MaDA = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.setString(2, maDA);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                pc = new PhanCong_DTO();
                pc.setMaNV(rs.getString("MaNV"));
                pc.setMaDA(rs.getString("MaDA"));
                pc.setNgayThamGia(rs.getDate("NgayThamGia"));
                pc.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                pc.setVaiTroDuAn(rs.getString("VaiTroDuAn"));
                
                Object danhGia = rs.getObject("DanhGia");
                pc.setDanhGia(danhGia != null ? rs.getInt("DanhGia") : 0);
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
        return pc;
    }
    
    // === CREATE: Thêm phân công mới (Phân công nhân viên vào dự án) ===
    public boolean themPhanCong(PhanCong_DTO pc) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "INSERT INTO PhanCong (MaNV, MaDA, NgayThamGia, NgayKetThuc, VaiTroDuAn, DanhGia) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, pc.getMaNV());
            ps.setString(2, pc.getMaDA());
            ps.setDate(3, new java.sql.Date(pc.getNgayThamGia().getTime()));
            
            // NgayKetThuc có thể null
            if (pc.getNgayKetThuc() != null) {
                ps.setDate(4, new java.sql.Date(pc.getNgayKetThuc().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            
            ps.setString(5, pc.getVaiTroDuAn());
            
            // DanhGia có thể null
            if (pc.getDanhGia() > 0) {
                ps.setInt(6, pc.getDanhGia());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            
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
    
    // === UPDATE: Cập nhật phân công ===
    public boolean suaPhanCong(PhanCong_DTO pc) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "UPDATE PhanCong SET NgayThamGia=?, NgayKetThuc=?, VaiTroDuAn=?, DanhGia=? " +
                        "WHERE MaNV=? AND MaDA=?";
            
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(pc.getNgayThamGia().getTime()));
            
            if (pc.getNgayKetThuc() != null) {
                ps.setDate(2, new java.sql.Date(pc.getNgayKetThuc().getTime()));
            } else {
                ps.setNull(2, java.sql.Types.DATE);
            }
            
            ps.setString(3, pc.getVaiTroDuAn());
            
            if (pc.getDanhGia() > 0) {
                ps.setInt(4, pc.getDanhGia());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, pc.getMaNV()); // WHERE condition
            ps.setString(6, pc.getMaDA()); // WHERE condition
            
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
    
    // === DELETE: Xóa phân công (Gỡ nhân viên khỏi dự án) ===
    public boolean xoaPhanCong(String maNV, String maDA) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "DELETE FROM PhanCong WHERE MaNV = ? AND MaDA = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.setString(2, maDA);
            
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
    
    // === Tiện ích: Đếm số dự án nhân viên đang tham gia ===
    public int demDuAnCuaNhanVien(String maNV) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT COUNT(*) as total FROM PhanCong WHERE MaNV = ? AND NgayKetThuc IS NULL";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("total");
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
        return count;
    }
}
