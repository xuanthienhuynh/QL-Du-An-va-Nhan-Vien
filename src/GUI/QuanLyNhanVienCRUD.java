package GUI;

import DAO.NhanVienDAO;
import DTO.NhanVien_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuanLyNhanVienCRUD extends JFrame {
    
    private NhanVienDAO dao;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtHoTen, txtLuong, txtMatKhau, txtTimKiem;
    private JComboBox<String> cboGioiTinh, cboTinhTrang, cboVaiTro;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    
    public QuanLyNhanVienCRUD() {
        dao = new NhanVienDAO();
        initComponents();
        loadData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("QUAN LY NHAN VIEN - CRUD");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // === PANEL TREN: TIM KIEM ===
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTop.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitle = new JLabel("QUAN LY NHAN VIEN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        panelTop.add(lblTitle);
        
        panelTop.add(Box.createHorizontalStrut(200));
        
        JLabel lblTimKiem = new JLabel("Tim kiem:");
        lblTimKiem.setForeground(Color.WHITE);
        panelTop.add(lblTimKiem);
        
        txtTimKiem = new JTextField(20);
        panelTop.add(txtTimKiem);
        
        btnTimKiem = new JButton("Tim");
        btnTimKiem.addActionListener(e -> timKiem());
        panelTop.add(btnTimKiem);
        
        btnLamMoi = new JButton("Lam Moi");
        btnLamMoi.addActionListener(e -> loadData());
        panelTop.add(btnLamMoi);
        
        add(panelTop, BorderLayout.NORTH);
        
        // === PANEL TRAI: FORM NHAP LIEU ===
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Nhan Vien"));
        panelLeft.setPreferredSize(new Dimension(350, 0));
        
        // Ma NV
        JPanel pMaNV = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMaNV.add(new JLabel("Ma NV:     "));
        txtMaNV = new JTextField(20);
        pMaNV.add(txtMaNV);
        panelLeft.add(pMaNV);
        
        // Ho Ten
        JPanel pHoTen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pHoTen.add(new JLabel("Ho Ten:    "));
        txtHoTen = new JTextField(20);
        pHoTen.add(txtHoTen);
        panelLeft.add(pHoTen);
        
        // Gioi Tinh
        JPanel pGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pGioiTinh.add(new JLabel("Gioi Tinh: "));
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nu"});
        pGioiTinh.add(cboGioiTinh);
        panelLeft.add(pGioiTinh);
        
        // Luong
        JPanel pLuong = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pLuong.add(new JLabel("Luong:     "));
        txtLuong = new JTextField(20);
        pLuong.add(txtLuong);
        panelLeft.add(pLuong);
        
        // Tinh Trang
        JPanel pTinhTrang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTinhTrang.add(new JLabel("Tinh Trang:"));
        cboTinhTrang = new JComboBox<>(new String[]{"DangLamViec", "DaNghiViec"});
        pTinhTrang.add(cboTinhTrang);
        panelLeft.add(pTinhTrang);
        
        // Vai Tro
        JPanel pVaiTro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pVaiTro.add(new JLabel("Vai Tro:   "));
        cboVaiTro = new JComboBox<>(new String[]{"NhanVien", "QuanLy", "Sep"});
        pVaiTro.add(cboVaiTro);
        panelLeft.add(pVaiTro);
        
        // Mat Khau
        JPanel pMatKhau = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMatKhau.add(new JLabel("Mat Khau:  "));
        txtMatKhau = new JTextField(20);
        pMatKhau.add(txtMatKhau);
        panelLeft.add(pMatKhau);
        
        panelLeft.add(Box.createVerticalStrut(20));
        
        // === BUTTONS ===
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnThem = new JButton("THEM");
        btnThem.setBackground(new Color(46, 204, 113));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        btnThem.addActionListener(e -> themNhanVien());
        panelButtons.add(btnThem);
        
        btnSua = new JButton("SUA");
        btnSua.setBackground(new Color(52, 152, 219));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        btnSua.addActionListener(e -> suaNhanVien());
        panelButtons.add(btnSua);
        
        btnXoa = new JButton("XOA");
        btnXoa.setBackground(new Color(231, 76, 60));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        btnXoa.addActionListener(e -> xoaNhanVien());
        panelButtons.add(btnXoa);
        
        panelLeft.add(panelButtons);
        
        add(panelLeft, BorderLayout.WEST);
        
        // === PANEL GIUA: TABLE ===
        String[] columns = {"Ma NV", "Ho Ten", "Gioi Tinh", "Luong", "Tinh Trang", "Vai Tro", "Mat Khau"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khong cho edit truc tiep tren table
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    hienThiChiTiet();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // === LOAD DATA ===
    private void loadData() {
        tableModel.setRowCount(0); // Xoa het du lieu cu
        ArrayList<NhanVien_DTO> list = dao.getAllNhanVien();
        
        for (NhanVien_DTO nv : list) {
            Object[] row = {
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh(),
                String.format("%,.0f", nv.getLuong()),
                nv.getTinhTrang(),
                nv.getVaiTro(),
                nv.getMatKhau()
            };
            tableModel.addRow(row);
        }
    }
    
    // === HIEN THI CHI TIET KHI CLICK VAO DONG ===
    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaNV.setText(table.getValueAt(row, 0).toString());
            txtMaNV.setEnabled(false); // Khong cho sua ma NV
            txtHoTen.setText(table.getValueAt(row, 1).toString());
            cboGioiTinh.setSelectedItem(table.getValueAt(row, 2).toString());
            String luongStr = table.getValueAt(row, 3).toString().replaceAll(",", "");
            txtLuong.setText(luongStr);
            cboTinhTrang.setSelectedItem(table.getValueAt(row, 4).toString());
            cboVaiTro.setSelectedItem(table.getValueAt(row, 5).toString());
            txtMatKhau.setText(table.getValueAt(row, 6).toString());
        }
    }
    
    // === THEM NHAN VIEN ===
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
            nv.setDcTinh("Ha Noi"); // Mac dinh, ban co the them JTextField
            nv.setDcPhuong("Cau Giay");
            nv.setDcSoNha("So 1");
            nv.setNgaySinh(new java.util.Date()); // Mac dinh hom nay
            nv.setLuong(Double.parseDouble(luongStr));
            nv.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
            nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
            nv.setMatKhau(matKhau);
            nv.setMaPB("PB_IT_HN"); // Mac dinh
            nv.setMaCN("CN_HN"); // Mac dinh
            
            boolean result = dao.themNhanVien(nv);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Them nhan vien thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Them nhan vien that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === SUA NHAN VIEN ===
    private void suaNhanVien() {
        try {
            String maNV = txtMaNV.getText().trim();
            
            if (maNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can sua!");
                return;
            }
            
            NhanVien_DTO nv = dao.getNhanVienByMa(maNV);
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Khong tim thay nhan vien!");
                return;
            }
            
            // Cap nhat thong tin
            nv.setHoTen(txtHoTen.getText().trim());
            nv.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
            nv.setLuong(Double.parseDouble(txtLuong.getText().trim()));
            nv.setTinhTrang(cboTinhTrang.getSelectedItem().toString());
            nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
            nv.setMatKhau(txtMatKhau.getText().trim());
            
            boolean result = dao.suaNhanVien(nv);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Sua nhan vien thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sua nhan vien that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === XOA NHAN VIEN ===
    private void xoaNhanVien() {
        String maNV = txtMaNV.getText().trim();
        
        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long chon nhan vien can xoa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Ban co chac muon xoa nhan vien " + maNV + "?", 
            "Xac nhan",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = dao.xoaNhanVien(maNV);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Xoa nhan vien thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xoa nhan vien that bai! (Co the do rang buoc du lieu)");
            }
        }
    }
    
    // === TIM KIEM ===
    private void timKiem() {
        String tuKhoa = txtTimKiem.getText().trim();
        
        if (tuKhoa.isEmpty()) {
            loadData();
            return;
        }
        
        tableModel.setRowCount(0);
        ArrayList<NhanVien_DTO> list = dao.timKiemNhanVien(tuKhoa);
        
        for (NhanVien_DTO nv : list) {
            Object[] row = {
                nv.getMaNV(),
                nv.getHoTen(),
                nv.getGioiTinh(),
                String.format("%,.0f", nv.getLuong()),
                nv.getTinhTrang(),
                nv.getVaiTro(),
                nv.getMatKhau()
            };
            tableModel.addRow(row);
        }
    }
    
    // === LAM MOI FORM ===
    private void clearForm() {
        txtMaNV.setText("");
        txtMaNV.setEnabled(true);
        txtHoTen.setText("");
        txtLuong.setText("");
        txtMatKhau.setText("");
        cboGioiTinh.setSelectedIndex(0);
        cboTinhTrang.setSelectedIndex(0);
        cboVaiTro.setSelectedIndex(0);
        table.clearSelection();
    }
    
    // === MAIN TEST ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanLyNhanVienCRUD().setVisible(true);
        });
    }
}
