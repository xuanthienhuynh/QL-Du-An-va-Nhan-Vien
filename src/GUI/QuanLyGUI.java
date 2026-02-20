package GUI;

import DAO.NhanVienDAO;
import DAO.DuAnDAO;
import DAO.PhanCongDAO;
import DTO.NhanVien_DTO;
import DTO.DuAn_DTO;
import DTO.PhanCong_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuanLyGUI extends JFrame {
    
    private JTabbedPane tabbedPane;
    
    public QuanLyGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HE THONG QUAN Ly DU AN VA NHAN VIEN");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // === HEADER ===
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 0, 153));
        header.setPreferredSize(new Dimension(0, 100));
        
        JLabel tenCty = new JLabel("ABC GROUP");
        tenCty.setFont(new Font("Arial", Font.BOLD, 48));
        tenCty.setForeground(new Color(255, 102, 0));
        tenCty.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JPanel rightHeader = new JPanel();
        rightHeader.setBackground(new Color(0, 0, 153));
        rightHeader.setLayout(new BoxLayout(rightHeader, BoxLayout.Y_AXIS));
        
        JLabel xinChao = new JLabel("Xin chao Quan Ly");
        xinChao.setFont(new Font("Dialog", Font.BOLD, 14));
        xinChao.setForeground(Color.WHITE);
        xinChao.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 20));
        
        JButton btnDangXuat = new JButton("Dang Xuat");
        btnDangXuat.setFont(new Font("Dialog", Font.BOLD, 14));
        btnDangXuat.setMaximumSize(new Dimension(150, 30));
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon dang xuat?", 
                "Xac nhan", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new DangNhap().setVisible(true);
            }
        });
        
        rightHeader.add(xinChao);
        rightHeader.add(Box.createVerticalStrut(5));
        rightHeader.add(btnDangXuat);
        
        header.add(tenCty, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);
        
        add(header, BorderLayout.NORTH);
        
        // === TABBED PANE ===
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        tabbedPane.addTab("QUAN LY NHAN VIEN", new NhanVienPanel());
        tabbedPane.addTab("QUAN LY DU AN", new DuAnPanel());
        tabbedPane.addTab("QUAN LY PHAN CONG", new PhanCongPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
    }
    
    // ===================================================================
    // PANEL QUAN LY NHAN VIEN
    // ===================================================================
    class NhanVienPanel extends JPanel {
        private NhanVienDAO dao;
        private JTable table;
        private DefaultTableModel tableModel;
        private JTextField txtMaNV, txtHoTen, txtLuong, txtMatKhau, txtTimKiem;
        private JComboBox<String> cboGioiTinh, cboTinhTrang, cboVaiTro;
        private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
        
        public NhanVienPanel() {
            dao = new NhanVienDAO();
            initPanel();
            loadData();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout(10, 10));
            
            // === PANEL TOP: TIM KIEM ===
            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel lblTimKiem = new JLabel("Tim kiem:");
            txtTimKiem = new JTextField(25);
            btnTimKiem = new JButton("Tim");
            btnTimKiem.addActionListener(e -> timKiem());
            btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> loadData());
            
            panelTop.add(lblTimKiem);
            panelTop.add(txtTimKiem);
            panelTop.add(btnTimKiem);
            panelTop.add(btnLamMoi);
            add(panelTop, BorderLayout.NORTH);
            
            // === PANEL LEFT: FORM ===
            JPanel panelLeft = new JPanel();
            panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
            panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Nhan Vien"));
            panelLeft.setPreferredSize(new Dimension(350, 0));
            
            txtMaNV = new JTextField(20);
            txtHoTen = new JTextField(20);
            txtLuong = new JTextField(20);
            txtMatKhau = new JTextField(20);
            cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nu"});
            cboTinhTrang = new JComboBox<>(new String[]{"DangLamViec", "DaNghiViec"});
            cboVaiTro = new JComboBox<>(new String[]{"NhanVien", "QuanLy", "Sep"});
            
            addFormRow(panelLeft, "Ma NV:", txtMaNV);
            addFormRow(panelLeft, "Ho Ten:", txtHoTen);
            addFormRow(panelLeft, "Gioi Tinh:", cboGioiTinh);
            addFormRow(panelLeft, "Luong:", txtLuong);
            addFormRow(panelLeft, "Tinh Trang:", cboTinhTrang);
            addFormRow(panelLeft, "Vai Tro:", cboVaiTro);
            addFormRow(panelLeft, "Mat Khau:", txtMatKhau);
            
            panelLeft.add(Box.createVerticalStrut(20));
            
            // BUTTONS
            JPanel panelButtons = new JPanel(new FlowLayout());
            btnThem = createButton("THEM", new Color(46, 204, 113));
            btnSua = createButton("SUA", new Color(52, 152, 219));
            btnXoa = createButton("XOA", new Color(231, 76, 60));
            btnThem.addActionListener(e -> themNhanVien());
            btnSua.addActionListener(e -> suaNhanVien());
            btnXoa.addActionListener(e -> xoaNhanVien());
            panelButtons.add(btnThem);
            panelButtons.add(btnSua);
            panelButtons.add(btnXoa);
            panelLeft.add(panelButtons);
            
            add(panelLeft, BorderLayout.WEST);
            
            // === TABLE ===
            String[] columns = {"Ma NV", "Ho Ten", "Gioi Tinh", "Luong", "Tinh Trang", "Vai Tro", "Mat Khau"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            table = new JTable(tableModel);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    hienThiChiTiet();
                }
            });
            
            add(new JScrollPane(table), BorderLayout.CENTER);
        }
        
        private void loadData() {
            tableModel.setRowCount(0);
            ArrayList<NhanVien_DTO> list = dao.getAllNhanVien();
            for (NhanVien_DTO nv : list) {
                tableModel.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh(),
                    String.format("%,.0f", nv.getLuong()), nv.getTinhTrang(),
                    nv.getVaiTro(), nv.getMatKhau()
                });
            }
        }
        
        private void hienThiChiTiet() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaNV.setText(table.getValueAt(row, 0).toString());
                txtMaNV.setEnabled(false);
                txtHoTen.setText(table.getValueAt(row, 1).toString());
                cboGioiTinh.setSelectedItem(table.getValueAt(row, 2).toString());
                txtLuong.setText(table.getValueAt(row, 3).toString().replaceAll(",", ""));
                cboTinhTrang.setSelectedItem(table.getValueAt(row, 4).toString());
                cboVaiTro.setSelectedItem(table.getValueAt(row, 5).toString());
                txtMatKhau.setText(table.getValueAt(row, 6).toString());
            }
        }
        
        private void themNhanVien() {
            try {
                String maNV = txtMaNV.getText().trim();
                String hoTen = txtHoTen.getText().trim();
                String luongStr = txtLuong.getText().trim();
                String matKhau = txtMatKhau.getText().trim();
                
                if (maNV.isEmpty() || hoTen.isEmpty() || luongStr.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
                    return;
                }
                
                NhanVien_DTO nv = new NhanVien_DTO();
                nv.setMaNV(maNV);
                nv.setHoTen(hoTen);
                nv.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
                nv.setDcTinh("Ha Noi");
                nv.setDcPhuong("Cau Giay");
                nv.setDcSoNha("So 1");
                nv.setNgaySinh(new Date());
                nv.setLuong(Double.parseDouble(luongStr));
                nv.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
                nv.setMatKhau(matKhau);
                nv.setMaPB("PB_IT_HN");
                nv.setMaCN("CN_HN");
                
                if (dao.themNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this, "Them thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Them that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void suaNhanVien() {
            try {
                String maNV = txtMaNV.getText().trim();
                if (maNV.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long chon nhan vien!");
                    return;
                }
                
                NhanVien_DTO nv = dao.getNhanVienByMa(maNV);
                if (nv == null) {
                    JOptionPane.showMessageDialog(this, "Khong tim thay!");
                    return;
                }
                
                nv.setHoTen(txtHoTen.getText().trim());
                nv.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
                nv.setLuong(Double.parseDouble(txtLuong.getText().trim()));
                nv.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
                nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
                nv.setMatKhau(txtMatKhau.getText().trim());
                
                if (dao.suaNhanVien(nv)) {
                    JOptionPane.showMessageDialog(this, "Sua thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sua that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void xoaNhanVien() {
            String maNV = txtMaNV.getText().trim();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Xoa nhan vien " + maNV + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION && dao.xoaNhanVien(maNV)) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                loadData();
                clearForm();
            }
        }
        
        private void timKiem() {
            String tuKhoa = txtTimKiem.getText().trim();
            if (tuKhoa.isEmpty()) {
                loadData();
                return;
            }
            
            tableModel.setRowCount(0);
            ArrayList<NhanVien_DTO> list = dao.timKiemNhanVien(tuKhoa);
            for (NhanVien_DTO nv : list) {
                tableModel.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getGioiTinh(),
                    String.format("%,.0f", nv.getLuong()), nv.getTinhTrang(),
                    nv.getVaiTro(), nv.getMatKhau()
                });
            }
        }
        
        private void clearForm() {
            txtMaNV.setText("");
            txtMaNV.setEnabled(true);
            txtHoTen.setText("");
            txtLuong.setText("");
            txtMatKhau.setText("");
            table.clearSelection();
        }
    }
    
    // ===================================================================
    // PANEL QUAN LY DU AN
    // ===================================================================
    class DuAnPanel extends JPanel {
        private DuAnDAO dao;
        private JTable table;
        private DefaultTableModel tableModel;
        private JTextField txtMaDA, txtTenDA, txtKinhPhi, txtTimKiem;
        private JComboBox<String> cboTrangThai, cboMaCN;
        private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
        
        public DuAnPanel() {
            dao = new DuAnDAO();
            initPanel();
            loadData();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout(10, 10));
            
            // TOP
            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JLabel lblTimKiem = new JLabel("Tim kiem:");
            txtTimKiem = new JTextField(25);
            btnTimKiem = new JButton("Tim");
            btnTimKiem.addActionListener(e -> timKiem());
            btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> loadData());
            
            panelTop.add(lblTimKiem);
            panelTop.add(txtTimKiem);
            panelTop.add(btnTimKiem);
            panelTop.add(btnLamMoi);
            add(panelTop, BorderLayout.NORTH);
            
            // LEFT
            JPanel panelLeft = new JPanel();
            panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
            panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Du An"));
            panelLeft.setPreferredSize(new Dimension(350, 0));
            
            txtMaDA = new JTextField(20);
            txtTenDA = new JTextField(20);
            txtKinhPhi = new JTextField(20);
            cboTrangThai = new JComboBox<>(new String[]{"DangChay", "KetThuc"});
            cboMaCN = new JComboBox<>(new String[]{"CN_HN", "CN_HCM", "CN_DN", "CN_HP", "CN_CT", "CN_VT", "CN_BD"});
            
            addFormRow(panelLeft, "Ma Du An:", txtMaDA);
            addFormRow(panelLeft, "Ten Du An:", txtTenDA);
            addFormRow(panelLeft, "Kinh Phi:", txtKinhPhi);
            addFormRow(panelLeft, "Trang Thai:", cboTrangThai);
            addFormRow(panelLeft, "Chi Nhanh:", cboMaCN);
            
            panelLeft.add(Box.createVerticalStrut(20));
            
            // BUTTONS
            JPanel panelButtons = new JPanel(new FlowLayout());
            btnThem = createButton("THEM", new Color(46, 204, 113));
            btnSua = createButton("SUA", new Color(52, 152, 219));
            btnXoa = createButton("XOA", new Color(231, 76, 60));
            btnThem.addActionListener(e -> themDuAn());
            btnSua.addActionListener(e -> suaDuAn());
            btnXoa.addActionListener(e -> xoaDuAn());
            panelButtons.add(btnThem);
            panelButtons.add(btnSua);
            panelButtons.add(btnXoa);
            panelLeft.add(panelButtons);
            
            add(panelLeft, BorderLayout.WEST);
            
            // TABLE
            String[] columns = {"Ma Du An", "Ten Du An", "Kinh Phi", "Ngay Bat Dau", "Ngay Ket Thuc", "Trang Thai", "Chi Nhanh"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            table = new JTable(tableModel);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    hienThiChiTiet();
                }
            });
            
            add(new JScrollPane(table), BorderLayout.CENTER);
        }
        
        private void loadData() {
            tableModel.setRowCount(0);
            ArrayList<DuAn_DTO> list = dao.layDanhSachDuAn();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (DuAn_DTO da : list) {
                tableModel.addRow(new Object[]{
                    da.getMaDA(), da.getTenDA(),
                    String.format("%,.0f", da.getKinhPhi()),
                    da.getNgayBatDau() != null ? sdf.format(da.getNgayBatDau()) : "",
                    da.getNgayKetThuc() != null ? sdf.format(da.getNgayKetThuc()) : "",
                    da.getTrangThai(), da.getMaCN()
                });
            }
        }
        
        private void hienThiChiTiet() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaDA.setText(table.getValueAt(row, 0).toString());
                txtMaDA.setEnabled(false);
                txtTenDA.setText(table.getValueAt(row, 1).toString());
                txtKinhPhi.setText(table.getValueAt(row, 2).toString().replaceAll(",", ""));
                cboTrangThai.setSelectedItem(table.getValueAt(row, 5).toString());
                cboMaCN.setSelectedItem(table.getValueAt(row, 6).toString());
            }
        }
        
        private void themDuAn() {
            try {
                String maDA = txtMaDA.getText().trim();
                String tenDA = txtTenDA.getText().trim();
                String kinhPhiStr = txtKinhPhi.getText().trim();
                
                if (maDA.isEmpty() || tenDA.isEmpty() || kinhPhiStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
                    return;
                }
                
                DuAn_DTO da = new DuAn_DTO();
                da.setMaDA(maDA);
                da.setTenDA(tenDA);
                da.setKinhPhi(Double.parseDouble(kinhPhiStr));
                da.setNgayBatDau(new Date());
                da.setNgayKetThuc(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000));
                da.setTrangThai(cboTrangThai.getSelectedItem().toString());
                da.setMaCN(cboMaCN.getSelectedItem().toString());
                
                if (dao.themDuAn(da)) {
                    JOptionPane.showMessageDialog(this, "Them thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Them that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void suaDuAn() {
            try {
                String maDA = txtMaDA.getText().trim();
                if (maDA.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long chon du an!");
                    return;
                }
                
                DuAn_DTO da = dao.getDuAnByMa(maDA);
                if (da == null) {
                    JOptionPane.showMessageDialog(this, "Khong tim thay!");
                    return;
                }
                
                da.setTenDA(txtTenDA.getText().trim());
                da.setKinhPhi(Double.parseDouble(txtKinhPhi.getText().trim()));
                da.setTrangThai(cboTrangThai.getSelectedItem().toString());
                da.setMaCN(cboMaCN.getSelectedItem().toString());
                
                if (dao.suaDuAn(da)) {
                    JOptionPane.showMessageDialog(this, "Sua thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sua that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void xoaDuAn() {
            String maDA = txtMaDA.getText().trim();
            if (maDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon du an!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Xoa du an " + maDA + "?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION && dao.xoaDuAn(maDA)) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                loadData();
                clearForm();
            }
        }
        
        private void timKiem() {
            String tuKhoa = txtTimKiem.getText().trim();
            if (tuKhoa.isEmpty()) {
                loadData();
                return;
            }
            
            tableModel.setRowCount(0);
            ArrayList<DuAn_DTO> list = dao.timKiemDuAn(tuKhoa);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (DuAn_DTO da : list) {
                tableModel.addRow(new Object[]{
                    da.getMaDA(), da.getTenDA(),
                    String.format("%,.0f", da.getKinhPhi()),
                    da.getNgayBatDau() != null ? sdf.format(da.getNgayBatDau()) : "",
                    da.getNgayKetThuc() != null ? sdf.format(da.getNgayKetThuc()) : "",
                    da.getTrangThai(), da.getMaCN()
                });
            }
        }
        
        private void clearForm() {
            txtMaDA.setText("");
            txtMaDA.setEnabled(true);
            txtTenDA.setText("");
            txtKinhPhi.setText("");
            table.clearSelection();
        }
    }
    
    // ===================================================================
    // PANEL QUAN LY PHAN CONG
    // ===================================================================
    class PhanCongPanel extends JPanel {
        private PhanCongDAO dao;
        private NhanVienDAO nvDao;
        private DuAnDAO daDao;
        private JTable table;
        private DefaultTableModel tableModel;
        private JComboBox<String> cboMaNV, cboMaDA;
        private JTextField txtVaiTro, txtDanhGia;
        private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnXemTheoNV, btnXemTheoDA;
        
        public PhanCongPanel() {
            dao = new PhanCongDAO();
            nvDao = new NhanVienDAO();
            daDao = new DuAnDAO();
            initPanel();
            loadComboBoxData();
            loadData();
        }
        
        private void initPanel() {
            setLayout(new BorderLayout(10, 10));
            
            // TOP
            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> loadData());
            panelTop.add(btnLamMoi);
            add(panelTop, BorderLayout.NORTH);
            
            // LEFT
            JPanel panelLeft = new JPanel();
            panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
            panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Phan Cong"));
            panelLeft.setPreferredSize(new Dimension(350, 0));
            
            cboMaNV = new JComboBox<>();
            cboMaDA = new JComboBox<>();
            txtVaiTro = new JTextField(20);
            txtDanhGia = new JTextField(20);
            
            addFormRow(panelLeft, "Nhan Vien:", cboMaNV);
            addFormRow(panelLeft, "Du An:", cboMaDA);
            addFormRow(panelLeft, "Vai Tro:", txtVaiTro);
            addFormRow(panelLeft, "Danh Gia:", txtDanhGia);
            
            panelLeft.add(Box.createVerticalStrut(20));
            
            // BUTTONS
            JPanel panelButtons = new JPanel();
            panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
            
            JPanel panelCRUD = new JPanel(new FlowLayout());
            btnThem = createButton("THEM", new Color(46, 204, 113));
            btnSua = createButton("SUA", new Color(52, 152, 219));
            btnXoa = createButton("XOA", new Color(231, 76, 60));
            btnThem.addActionListener(e -> themPhanCong());
            btnSua.addActionListener(e -> suaPhanCong());
            btnXoa.addActionListener(e -> xoaPhanCong());
            panelCRUD.add(btnThem);
            panelCRUD.add(btnSua);
            panelCRUD.add(btnXoa);
            panelButtons.add(panelCRUD);
            
            JPanel panelFilter = new JPanel(new FlowLayout());
            btnXemTheoNV = new JButton("Xem Theo NV");
            btnXemTheoDA = new JButton("Xem Theo DA");
            btnXemTheoNV.addActionListener(e -> xemTheoNhanVien());
            btnXemTheoDA.addActionListener(e -> xemTheoDuAn());
            panelFilter.add(btnXemTheoNV);
            panelFilter.add(btnXemTheoDA);
            panelButtons.add(panelFilter);
            
            panelLeft.add(panelButtons);
            add(panelLeft, BorderLayout.WEST);
            
            // TABLE
            String[] columns = {"Ma NV", "Ma Du An", "Vai Tro", "Ngay Tham Gia", "Ngay Ket Thuc", "Danh Gia"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            table = new JTable(tableModel);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    hienThiChiTiet();
                }
            });
            
            add(new JScrollPane(table), BorderLayout.CENTER);
        }
        
        private void loadComboBoxData() {
            cboMaNV.removeAllItems();
            ArrayList<NhanVien_DTO> listNV = nvDao.getAllNhanVien();
            for (NhanVien_DTO nv : listNV) {
                cboMaNV.addItem(nv.getMaNV() + " - " + nv.getHoTen());
            }
            
            cboMaDA.removeAllItems();
            ArrayList<DuAn_DTO> listDA = daDao.layDanhSachDuAn();
            for (DuAn_DTO da : listDA) {
                cboMaDA.addItem(da.getMaDA() + " - " + da.getTenDA());
            }
        }
        
        private void loadData() {
            tableModel.setRowCount(0);
            ArrayList<PhanCong_DTO> list = dao.getAllPhanCong();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (PhanCong_DTO pc : list) {
                tableModel.addRow(new Object[]{
                    pc.getMaNV(), pc.getMaDA(), pc.getVaiTroDuAn(),
                    pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                    pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                    pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
                });
            }
        }
        
        private void hienThiChiTiet() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String maNV = table.getValueAt(row, 0).toString();
                String maDA = table.getValueAt(row, 1).toString();
                
                for (int i = 0; i < cboMaNV.getItemCount(); i++) {
                    if (cboMaNV.getItemAt(i).startsWith(maNV)) {
                        cboMaNV.setSelectedIndex(i);
                        break;
                    }
                }
                
                for (int i = 0; i < cboMaDA.getItemCount(); i++) {
                    if (cboMaDA.getItemAt(i).startsWith(maDA)) {
                        cboMaDA.setSelectedIndex(i);
                        break;
                    }
                }
                
                cboMaNV.setEnabled(false);
                cboMaDA.setEnabled(false);
                txtVaiTro.setText(table.getValueAt(row, 2).toString());
                
                Object danhGia = table.getValueAt(row, 5);
                txtDanhGia.setText(!"Chua danh gia".equals(danhGia.toString()) ? danhGia.toString() : "");
            }
        }
        
        private void themPhanCong() {
            try {
                String maNV = getMaNVFromCombo();
                String maDA = getMaDAFromCombo();
                String vaiTro = txtVaiTro.getText().trim();
                
                if (maNV.isEmpty() || maDA.isEmpty() || vaiTro.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
                    return;
                }
                
                PhanCong_DTO pc = new PhanCong_DTO();
                pc.setMaNV(maNV);
                pc.setMaDA(maDA);
                pc.setVaiTroDuAn(vaiTro);
                pc.setNgayThamGia(new Date());
                pc.setNgayKetThuc(null);
                
                String danhGiaStr = txtDanhGia.getText().trim();
                pc.setDanhGia(!danhGiaStr.isEmpty() ? Integer.parseInt(danhGiaStr) : 0);
                
                if (dao.themPhanCong(pc)) {
                    JOptionPane.showMessageDialog(this, "Them thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Them that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void suaPhanCong() {
            try {
                String maNV = getMaNVFromCombo();
                String maDA = getMaDAFromCombo();
                
                if (maNV.isEmpty() || maDA.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui long chon phan cong!");
                    return;
                }
                
                PhanCong_DTO pc = dao.getPhanCong(maNV, maDA);
                if (pc == null) {
                    JOptionPane.showMessageDialog(this, "Khong tim thay!");
                    return;
                }
                
                pc.setVaiTroDuAn(txtVaiTro.getText().trim());
                String danhGiaStr = txtDanhGia.getText().trim();
                pc.setDanhGia(!danhGiaStr.isEmpty() ? Integer.parseInt(danhGiaStr) : 0);
                
                if (dao.suaPhanCong(pc)) {
                    JOptionPane.showMessageDialog(this, "Sua thanh cong!");
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sua that bai!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
            }
        }
        
        private void xoaPhanCong() {
            String maNV = getMaNVFromCombo();
            String maDA = getMaDAFromCombo();
            
            if (maNV.isEmpty() || maDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon phan cong!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Xoa phan cong nay?", "Xac nhan", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION && dao.xoaPhanCong(maNV, maDA)) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                loadData();
                clearForm();
            }
        }
        
        private void xemTheoNhanVien() {
            String maNV = getMaNVFromCombo();
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien!");
                return;
            }
            
            tableModel.setRowCount(0);
            ArrayList<PhanCong_DTO> list = dao.getPhanCongByNhanVien(maNV);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (PhanCong_DTO pc : list) {
                tableModel.addRow(new Object[]{
                    pc.getMaNV(), pc.getMaDA(), pc.getVaiTroDuAn(),
                    pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                    pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                    pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
                });
            }
        }
        
        private void xemTheoDuAn() {
            String maDA = getMaDAFromCombo();
            if (maDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon du an!");
                return;
            }
            
            tableModel.setRowCount(0);
            ArrayList<PhanCong_DTO> list = dao.getPhanCongByDuAn(maDA);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (PhanCong_DTO pc : list) {
                tableModel.addRow(new Object[]{
                    pc.getMaNV(), pc.getMaDA(), pc.getVaiTroDuAn(),
                    pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                    pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                    pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
                });
            }
        }
        
        private String getMaNVFromCombo() {
            String selected = (String) cboMaNV.getSelectedItem();
            return (selected != null && selected.contains(" - ")) ? selected.split(" - ")[0] : "";
        }
        
        private String getMaDAFromCombo() {
            String selected = (String) cboMaDA.getSelectedItem();
            return (selected != null && selected.contains(" - ")) ? selected.split(" - ")[0] : "";
        }
        
        private void clearForm() {
            if (cboMaNV.getItemCount() > 0) cboMaNV.setSelectedIndex(0);
            if (cboMaDA.getItemCount() > 0) cboMaDA.setSelectedIndex(0);
            cboMaNV.setEnabled(true);
            cboMaDA.setEnabled(true);
            txtVaiTro.setText("");
            txtDanhGia.setText("");
            table.clearSelection();
        }
    }
    
    // ===================================================================
    // HELPER METHODS
    // ===================================================================
    private void addFormRow(JPanel panel, String label, JComponent component) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(100, 25));
        row.add(lbl);
        row.add(component);
        panel.add(row);
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }
    
    // ===================================================================
    // MAIN
    // ===================================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanLyGUI().setVisible(true);
        });
    }
}
