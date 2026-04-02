package GUI.QuanLy_component;

import BUS.NhanVienBUS;
import BUS.PhanCongBUS;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.table.TableRowSorter;

public class NhanVienRanhForm extends javax.swing.JPanel {
    private NhanVienBUS nvBUS = new NhanVienBUS();
    private PhanCongBUS pcBUS = new PhanCongBUS();
    
    private JTable tblNhanVien;
    private DefaultTableModel model;
    private JTextField txtTimKiem;
    private JButton btnLamMoi, btnXuatFile;
    private JLabel lblThongKe;

    public NhanVienRanhForm() {
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL TIÊU ĐỀ & THÔNG KÊ ---
        JPanel pnlNorth = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("DANH SÁCH NHÂN VIÊN CHƯA THAM GIA DỰ ÁN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(25, 118, 210));
        
        lblThongKe = new JLabel("Tổng cộng: 0 nhân viên rảnh");
        lblThongKe.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        
        pnlNorth.add(lblTitle, BorderLayout.WEST);
        pnlNorth.add(lblThongKe, BorderLayout.EAST);

        // --- 2. PANEL ĐIỀU KHIỂN (LỌC) ---
        JPanel pnlControl = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlControl.setBorder(BorderFactory.createTitledBorder("Bộ lọc nhanh"));

        txtTimKiem = new JTextField(20);
        btnLamMoi = new JButton("Làm mới");
//        btnXuatFile = new JButton("Xuất báo cáo");

        pnlControl.add(new JLabel("Tìm tên/mã:"));
        pnlControl.add(txtTimKiem);
        pnlControl.add(btnLamMoi);
//        pnlControl.add(btnXuatFile);

        // --- 3. BẢNG DỮ LIỆU ---
        String[] headers = { "STT", "Mã NV", "Họ Tên", "Giới Tính", "Phòng Ban", "Vai Trò", "Tình Trạng" };
        model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        tblNhanVien = new JTable(model);
        tblNhanVien.setRowHeight(30);
        tblNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // --- 4. SỰ KIỆN ---
        btnLamMoi.addActionListener(e -> {
            txtTimKiem.setText("");
            loadDataToTable();
        });

        // Tìm kiếm nhanh khi gõ phím
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actionTimKiem();
            }
        });

        // Gom nhóm layout
        JPanel pnlTopWrap = new JPanel(new BorderLayout());
        pnlTopWrap.add(pnlNorth, BorderLayout.NORTH);
        pnlTopWrap.add(pnlControl, BorderLayout.CENTER);

        add(pnlTopWrap, BorderLayout.NORTH);
        add(new JScrollPane(tblNhanVien), BorderLayout.CENTER);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        
        // 1. Lấy toàn bộ danh sách nhân viên đang làm việc
        ArrayList<NhanVien_DTO> allNV = nvBUS.layDanhSachNhanVien();
        
        // 2. Lấy danh sách mã nhân viên đã có trong bảng Phân Công
        ArrayList<String> nvCoDuAn = pcBUS.layToanBoPhanCong().stream()
                                         .map(PhanCong_DTO::getMaNV)
                                         .distinct()
                                         .collect(Collectors.toCollection(ArrayList::new));

        // 3. Lọc ra những người KHÔNG nằm trong danh sách đã có dự án
        int stt = 1;
        int count = 0;
        for (NhanVien_DTO nv : allNV) {
            // Kiểm tra: Không nằm trong bảng Phân Công VÀ Tình trạng phải là '1' (Đang làm việc)
            if (!nvCoDuAn.contains(nv.getMaNV()) && "DangLamViec".equals(nv.getTinhTrang())) {
                model.addRow(new Object[]{
                    stt++,
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getGioiTinh(),
                    nv.getMaPB(),
                    nv.getVaiTro(),
                    "Sẵn sàng"
                });
                count++;
            }
        }
        lblThongKe.setText("Tổng cộng: " + count + " nhân viên rảnh");
    }

    private void actionTimKiem() {
        String key = txtTimKiem.getText().toLowerCase().trim();
        // Bạn có thể dùng RowSorter để lọc trực tiếp trên Table cho nhanh
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNhanVien.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + key));
    }
}