package DAO;

import DTO.PhongBan_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PhongBanDAO {

    // 1. Lấy toàn bộ danh sách phòng ban
    public ArrayList<PhongBan_DTO> layToanBoPhongBan() {
        ArrayList<PhongBan_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PhongBan";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhongBan_DTO pb = new PhongBan_DTO();
                // Sử dụng .trim() vì MaPB và MaCN là kiểu CHAR trong SQL
                pb.setMaPB(rs.getString("MaPB").trim());
                pb.setTenPB(rs.getString("TenPB"));
                pb.setMaCN(rs.getString("MaCN").trim());
                list.add(pb);
            }
            database.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy danh sách phòng ban theo Chi Nhánh (Dùng cho ComboBox lọc)
    public ArrayList<PhongBan_DTO> layPhongBanTheoChiNhanh(String maCN) {
        ArrayList<PhongBan_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PhongBan WHERE MaCN = ?";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhongBan_DTO pb = new PhongBan_DTO();
                pb.setMaPB(rs.getString("MaPB").trim());
                pb.setTenPB(rs.getString("TenPB"));
                pb.setMaCN(rs.getString("MaCN").trim());
                list.add(pb);
            }
            database.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Lấy tên phòng ban khi biết mã (Dùng để hiển thị lên bảng)
    public String layTenPhongBan(String maPB) {
        String tenPB = "";
        String sql = "SELECT TenPB FROM PhongBan WHERE MaPB = ?";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maPB);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tenPB = rs.getString("TenPB");
            }
            database.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenPB;
    }

    // 4. Thêm phòng ban mới

    public boolean themPhongBan(PhongBan_DTO pb) {
        String sql = "INSERT INTO PhongBan (MaPB, TenPB, MaCN) VALUES (?, ?, ?)";
        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getMaPB());
            ps.setString(2, pb.getTenPB());
            ps.setString(3, pb.getMaCN());

            // int rows = ps.executeUpdate();
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.closeConnection(conn); // Đảm bảo nhả Lock cho Replication
        }
    }

}