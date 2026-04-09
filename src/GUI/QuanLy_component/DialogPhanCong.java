package GUI.QuanLy_component;

import BUS.DuAnBUS;
import BUS.NhanVienBUS;
import BUS.PhanCongBUS;
import BUS.PhongBanBUS;
import DTO.DuAn_DTO;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import DTO.PhongBan_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class DialogPhanCong extends JDialog {
    private JComboBox<String> cbDuAn, cbPhongBan;
    private JTable tblNhanVien;
    private DefaultTableModel modelNV;
    private JTextField txtVaiTroChung;
    private JButton btnLuu, btnHuy;

    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DuAnBUS daBUS = new DuAnBUS();
    private PhanCongBUS pcBUS = new PhanCongBUS();
    private PhongBanBUS pbBUS = new PhongBanBUS();

    public DialogPhanCong(Frame parent) {
        super(parent, "Phân công nhân viên hàng loạt", true);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- TOP: PANEL ĐIỀU KHIỂN ---
        JPanel pnlTop = new JPanel(new GridBagLayout());
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 1: Chọn dự án
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlTop.add(new JLabel("Chọn Dự Án:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbDuAn = new JComboBox<>();
        pnlTop.add(cbDuAn, gbc);

        // Dòng 2: Vai trò chung
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlTop.add(new JLabel("Vai trò chung:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtVaiTroChung = new JTextField("Thành viên");
        pnlTop.add(txtVaiTroChung, gbc);

        // Dòng 3: CHỌN NHANH THEO PHÒNG BAN
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        pnlTop.add(new JLabel("Chọn theo Phòng:"), gbc);
        gbc.gridx = 1;
        cbPhongBan = new JComboBox<>();
        pnlTop.add(cbPhongBan, gbc);

        // --- CENTER: BẢNG NHÂN VIÊN ---
        modelNV = new DefaultTableModel(new Object[] { "Chọn", "Mã NV", "Họ Tên", "Phòng Ban" }, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0;
            }
        };
        tblNhanVien = new JTable(modelNV);
        tblNhanVien.setRowHeight(30);

        // --- BOTTOM: NÚT BẤM ---
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Xác nhận phân công");
        btnHuy = new JButton("Hủy bỏ");
        pnlBtns.add(btnLuu);
        pnlBtns.add(btnHuy);

        add(pnlTop, BorderLayout.NORTH);
        add(new JScrollPane(tblNhanVien), BorderLayout.CENTER);
        add(pnlBtns, BorderLayout.SOUTH);

        // --- SỰ KIỆN ---
        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> thucHienPhanCong());

        // Sự kiện chọn nhanh phòng ban
        cbPhongBan.addActionListener(e -> tuDongCheckTheoPhongBan());
    }

    private void loadData() {
        // 1. Load Dự án
        cbDuAn.removeAllItems();
        cbDuAn.addItem("--- Chọn dự án ---");
        ArrayList<DuAn_DTO> listDA = daBUS.layToanBoDuAn();
        if (listDA != null) {
            for (DuAn_DTO da : listDA) {
                if ("DangChay".equals(da.getTrangThai())) {
                    cbDuAn.addItem(da.getMaDA() + " - " + da.getTenDA());
                }
            }
        }

        // 2. Load Phòng Ban vào ComboBox
        cbPhongBan.removeAllItems();
        cbPhongBan.addItem("--- Chọn phòng để tích nhanh ---");
        ArrayList<PhongBan_DTO> listPB = pbBUS.layToanBoPhongBan();
        if (listPB != null) {
            for (PhongBan_DTO pb : listPB) {
                cbPhongBan.addItem(pb.getMaPB() + " - " + pb.getTenPB());
            }
        }

        // 3. Load danh sách Nhân viên lên bảng
        modelNV.setRowCount(0);
        ArrayList<NhanVien_DTO> listNV = nvBUS.timKiem("");

        for (NhanVien_DTO nv : listNV) {
            // Kiểm tra MaPB của nhân viên có tồn tại không
            String maPB = nv.getMaPB();
            String tenPB = "Chưa xác định";

            if (maPB != null && !maPB.trim().isEmpty()) {
                tenPB = pbBUS.getTenPhongBan(maPB); // Lấy tên từ BUS
            }

            modelNV.addRow(new Object[] {
                    false,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    (tenPB != null) ? tenPB : "Trống tên" // Đảm bảo cột index 3 có giá trị
            });
        }
    }

    private void tuDongCheckTheoPhongBan() {
        if (cbPhongBan.getSelectedIndex() <= 0)
            return;

        // Ví dụ: "PB01 - Phòng Nhân Sự" -> lấy "Phòng Nhân Sự"
        String selectedItem = cbPhongBan.getSelectedItem().toString();
        String tenPB_Combo = "";
        if (selectedItem.contains("-")) {
            tenPB_Combo = selectedItem.split("-")[1].trim();
        } else {
            tenPB_Combo = selectedItem.trim();
        }

        for (int i = 0; i < modelNV.getRowCount(); i++) {
            // Cột index 3 là cột "Phòng Ban" hiển thị tên
            String tenPBTrongBang = modelNV.getValueAt(i, 3).toString().trim();

            if (tenPBTrongBang.equalsIgnoreCase(tenPB_Combo)) {
                modelNV.setValueAt(true, i, 0); // Tích chọn cột checkbox
            } else {
                modelNV.setValueAt(false, i, 0); // Bỏ tích những người khác
            }
        }
    }

    private void thucHienPhanCong() {
        if (cbDuAn.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dự án!");
            return;
        }

        String maDA = cbDuAn.getSelectedItem().toString().split(" - ")[0];
        String vaiTro = txtVaiTroChung.getText().trim();
        StringBuilder errorLimit = new StringBuilder();
        boolean hasSelected = false;

        // BƯỚC 1: KIỂM TRA LỖI TRƯỚC
        for (int i = 0; i < modelNV.getRowCount(); i++) {
            Boolean isSelected = (Boolean) modelNV.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                hasSelected = true;
                String maNV = modelNV.getValueAt(i, 1).toString().trim();
                String tenNV = modelNV.getValueAt(i, 2).toString().trim();

                if (pcBUS.isOverLimit(maNV)) {
                    errorLimit.append("- ").append(tenNV).append(" (").append(maNV).append(")\n");
                }
            }
        }

        if (!hasSelected) {
            JOptionPane.showMessageDialog(this, "Vui lòng tích chọn ít nhất một nhân viên!");
            return;
        }

        // Nếu có bất kỳ ai bị lỗi 3 dự án -> Dừng lại ngay
        if (errorLimit.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Không thể phân công. Các nhân viên sau đã làm đủ 3 dự án:\n" + errorLimit.toString(),
                    "Lỗi giới hạn", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // BƯỚC 2: THỰC HIỆN LƯU VÀO DB
        int countSuccess = 0;
        StringBuilder errorDuplicate = new StringBuilder();

        for (int i = 0; i < modelNV.getRowCount(); i++) {
            Boolean isSelected = (Boolean) modelNV.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                String maNV = modelNV.getValueAt(i, 1).toString().trim();
                String tenNV = modelNV.getValueAt(i, 2).toString().trim();

                PhanCong_DTO pc = new PhanCong_DTO(maNV, maDA, new Date(), null, vaiTro, 0);

                if (pcBUS.themPhanCong(pc)) {
                    countSuccess++;
                } else {
                    errorDuplicate.append("- ").append(tenNV).append("\n");
                }
            }
        }

        // BƯỚC 3: TỔNG KẾT
        if (errorDuplicate.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Các nhân viên sau đã có trong dự án nên không thêm trùng:\n" + errorDuplicate.toString());
        }

        if (countSuccess > 0) {
            JOptionPane.showMessageDialog(this, "Phân công thành công cho " + countSuccess + " nhân viên.");
            dispose();
        }
    }
}