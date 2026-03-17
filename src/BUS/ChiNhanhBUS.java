package BUS;

import DAO.ChiNhanhDAO;
import DTO.ChiNhanh_DTO;
import java.util.ArrayList;

public class ChiNhanhBUS {
    private ChiNhanhDAO dao = new ChiNhanhDAO();

    // 1. LẤY TOÀN BỘ DANH SÁCH
    public ArrayList<ChiNhanh_DTO> layToanBoChiNhanh() {
        return dao.layDanhSachChiNhanh();
    }

    // 2. TÌM KIẾM THEO TỪ KHÓA (Tên hoặc Địa chỉ)
    public ArrayList<ChiNhanh_DTO> timKiem(String tuKhoa) {
        ArrayList<ChiNhanh_DTO> tatCa = layToanBoChiNhanh();
        ArrayList<ChiNhanh_DTO> ketQua = new ArrayList<>();

        if (tatCa == null || tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return tatCa;
        }

        String keyword = tuKhoa.toLowerCase().trim();
        for (ChiNhanh_DTO cn : tatCa) {
            boolean matchTen = cn.getTenCN() != null && cn.getTenCN().toLowerCase().contains(keyword);
            boolean matchDiaChi = cn.getDiaChi() != null && cn.getDiaChi().toLowerCase().contains(keyword);
            boolean matchMa = cn.getMaCN() != null && cn.getMaCN().toLowerCase().contains(keyword);

            if (matchTen || matchDiaChi || matchMa) {
                ketQua.add(cn);
            }
        }
        return ketQua;
    }

    // 3. THÊM CHI NHÁNH MỚI (Có kiểm tra mã trùng)
    public boolean themChiNhanh(ChiNhanh_DTO cn) {
        // Kiểm tra xem mã chi nhánh đã tồn tại chưa
        ChiNhanh_DTO check = dao.layThongTinChiNhanh(cn.getMaCN());
        if (check != null) {
            System.out.println("Lỗi: Mã chi nhánh đã tồn tại trên hệ thống!");
            return false;
        }
        return dao.themChiNhanh(cn);
    }

    // 4. CẬP NHẬT THÔNG TIN
    public boolean suaThongTin(ChiNhanh_DTO cn) {
        return dao.capNhatChiNhanh(cn);
    }

    // 5. XÓA CHI NHÁNH
    public boolean xoaChiNhanh(String maCN) {
        // Lưu ý: Ở tầng này bạn có thể kiểm tra thêm ràng buộc
        // (ví dụ: chi nhánh còn nhân viên hay không trước khi xóa)
        return dao.xoaChiNhanh(maCN);
    }

    // 6. LẤY CHI TIẾT THEO MÃ
    public ChiNhanh_DTO getChiTiet(String maCN) {
        return dao.layThongTinChiNhanh(maCN);
    }

    // 7. TỰ ĐỘNG TẠO MÃ CHI NHÁNH MỚI (Dạng CN01, CN02,...)
    public String taoMaMoi() {
        ArrayList<ChiNhanh_DTO> list = layToanBoChiNhanh();
        if (list == null || list.isEmpty()) {
            return "CN01";
        }

        int max = 0;
        for (ChiNhanh_DTO cn : list) {
            String ma = cn.getMaCN();
            if (ma != null && ma.startsWith("CN")) {
                try {
                    int so = Integer.parseInt(ma.substring(2).trim());
                    if (so > max)
                        max = so;
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu mã không đúng định dạng số phía sau
                }
            }
        }
        max++;
        return String.format("CN%02d", max);
    }
}