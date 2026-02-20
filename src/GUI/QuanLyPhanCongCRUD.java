package GUI;

import DAO.PhanCongDAO;
import DAO.NhanVienDAO;
import DAO.DuAnDAO;
import DTO.PhanCong_DTO;
import DTO.NhanVien_DTO;
import DTO.DuAn_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuanLyPhanCongCRUD extends JFrame {
    
    private PhanCongDAO dao;
    private NhanVienDAO nvDao;
    private DuAnDAO daDao;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> cboMaNV, cboMaDA;
    private JTextField txtVaiTro, txtDanhGia;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnXemTheoNV, btnXemTheoDA;
    
    public QuanLyPhanCongCRUD() {
        dao = new PhanCongDAO();
        nvDao = new NhanVienDAO();
        daDao = new DuAnDAO();
        initComponents();
        loadComboBoxData();
        loadData();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("QUAN LY PHAN CONG - CRUD");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // === PANEL TREN ===
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelTop.setBackground(new Color(0, 102, 204));
        
        JLabel lblTitle = new JLabel("QUAN LY PHAN CONG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        panelTop.add(lblTitle);
        
        panelTop.add(Box.createHorizontalStrut(200));
        
        btnLamMoi = new JButton("Lam Moi");
        btnLamMoi.addActionListener(e -> loadData());
        panelTop.add(btnLamMoi);
        
        add(panelTop, BorderLayout.NORTH);
        
        // === PANEL TRAI: FORM ===
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Phan Cong"));
        panelLeft.setPreferredSize(new Dimension(350, 0));
        
        // Ma Nhan Vien
        JPanel pMaNV = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMaNV.add(new JLabel("Nhan Vien:   "));
        cboMaNV = new JComboBox<>();
        cboMaNV.setPreferredSize(new Dimension(200, 25));
        pMaNV.add(cboMaNV);
        panelLeft.add(pMaNV);
        
        // Ma Du An
        JPanel pMaDA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pMaDA.add(new JLabel("Du An:       "));
        cboMaDA = new JComboBox<>();
        cboMaDA.setPreferredSize(new Dimension(200, 25));
        pMaDA.add(cboMaDA);
        panelLeft.add(pMaDA);
        
        // Vai Tro Du An
        JPanel pVaiTro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pVaiTro.add(new JLabel("Vai Tro:     "));
        txtVaiTro = new JTextField(20);
        pVaiTro.add(txtVaiTro);
        panelLeft.add(pVaiTro);
        
        // Danh Gia
        JPanel pDanhGia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pDanhGia.add(new JLabel("Danh Gia:    "));
        txtDanhGia = new JTextField(20);
        txtDanhGia.setToolTipText("Diem tu 1-10, de trong neu chua danh gia");
        pDanhGia.add(txtDanhGia);
        panelLeft.add(pDanhGia);
        
        panelLeft.add(Box.createVerticalStrut(20));
        
        // === BUTTONS ===
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        
        JPanel panelCRUD = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnThem = new JButton("THEM");
        btnThem.setBackground(new Color(46, 204, 113));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Arial", Font.BOLD, 14));
        btnThem.addActionListener(e -> themPhanCong());
        panelCRUD.add(btnThem);
        
        btnSua = new JButton("SUA");
        btnSua.setBackground(new Color(52, 152, 219));
        btnSua.setForeground(Color.WHITE);
        btnSua.setFont(new Font("Arial", Font.BOLD, 14));
        btnSua.addActionListener(e -> suaPhanCong());
        panelCRUD.add(btnSua);
        
        btnXoa = new JButton("XOA");
        btnXoa.setBackground(new Color(231, 76, 60));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Arial", Font.BOLD, 14));
        btnXoa.addActionListener(e -> xoaPhanCong());
        panelCRUD.add(btnXoa);
        
        panelButtons.add(panelCRUD);
        
        // Filter buttons
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        btnXemTheoNV = new JButton("Xem Theo NV");
        btnXemTheoNV.addActionListener(e -> xemTheoNhanVien());
        panelFilter.add(btnXemTheoNV);
        
        btnXemTheoDA = new JButton("Xem Theo DA");
        btnXemTheoDA.addActionListener(e -> xemTheoDuAn());
        panelFilter.add(btnXemTheoDA);
        
        panelButtons.add(panelFilter);
        
        panelLeft.add(panelButtons);
        
        add(panelLeft, BorderLayout.WEST);
        
        // === TABLE ===
        String[] columns = {"Ma NV", "Ma Du An", "Vai Tro", "Ngay Tham Gia", "Ngay Ket Thuc", "Danh Gia"};
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
    
    // === LOAD COMBOBOX DATA ===
    private void loadComboBoxData() {
        // Load Nhan Vien
        cboMaNV.removeAllItems();
        ArrayList<NhanVien_DTO> listNV = nvDao.getAllNhanVien();
        for (NhanVien_DTO nv : listNV) {
            cboMaNV.addItem(nv.getMaNV() + " - " + nv.getHoTen());
        }
        
        // Load Du An
        cboMaDA.removeAllItems();
        ArrayList<DuAn_DTO> listDA = daDao.layDanhSachDuAn();
        for (DuAn_DTO da : listDA) {
            cboMaDA.addItem(da.getMaDA() + " - " + da.getTenDA());
        }
    }
    
    // === LOAD DATA ===
    private void loadData() {
        tableModel.setRowCount(0);
        ArrayList<PhanCong_DTO> list = dao.getAllPhanCong();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (PhanCong_DTO pc : list) {
            Object[] row = {
                pc.getMaNV(),
                pc.getMaDA(),
                pc.getVaiTroDuAn(),
                pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
            };
            tableModel.addRow(row);
        }
    }
    
    // === HIEN THI CHI TIET ===
    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maNV = table.getValueAt(row, 0).toString();
            String maDA = table.getValueAt(row, 1).toString();
            
            // Set combobox
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
            if (!"Chua danh gia".equals(danhGia.toString())) {
                txtDanhGia.setText(danhGia.toString());
            } else {
                txtDanhGia.setText("");
            }
        }
    }
    
    // === THEM PHAN CONG ===
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
            pc.setNgayKetThuc(null); // Dang lam viec
            
            String danhGiaStr = txtDanhGia.getText().trim();
            if (!danhGiaStr.isEmpty()) {
                pc.setDanhGia(Integer.parseInt(danhGiaStr));
            } else {
                pc.setDanhGia(0);
            }
            
            boolean result = dao.themPhanCong(pc);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Them phan cong thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Them phan cong that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === SUA PHAN CONG ===
    private void suaPhanCong() {
        try {
            String maNV = getMaNVFromCombo();
            String maDA = getMaDAFromCombo();
            
            if (maNV.isEmpty() || maDA.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long chon phan cong can sua!");
                return;
            }
            
            PhanCong_DTO pc = dao.getPhanCong(maNV, maDA);
            if (pc == null) {
                JOptionPane.showMessageDialog(this, "Khong tim thay phan cong!");
                return;
            }
            
            pc.setVaiTroDuAn(txtVaiTro.getText().trim());
            
            String danhGiaStr = txtDanhGia.getText().trim();
            if (!danhGiaStr.isEmpty()) {
                pc.setDanhGia(Integer.parseInt(danhGiaStr));
            } else {
                pc.setDanhGia(0);
            }
            
            boolean result = dao.suaPhanCong(pc);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Sua phan cong thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sua phan cong that bai!");
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loi: " + ex.getMessage());
        }
    }
    
    // === XOA PHAN CONG ===
    private void xoaPhanCong() {
        String maNV = getMaNVFromCombo();
        String maDA = getMaDAFromCombo();
        
        if (maNV.isEmpty() || maDA.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long chon phan cong can xoa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Ban co chac muon xoa phan cong nay?", 
            "Xac nhan",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = dao.xoaPhanCong(maNV, maDA);
            
            if (result) {
                JOptionPane.showMessageDialog(this, "Xoa phan cong thanh cong!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xoa phan cong that bai!");
            }
        }
    }
    
    // === XEM THEO NHAN VIEN ===
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
            Object[] row = {
                pc.getMaNV(),
                pc.getMaDA(),
                pc.getVaiTroDuAn(),
                pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
            };
            tableModel.addRow(row);
        }
    }
    
    // === XEM THEO DU AN ===
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
            Object[] row = {
                pc.getMaNV(),
                pc.getMaDA(),
                pc.getVaiTroDuAn(),
                pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "",
                pc.getNgayKetThuc() != null ? sdf.format(pc.getNgayKetThuc()) : "Dang lam",
                pc.getDanhGia() > 0 ? pc.getDanhGia() : "Chua danh gia"
            };
            tableModel.addRow(row);
        }
    }
    
    // === HELPER METHODS ===
    private String getMaNVFromCombo() {
        String selected = (String) cboMaNV.getSelectedItem();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0];
        }
        return "";
    }
    
    private String getMaDAFromCombo() {
        String selected = (String) cboMaDA.getSelectedItem();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0];
        }
        return "";
    }
    
    private void clearForm() {
        cboMaNV.setSelectedIndex(0);
        cboMaNV.setEnabled(true);
        cboMaDA.setSelectedIndex(0);
        cboMaDA.setEnabled(true);
        txtVaiTro.setText("");
        txtDanhGia.setText("");
        table.clearSelection();
    }
    
    // === MAIN TEST ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuanLyPhanCongCRUD().setVisible(true);
        });
    }
}
