package GUI.QuanLy_component;

import BUS.DuAnBUS;
import BUS.NhanVienBUS;
import BUS.PhanCongBUS;
import BUS.PhongBanBUS;
import DTO.DuAn_DTO;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class DialogPhanCong extends JDialog {
    private JComboBox<String> cbDuAn;
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
        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // --- TOP: Chọn dự án và Vai trò ---
        JPanel pnlTop = new JPanel(new GridBagLayout());
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlTop.add(new JLabel("Chọn Dự Án:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbDuAn = new JComboBox<>();
        pnlTop.add(cbDuAn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlTop.add(new JLabel("Vai trò chung:"), gbc);
        gbc.gridx = 1;
        txtVaiTroChung = new JTextField("Thành viên");
        pnlTop.add(txtVaiTroChung, gbc);

        // --- CENTER: Bảng chọn nhân viên (Có checkbox) ---
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
        tblNhanVien.setRowHeight(25);

        // --- BOTTOM: Nút bấm ---
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Xác nhận phân công");
        btnHuy = new JButton("Hủy bỏ");
        pnlBtns.add(btnLuu);
        pnlBtns.add(btnHuy);

        add(pnlTop, BorderLayout.NORTH);
        add(new JScrollPane(tblNhanVien), BorderLayout.CENTER);
        add(pnlBtns, BorderLayout.SOUTH);

        loadData();

        btnHuy.addActionListener(e -> dispose());
        btnLuu.addActionListener(e -> thucHienPhanCong());
    }

    private void loadData() {
        cbDuAn.addItem("--- Chọn dự án ---");
        for (DuAn_DTO da : daBUS.layToanBoDuAn()) {
            cbDuAn.addItem(da.getMaDA() + " - " + da.getTenDA());
        }

        for (NhanVien_DTO nv : nvBUS.timKiem("")) {
            String tenPB = pbBUS.getTenPhongBan(nv.getMaPB());

            modelNV.addRow(new Object[] {
                    false,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    tenPB
            });
        }
    }

    private void thucHienPhanCong() {
        // 1. Kiểm tra chọn dự án
        if (cbDuAn.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dự án!");
            return;
        }

        String maDA = cbDuAn.getSelectedItem().toString().split(" - ")[0];
        String vaiTro = txtVaiTroChung.getText().trim();
        StringBuilder errorLimit = new StringBuilder();
        boolean hasSelected = false;

        // --- BƯỚC 1: CHỈ KIỂM TRA GIỚI HẠN 3 DỰ ÁN ---
        // (Nếu dính lỗi này thì dừng ngay, vì đây là lỗi nghiệp vụ nặng)
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
            JOptionPane.showMessageDialog(this, "Vui lòng tích chọn nhân viên!");
            return;
        }

        // Nếu có người quá 3 dự án -> Thông báo và DỪNG HÀM (quay lại giao diện)
        if (errorLimit.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Các nhân viên sau đã tham gia tối đa 3 dự án, không thể phân công thêm:\n" + errorLimit.toString(),
                    "Lỗi giới hạn dự án", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- BƯỚC 2: THỰC HIỆN THÊM (Lúc này mới dùng đến themPhanCong) ---
        int countSuccess = 0;
        StringBuilder errorDuplicate = new StringBuilder();

        for (int i = 0; i < modelNV.getRowCount(); i++) {
            Boolean isSelected = (Boolean) modelNV.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                String maNV = modelNV.getValueAt(i, 1).toString().trim();
                String tenNV = modelNV.getValueAt(i, 2).toString().trim();

                PhanCong_DTO pc = new PhanCong_DTO(maNV, maDA, new java.util.Date(), null, vaiTro, 0);

                if (pcBUS.themPhanCong(pc)) {
                    countSuccess++;
                } else {
                    // Nếu trùng mã (đã phân công rồi), themPhanCong sẽ trả về false
                    errorDuplicate.append("- ").append(tenNV).append("\n");
                }
            }
        }

        // --- BƯỚC 3: TỔNG KẾT ---
        if (errorDuplicate.length() > 0) {
            // Thông báo những người bị trùng nhưng không đóng form để user biết mà chỉnh
            JOptionPane.showMessageDialog(this,
                    "Một số nhân viên đã có trong dự án này nên không thêm lại:\n" + errorDuplicate.toString());
        }

        if (countSuccess > 0) {
            JOptionPane.showMessageDialog(this, "Đã hoàn tất phân công cho " + countSuccess + " nhân viên mới.");
            dispose();
        }
    }
}