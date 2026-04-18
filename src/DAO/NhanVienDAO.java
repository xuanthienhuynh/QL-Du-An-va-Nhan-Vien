package DAO;

import DTO.NhanVien_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhanVienDAO {

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
                nv.setMaNV(rs.getString("MaNV").trim());
                nv.setHoTen(rs.getString("HoTen").trim());
                nv.setVaiTro(rs.getString("VaiTro") != null ? rs.getString("VaiTro").trim() : "");
                nv.setTinhTrang(rs.getBoolean("TinhTrang"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setLuong(rs.getDouble("Luong"));

                String maPB = rs.getString("MaPB");
                nv.setMaPB(maPB != null ? maPB.trim() : "");

                String maCN = rs.getString("MaCN");
                nv.setMaCN(maCN != null ? maCN.trim() : "");

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

    public java.util.ArrayList<DTO.NhanVien_DTO> layDanhSachNhanVien() {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT MaNV, HoTen, VaiTro, TinhTrang, MaCN, MaPB, Luong FROM NhanVien";
        Connection conn = null;

        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DTO.NhanVien_DTO nv = new DTO.NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setLuong(rs.getDouble("Luong"));
                nv.setMaPB(rs.getString("MaPB"));
                nv.setVaiTro(rs.getString("VaiTro"));
                nv.setTinhTrang(rs.getBoolean("TinhTrang"));
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public java.util.ArrayList<DTO.NhanVien_DTO> timKiemNhanVien(String tuKhoa) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT MaNV, HoTen, VaiTro, MaCN, MaPB FROM NhanVien WHERE MaNV LIKE ? OR HoTen LIKE ? OR VaiTro LIKE ?";
        Connection conn = null;

        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);

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
                nv.setMaCN(rs.getString("MaCN"));
                nv.setMaPB(rs.getString("MaPB") != null ? rs.getString("MaPB").trim() : "");
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public java.util.ArrayList<String> layDanhSachMaPB(String maCN) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        String sql = "SELECT MaPB FROM PhongBan WHERE MaCN = ?";
        Connection conn = null;

        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCN);

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("MaPB"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public java.util.ArrayList<String> layDanhSachMaCN() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        String sql = "SELECT MaCN FROM ChiNhanh";
        Connection conn = null;

        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("MaCN"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public String layMaNVCuoiCung() {
        String ma = "";
        String sql = "SELECT TOP 1 MaNV FROM NhanVien ORDER BY LEN(MaNV) DESC, MaNV DESC";
        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ma = rs.getString("MaNV");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return ma;
    }

    public boolean themNhanVien(DTO.NhanVien_DTO nv) {
        Connection conn = null;
        String sql = "INSERT INTO NhanVien(MaNV, HoTen, GioiTinh, NgaySinh, Luong, MaPB, MaCN, VaiTro, MatKhau, TinhTrang) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(5, nv.getLuong());
            ps.setString(6, nv.getMaPB());
            ps.setString(7, nv.getMaCN());
            ps.setString(8, "NhanVien");
            ps.setString(9, "123");
            ps.setBoolean(10, nv.getTinhTrang());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return false;
    }

    public DTO.NhanVien_DTO layThongTinNhanVien(String maNV) {
        DTO.NhanVien_DTO nv = null;
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        Connection conn = null;

        try {
            conn = database.createConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return nv;
    }

    public boolean capNhatNhanVien(DTO.NhanVien_DTO nv) {
        Connection conn = null;
        String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, NgaySinh=?, Luong=?, MaPB=?, MaCN=?, VaiTro=? WHERE MaNV=?";
        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getGioiTinh());
            ps.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setDouble(4, nv.getLuong());
            ps.setString(5, nv.getMaPB());
            ps.setString(6, nv.getMaCN());
            ps.setString(7, nv.getVaiTro());
            ps.setString(8, nv.getMaNV());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return false;
    }

    public boolean choNghiViec(String maNV) {
        Connection conn = null;
        String sql = "UPDATE NhanVien SET TinhTrang = 0 WHERE MaNV = ?";
        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return false;
    }

    public java.util.ArrayList<DTO.NhanVien_DTO> thongKeLuongNhanVien(String maCN, String maPB, String sapXep) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT TOP 10 MaNV, HoTen, MaPB, Luong FROM NhanVien WHERE TinhTrang = 1 ";

        if (maCN != null && !maCN.equals("Chi Nhánh") && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            sql += " AND NhanVien.MaCN = '" + maCN + "' ";
        }
        if (maPB != null && !maPB.equals("Phòng ban") && !maPB.equals("Tất cả Phòng Ban") && !maPB.isEmpty()) {
            sql += " AND NhanVien.MaPB = '" + maPB + "' ";
        }
        if (sapXep != null && sapXep.equals("Sắp xếp giảm ")) {
            sql += " ORDER BY Luong DESC";
        } else {
            sql += " ORDER BY Luong ASC";
        }

        Connection conn = null;
        try {
            conn = database.createConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public java.util.ArrayList<DTO.NhanVien_DTO> thongKeTopDuAn(String maCN, String maPB, String sapXep) {
        java.util.ArrayList<DTO.NhanVien_DTO> list = new java.util.ArrayList<>();
        String sql = "SELECT TOP 10 NhanVien.MaNV, NhanVien.HoTen, NhanVien.MaPB, COUNT(PhanCong.MaDA) AS SoLuongDuAn "
                + "FROM NhanVien LEFT JOIN PhanCong ON NhanVien.MaNV = PhanCong.MaNV " +
                "WHERE NhanVien.TinhTrang = 1 ";

        if (maCN != null && !maCN.equals("Chi Nhánh") && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            sql += " AND NhanVien.MaCN = '" + maCN + "' ";
        }
        if (maPB != null && !maPB.equals("Phòng ban") && !maPB.equals("Tất cả Phòng Ban") && !maPB.isEmpty()) {
            sql += " AND NhanVien.MaPB = '" + maPB + "' ";
        }
        sql += " GROUP BY NhanVien.MaNV, NhanVien.HoTen, NhanVien.MaPB ";
        if (sapXep != null && sapXep.equals("Sắp xếp giảm ")) {
            sql += " ORDER BY SoLuongDuAn DESC";
        } else {
            sql += " ORDER BY SoLuongDuAn ASC";
        }

        Connection conn = null;
        try {
            conn = database.createConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public java.util.ArrayList<String> layDuAnCuaNhanVien(String maNV) {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        String sql = "SELECT DuAn.MaDA, DuAn.TenDA FROM PhanCong JOIN DuAn ON PhanCong.MaDA = DuAn.MaDA WHERE PhanCong.MaNV = ?";
        Connection conn = null;

        try {
            conn = database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            java.sql.ResultSet rs = ps.executeQuery();

            int stt = 1;
            while (rs.next()) {
                list.add(stt + ") " + rs.getString("MaDA") + " - " + rs.getString("TenDA"));
                stt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public ArrayList<NhanVien_DTO> layNhanVienTheoPhongBan(String maPB) {
        ArrayList<NhanVien_DTO> list = new ArrayList<>();
        String sql = "SELECT MaNV, HoTen, VaiTro FROM NhanVien WHERE MaPB = ?";
        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maPB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(rs.getString("MaNV").trim());
                nv.setHoTen(rs.getString("HoTen"));
                nv.setVaiTro(rs.getString("VaiTro"));
                list.add(nv);
            }
            database.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}