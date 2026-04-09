package BUS;

import DAO.PhanCongDAO;
import DTO.PhanCong_DTO;
import java.util.ArrayList;
import java.util.Date;

public class PhanCongBUS {
    private PhanCongDAO dao = new PhanCongDAO();

    // 1. LẤY TOÀN BỘ DANH SÁCH
    public ArrayList<PhanCong_DTO> layToanBoPhanCong() {
        return dao.layDanhSachPhanCong();
    }

    // 2. TÌM KIẾM THEO TỪ KHÓA (Mã NV hoặc Mã DA)
    public ArrayList<PhanCong_DTO> timKiem(String tuKhoa) {
        ArrayList<PhanCong_DTO> tatCa = layToanBoPhanCong();
        ArrayList<PhanCong_DTO> ketQua = new ArrayList<>();

        if (tatCa == null || tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return tatCa;
        }

        String keyword = tuKhoa.toLowerCase().trim();
        for (PhanCong_DTO pc : tatCa) {
            if ((pc.getMaNV() != null && pc.getMaNV().toLowerCase().contains(keyword)) ||
                    (pc.getMaDA() != null && pc.getMaDA().toLowerCase().contains(keyword))) {
                ketQua.add(pc);
            }
        }
        return ketQua;
    }

    // 3. TÌM KIẾM NÂNG CAO (Theo thời gian và Vai trò)
    public ArrayList<PhanCong_DTO> timKiemNangCao(String tuKhoa, Date tuNgay, Date denNgay, String vaiTro) {
        ArrayList<PhanCong_DTO> tatCa = layToanBoPhanCong();
        ArrayList<PhanCong_DTO> ketQua = new ArrayList<>();

        if (tatCa == null)
            return ketQua;

        for (PhanCong_DTO pc : tatCa) {
            boolean matchTuKhoa = true;
            boolean matchVaiTro = true;
            boolean matchThoiGian = true;

            // Lọc theo Mã NV / Mã DA
            if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
                String kw = tuKhoa.toLowerCase();
                matchTuKhoa = pc.getMaNV().toLowerCase().contains(kw) || pc.getMaDA().toLowerCase().contains(kw);
            }

            // Lọc theo Vai trò
            if (vaiTro != null && !vaiTro.equals("Tất cả vai trò")) {
                matchVaiTro = (pc.getVaiTroDuAn() != null && pc.getVaiTroDuAn().equals(vaiTro));
            }

            // Lọc theo Ngày tham gia (Nằm trong khoảng)
            if (tuNgay != null && pc.getNgayThamGia() != null) {
                if (pc.getNgayThamGia().before(tuNgay))
                    matchThoiGian = false;
            }
            if (denNgay != null && pc.getNgayThamGia() != null) {
                if (pc.getNgayThamGia().after(denNgay))
                    matchThoiGian = false;
            }

            if (matchTuKhoa && matchVaiTro && matchThoiGian) {
                ketQua.add(pc);
            }
        }
        return ketQua;
    }

    // 4. THÊM PHÂN CÔNG (Có kiểm tra trùng lặp)
    public boolean themPhanCong(PhanCong_DTO pc) {
        // Kiểm tra xem nhân viên này đã được phân công vào dự án này chưa
        PhanCong_DTO check = dao.layThongTinPhanCong(pc.getMaNV(), pc.getMaDA());
        if (check != null) {
            System.out.println("Lỗi: Nhân viên đã tham gia dự án này rồi!");
            return false;
        }
        return dao.themPhanCong(pc);
    }

    // 5. CẬP NHẬT THÔNG TIN
    public boolean suaThongTin(PhanCong_DTO pc) {
        return dao.capNhatPhanCong(pc);
    }

    // 6. XÓA PHÂN CÔNG
    public boolean xoaPhanCong(String maNV, String maDA) {
        return dao.xoaPhanCong(maNV, maDA);
    }

    // 7. LẤY CHI TIẾT
    public PhanCong_DTO getChiTiet(String maNV, String maDA) {
        return dao.layThongTinPhanCong(maNV, maDA);
    }
}