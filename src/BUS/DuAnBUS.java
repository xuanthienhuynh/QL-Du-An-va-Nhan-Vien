package BUS;

import DAO.DuAnDAO;
import DTO.DuAn_DTO;
import java.util.ArrayList;

public class DuAnBUS {
    private DuAnDAO dao = new DuAnDAO();

    public ArrayList<DuAn_DTO> layToanBoDuAn() {
        return dao.layDanhSachDuAn();
    }

    public java.util.ArrayList<DTO.DuAn_DTO> thongKeDacBietDuAn(String kieu, String maCN, java.util.Date ngayBD,
            java.util.Date ngayKT, String sapXep) {
        return dao.thongKeDacBietDuAn(kieu, maCN, ngayBD, ngayKT, sapXep);
    }

    public java.util.ArrayList<DTO.DuAn_DTO> timKiemNangCao(String tuKhoa, java.util.Date tuNgay,
            java.util.Date denNgay, String chiNhanh) {
        // Lấy tất cả dự án lên trước
        java.util.ArrayList<DTO.DuAn_DTO> tatCa = layToanBoDuAn();
        java.util.ArrayList<DTO.DuAn_DTO> ketQua = new java.util.ArrayList<>();

        if (tatCa == null)
            return ketQua;

        for (DTO.DuAn_DTO da : tatCa) {
            boolean matchTuKhoa = true;
            boolean matchChiNhanh = true;
            boolean matchThoiGian = true;

            // 1. Lọc theo từ khóa (Tên DA hoặc Mã DA)
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String keyword = tuKhoa.toLowerCase();
                matchTuKhoa = (da.getTenDA() != null && da.getTenDA().toLowerCase().contains(keyword)) ||
                        (da.getMaDA() != null && da.getMaDA().toLowerCase().contains(keyword));
            }

            // 2. Lọc theo Chi nhánh
            // Nếu người dùng chọn "Tất cả chi nhánh" thì bỏ qua bước lọc này (luôn true)
            if (chiNhanh != null && !chiNhanh.equals("Tất cả chi nhánh")) {
                matchChiNhanh = (da.getDanhSachMaCN() != null && da.getDanhSachMaCN().contains(chiNhanh));
            }

            // 3. Lọc theo Thời gian (Nằm trong khoảng từ ngày cmbNBD đến ngày cmbNKT)
            if (tuNgay != null && da.getNgayBatDau() != null) {
                // Nếu ngày bắt đầu của dự án xảy ra TRƯỚC "tuNgay" -> Không thỏa mãn
                if (da.getNgayBatDau().before(tuNgay)) {
                    matchThoiGian = false;
                }
            }

            if (denNgay != null && da.getNgayKetThuc() != null) {
                // Nếu ngày kết thúc của dự án xảy ra SAU "denNgay" -> Không thỏa mãn
                if (da.getNgayKetThuc().after(denNgay)) {
                    matchThoiGian = false;
                }
            }

            // Nếu dự án vượt qua được cả 3 bài kiểm tra trên thì đưa vào danh sách kết quả
            if (matchTuKhoa && matchChiNhanh && matchThoiGian) {
                ketQua.add(da);
            }
        }

        return ketQua;
    }

    public java.util.ArrayList<DTO.DuAn_DTO> timKiem(String tuKhoa) {
        // 1. Lấy toàn bộ danh sách dự án
        java.util.ArrayList<DTO.DuAn_DTO> tatCa = layToanBoDuAn();
        java.util.ArrayList<DTO.DuAn_DTO> ketQua = new java.util.ArrayList<>();

        // 2. Nếu từ khóa rỗng hoặc không có dữ liệu, trả về toàn bộ danh sách
        if (tatCa == null || tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return tatCa;
        }

        // 3. Chuyển từ khóa về chữ thường và cắt khoảng trắng thừa
        // (Giúp tìm kiếm không phân biệt chữ HOA/thường)
        String keyword = tuKhoa.toLowerCase().trim();

        // 4. Duyệt qua từng dự án để kiểm tra
        for (DTO.DuAn_DTO da : tatCa) {
            boolean matchTen = da.getTenDA() != null && da.getTenDA().toLowerCase().contains(keyword);
            boolean matchMa = da.getMaDA() != null && da.getMaDA().toLowerCase().contains(keyword);

            // Nếu Tên dự án hoặc Mã dự án có chứa cụm từ khóa thì thêm vào kết quả
            if (matchTen || matchMa) {
                ketQua.add(da);
            }
        }

        // 5. Trả về danh sách đã được lọc
        return ketQua;
    }

    public String taoMaDuAnMoi() {
        java.util.ArrayList<DTO.DuAn_DTO> list = layToanBoDuAn();

        // Nếu danh sách trống, trả về mã đầu tiên
        if (list == null || list.isEmpty()) {
            return "DA01";
        }

        int maxId = 0;
        for (DTO.DuAn_DTO da : list) {
            String ma = da.getMaDA();
            // Kiểm tra xem mã có đúng chuẩn bắt đầu bằng "DA" không
            if (ma != null && ma.toUpperCase().startsWith("DA")) {
                try {
                    // Cắt bỏ 2 ký tự đầu ("DA"), lấy phần số phía sau để chuyển thành int
                    String phanSo = ma.substring(2).trim();
                    int currentNum = Integer.parseInt(phanSo);

                    // Tìm số lớn nhất
                    if (currentNum > maxId) {
                        maxId = currentNum;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu có dự án bị gõ sai mã (ví dụ: DAAAA)
                }
            }
        }

        // Cộng thêm 1 vào số lớn nhất tìm được
        maxId++;

        // Ghép lại chữ "DA" và định dạng số luôn có 2 chữ số (vd: 01, 02... 10)
        return String.format("DA%02d", maxId);
    }

    public DTO.DuAn_DTO getChiTietDuAn(String maDA) {
        return dao.layThongTinDuAn(maDA);
    }

    public boolean capNhatDuAn(DTO.DuAn_DTO DA) {
        return dao.capNhatDuAn(DA);
    }

    public boolean themDuAn(DTO.DuAn_DTO da) {
        DAO.DuAnDAO dao = new DAO.DuAnDAO();
        return dao.themDuAn(da);
    }

    public boolean xoaDuAn(String maDA) {
        DAO.DuAnDAO dao = new DAO.DuAnDAO();
        return dao.xoaDuAn(maDA);
    }

    public boolean capNhatTrangThaiDuAn(DTO.DuAn_DTO da) {
        DAO.DuAnDAO dao = new DAO.DuAnDAO();
        return dao.capNhatTrangThaiDuAn(da);
    }

    public boolean suaThongTin(DuAn_DTO dto) {
        DuAnDAO dao = new DuAnDAO();
        return dao.capNhatDuAn(dto);
    }

}