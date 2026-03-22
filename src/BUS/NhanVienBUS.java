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
}