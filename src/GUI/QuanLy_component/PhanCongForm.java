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

public class PhanCongForm extends JPanel {
    private PhanCongBUS pcBUS = new PhanCongBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private DuAnBUS daBUS = new DuAnBUS();

    private JTable tblPhanCong;
    private DefaultTableModel model;
    // THAY THẾ: JComboBox thành JTextField
    private JTextField txtMaNV, txtMaDA, txtVaiTro;
    private JButton btnThem, btnXoa, btnLamMoi;

    public PhanCongForm() {
        initComponents();
        loadDataToTable();
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
        pnlInput.add(new JLabel("Mã Nhân viên:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        txtMaNV = new JTextField(); // JTextField thay cho ComboBox
        pnlInput.add(txtMaNV, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        pnlInput.add(new JLabel("Mã Dự án:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;
        txtMaDA = new JTextField(); // JTextField thay cho ComboBox
        pnlInput.add(txtMaDA, gbc);

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

        // --- 3. BẢNG DỮ LIỆU ---
        model = new DefaultTableModel(new Object[] { "Mã Nhân Viên", "Mã Dự Án", "Vai Trò Đảm Nhiệm" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPhanCong = new JTable(model);
        tblPhanCong.setRowHeight(32);
        tblPhanCong.setShowGrid(true);
        tblPhanCong.setGridColor(new Color(150, 150, 150));

        JScrollPane scroll = new JScrollPane(tblPhanCong);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlBtns, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // --- 4. XỬ LÝ SỰ KIỆN ---
        btnThem.addActionListener(e -> actionThem());
        btnXoa.addActionListener(e -> actionXoa());
        btnLamMoi.addActionListener(e -> lamMoi());

        tblPhanCong.getSelectionModel().addListSelectionListener(e -> {
            int row = tblPhanCong.getSelectedRow();
            if (row >= 0) {
                // Đổ ngược dữ liệu từ bảng vào TextField khi click dòng
                txtMaNV.setText(model.getValueAt(row, 0).toString());
                txtMaDA.setText(model.getValueAt(row, 1).toString());
                txtVaiTro.setText(model.getValueAt(row, 2).toString());

                // Khóa MaNV và MaDA khi đang chọn dòng (vì Phân công thường không sửa khóa
                // chính)
                txtMaNV.setEditable(false);
                txtMaDA.setEditable(false);
            }
        });
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
        // Nếu bạn muốn dùng Dialog nhập hàng loạt như cũ:
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        Frame parentFrame = (parentWindow instanceof Frame) ? (Frame) parentWindow : null;

        DialogPhanCong diag = new DialogPhanCong(parentFrame);
        diag.setVisible(true);

        loadDataToTable();
        lamMoi();
    }

    private void actionXoa() {
        int row = tblPhanCong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            return;
        }

        String maNV = txtMaNV.getText().trim();
        String maDA = txtMaDA.getText().trim();

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
        txtMaNV.setText("");
        txtMaDA.setText("");
        txtVaiTro.setText("");
        txtMaNV.setEditable(true);
        txtMaDA.setEditable(true);
        loadDataToTable();
    }
}