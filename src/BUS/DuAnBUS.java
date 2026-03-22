package BUS;

import DAO.DuAnDAO;
import DTO.DuAn_DTO;
import java.util.ArrayList;

public class DuAnBUS {
    private DuAnDAO dao = new DuAnDAO();

    public ArrayList<DuAn_DTO> layToanBoDuAn() {
        return dao.layDanhSachDuAn();
    }
}