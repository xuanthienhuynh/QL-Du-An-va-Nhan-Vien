package GUI.QuanLy_component;

import BUS.DuAnBUS;
import BUS.PhanCongBUS;
import BUS.NhanVienBUS;
import BUS.ChiNhanhBUS; // Thêm BUS Chi Nhánh
import DTO.DuAn_DTO;
import DTO.PhanCong_DTO;
import DTO.NhanVien_DTO;
import DTO.ChiNhanh_DTO; // Thêm DTO Chi Nhánh
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuanLyDuAnForm extends JPanel {
    private DuAnBUS daBUS = new DuAnBUS();
    private PhanCongBUS pcBUS = new PhanCongBUS();
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private ChiNhanhBUS cnBUS = new ChiNhanhBUS(); // Khởi tạo BUS Chi Nhánh
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private JTable tblDuAn;
    private DefaultTableModel model;
    private JTextField txtMaDA, txtTenDA, txtKinhPhi, txtDoanhThu, txtTimKiem;
    private JComboBox<String> cbMaCN; // Đổi từ JTextField sang JComboBox
    private JSpinner spinNgayBD, spinNgayKT, spinLocBD, spinLocKT;
    private JComboBox<String> cbTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnLoc;

    public QuanLyDuAnForm() {
        initComponents();
        loadDataToTable();
        loadChiNhanhToComboBox(); // Tải danh sách chi nhánh khi mở form
        lamMoiForm();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- 1. PANEL NHẬP LIỆU ---
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Dự án chi tiết"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaDA = new JTextField();
        txtMaDA.setEditable(false);
        txtTenDA = new JTextField();
        txtKinhPhi = new JTextField("0");
        txtDoanhThu = new JTextField("0");

        // Khởi tạo ComboBox Mã Chi Nhánh
        cbMaCN = new JComboBox<>();
        cbTrangThai = new JComboBox<>(new String[] { "DangChay", "KetThuc" });

        spinNgayBD = new JSpinner(new SpinnerDateModel());
        spinNgayBD.setEditor(new JSpinner.DateEditor(spinNgayBD, "dd/MM/yyyy"));
        spinNgayKT = new JSpinner(new SpinnerDateModel());
        spinNgayKT.setEditor(new JSpinner.DateEditor(spinNgayKT, "dd/MM/yyyy"));

        addComp(pnlInput, "Mã DA:", txtMaDA, 0, 0, gbc);
        addComp(pnlInput, "Tên DA:", txtTenDA, 2, 0, gbc);
        addComp(pnlInput, "Kinh Phí:", txtKinhPhi, 0, 1, gbc);
        addComp(pnlInput, "Doanh Thu:", txtDoanhThu, 2, 1, gbc);
        addComp(pnlInput, "Ngày Bắt Đầu:", spinNgayBD, 0, 2, gbc);
        addComp(pnlInput, "Ngày Kết Thúc:", spinNgayKT, 2, 2, gbc);
        addComp(pnlInput, "Trạng Thái:", cbTrangThai, 0, 3, gbc);
        addComp(pnlInput, "Chi Nhánh:", cbMaCN, 2, 3, gbc); // Đổi nhãn thành Chi Nhánh

        // --- 2. PANEL ĐIỀU KHIỂN & LỌC ---
        JPanel pnlControl = new JPanel(new GridLayout(2, 1));
        JPanel pnlCRUD = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        btnThem = new JButton("Thêm mới");
        btnSua = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        pnlCRUD.add(btnThem);
        pnlCRUD.add(btnSua);
        pnlCRUD.add(btnXoa);
        pnlCRUD.add(btnLamMoi);

        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        txtTimKiem = new JTextField(12);
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.set(2000, 0, 1);
        Date farPast = cal.getTime();

        spinLocBD = new JSpinner(new SpinnerDateModel(farPast, null, null, Calendar.DAY_OF_MONTH));
        spinLocBD.setEditor(new JSpinner.DateEditor(spinLocBD, "dd/MM/yyyy"));
        spinLocKT = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH));
        spinLocKT.setEditor(new JSpinner.DateEditor(spinLocKT, "dd/MM/yyyy"));
        btnLoc = new JButton("Tìm & Lọc");

        pnlFilter.add(new JLabel("Tìm tên/mã:"));
        pnlFilter.add(txtTimKiem);
        pnlFilter.add(new JLabel("Từ:"));
        pnlFilter.add(spinLocBD);
        pnlFilter.add(new JLabel("Đến:"));
        pnlFilter.add(spinLocKT);
        pnlFilter.add(btnLoc);

        pnlControl.add(pnlCRUD);
        pnlControl.add(pnlFilter);

        // --- 3. BẢNG DỮ LIỆU ---
        String[] headers = { "Mã DA", "Tên DA", "Kinh Phí", "Doanh Thu", "Ngày BD", "Ngày KT", "Trạng Thái", "Mã CN",
                "Số NV", "Chi tiết" };
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 9;
            }
        };
        tblDuAn = new JTable(model);
        tblDuAn.setRowHeight(35);
        tblDuAn.setShowGrid(true);
        tblDuAn.setGridColor(new Color(150, 150, 150));
        tblDuAn.setIntercellSpacing(new Dimension(1, 1));
        tblDuAn.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        tblDuAn.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
        tblDuAn.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox()));

        // --- 4. SỰ KIỆN ---
        btnThem.addActionListener(e -> actionThem());
        btnSua.addActionListener(e -> actionSua());
        btnXoa.addActionListener(e -> actionXoa());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        btnLoc.addActionListener(e -> actionLocNangCao());

        tblDuAn.getSelectionModel().addListSelectionListener(e -> {
            int row = tblDuAn.getSelectedRow();
            if (row >= 0)
                fillForm(row);
        });

        add(pnlNorthWrap(pnlInput, pnlControl), BorderLayout.NORTH);
        add(new JScrollPane(tblDuAn), BorderLayout.CENTER);
    }

    // --- LOGIC HỖ TRỢ COMBOBOX ---
    private void loadChiNhanhToComboBox() {
        cbMaCN.removeAllItems();
        cbMaCN.addItem("--- Chọn chi nhánh ---");
        ArrayList<ChiNhanh_DTO> dscn = cnBUS.layToanBoChiNhanh();
        if (dscn != null) {
            for (ChiNhanh_DTO cn : dscn) {
                cbMaCN.addItem(cn.getMaCN() + " - " + cn.getTenCN());
            }
        }
    }

    private void setSelectedChiNhanh(String maCN) {
        for (int i = 0; i < cbMaCN.getItemCount(); i++) {
            if (cbMaCN.getItemAt(i).startsWith(maCN)) {
                cbMaCN.setSelectedIndex(i);
                return;
            }
        }
        cbMaCN.setSelectedIndex(0);
    }

    // --- LOGIC XỬ LÝ DỮ LIỆU ---
    private DuAn_DTO getDTOFromInput() {
        try {
            String maDA = txtMaDA.getText().trim();
            String tenDA = txtTenDA.getText().trim();
            double kinhPhi = Double.parseDouble(txtKinhPhi.getText().trim());
            double doanhThu = Double.parseDouble(txtDoanhThu.getText().trim());
            Date ngayBD = (Date) spinNgayBD.getValue();
            Date ngayKT = (Date) spinNgayKT.getValue();
            String trangThai = cbTrangThai.getSelectedItem().toString();

            // Lấy mã CN từ ComboBox
            if (cbMaCN.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh!");
                return null;
            }
            String maCN = cbMaCN.getSelectedItem().toString().split(" - ")[0];

            if (tenDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên dự án không được để trống!");
                return null;
            }
            return new DuAn_DTO(maDA, tenDA, kinhPhi, doanhThu, ngayBD, ngayKT, trangThai, maCN);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu!");
            return null;
        }
    }

    private void actionThem() {
        DuAn_DTO da = getDTOFromInput();
        if (da != null) {
            da.setMaDA(daBUS.taoMaDuAnMoi());
            if (daBUS.themDuAn(da)) {
                JOptionPane.showMessageDialog(this, "Thêm dự án thành công!");
                loadDataToTable();
                lamMoiForm();
            }
        }
    }

    private void actionSua() {
        int row = tblDuAn.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dự án cần sửa!");
            return;
        }
        DuAn_DTO da = getDTOFromInput();
        if (da != null) {
            if (daBUS.capNhatDuAn(da)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataToTable();
            }
        }
    }

    private void actionXoa() {
        int row = tblDuAn.getSelectedRow();
        if (row >= 0) {
            String ma = model.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa dự án " + ma + "?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (daBUS.xoaDuAn(ma)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa!");
                    loadDataToTable();
                    lamMoiForm();
                }
            }
        }
    }

    private void actionLocNangCao() {
        String key = txtTimKiem.getText().trim();
        Date tu = (Date) spinLocBD.getValue();
        Date den = (Date) spinLocKT.getValue();
        ArrayList<DuAn_DTO> filtered = daBUS.timKiemNangCao(key, tu, den, "Tất cả chi nhánh");
        hienThiDanhSach(filtered);
    }

    private void fillForm(int r) {
        // Kiểm tra an toàn trước khi lấy giá trị
        Object maDA = model.getValueAt(r, 0);
        Object tenDA = model.getValueAt(r, 1);
        Object kinhPhi = model.getValueAt(r, 2);
        Object doanhThu = model.getValueAt(r, 3);
        Object ngayBD = model.getValueAt(r, 4);
        Object ngayKT = model.getValueAt(r, 5);
        Object trangThai = model.getValueAt(r, 6);
        Object maCN = model.getValueAt(r, 7);

        txtMaDA.setText(maDA != null ? maDA.toString() : "");
        txtTenDA.setText(tenDA != null ? tenDA.toString() : "");
        txtKinhPhi.setText(kinhPhi != null ? kinhPhi.toString() : "0");
        txtDoanhThu.setText(doanhThu != null ? doanhThu.toString() : "0");

        try {
            if (ngayBD != null && !ngayBD.toString().isEmpty()) {
                spinNgayBD.setValue(sdf.parse(ngayBD.toString()));
            }

            if (ngayKT != null && !ngayKT.toString().equals("---") && !ngayKT.toString().isEmpty()) {
                spinNgayKT.setValue(sdf.parse(ngayKT.toString()));
            }
        } catch (Exception e) {
            // Nếu ngày lỗi, để mặc định ngày hiện tại
            spinNgayBD.setValue(new Date());
        }

        // Xử lý ComboBox Trạng thái an toàn
        if (trangThai != null) {
            cbTrangThai.setSelectedItem(trangThai.toString());
        } else {
            cbTrangThai.setSelectedIndex(0); // Mặc định chọn cái đầu tiên nếu null
        }

        // Xử lý ComboBox Chi Nhánh an toàn
        if (maCN != null) {
            setSelectedChiNhanh(maCN.toString());
        } else {
            cbMaCN.setSelectedIndex(0);
        }
    }

    private void lamMoiForm() {
        txtMaDA.setText(daBUS.taoMaDuAnMoi());
        txtTenDA.setText("");
        txtKinhPhi.setText("0");
        txtDoanhThu.setText("0");
        cbMaCN.setSelectedIndex(0);
        spinNgayBD.setValue(new Date());
        spinNgayKT.setValue(new Date());
        cbTrangThai.setSelectedIndex(0);
        txtTimKiem.setText("");
        loadDataToTable();
        loadChiNhanhToComboBox(); // Làm mới luôn danh sách chi nhánh
    }

    private void hienThiDanhSach(ArrayList<DuAn_DTO> list) {
        model.setRowCount(0);
        ArrayList<PhanCong_DTO> listPC = pcBUS.layToanBoPhanCong();
        if (list != null) {
            for (DuAn_DTO da : list) {
                long soNV = listPC.stream().filter(pc -> pc.getMaDA().trim().equalsIgnoreCase(da.getMaDA().trim()))
                        .count();
                model.addRow(new Object[] {
                        da.getMaDA(), da.getTenDA(), da.getKinhPhi(), da.getDoanhThu(),
                        da.getNgayBatDau() != null ? sdf.format(da.getNgayBatDau()) : "",
                        da.getNgayKetThuc() != null ? sdf.format(da.getNgayKetThuc()) : "---",
                        da.getTrangThai(), da.getMaCN(), soNV, "Xem NV"
                });
            }
        }
    }

    private void loadDataToTable() {
        hienThiDanhSach(daBUS.layToanBoDuAn());
    }

    // --- RENDERER & EDITOR NÚT ---
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Xem NV");
        }

        @Override
        public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton btn;
        private String currentMaDA;

        public ButtonEditor(JCheckBox chk) {
            super(chk);
            btn = new JButton("Xem NV");
            btn.addActionListener(e -> showNhanVienPopup(currentMaDA));
        }

        @Override
        public Component getTableCellEditorComponent(JTable t, Object v, boolean s, int r, int c) {
            currentMaDA = t.getValueAt(r, 0).toString();
            return btn;
        }
    }

    private void showNhanVienPopup(String maDA) {
        JDialog dialog = new JDialog((Frame) null, "Danh sách nhân viên dự án: " + maDA, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        String[] cols = { "Mã NV", "Tên Nhân Viên", "Vai Trò Đảm Nhiệm" };
        DefaultTableModel m = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        pcBUS.layToanBoPhanCong().stream()
                .filter(pc -> pc.getMaDA().trim().equalsIgnoreCase(maDA.trim()))
                .forEach(pc -> {
                    NhanVien_DTO nv = nvBUS.getChiTietNhanVien(pc.getMaNV());
                    m.addRow(new Object[] { pc.getMaNV(), (nv != null ? nv.getHoTen() : "N/A"), pc.getVaiTroDuAn() });
                });

        JTable table = new JTable(m);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(150, 150, 150));
        table.setIntercellSpacing(new Dimension(1, 1));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(scroll, BorderLayout.CENTER);

        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dialog.dispose());
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottom.add(btnClose);
        dialog.add(pnlBottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void addComp(JPanel p, String l, Component c, int x, int y, GridBagConstraints g) {
        g.gridx = x;
        g.gridy = y;
        g.weightx = 0;
        p.add(new JLabel(l), g);
        g.gridx = x + 1;
        g.gridy = y;
        g.weightx = 0.5;
        p.add(c, g);
    }

    private JPanel pnlNorthWrap(JPanel p1, JPanel p2) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(p1, BorderLayout.NORTH);
        p.add(p2, BorderLayout.SOUTH);
        return p;
    }
}