package DAO;

import DTO.DuAn_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DuAnDAO {
    public ArrayList<DuAn_DTO> layDanhSachDuAn() {
        ArrayList<DuAn_DTO> list = new ArrayList<>();
        // Lấy đầy đủ các cột theo DTO
        String sql = "SELECT MaDA, TenDA, KinhPhi, DoanhThu, NgayBatDau, NgayKetThuc, TrangThai, MaCN FROM DuAn";

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
                da.setMaCN(rs.getString("MaCN"));
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
        String join = " FROM DuAn DA ";
        String where = " WHERE 1=1 ";
        String group = "";
        String order = "";

        // Lọc theo ngày tháng (nếu người dùng có chọn)
        if (ngayBD != null) {
            where += " AND DA.NgayBatDau >= ? ";
        }
        if (ngayKT != null) {
            where += " AND DA.NgayKetThuc <= ? ";
        }

        // 1. XỬ LÝ 5 KIỂU THỐNG KÊ
        if (kieuThongKe.contains("doanh thu")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DA.DoanhThu AS ChiSo ";
        } else if (kieuThongKe.contains("kinh phí")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DA.KinhPhi AS ChiSo ";
        } else if (kieuThongKe.contains("lâu nhất")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DATEDIFF(day, DA.NgayBatDau, DA.NgayKetThuc) AS ChiSo ";
        } else if (kieuThongKe.contains("Deadline")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, DATEDIFF(day, GETDATE(), DA.NgayKetThuc) AS ChiSo ";
            where += " AND DA.NgayKetThuc >= GETDATE() "; // Chỉ lấy dự án chưa tới hạn
        } else if (kieuThongKe.contains("quy mô")) {
            select = "SELECT TOP 10 DA.MaDA, DA.TenDA, DA.MaCN, COUNT(PC.MaNV) AS ChiSo ";
            join += " LEFT JOIN PhanCong PC ON DA.MaDA = PC.MaDA ";
            group = " GROUP BY DA.MaDA, DA.TenDA, DA.MaCN ";
        }

        // 2. XỬ LÝ LỌC THEO CHI NHÁNH
        if (maCN != null && !maCN.equals("Tất cả Chi Nhánh") && !maCN.isEmpty()) {
            where += " AND DA.MaCN = '" + maCN + "' ";
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

            // Nạp tham số ngày tháng vào các dấu ? trong SQL
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
                da.setMaCN(rs.getString("MaCN")); // Hiện mã chi nhánh
                da.setDoanhThu(rs.getDouble("ChiSo")); // Mượn tạm biến DoanhThu của DTO để lưu chỉ số trả về
                list.add(da);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}