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
}