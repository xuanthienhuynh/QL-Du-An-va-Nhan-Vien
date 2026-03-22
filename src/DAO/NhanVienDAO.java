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

    // === CREATE: Thêm nhân viên mới ===
    public boolean themNhanVien(NhanVien_DTO nv) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "INSERT INTO NhanVien (MaNV, HoTen, GioiTinh, DcTinh, DcPhuong, DcSoNha, NgaySinh, Luong, TinhTrang, VaiTro, MatKhau, MaPB, MaCN) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDcTinh());
            ps.setString(5, nv.getDcPhuong());
            ps.setString(6, nv.getDcSoNha());
            ps.setDate(7, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(8, nv.getLuong());
            ps.setString(9, nv.getTinhTrang());
            ps.setString(10, nv.getVaiTro());
            ps.setString(11, nv.getMatKhau());
            ps.setString(12, nv.getMaPB());
            ps.setString(13, nv.getMaCN());
            
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
    
    // === READ: Lấy thông tin 1 nhân viên theo mã ===
    public NhanVien_DTO getNhanVienByMa(String maNV) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        NhanVien_DTO nv = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setDcTinh(rs.getString("DcTinh"));
                nv.setDcPhuong(rs.getString("DcPhuong"));
                nv.setDcSoNha(rs.getString("DcSoNha"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setTinhTrang(rs.getString("TinhTrang"));
                nv.setVaiTro(rs.getString("VaiTro"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setMaCN(rs.getString("MaCN"));
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
        return nv;
    }
    
    // === READ: Lấy tất cả nhân viên (đầy đủ thông tin) ===
    public java.util.ArrayList<NhanVien_DTO> getAllNhanVien() {
        java.util.ArrayList<NhanVien_DTO> list = new java.util.ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = database.createConnection();
            String sql = "SELECT * FROM NhanVien ORDER BY MaNV";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setDcTinh(rs.getString("DcTinh"));
                nv.setDcPhuong(rs.getString("DcPhuong"));
                nv.setDcSoNha(rs.getString("DcSoNha"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setTinhTrang(rs.getString("TinhTrang"));
                nv.setVaiTro(rs.getString("VaiTro"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setMaCN(rs.getString("MaCN"));
                list.add(nv);
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
    
    // === UPDATE: Cập nhật thông tin nhân viên ===
    public boolean suaNhanVien(NhanVien_DTO nv) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, DcTinh=?, DcPhuong=?, DcSoNha=?, " +
                        "NgaySinh=?, Luong=?, TinhTrang=?, VaiTro=?, MatKhau=?, MaPB=?, MaCN=? " +
                        "WHERE MaNV=?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setString(3, nv.getDcTinh());
            ps.setString(4, nv.getDcPhuong());
            ps.setString(5, nv.getDcSoNha());
            ps.setDate(6, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(7, nv.getLuong());
            ps.setString(8, nv.getTinhTrang());
            ps.setString(9, nv.getVaiTro());
            ps.setString(10, nv.getMatKhau());
            ps.setString(11, nv.getMaPB());
            ps.setString(12, nv.getMaCN());
            ps.setString(13, nv.getMaNV()); // WHERE condition
            
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
    
    // === DELETE: Xóa nhân viên ===
    public boolean xoaNhanVien(String maNV) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = database.createConnection();
            String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            
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
}