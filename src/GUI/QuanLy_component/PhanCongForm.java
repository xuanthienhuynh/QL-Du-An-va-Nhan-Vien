package GUI.QuanLy_component;

import BUS.DuAnBUS;
import BUS.NhanVienBUS;
import BUS.PhanCongBUS;
import DTO.DuAn_DTO;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class PhanCongForm extends JPanel {
    private PhanCongBUS pcBUS = new PhanCongBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DuAnBUS daBUS = new DuAnBUS();

    private JTable tblPhanCong;
    private DefaultTableModel model;
    private JComboBox<String> cbNhanVien, cbDuAn;
    private JTextField txtVaiTro;
    private JButton btnThem, btnXoa, btnLamMoi;

    public PhanCongForm() {
        initComponents();
        loadDataToTable();
        loadComboBoxData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- 1. PANEL NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin phân công dự án"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dòng 0: Nhân viên và Dự án
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pnlInput.add(new JLabel("Nhân viên:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        cbNhanVien = new JComboBox<>();
        pnlInput.add(cbNhanVien, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        pnlInput.add(new JLabel("Dự án:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        cbDuAn = new JComboBox<>();
        pnlInput.add(cbDuAn, gbc);

        // Dòng 1: Vai trò (Trải dài)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlInput.add(new JLabel("Vai trò:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        txtVaiTro = new JTextField();
        pnlInput.add(txtVaiTro, gbc);

        // --- 2. PANEL NÚT BẤM ---
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnThem = new JButton("Thêm Phân Công");
        btnXoa = new JButton("Xóa Phân Công");
        btnLamMoi = new JButton("Làm mới");

        Dimension btnSize = new Dimension(160, 38);
        btnThem.setPreferredSize(btnSize);
        btnXoa.setPreferredSize(btnSize);
        btnLamMoi.setPreferredSize(btnSize);

        pnlBtns.add(btnThem);
        pnlBtns.add(btnXoa);
        pnlBtns.add(btnLamMoi);

        // --- 3. BẢNG DỮ LIỆU (CHỈNH BORDER ĐẬM) ---
        model = new DefaultTableModel(new Object[] { "Mã Nhân Viên", "Mã Dự Án", "Vai Trò Đảm Nhiệm" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPhanCong = new JTable(model);

        // Cấu hình border và grid đậm nét
        tblPhanCong.setRowHeight(32);
        tblPhanCong.setShowGrid(true);
        tblPhanCong.setGridColor(new Color(150, 150, 150)); // Màu kẻ ô đậm
        tblPhanCong.setIntercellSpacing(new Dimension(1, 1));
        tblPhanCong.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPhanCong.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tblPhanCong);

        // Gom nhóm phía trên
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlBtns, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // --- 4. XỬ LÝ SỰ KIỆN ---
        btnThem.addActionListener(e -> actionThem());
        btnXoa.addActionListener(e -> actionXoa());
        btnLamMoi.addActionListener(e -> lamMoi());

        // Sự kiện click bảng để điền ngược lại form
        tblPhanCong.getSelectionModel().addListSelectionListener(e -> {
            int row = tblPhanCong.getSelectedRow();
            if (row >= 0) {
                String maNV = model.getValueAt(row, 0).toString();
                String maDA = model.getValueAt(row, 1).toString();
                String vaiTro = model.getValueAt(row, 2).toString();

                txtVaiTro.setText(vaiTro);
                setComboValue(cbNhanVien, maNV);
                setComboValue(cbDuAn, maDA);
            }
        });
    }

    // Hàm bổ trợ để set giá trị ComboBox theo Mã
    private void setComboValue(JComboBox<String> cb, String ma) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).startsWith(ma)) {
                cb.setSelectedIndex(i);
                break;
            }
        }
    }

    private void loadComboBoxData() {
        cbNhanVien.removeAllItems();
        cbDuAn.removeAllItems();
        cbNhanVien.addItem("--- Chọn nhân viên ---");
        cbDuAn.addItem("--- Chọn dự án ---");

        ArrayList<NhanVien_DTO> listNV = nvBUS.timKiem("");
        if (listNV != null) {
            for (NhanVien_DTO nv : listNV) {
                // Tùy vào DTO của bạn là getHoTen hay getTenNV
                cbNhanVien.addItem(nv.getMaNV() + " - " + nv.getHoTen());
            }
        }

        ArrayList<DuAn_DTO> listDA = daBUS.layToanBoDuAn();
        if (listDA != null) {
            for (DuAn_DTO da : listDA) {
                cbDuAn.addItem(da.getMaDA() + " - " + da.getTenDA());
            }
        }
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        ArrayList<PhanCong_DTO> list = pcBUS.layToanBoPhanCong();
        if (list != null) {
            for (PhanCong_DTO pc : list) {
                model.addRow(new Object[] { pc.getMaNV(), pc.getMaDA(), pc.getVaiTroDuAn() });
            }
        }
    }

    private void actionThem() {
        if (cbNhanVien.getSelectedIndex() <= 0 || cbDuAn.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ Nhân viên và Dự án!");
            return;
        }

        String vaiTro = txtVaiTro.getText().trim();
        if (vaiTro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập vai trò!");
            return;
        }

        try {
            String maNV = cbNhanVien.getSelectedItem().toString().split(" - ")[0];
            String maDA = cbDuAn.getSelectedItem().toString().split(" - ")[0];

            // Tạo DTO phân công (Ngày bắt đầu mặc định là hôm nay)
            PhanCong_DTO pc = new PhanCong_DTO(maNV, maDA, new Date(), null, vaiTro, 0);

            if (pcBUS.themPhanCong(pc)) {
                JOptionPane.showMessageDialog(this, "Phân công thành công!");
                loadDataToTable();
                txtVaiTro.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Nhân viên này đã có trong dự án!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void actionXoa() {
        int row = tblPhanCong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        String maNV = model.getValueAt(row, 0).toString();
        String maDA = model.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Xác nhận xóa phân công của NV " + maNV + " tại dự án " + maDA + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (pcBUS.xoaPhanCong(maNV, maDA)) {
                JOptionPane.showMessageDialog(this, "Đã xóa phân công!");
                loadDataToTable();
                lamMoi();
            }
        }
    }

    private void lamMoi() {
        txtVaiTro.setText("");
        cbNhanVien.setSelectedIndex(0);
        cbDuAn.setSelectedIndex(0);
        loadDataToTable();
    }
}