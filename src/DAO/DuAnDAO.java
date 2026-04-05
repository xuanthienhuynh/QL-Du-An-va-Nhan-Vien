package DAO;

import DTO.DuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuAnDAO {
    public ArrayList<DuAn_DTO> layDanhSachDuAn() {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        String sql = "SELECT DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai, "
                + "STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh "
                + "FROM DuAn DA "
                + "LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA "
                + "GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai";

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

                // Cắt chuỗi 'CN_HCM,CN_HN' thành List để nạp vào DTO
                String chuoiCN = rs.getString("CacChiNhanh");
                if (chuoiCN != null && !chuoiCN.isEmpty()) {
                    List<String> listCN = Arrays.asList(chuoiCN.split(","));
                    da.setDanhSachMaCN(new ArrayList<>(listCN));
                }

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
        // SỬA LỖI 1: Phải JOIN với DuAn_ChiNhanh vì DA không còn cột MaCN
        String join = " FROM DuAn DA LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA ";
        String where = " WHERE 1=1 ";
        String group = "";
        String order = "";

        if (ngayBD != null) {
            where += " AND DA.NgayBatDau >= ? ";
        }
        if (ngayKT != null) {
            where += " AND DA.NgayKetThuc <= ? ";
        }

        // 1. XỬ LÝ 5 KIỂU THỐNG KÊ (Dùng STRING_AGG để gom chi nhánh)
        if (kieuThongKe.contains("doanh thu")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, DA.DoanhThu AS ChiSo ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.DoanhThu ";
        } else if (kieuThongKe.contains("kinh phí")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, DA.KinhPhi AS ChiSo ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi ";
        } else if (kieuThongKe.contains("lâu nhất")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, DATEDIFF(day, DA.NgayBatDau, DA.NgayKetThuc) AS ChiSo ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.NgayBatDau, DA.NgayKetThuc ";
        } else if (kieuThongKe.contains("Deadline")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, DATEDIFF(day, GETDATE(), DA.NgayKetThuc) AS ChiSo ";
            where += " AND DA.NgayKetThuc >= GETDATE() ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.NgayKetThuc ";
        } else if (kieuThongKe.contains("quy mô")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh, COUNT(DISTINCT PC.MaNV) AS ChiSo ";
            join += " LEFT JOIN PhanCong PC ON DA.MaDA = PC.MaDA ";
            group = " GROUP BY DA.MaDA, DA.TenDA ";
        }

        // 2. XỬ LÝ LỌC THEO CHI NHÁNH (Lọc trên bảng DuAn_ChiNhanh)
        if (maCN != null && !maCN.toLowerCase().contains("tất cả") && !maCN.isEmpty()) {
            where += " AND DACN.MaCN = '" + maCN + "' ";
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

                // SỬA LỖI 2: Lấy chuỗi chi nhánh, cắt ra và nạp vào List
                String chuoiCN = rs.getString("CacChiNhanh");
                if (chuoiCN != null && !chuoiCN.isEmpty()) {
                    da.setDanhSachMaCN(new java.util.ArrayList<>(java.util.Arrays.asList(chuoiCN.split(","))));
                }

                da.setDoanhThu(rs.getDouble("ChiSo"));
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
                conn.setAutoCommit(false); // Tắt Auto Commit

                // BƯỚC 1: Thêm vào bảng DuAn (Đã bỏ cột MaCN)
                String sqlDA = "INSERT INTO DuAn (MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psDA = conn.prepareStatement(sqlDA);
                psDA.setString(1, da.getMaDA());
                psDA.setString(2, da.getTenDA());
                psDA.setDouble(3, da.getKinhPhi());
                psDA.setDouble(4, da.getDoanhThu());
                psDA.setDate(5, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                psDA.setDate(6, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);
                psDA.executeUpdate();

                // BƯỚC 2: Thêm danh sách chi nhánh vào bảng DuAn_ChiNhanh
                String sqlDACN = "INSERT INTO DuAn_ChiNhanh (MaDA, MaCN) VALUES (?, ?)";
                PreparedStatement psDACN = conn.prepareStatement(sqlDACN);
                if (da.getDanhSachMaCN() != null) {
                    for (String maCN : da.getDanhSachMaCN()) {
                        psDACN.setString(1, da.getMaDA());
                        psDACN.setString(2, maCN);
                        psDACN.executeUpdate();
                    }
                }

                conn.commit(); // Thành công thì lưu tất cả
                return true;

            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                } // Lỗi thì hủy hết
                e.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception ex) {
                }
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
                conn.setAutoCommit(false);

                // BƯỚC 1: Cập nhật bảng DuAn
                String sqlDA = "UPDATE DuAn SET TenDA=?, KinhPhi=?, DoanhThu=?, NgayBatDau=?, NgayKetThuc=? WHERE MaDA=?";
                PreparedStatement psDA = conn.prepareStatement(sqlDA);
                psDA.setString(1, da.getTenDA());
                psDA.setDouble(2, da.getKinhPhi());
                psDA.setDouble(3, da.getDoanhThu());
                psDA.setDate(4, da.getNgayBatDau() != null ? new java.sql.Date(da.getNgayBatDau().getTime()) : null);
                psDA.setDate(5, da.getNgayKetThuc() != null ? new java.sql.Date(da.getNgayKetThuc().getTime()) : null);
                psDA.setString(6, da.getMaDA());
                psDA.executeUpdate();

                // BƯỚC 2: Xóa sạch danh sách chi nhánh cũ của dự án này
                String sqlDeleteCN = "DELETE FROM DuAn_ChiNhanh WHERE MaDA=?";
                PreparedStatement psDelCN = conn.prepareStatement(sqlDeleteCN);
                psDelCN.setString(1, da.getMaDA());
                psDelCN.executeUpdate();

                // BƯỚC 3: Insert lại danh sách chi nhánh mới
                String sqlInsertCN = "INSERT INTO DuAn_ChiNhanh (MaDA, MaCN) VALUES (?, ?)";
                PreparedStatement psInsertCN = conn.prepareStatement(sqlInsertCN);
                if (da.getDanhSachMaCN() != null) {
                    for (String maCN : da.getDanhSachMaCN()) {
                        psInsertCN.setString(1, da.getMaDA());
                        psInsertCN.setString(2, maCN);
                        psInsertCN.executeUpdate();
                    }
                }

                conn.commit();
                return true;

            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                }
                e.printStackTrace();
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (Exception ex) {
                }
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
                conn.setAutoCommit(false);

                // Xóa bảng con PhanCong
                String sqlPhanCong = "DELETE FROM PhanCong WHERE MaDA = ?";
                PreparedStatement psPhanCong = conn.prepareStatement(sqlPhanCong);
                psPhanCong.setString(1, maDA);
                psPhanCong.executeUpdate();

                // Xóa bảng con DuAn_ChiNhanh
                String sqlDACN = "DELETE FROM DuAn_ChiNhanh WHERE MaDA = ?";
                PreparedStatement psDACN = conn.prepareStatement(sqlDACN);
                psDACN.setString(1, maDA);
                psDACN.executeUpdate();

                // Xóa bảng cha DuAn
                String sqlDuAn = "DELETE FROM DuAn WHERE MaDA = ?";
                PreparedStatement psDuAn = conn.prepareStatement(sqlDuAn);
                psDuAn.setString(1, maDA);
                int rowsAffected = psDuAn.executeUpdate();

                conn.commit();
                return rowsAffected > 0;

            } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                }
                e.printStackTrace();
            } finally {
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

        // --- ĐÃ FIX: Thay DA.* bằng danh sách các cột cụ thể để không bị dính cột
        // rowguid của Replication
        String sql = "SELECT DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai, "
                + "STRING_AGG(DACN.MaCN, ',') AS CacChiNhanh "
                + "FROM DuAn DA LEFT JOIN DuAn_ChiNhanh DACN ON DA.MaDA = DACN.MaDA "
                + "WHERE DA.MaDA = ? "
                + "GROUP BY DA.MaDA, DA.TenDA, DA.KinhPhi, DA.DoanhThu, DA.NgayBatDau, DA.NgayKetThuc, DA.TrangThai";

        try {
            Connection conn = database.createConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maDA);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                da = new DTO.DuAn_DTO();
                da.setMaDA(rs.getString("MaDA"));
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
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return da;
    }

    public boolean capNhatTrangThaiDuAn(DTO.DuAn_DTO da) {
        Connection conn = database.createConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE DuAn SET TrangThai = ? WHERE MaDA = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, da.getTrangThai());
                ps.setString(2, da.getMaDA());
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                database.closeConnection(conn);
            }
        }
        return false;
    }

}