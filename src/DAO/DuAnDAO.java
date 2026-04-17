package DAO;

import DTO.DuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class DuAnDAO {

    // 1. LẤY DANH SÁCH DỰ ÁN
    public ArrayList<DuAn_DTO> layDanhSachDuAn() {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        String sql = "SELECT DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai, "
                + "STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh "
                + "FROM DuAn DA "
                + "LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA "
                + "GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai";

        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA").trim());
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setDoanhThu(rs.getDouble("DoanhThu"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));

                String chuoiCN = rs.getString("CacChiNhanh");
                if (chuoiCN != null && !chuoiCN.isEmpty()) {
                    da.setDanhSachMaCN(new ArrayList<>(Arrays.asList(chuoiCN.split(","))));
                }
                list.add(da);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    // 2. THÊM DỰ ÁN MỚI (Cần Transaction để đồng bộ sang bảng trung gian)
    public boolean themDuAn(DuAn_DTO da) {
        String sqlDA = "INSERT INTO DuAn (MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDACN = "INSERT INTO DuAn_ChiNhanh (MaDA, MaCN) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = database.createConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // Chèn vào bảng DuAn
            PreparedStatement psDA = conn.prepareStatement(sqlDA);
            psDA.setString(1, da.getMaDA());
            psDA.setString(2, da.getTenDA());
            psDA.setDouble(3, da.getKinhPhi());
            psDA.setDouble(4, da.getDoanhThu());
            psDA.setObject(5, da.getNgayBatDau());
            psDA.setObject(6, da.getNgayKetThuc());
            psDA.setString(7, da.getTrangThai());
            psDA.executeUpdate();

            // Chèn vào bảng DuAn_ChiNhanh cho từng chi nhánh được chọn
            if (da.getDanhSachMaCN() != null) {
                PreparedStatement psDACN = conn.prepareStatement(sqlDACN);
                for (String maCN : da.getDanhSachMaCN()) {
                    psDACN.setString(1, da.getMaDA());
                    psDACN.setString(2, maCN);
                    psDACN.executeUpdate();
                }
            }

            conn.commit(); // Hoàn tất Transaction
            return true;
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            database.closeConnection(conn);
        }
    }

    // 3. CẬP NHẬT TRẠNG THÁI (Đơn giản)
    public boolean capNhatTrangThaiDuAn(DuAn_DTO da) {
        String sql = "UPDATE DuAn SET TrangThai = ? WHERE MaDA = ?";
        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, da.getTrangThai());
            ps.setString(2, da.getMaDA());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.closeConnection(conn);
        }
    }

    // 4. LẤY THÔNG TIN CHI TIẾT
    public DuAn_DTO layThongTinDuAn(String maDA) {
        DuAn_DTO da = null;
        String sql = "SELECT DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai, "
                + "STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh "
                + "FROM DuAn DA "
                + "LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA "
                + "WHERE DA.MaDA = ? "
                + "GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai";

        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA").trim());
                da.setTenDA(rs.getString("TenDA"));
                da.setKinhPhi(rs.getDouble("KinhPhi"));
                da.setDoanhThu(rs.getDouble("DoanhThu"));
                da.setNgayBatDau(rs.getDate("NgayBatDau"));
                da.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                da.setTrangThai(rs.getString("TrangThai"));

                String chuoiCN = rs.getString("CacChiNhanh");
                if (chuoiCN != null && !chuoiCN.isEmpty()) {
                    da.setDanhSachMaCN(new ArrayList<>(Arrays.asList(chuoiCN.split(","))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return da;
    }

    public ArrayList<String[]> layThongKeDuAn() {
        ArrayList<String[]> list = new ArrayList<>();
        // Truy vấn lấy: Mã DA, Tên DA, Tên PB (của nhân viên tham gia đầu tiên), và
        // Tổng số nhân viên
        String sql = "SELECT da.MaDA, da.TenDA, " +
                "ISNULL((SELECT TOP 1 pb.TenPB FROM PhanCong pc JOIN NhanVien nv ON pc.MaNV = nv.MaNV " +
                "JOIN PhongBan pb ON nv.MaPB = pb.MaPB WHERE pc.MaDA = da.MaDA), N'Chưa có') as TenPB, " +
                "(SELECT COUNT(*) FROM PhanCong pc WHERE pc.MaDA = da.MaDA) as SoNV " +
                "FROM DuAn da";

        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("MaDA").trim();
                row[1] = rs.getString("TenDA");
                row[2] = rs.getString("TenPB");
                row[3] = rs.getString("SoNV") + " Nhân viên";
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return list;
    }

    public ArrayList<DuAn_DTO> thongKeDacBietDuAn(String kieu, String maCN, java.util.Date ngayBD,
            java.util.Date ngayKT, String sapXep) {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = database.createConnection();
            StringBuilder sql = new StringBuilder();

            // 1. CHỌN CỘT DỮ LIỆU CƠ BẢN
            sql.append("SELECT top 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, ");

            // 2. XÁC ĐỊNH CHỈ SỐ THỐNG KÊ DỰA VÀO COMBOBOX
            String metricCol = "";
            if (kieu.contains("doanh thu")) {
                metricCol = "DA.DoanhThu";
            } else if (kieu.contains("kinh phí")) {
                metricCol = "DA.KinhPhi";
            } else if (kieu.contains("lâu nhất") || kieu.contains("Deadline")) {
                // Tính số ngày chênh lệch giữa Ngày bắt đầu và Ngày kết thúc
                metricCol = "ISNULL(DATEDIFF(day, DA.NgayBatDau, DA.NgayKetThuc), 0)";
            } else if (kieu.contains("quy mô")) {
                // Đếm số lượng nhân viên tham gia dự án
                metricCol = "ISNULL((SELECT COUNT(*) FROM PhanCong PC WHERE PC.MaDA = DA.MaDA), 0)";
            } else {
                metricCol = "DA.DoanhThu"; // Mặc định
            }
            // Đặt tên cột ảo là GiaTriThongKe
            sql.append(metricCol).append(" AS GiaTriThongKe ");

            sql.append("FROM DuAn DA ");
            sql.append("LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA ");
            sql.append("WHERE 1=1 ");

            // 3. LỌC THEO NGÀY THÁNG
            if (ngayBD != null) {
                sql.append("AND DA.NgayBatDau >= ? ");
            }
            if (ngayKT != null) {
                sql.append("AND DA.NgayKetThuc <= ? ");
            }

            // Gộp nhóm để dùng STRING_AGG
            sql.append("GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc ");

            // 4. LỌC THEO CHI NHÁNH
            if (maCN != null && !maCN.equals("Tất cả Chi Nhánh") && !maCN.equals("Chi Nhánh")) {
                sql.append("HAVING STRING_AGG(DACN.MaCN, ',') LIKE ? ");
            }

            // 5. SẮP XẾP TĂNG / GIẢM
            String orderDir = (sapXep != null && sapXep.contains("giảm")) ? "DESC" : "ASC";
            sql.append("ORDER BY GiaTriThongKe ").append(orderDir);

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            // 6. GÁN THAM SỐ VÀO DẤU "?" CHUẨN XÁC
            int paramIndex = 1;
            if (ngayBD != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayBD.getTime()));
            }
            if (ngayKT != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayKT.getTime()));
            }
            if (maCN != null && !maCN.equals("Tất cả Chi Nhánh") && !maCN.equals("Chi Nhánh")) {
                ps.setString(paramIndex++, "%" + maCN + "%");
            }

            // 7. CHẠY VÀ ĐỔ DỮ LIỆU RA
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA").trim());
                da.setTenDA(rs.getString("TenDA"));

                // LƯU Ý QUAN TRỌNG:
                // Vì SepGUI dùng da.getDoanhThu() để vẽ biểu đồ nên ta phải gán giá trị thống
                // kê vào biến DoanhThu
                da.setDoanhThu(rs.getDouble("GiaTriThongKe"));

                String chuoiCN = rs.getString("CacChiNhanh");
                if (chuoiCN != null && !chuoiCN.isEmpty()) {
                    da.setDanhSachMaCN(new ArrayList<>(Arrays.asList(chuoiCN.split(","))));
                }
                list.add(da);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn); // Luôn đóng kết nối bảo vệ hệ thống Replication
        }
        return list;
    }

    public boolean capNhatDuAn(DuAn_DTO da) {
        String sqlUpdateDA = "UPDATE DuAn SET TenDA = ?, KinhPhi = ?, DoanhThu = ?, NgayBatDau = ?, NgayKetThuc = ?, TrangThai = ? WHERE MaDA = ?";
        String sqlDeleteCN = "DELETE FROM DuAn_ChiNhanh WHERE MaDA = ?";
        String sqlInsertCN = "INSERT INTO DuAn_ChiNhanh (MaDA, MaCN) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = database.createConnection();
            conn.setAutoCommit(false); // Dùng Transaction để bảo vệ dữ liệu

            // 1. Cập nhật bảng DuAn
            PreparedStatement ps = conn.prepareStatement(sqlUpdateDA);
            ps.setString(1, da.getTenDA());
            ps.setDouble(2, da.getKinhPhi());
            ps.setDouble(3, da.getDoanhThu());
            ps.setObject(4, da.getNgayBatDau());
            ps.setObject(5, da.getNgayKetThuc());
            ps.setString(6, da.getTrangThai());
            ps.setString(7, da.getMaDA());
            ps.executeUpdate();

            // 2. Xóa các chi nhánh cũ của dự án này
            PreparedStatement psDel = conn.prepareStatement(sqlDeleteCN);
            psDel.setString(1, da.getMaDA());
            psDel.executeUpdate();

            // 3. Chèn lại danh sách chi nhánh mới
            if (da.getDanhSachMaCN() != null) {
                PreparedStatement psIns = conn.prepareStatement(sqlInsertCN);
                for (String maCN : da.getDanhSachMaCN()) {
                    psIns.setString(1, da.getMaDA());
                    psIns.setString(2, maCN);
                    psIns.executeUpdate();
                }
            }

            conn.commit(); // Hoàn tất
            return true;
        } catch (Exception e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            }
            e.printStackTrace();
            return false;
        } finally {
            database.closeConnection(conn);
        }
    }

    // Bổ sung hàm xóa bị thiếu mà BUS đang gọi
    public boolean xoaDuAn(String maDA) {
        String sql = "DELETE FROM DuAn WHERE MaDA = ?";
        Connection conn = null;
        try {
            conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.closeConnection(conn);
        }
    }

    public DuAn_DTO getChiTietDuAn(String maDA) {
        DuAn_DTO da = null;
        Connection conn = null;
        try {
            conn = database.createConnection();
            // 1. Lấy thông tin cơ bản của dự án
            String sql = "SELECT * FROM DuAn WHERE MaDA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                da = new DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
                da.setTenDA(rs.getString("TenDA"));
                // ... (bạn gán các trường kinh phí, doanh thu, ngày tháng như bình thường) ...

                // --- BỔ SUNG ĐOẠN NÀY ĐỂ LẤY CHI NHÁNH ---
                String sqlCN = "SELECT MaCN FROM DuAn_ChiNhanh WHERE MaDA = ?";
                PreparedStatement psCN = conn.prepareStatement(sqlCN);
                psCN.setString(1, maDA);
                ResultSet rsCN = psCN.executeQuery();

                java.util.List<String> listCN = new java.util.ArrayList<>();
                while (rsCN.next()) {
                    // Nhớ dùng .trim() để phòng trường hợp SQL bị dư khoảng trắng (VD: "CN_HCM ")
                    listCN.add(rsCN.getString("MaCN").trim());
                }
                // Đưa danh sách chi nhánh vào đối tượng dự án
                da.setDanhSachMaCN(listCN);
                // -----------------------------------------
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeConnection(conn);
        }
        return da;
    }

    // dinh
    public java.util.ArrayList<Object[]> layDanhSachDuAnDangLam(String maNV) {
        java.util.ArrayList<Object[]> list = new java.util.ArrayList<>();

        String sql = "SELECT DA.MaDA, DA.TenDA, DA.NgayBatDau, DA.TrangThai, PC.VaiTroDuAn "
                + "FROM PhanCong PC "
                + "JOIN DuAn DA ON PC.MaDA = DA.MaDA "
                + "WHERE PC.MaNV = ? AND (DA.TrangThai != 'KetThuc' OR DA.TrangThai IS NULL)";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("MaDA");
                row[1] = rs.getString("TenDA");
                // Format ngày nếu có, null thì để trống
                java.sql.Date ngayBD = rs.getDate("NgayBatDau");
                row[2] = (ngayBD != null) ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayBD)
                        : "Chưa xác định";

                String trangThai = rs.getString("TrangThai");
                row[3] = (trangThai == null || trangThai.trim().isEmpty()) ? "Đang thực hiện" : trangThai;

                row[4] = rs.getString("VaiTroDuAn");
                list.add(row);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public java.util.ArrayList<Object[]> layLichSuDuAnCuaNhanVien(String maNV) {
        java.util.ArrayList<Object[]> list = new java.util.ArrayList<>();

        // BỔ SUNG: Lấy thêm cột PC.DanhGia
        String sql = "SELECT DA.MaDA, DA.TenDA, DA.NgayBatDau, DA.NgayKetThuc, PC.VaiTroDuAn, PC.DanhGia "
                + "FROM PhanCong PC "
                + "JOIN DuAn DA ON PC.MaDA = DA.MaDA "
                + "WHERE PC.MaNV = ? AND DA.TrangThai = 'KetThuc'";

        try {
            java.sql.Connection conn = DAO.database.createConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[6]; // Tăng lên 6 ô để chứa điểm
                row[0] = rs.getString("MaDA");
                row[1] = rs.getString("TenDA");
                row[2] = rs.getDate("NgayBatDau");
                row[3] = rs.getDate("NgayKetThuc");
                row[4] = rs.getString("VaiTroDuAn");

                // Xử lý điểm đánh giá (Lỡ dự án chưa có điểm thì để chữ "Chưa chấm")
                Object diem = rs.getObject("DanhGia");
                row[5] = (diem != null) ? diem : "Chưa chấm";

                list.add(row);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}