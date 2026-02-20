package GUI;

import DAO.DuAnDAO;
import DTO.DuAn_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuanLyDuAnCRUD extends JFrame {
    
    private DuAnDAO dao;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaDA, txtTenDA, txtKinhPhi, txtTimKiem;
    private JComboBox<String> cboTrangThai, cboMaCN;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    
    public QuanLyDuAnCRUD() {
        dao = new DuAnDAO();
        initComponents();
        loadData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("QUAN LY DU AN - CRUD");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // === PANEL TREN: TIM KIEM ===
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTop.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitle = new JLabel("QUAN LY DU AN");
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
        panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Du An"));
        panelLeft.setPreferredSize(new Dimension(350, 0));
        
        // Ma DA
        JPanel pMaDA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMaDA.add(new JLabel("Ma Du An:    "));
        txtMaDA = new JTextField(20);
        pMaDA.add(txtMaDA);
        panelLeft.add(pMaDA);
        
        // Ten DA
        JPanel pTenDA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTenDA.add(new JLabel("Ten Du An:   "));
        txtTenDA = new JTextField(20);
        pTenDA.add(txtTenDA);
        panelLeft.add(pTenDA);
        
        // Kinh Phi
        JPanel pKinhPhi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pKinhPhi.add(new JLabel("Kinh Phi:    "));
        txtKinhPhi = new JTextField(20);
        pKinhPhi.add(txtKinhPhi);
        panelLeft.add(pKinhPhi);
        
        // Trang Thai
        JPanel pTrangThai = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pTrangThai.add(new JLabel("Trang Thai:  "));
        cboTrangThai = new JComboBox<>(new String[]{"DangChay", "KetThuc"});
        pTrangThai.add(cboTrangThai);
        panelLeft.add(pTrangThai);
        
        // Ma Chi Nhanh
        JPanel pMaCN = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMaCN.add(new JLabel("Chi Nhanh:   "));
        cboMaCN = new JComboBox<>(new String[]{"CN_HN", "CN_HCM", "CN_DN", "CN_HP", "CN_CT", "CN_VT", "CN_BD"});
        pMaCN.add(cboMaCN);
        panelLeft.add(pMaCN);
        
        panelLeft.add(Box.createVerticalStrut(20));
        
        // === BUTTONS ===
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnThem = new JButton("THEM");
        btnThem.setBackground(new Color(46, 204, 113));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        btnThem.addActionListener(e -> themDuAn());
        panelButtons.add(btnThem);
        
        btnSua = new JButton("SUA");
        btnSua.setBackground(new Color(52, 152, 219));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        btnSua.addActionListener(e -> suaDuAn());
        panelButtons.add(btnSua);
        
        btnXoa = new JButton("XOA");
        btnXoa.setBackground(new Color(231, 76, 60));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        btnXoa.addActionListener(e -> xoaDuAn());
        panelButtons.add(btnXoa);
        
        panelLeft.add(panelButtons);
        
        add(panelLeft, BorderLayout.WEST);
        
        // === PANEL GIUA: TABLE ===
        String[] columns = {"Ma Du An", "Ten Du An", "Kinh Phi", "Ngay Bat Dau", "Ngay Ket Thuc", "Trang Thai", "Chi Nhanh"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
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
        tableModel.setRowCount(0);
        ArrayList<DuAn_DTO> list = dao.layDanhSachDuAn();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (DuAn_DTO da : list) {
            Object[] row = {
                da.getMaDA(),
                da.getTenDA(),
                String.format("%,.0f", da.getKinhPhi()),
                da.getNgayBatDau() != null ? sdf.format(da.getNgayBatDau()) : "",
                da.getNgayKetThuc() != null ? sdf.format(da.getNgayKetThuc()) : "",
                da.getTrangThai(),
                da.getMaCN()
            };
            tableModel.addRow(row);
        }
    }
    
    // === HIEN THI CHI TIET ===
    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaDA.setText(table.getValueAt(row, 0).toString());
            txtMaDA.setEnabled(false);
            txtTenDA.setText(table.getValueAt(row, 1).toString());
            String kinhPhiStr = table.getValueAt(row, 2).toString().replaceAll(",", "");
            txtKinhPhi.setText(kinhPhiStr);
            cboTrangThai.setSelectedItem(table.getValueAt(row, 5).toString());
            cboMaCN.setSelectedItem(table.getValueAt(row, 6).toString());
        }
    }
    
    // === THEM DU AN ===
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
            da.setNgayBatDau(new Date()); // Mac dinh hom nay
            da.setNgayKetThuc(new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000)); // +1 nam
            da.setTrangThai(cboTrangThai.getSelectedItem().toString());
            da.setMaCN(cboMaCN.getSelectedItem().toString());
            
            boolean result = dao.themDuAn(da);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Them du an thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Them du an that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === SUA DU AN ===
    private void suaDuAn() {
        try {
            String maDA = txtMaDA.getText().trim();
            
            if (maDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon du an can sua!");
                return;
            }
            
            DuAn_DTO da = dao.getDuAnByMa(maDA);
            if (da == null) {
                JOptionPane.showMessageDialog(this, "Khong tim thay du an!");
                return;
            }
            
            da.setTenDA(txtTenDA.getText().trim());
            da.setKinhPhi(Double.parseDouble(txtKinhPhi.getText().trim()));
            da.setTrangThai(cboTrangThai.getSelectedItem().toString());
            da.setMaCN(cboMaCN.getSelectedItem().toString());
            
            boolean result = dao.suaDuAn(da);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Sua du an thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sua du an that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === XOA DU AN ===
    private void xoaDuAn() {
        String maDA = txtMaDA.getText().trim();
        
        if (maDA.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long chon du an can xoa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Ban co chac muon xoa du an " + maDA + "?", 
            "Xac nhan",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = dao.xoaDuAn(maDA);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Xoa du an thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xoa du an that bai! (Co the do rang buoc du lieu)");
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
        ArrayList<DuAn_DTO> list = dao.timKiemDuAn(tuKhoa);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (DuAn_DTO da : list) {
            Object[] row = {
                da.getMaDA(),
                da.getTenDA(),
                String.format("%,.0f", da.getKinhPhi()),
                da.getNgayBatDau() != null ? sdf.format(da.getNgayBatDau()) : "",
                da.getNgayKetThuc() != null ? sdf.format(da.getNgayKetThuc()) : "",
                da.getTrangThai(),
                da.getMaCN()
            };
            tableModel.addRow(row);
        }
    }
    
    // === LAM MOI FORM ===
    private void clearForm() {
        txtMaDA.setText("");
        txtMaDA.setEnabled(true);
        txtTenDA.setText("");
        txtKinhPhi.setText("");
        cboTrangThai.setSelectedIndex(0);
        cboMaCN.setSelectedIndex(0);
        table.clearSelection();
    }
    
    // === MAIN TEST ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanLyDuAnCRUD().setVisible(true);
        });
    }
}
