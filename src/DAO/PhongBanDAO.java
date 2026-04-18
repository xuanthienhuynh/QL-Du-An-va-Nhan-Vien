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

    // 5. Tìm kiếm và lọc kết hợp (Dùng cho thanh Search và ComboBox Chi nhánh)
    public ArrayList<PhongBan_DTO> timKiemVaLoc(String tuKhoa, String maCN) {
        ArrayList<PhongBan_DTO> list = new ArrayList<>();

        // Câu SQL cơ bản: Tìm theo Mã hoặc Tên Phòng Ban (Dùng N? để tìm tiếng Việt có
        // dấu)
        String sql = "SELECT * FROM PhongBan WHERE (MaPB LIKE ? OR TenPB LIKE ?) ";

        // Nếu Chi nhánh được chọn KHÔNG PHẢI là "Tất cả", thì nối thêm điều kiện lọc
        boolean coLocChiNhanh = (maCN != null && !maCN.isEmpty() && !maCN.equals("Tất cả chi nhánh"));
        if (coLocChiNhanh) {
            sql += " AND MaCN = ?";
        }

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            // Đổ chuỗi tìm kiếm vào
            String keyword = "%" + tuKhoa + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);

            // Nếu có lọc chi nhánh thì truyền tham số thứ 3 vào
            if (coLocChiNhanh) {
                ps.setString(3, maCN);
            }

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

    // 5. Cập nhật phòng ban
    public boolean capNhatPhongBan(PhongBan_DTO pb) {
        String sql = "UPDATE PhongBan SET TenPB = ?, MaCN = ? WHERE MaPB = ?";
        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getTenPB());
            ps.setString(2, pb.getMaCN());
            ps.setString(3, pb.getMaPB());

            int rows = ps.executeUpdate();
            database.closeConnection(conn);
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Xóa phòng ban
    public boolean xoaPhongBan(String maPB) {
        String sql = "DELETE FROM PhongBan WHERE MaPB = ?";
        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maPB);

            int rows = ps.executeUpdate();
            database.closeConnection(conn);
            return rows > 0;
        } catch (Exception e) {
            // Nếu có nhân viên thuộc phòng này, SQL sẽ văng lỗi ở đây
            return false;
        }
    }

}