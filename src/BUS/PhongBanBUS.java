package BUS;

import DAO.PhongBanDAO;
import DTO.PhongBan_DTO;
import java.util.ArrayList;

public class PhongBanBUS {
    private PhongBanDAO pbDAO = new PhongBanDAO();

    // 1. Lấy toàn bộ danh sách phòng ban
    public ArrayList<PhongBan_DTO> layToanBoPhongBan() {
        return pbDAO.layToanBoPhongBan();
    }

    // 2. Lấy danh sách phòng ban theo chi nhánh
    public ArrayList<PhongBan_DTO> layPhongBanTheoChiNhanh(String maCN) {
        if (maCN == null || maCN.isEmpty()) {
            return new ArrayList<>();
        }
        return pbDAO.layPhongBanTheoChiNhanh(maCN);
    }

    // 3. Lấy tên phòng ban từ mã (Rất hữu ích để hiển thị lên Table)
    public String getTenPhongBan(String maPB) {
        if (maPB == null || maPB.trim().isEmpty()) {
            return "Trống mã";
        }
        String ten = pbDAO.layTenPhongBan(maPB.trim());
        return (ten != null && !ten.isEmpty()) ? ten : "Không tìm thấy";
    }

    // 4. Kiểm tra và thêm phòng ban
    public String themPhongBan(PhongBan_DTO pb) {
        // Kiểm tra logic trước khi gọi DAO
        if (pb.getMaPB().isEmpty())
            return "Mã phòng ban không được để trống!";
        if (pb.getTenPB().isEmpty())
            return "Tên phòng ban không được để trống!";

        if (pbDAO.themPhongBan(pb)) {
            return "Thêm thành công!";
        } else {
            return "Thêm thất bại (Có thể trùng mã)!";
        }
    }
}