package GUI.QuanLy_component;

import BUS.NhanVienBUS;
import BUS.PhanCongBUS;
import BUS.ChiNhanhBUS;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import DTO.ChiNhanh_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.table.TableRowSorter;
import java.text.DecimalFormat;

public class NhanVienRanhForm extends javax.swing.JPanel {

    private NhanVienBUS nvBUS = new NhanVienBUS();
    private PhanCongBUS pcBUS = new PhanCongBUS();
    private ChiNhanhBUS cnBUS = new ChiNhanhBUS();

    private JTable tblNhanVien;
    private DefaultTableModel model;
    private JTextField txtMaNV, txtHoTen, txtLuong, txtTimKiem, txtSoDALoc;
    private JComboBox<String> cbVaiTro, cbPhongBan, cbChiNhanh, cbTinhTrang;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnLocDA;
    private JLabel lblThongKe;

    public NhanVienRanhForm() {
        initComponents();
        loadDataToComboBox(); // Tải dữ liệu cho các ComboBox
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL NHẬP LIỆU ---
        JPanel pnlTop = new JPanel(new BorderLayout(5, 5));
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên chi tiết"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaNV = new JTextField(15);
        txtMaNV.setEditable(false); // Khóa không cho nhập, chỉ hiển thị mã tự sinh
        txtMaNV.setFocusable(false);

        txtHoTen = new JTextField(15);
        txtLuong = new JTextField("0", 15);

        cbVaiTro = new JComboBox<>(new String[] { "NhanVien", "QuanLy", "Sep" });
        cbPhongBan = new JComboBox<>();
        cbChiNhanh = new JComboBox<>();
        cbTinhTrang = new JComboBox<>(new String[] { "DangLamViec", "DaNghiViec" });

        // Đổ dữ liệu vào GridBagLayout
        addComp(pnlInput, "Mã NV:", txtMaNV, 0, 0, gbc);
        addComp(pnlInput, "Họ Tên:", txtHoTen, 2, 0, gbc);
        addComp(pnlInput, "Lương:", txtLuong, 0, 1, gbc);
        addComp(pnlInput, "Vai Trò:", cbVaiTro, 2, 1, gbc);
        addComp(pnlInput, "Chi Nhánh:", cbChiNhanh, 0, 2, gbc);
        addComp(pnlInput, "Phòng Ban:", cbPhongBan, 2, 2, gbc);
        addComp(pnlInput, "Trạng Thái:", cbTinhTrang, 0, 3, gbc);

        // --- 2. PANEL ĐIỀU KHIỂN ---
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        btnThem = new JButton("Thêm mới");
        btnSua = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        txtTimKiem = new JTextField(10);
        txtSoDALoc = new JTextField(3);
        btnLocDA = new JButton("Lọc Số DA");

        pnlActions.add(btnThem);
        pnlActions.add(btnSua);
        pnlActions.add(btnXoa);
        pnlActions.add(btnLamMoi);
        pnlActions.add(new JLabel("| Tìm kiếm:"));
        pnlActions.add(txtTimKiem);
        pnlActions.add(new JLabel("| Số DA:"));
        pnlActions.add(txtSoDALoc);
        pnlActions.add(btnLocDA);

        pnlTop.add(pnlInput, BorderLayout.NORTH);
        pnlTop.add(pnlActions, BorderLayout.CENTER);

        // --- 3. BẢNG DỮ LIỆU ---
        String[] headers = { "STT", "Mã NV", "Họ Tên", "Lương", "Phòng Ban", "Vai Trò", "Số DA", "Trạng Thái" };
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblNhanVien = new JTable(model);
        tblNhanVien.setRowHeight(30);

        // --- 4. SỰ KIỆN ---
        tblNhanVien.getSelectionModel().addListSelectionListener(e -> {
            int row = tblNhanVien.getSelectedRow();
            if (row >= 0)
                fillForm(row);
        });

        cbChiNhanh.addActionListener(e -> updatePhongBanCombo()); // Đổi chi nhánh thì đổi list phòng ban
        btnThem.addActionListener(e -> actionThem());
        btnSua.addActionListener(e -> actionSua());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        btnLocDA.addActionListener(e -> actionLocTheoSoDA());
        btnXoa.addActionListener(e -> actionXoa());

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actionTimKiem();
            }
        });

        add(pnlTop, BorderLayout.NORTH);
        add(new JScrollPane(tblNhanVien), BorderLayout.CENTER);

        lblThongKe = new JLabel("Tổng cộng: 0 nhân viên");
        add(lblThongKe, BorderLayout.SOUTH);
    }

    private void loadDataToComboBox() {
        cbChiNhanh.removeAllItems();
        ArrayList<ChiNhanh_DTO> dscn = cnBUS.layToanBoChiNhanh();
        for (ChiNhanh_DTO cn : dscn) {
            cbChiNhanh.addItem(cn.getMaCN().trim());
        }
        updatePhongBanCombo();
    }

    private void updatePhongBanCombo() {
        cbPhongBan.removeAllItems();
        String maCN = cbChiNhanh.getSelectedItem() != null ? cbChiNhanh.getSelectedItem().toString() : "";
        ArrayList<String> dspb = nvBUS.getListMaPB(maCN);
        for (String pb : dspb) {
            cbPhongBan.addItem(pb.trim());
        }
    }

    private void loadDataToTable() {
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        model.setRowCount(0);
        ArrayList<NhanVien_DTO> allNV = nvBUS.layDanhSachNhanVien();

        // Kiểm tra xem allNV có dữ liệu không
        if (allNV == null || allNV.isEmpty()) {
            System.out.println("DEBUG: Danh sách nhân viên trống!");
            return;
        }

        Map<String, Long> mapSoDA = pcBUS.layToanBoPhanCong().stream()
                .collect(Collectors.groupingBy(pc -> pc.getMaNV().trim(), Collectors.counting()));

        int stt = 1;
        for (NhanVien_DTO nv : allNV) {
            String maNV = nv.getMaNV().trim();
            long soDA = mapSoDA.getOrDefault(maNV, 0L);

            // Đảm bảo nv.getLuong() trả về giá trị > 0
            model.addRow(new Object[] {
                    stt++,
                    maNV,
                    nv.getHoTen(),
                    df.format(nv.getLuong()),
                    nv.getMaPB(),
                    nv.getVaiTro(),
                    soDA,
                    nv.getTinhTrang() ? "Đang làm việc" : "Đã nghỉ việc"
            });
        }
        lblThongKe.setText("Tổng cộng: " + allNV.size() + " nhân viên");
    }

    private void fillForm(int row) {
        txtMaNV.setText(model.getValueAt(row, 1).toString());
        txtHoTen.setText(model.getValueAt(row, 2).toString());
        txtLuong.setText(model.getValueAt(row, 3).toString());

        String maPB = model.getValueAt(row, 4).toString();
        String vaiTro = model.getValueAt(row, 5).toString();
        String tinhTrang = model.getValueAt(row, 7).toString();

        cbVaiTro.setSelectedItem(vaiTro);
        cbPhongBan.setSelectedItem(maPB);
        cbTinhTrang.setSelectedItem(tinhTrang);

        // Tìm chi nhánh của phòng ban này (dựa trên dữ liệu BUS)
        NhanVien_DTO detail = nvBUS.getChiTietNhanVien(txtMaNV.getText());
        if (detail != null)
            cbChiNhanh.setSelectedItem(detail.getMaCN().trim());
    }

    private void actionThem() {
        NhanVien_DTO nv = new NhanVien_DTO();
        nv.setMaNV(nvBUS.taoMaNVMoi());
        nv.setHoTen(txtHoTen.getText().trim());
        nv.setLuong(Double.parseDouble(txtLuong.getText().trim()));

        nv.setVaiTro(cbVaiTro.getSelectedItem().toString());

        nv.setMaPB(cbPhongBan.getSelectedItem().toString());
        nv.setMaCN(cbChiNhanh.getSelectedItem().toString());
        nv.setTinhTrang(true);

        nv.setNgaySinh(new java.util.Date());
        nv.setMatKhau("123");

        if (nvBUS.themNhanVien(nv)) {
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
            loadDataToTable();
            lamMoiForm();
        }
    }

    private void actionSua() {
        String maNV = txtMaNV.getText();
        NhanVien_DTO nv = nvBUS.getChiTietNhanVien(maNV);
        if (nv != null) {
            nv.setHoTen(txtHoTen.getText().trim());
            nv.setLuong(Double.parseDouble(txtLuong.getText()));
            nv.setMaPB(cbPhongBan.getSelectedItem().toString());
            nv.setMaCN(cbChiNhanh.getSelectedItem().toString());
            nv.setVaiTro(cbVaiTro.getSelectedItem().toString());
            nv.setTinhTrang(true);

            if (nvBUS.capNhatNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataToTable();
            }
        }
    }

    private void actionXoa() {
        String ma = txtMaNV.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy chọn nhân viên cần cho nghỉ!");
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn chuyển trạng thái nhân viên " + ma + " thành 'Đã nghỉ việc'?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (opt == JOptionPane.YES_OPTION) {
            if (nvBUS.choNghiViec(ma)) {
                JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ việc!");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái!");
            }
        }
    }

    private void lamMoiForm() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtLuong.setText("0");
        txtTimKiem.setText("");
        txtSoDALoc.setText("");
        tblNhanVien.setRowSorter(null);
        loadDataToTable();
    }

    private void actionTimKiem() {
        String key = txtTimKiem.getText().toLowerCase().trim();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + key));
    }

    private void actionLocTheoSoDA() {
        try {
            int soDA = Integer.parseInt(txtSoDALoc.getText().trim());
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tblNhanVien.setRowSorter(sorter);
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    return Integer.parseInt(entry.getStringValue(6)) == soDA;
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nhập số hợp lệ!");
        }
    }

    private void addComp(JPanel p, String l, Component c, int x, int y, GridBagConstraints g) {
        g.gridx = x;
        g.gridy = y;
        g.weightx = 0;
        p.add(new JLabel(l), g);
        g.gridx = x + 1;
        g.weightx = 0.5;
        p.add(c, g);
    }
}