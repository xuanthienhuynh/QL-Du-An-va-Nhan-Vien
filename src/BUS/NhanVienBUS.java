package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVien_DTO;
import java.util.ArrayList;

public class NhanVienBUS {
    private NhanVienDAO dao = new NhanVienDAO();

    public ArrayList<NhanVien_DTO> timKiem(String tuKhoa) {

        if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
            return dao.layDanhSachNhanVien();
        }

        return dao.timKiemNhanVien(tuKhoa.trim());
    }

    public String taoMaNVMoi() {
        String maCuoi = dao.layMaNVCuoiCung();

        if (maCuoi == null || maCuoi.isEmpty()) {
            return "NV01";
        }

        try {
            String phanSo = maCuoi.substring(2).trim();
            int soMoi = Integer.parseInt(phanSo) + 1;

            if (soMoi < 10) {
                return "NV0" + soMoi;
            } else {
                return "NV" + soMoi;
            }
        } catch (Exception e) {

            System.out.println("Lỗi sinh mã: " + e.getMessage());
            e.printStackTrace();
            return "NV11";
        }
    }

    // Các hàm gọi thông qua DAO
    public java.util.ArrayList<String> getListMaPB(String maCN) {
        return dao.layDanhSachMaPB(maCN);
    }

    public java.util.ArrayList<String> getListMaCN() {
        return dao.layDanhSachMaCN();
    }

    public boolean themNhanVien(DTO.NhanVien_DTO nv) {
        return dao.themNhanVien(nv);
    }

    public DTO.NhanVien_DTO getChiTietNhanVien(String maNV) {
        return dao.layThongTinNhanVien(maNV);
    }

    // Gọi DAO cập nhật
    public boolean capNhatNhanVien(DTO.NhanVien_DTO nv) {
        return dao.capNhatNhanVien(nv);
    }

    // Gọi DAO cho nghỉ việc
    public boolean choNghiViec(String maNV) {
        return dao.choNghiViec(maNV);
    }

    public java.util.ArrayList<NhanVien_DTO> thongKeTopDuAn(String maCN, String maPB, String sapXep) {
        return dao.thongKeTopDuAn(maCN, maPB, sapXep);
    }

    public java.util.ArrayList<NhanVien_DTO> thongKeLuong(String maCN, String maPB, String sapXep) {
        return dao.thongKeLuongNhanVien(maCN, maPB, sapXep);
    }

    public java.util.ArrayList<String> layDuAnCuaNhanVien(String maNV) {
        return dao.layDuAnCuaNhanVien(maNV);
    }

    public java.util.ArrayList<DTO.NhanVien_DTO> timKiemVaLoc(String tuKhoa, String maCN) {
        // 1. Lấy danh sách theo từ khóa trước (Nếu không nhập gì thì nó tự lấy full)
        java.util.ArrayList<DTO.NhanVien_DTO> list = timKiem(tuKhoa);

        // 2. Lọc tiếp theo Chi Nhánh
        if (maCN == null || maCN.equals("Tất cả Chi Nhánh") || maCN.trim().isEmpty()) {
            return list;
        }

        java.util.ArrayList<DTO.NhanVien_DTO> listLoc = new java.util.ArrayList<>();
        if (list != null) {
            for (DTO.NhanVien_DTO nv : list) {
                // MẤU CHỐT SỬA LỖI: Dùng .trim() để gọt sạch khoảng trắng thừa của SQL
                if (nv.getMaCN() != null && nv.getMaCN().trim().equalsIgnoreCase(maCN.trim())) {
                    listLoc.add(nv);
                }
            }
        }
        return listLoc;
    }
}
