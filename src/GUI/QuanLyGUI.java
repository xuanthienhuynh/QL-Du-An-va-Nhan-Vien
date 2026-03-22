package GUI;

import DAO.DuAnDAO;
import DAO.NhanVienDAO;
import DAO.PhanCongDAO;
import DTO.DuAn_DTO;
import DTO.NhanVien_DTO;
import DTO.PhanCong_DTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QuanLyGUI extends JFrame {

    private static final int NGUONG_DU_NGUOI_MAC_DINH = 3;
    private static final String[] GOI_Y_VAI_TRO_DU_AN = {
        "PM - Quan ly",
        "Team Leader",
        "Developer",
        "Tester",
        "Business Analyst",
        "Designer",
        "DevOps",
        "QA"
    };

    private final NhanVien_DTO quanLyDangNhap;
    private JTabbedPane tabbedPane;
    private PhanCongPanel phanCongPanel;
    private DuAnNhanSuPanel duAnNhanSuPanel;
    private NhanVienChuaCoDuAnPanel nhanVienChuaCoDuAnPanel;

    public QuanLyGUI() {
        this(null);
    }

    public QuanLyGUI(NhanVien_DTO quanLyDangNhap) {
        this.quanLyDangNhap = quanLyDangNhap;
        initComponents();
    }

    private void initComponents() {
        setTitle("HE THONG QUAN LY - VAI TRO QUAN LY");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        phanCongPanel = new PhanCongPanel();
        duAnNhanSuPanel = new DuAnNhanSuPanel();
        nhanVienChuaCoDuAnPanel = new NhanVienChuaCoDuAnPanel();

        tabbedPane.addTab("PHAN CONG NHAN VIEN", phanCongPanel);
        tabbedPane.addTab("DANH SACH DU AN", duAnNhanSuPanel);
        tabbedPane.addTab("NHAN VIEN CHUA CO DU AN", nhanVienChuaCoDuAnPanel);

        add(tabbedPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private JPanel buildHeader() {
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

        JLabel xinChao = new JLabel("Xin chao: " + getTenQuanLyHienThi());
        xinChao.setFont(new Font("Dialog", Font.BOLD, 14));
        xinChao.setForeground(Color.WHITE);
        xinChao.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 20));

        JButton btnDangXuat = new JButton("Dang Xuat");
        btnDangXuat.setFont(new Font("Dialog", Font.BOLD, 14));
        btnDangXuat.setMaximumSize(new Dimension(150, 30));
        btnDangXuat.addActionListener(e -> dangXuat());

        rightHeader.add(xinChao);
        rightHeader.add(Box.createVerticalStrut(5));
        rightHeader.add(btnDangXuat);

        header.add(tenCty, BorderLayout.WEST);
        header.add(rightHeader, BorderLayout.EAST);
        return header;
    }

    private String getTenQuanLyHienThi() {
        if (quanLyDangNhap != null && quanLyDangNhap.getHoTen() != null && !quanLyDangNhap.getHoTen().trim().isEmpty()) {
            return quanLyDangNhap.getHoTen().trim();
        }
        return "Quan Ly";
    }

    private void dangXuat() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Ban co chac muon dang xuat?",
                "Xac nhan",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new DangNhap().setVisible(true);
        }
    }

    private boolean isDangLamViec(String tinhTrang) {
        return tinhTrang != null && "DangLamViec".equalsIgnoreCase(tinhTrang.trim());
    }

    private boolean isNhanVienThuong(String vaiTro) {
        return vaiTro != null && "NhanVien".equalsIgnoreCase(vaiTro.trim());
    }

    private void refreshTongHopNhanSu() {
        duAnNhanSuPanel.loadData();
        nhanVienChuaCoDuAnPanel.loadData();
    }

    private Map<String, Integer> taoMapSoDuAnDangLamTheoNhanVien() {
        Map<String, Integer> soDuAnDangLam = new HashMap<>();
        PhanCongDAO phanCongDAO = new PhanCongDAO();
        ArrayList<PhanCong_DTO> phanCongList = phanCongDAO.getAllPhanCong();

        for (PhanCong_DTO pc : phanCongList) {
            if (pc.getNgayKetThuc() == null) {
                soDuAnDangLam.put(pc.getMaNV(), soDuAnDangLam.getOrDefault(pc.getMaNV(), 0) + 1);
            }
        }
        return soDuAnDangLam;
    }

    private Map<String, Integer> taoMapSoNhanVienDangLamTheoDuAn() {
        Map<String, Integer> soNhanVienDangLam = new HashMap<>();
        PhanCongDAO phanCongDAO = new PhanCongDAO();
        ArrayList<PhanCong_DTO> phanCongList = phanCongDAO.getAllPhanCong();

        for (PhanCong_DTO pc : phanCongList) {
            if (pc.getNgayKetThuc() == null) {
                soNhanVienDangLam.put(pc.getMaDA(), soNhanVienDangLam.getOrDefault(pc.getMaDA(), 0) + 1);
            }
        }
        return soNhanVienDangLam;
    }

    private static class ComboItem {

        private final String id;
        private final String ten;

        ComboItem(String id, String ten) {
            this.id = id;
            this.ten = ten;
        }

        String getId() {
            return id;
        }

        @Override
        public String toString() {
            return id + " - " + ten;
        }
    }

    class PhanCongPanel extends JPanel {

        private final PhanCongDAO phanCongDAO;
        private final NhanVienDAO nhanVienDAO;
        private final DuAnDAO duAnDAO;

        private JTable table;
        private DefaultTableModel tableModel;
        private JComboBox<ComboItem> cboNhanVien;
        private JComboBox<ComboItem> cboDuAn;
        private JComboBox<String> cboVaiTroDuAn;

        PhanCongPanel() {
            this.phanCongDAO = new PhanCongDAO();
            this.nhanVienDAO = new NhanVienDAO();
            this.duAnDAO = new DuAnDAO();

            initPanel();
            loadComboBoxData();
            loadData();
        }

        private void initPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JButton btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> {
                loadComboBoxData();
                loadData();
                resetVaiTroDuAnInput();
            });
            panelTop.add(btnLamMoi);
            add(panelTop, BorderLayout.NORTH);

            JPanel panelLeft = new JPanel();
            panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
            panelLeft.setBorder(BorderFactory.createTitledBorder("Thong Tin Phan Cong"));
            panelLeft.setPreferredSize(new Dimension(420, 0));

            cboNhanVien = new JComboBox<>();
            cboDuAn = new JComboBox<>();
            cboVaiTroDuAn = new JComboBox<>(GOI_Y_VAI_TRO_DU_AN);
            cboVaiTroDuAn.setEditable(true);
            cboVaiTroDuAn.setPreferredSize(new Dimension(200, 25));
            cboNhanVien.addActionListener(e -> capNhatDanhSachDuAnTheoNhanVienDangChon());

            addFormRow(panelLeft, "Nhan Vien:", cboNhanVien);
            addFormRow(panelLeft, "Du An:", cboDuAn);
            addFormRow(panelLeft, "Vai Tro Du An:", cboVaiTroDuAn);

            panelLeft.add(Box.createVerticalStrut(12));

            JButton btnPhanCong = createButton("PHAN CONG", new Color(46, 204, 113));
            btnPhanCong.addActionListener(e -> phanCongNhanVien());

            JPanel panelButton = new JPanel(new FlowLayout());
            panelButton.add(btnPhanCong);
            panelLeft.add(panelButton);

            add(panelLeft, BorderLayout.WEST);

            String[] columns = {"Ma NV", "Ho Ten", "Ma DA", "Ten Du An", "Vai Tro", "Ngay Tham Gia", "Trang Thai"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(tableModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            caiDatKieuHienThiNhomNhanVien();
            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        private void caiDatKieuHienThiNhomNhanVien() {
            final Font fontThuong = table.getFont();
            final Font fontDam = fontThuong.deriveFont(Font.BOLD);

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable table,
                        Object value,
                        boolean isSelected,
                        boolean hasFocus,
                        int row,
                        int column
                ) {
                    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    int modelRow = table.convertRowIndexToModel(row);

                    if (column == 0 && laDongDauNhomNhanVien(modelRow)) {
                        comp.setFont(fontDam);
                    } else {
                        comp.setFont(fontThuong);
                    }

                    return comp;
                }
            });
        }

        private boolean laDongDauNhomNhanVien(int modelRow) {
            Object maNV = tableModel.getValueAt(modelRow, 0);
            return maNV != null && !maNV.toString().trim().isEmpty();
        }

        private void loadComboBoxData() {
            cboNhanVien.removeAllItems();
            ArrayList<NhanVien_DTO> nhanVienList = nhanVienDAO.getAllNhanVien();
            Map<String, Integer> soDuAnDangLamTheoNhanVien = taoMapSoDuAnDangLamTheoNhanVien();

            for (NhanVien_DTO nv : nhanVienList) {
                int soDuAnDangLam = soDuAnDangLamTheoNhanVien.getOrDefault(nv.getMaNV(), 0);
                if (soDuAnDangLam < 3) {
                    cboNhanVien.addItem(new ComboItem(nv.getMaNV(), nv.getHoTen()));
                }
            }

            capNhatDanhSachDuAnTheoNhanVienDangChon();
        }

        private void capNhatDanhSachDuAnTheoNhanVienDangChon() {
            cboDuAn.removeAllItems();

            ComboItem nhanVienDangChon = (ComboItem) cboNhanVien.getSelectedItem();
            Set<String> duAnCuaNhanVien = new HashSet<>();
            if (nhanVienDangChon != null) {
                ArrayList<PhanCong_DTO> phanCongTheoNhanVien = phanCongDAO.getPhanCongByNhanVien(nhanVienDangChon.getId());
                for (PhanCong_DTO pc : phanCongTheoNhanVien) {
                    duAnCuaNhanVien.add(pc.getMaDA());
                }
            }

            ArrayList<DuAn_DTO> duAnList = duAnDAO.layDanhSachDuAn();
            Map<String, Integer> soNhanVienDangLamTheoDuAn = taoMapSoNhanVienDangLamTheoDuAn();

            for (DuAn_DTO da : duAnList) {
                int soNhanVienDangLam = soNhanVienDangLamTheoDuAn.getOrDefault(da.getMaDA(), 0);
                if (soNhanVienDangLam < NGUONG_DU_NGUOI_MAC_DINH && !duAnCuaNhanVien.contains(da.getMaDA())) {
                    cboDuAn.addItem(new ComboItem(da.getMaDA(), da.getTenDA()));
                }
            }
        }

        private String layVaiTroDuAnNhap() {
            Object selected = cboVaiTroDuAn.getEditor().getItem();
            return selected == null ? "" : selected.toString().trim();
        }

        private void resetVaiTroDuAnInput() {
            cboVaiTroDuAn.setSelectedIndex(-1);
            cboVaiTroDuAn.getEditor().setItem("");
        }

        private void loadData() {
            tableModel.setRowCount(0);
            ArrayList<PhanCong_DTO> phanCongList = phanCongDAO.getAllPhanCong();
            Map<String, String> mapTenNhanVien = taoMapTenNhanVien();
            Map<String, String> mapTenDuAn = taoMapTenDuAn();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String maNVTroc = null;
            boolean laNhomDauTien = true;

            for (PhanCong_DTO pc : phanCongList) {
                boolean laNhomMoi = maNVTroc == null || !pc.getMaNV().equals(maNVTroc);
                if (laNhomMoi && !laNhomDauTien) {
                    tableModel.addRow(new Object[]{"", "", "", "", "", "", ""});
                }

                String ngayThamGia = pc.getNgayThamGia() != null ? sdf.format(pc.getNgayThamGia()) : "";
                String trangThai = pc.getNgayKetThuc() == null ? "Dang lam" : "Da ket thuc";
                tableModel.addRow(new Object[]{
                    laNhomMoi ? pc.getMaNV() : "",
                    laNhomMoi ? mapTenNhanVien.getOrDefault(pc.getMaNV(), "") : "",
                    pc.getMaDA(),
                    mapTenDuAn.getOrDefault(pc.getMaDA(), ""),
                    pc.getVaiTroDuAn(),
                    ngayThamGia,
                    trangThai
                });

                maNVTroc = pc.getMaNV();
                laNhomDauTien = false;
            }
        }

        private Map<String, String> taoMapTenNhanVien() {
            Map<String, String> map = new HashMap<>();
            ArrayList<NhanVien_DTO> nhanVienList = nhanVienDAO.getAllNhanVien();
            for (NhanVien_DTO nv : nhanVienList) {
                map.put(nv.getMaNV(), nv.getHoTen());
            }
            return map;
        }

        private Map<String, String> taoMapTenDuAn() {
            Map<String, String> map = new HashMap<>();
            ArrayList<DuAn_DTO> duAnList = duAnDAO.layDanhSachDuAn();
            for (DuAn_DTO da : duAnList) {
                map.put(da.getMaDA(), da.getTenDA());
            }
            return map;
        }

        private void phanCongNhanVien() {
            ComboItem nhanVien = (ComboItem) cboNhanVien.getSelectedItem();
            ComboItem duAn = (ComboItem) cboDuAn.getSelectedItem();
            String vaiTroDuAn = layVaiTroDuAnNhap();

            if (nhanVien == null || duAn == null || vaiTroDuAn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin phan cong!");
                return;
            }

            String maNV = nhanVien.getId();
            String maDA = duAn.getId();

            Map<String, Integer> soDuAnDangLamTheoNhanVien = taoMapSoDuAnDangLamTheoNhanVien();
            Map<String, Integer> soNhanVienDangLamTheoDuAn = taoMapSoNhanVienDangLamTheoDuAn();

            PhanCong_DTO phanCongDaTonTai = phanCongDAO.getPhanCong(maNV, maDA);
            if (phanCongDaTonTai != null && phanCongDaTonTai.getNgayKetThuc() == null) {
                JOptionPane.showMessageDialog(this, "Nhan vien nay da dang duoc phan cong vao du an nay.");
                return;
            }

            int soDuAnDangLam = soDuAnDangLamTheoNhanVien.getOrDefault(maNV, 0);
            if (soDuAnDangLam >= 3) {
                JOptionPane.showMessageDialog(this, "Nhan vien nay dang tham gia toi da 3 du an dang chay.");
                loadComboBoxData();
                return;
            }

            int soNhanVienDangLam = soNhanVienDangLamTheoDuAn.getOrDefault(maDA, 0);
            if (soNhanVienDangLam >= NGUONG_DU_NGUOI_MAC_DINH) {
                JOptionPane.showMessageDialog(this, "Du an nay da du nguoi theo nguong hien tai.");
                loadComboBoxData();
                return;
            }

            Date ngayHienTai = new Date();
            if (phanCongDaTonTai != null) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Cap phan cong nay da ket thuc. Ban muon tai phan cong lai?",
                        "Xac nhan",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                phanCongDaTonTai.setNgayThamGia(ngayHienTai);
                phanCongDaTonTai.setNgayKetThuc(null);
                phanCongDaTonTai.setVaiTroDuAn(vaiTroDuAn);
                phanCongDaTonTai.setDanhGia(0);

                boolean result = phanCongDAO.suaPhanCong(phanCongDaTonTai);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Tai phan cong thanh cong!");
                    resetVaiTroDuAnInput();
                    loadData();
                    loadComboBoxData();
                    refreshTongHopNhanSu();
                } else {
                    JOptionPane.showMessageDialog(this, "Tai phan cong that bai! Vui long kiem tra rang buoc du lieu.");
                }
                return;
            }

            PhanCong_DTO pc = new PhanCong_DTO();
            pc.setMaNV(maNV);
            pc.setMaDA(maDA);
            pc.setVaiTroDuAn(vaiTroDuAn);
            pc.setNgayThamGia(ngayHienTai);
            pc.setNgayKetThuc(null);
            pc.setDanhGia(0);

            boolean result = phanCongDAO.themPhanCong(pc);
            if (result) {
                JOptionPane.showMessageDialog(this, "Phan cong thanh cong!");
                resetVaiTroDuAnInput();
                loadData();
                loadComboBoxData();
                refreshTongHopNhanSu();
            } else {
                JOptionPane.showMessageDialog(this, "Phan cong that bai! Vui long kiem tra rang buoc du lieu.");
            }
        }
    }

    class DuAnNhanSuPanel extends JPanel {

        private final DuAnDAO duAnDAO;
        private JTable table;
        private DefaultTableModel tableModel;
        private JComboBox<String> cboLoc;

        DuAnNhanSuPanel() {
            this.duAnDAO = new DuAnDAO();
            initPanel();
            loadData();
        }

        private void initPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelTop.add(new JLabel("Loc nhan su:"));

            cboLoc = new JComboBox<>(new String[]{"Tat ca", "Du nguoi", "Thieu nguoi"});
            panelTop.add(cboLoc);

            JButton btnApDung = new JButton("Ap dung");
            btnApDung.addActionListener(e -> loadData());
            panelTop.add(btnApDung);

            JButton btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> {
                cboLoc.setSelectedIndex(0);
                loadData();
            });
            panelTop.add(btnLamMoi);

            add(panelTop, BorderLayout.NORTH);

            String[] columns = {"Ma DA", "Ten Du An", "Trang Thai", "So NV dang lam", "Tinh Trang Nhan Su"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        private void loadData() {
            tableModel.setRowCount(0);

            String loaiLoc = (String) cboLoc.getSelectedItem();

            Map<String, Integer> soNhanVienDangLamTheoDuAn = taoMapSoNhanVienDangLamTheoDuAn();
            ArrayList<DuAn_DTO> duAnList = duAnDAO.layDanhSachDuAn();

            for (DuAn_DTO duAn : duAnList) {
                if (duAn.getTrangThai() == null || !"DangChay".equalsIgnoreCase(duAn.getTrangThai().trim())) {
                    continue;
                }

                int soNhanVienDangLam = soNhanVienDangLamTheoDuAn.getOrDefault(duAn.getMaDA(), 0);
                boolean duNguoi = soNhanVienDangLam >= NGUONG_DU_NGUOI_MAC_DINH;

                if ("Du nguoi".equals(loaiLoc) && !duNguoi) {
                    continue;
                }
                if ("Thieu nguoi".equals(loaiLoc) && duNguoi) {
                    continue;
                }

                tableModel.addRow(new Object[]{
                    duAn.getMaDA(),
                    duAn.getTenDA(),
                    duAn.getTrangThai(),
                    soNhanVienDangLam,
                    duNguoi ? "Du nguoi" : "Thieu nguoi"
                });
            }
        }
    }

    class NhanVienChuaCoDuAnPanel extends JPanel {

        private final NhanVienDAO nhanVienDAO;
        private JTable table;
        private DefaultTableModel tableModel;

        NhanVienChuaCoDuAnPanel() {
            this.nhanVienDAO = new NhanVienDAO();
            initPanel();
            loadData();
        }

        private void initPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            JButton btnLamMoi = new JButton("Lam Moi");
            btnLamMoi.addActionListener(e -> loadData());
            panelTop.add(btnLamMoi);
            add(panelTop, BorderLayout.NORTH);

            String[] columns = {"Ma NV", "Ho Ten", "Vai Tro", "Tinh Trang", "So Du An Dang Lam", "KPI"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        private void loadData() {
            tableModel.setRowCount(0);

            Map<String, Integer> soDuAnDangLamTheoNhanVien = taoMapSoDuAnDangLamTheoNhanVien();
            ArrayList<NhanVien_DTO> nhanVienList = nhanVienDAO.getAllNhanVien();

            for (NhanVien_DTO nv : nhanVienList) {
                if (!isDangLamViec(nv.getTinhTrang()) || !isNhanVienThuong(nv.getVaiTro())) {
                    continue;
                }

                int soDuAnDangLam = soDuAnDangLamTheoNhanVien.getOrDefault(nv.getMaNV(), 0);
                if (soDuAnDangLam == 0) {
                    tableModel.addRow(new Object[]{
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getVaiTro(),
                        nv.getTinhTrang(),
                        soDuAnDangLam,
                        "Chua dat KPI"
                    });
                }
            }
        }
    }

    private void addFormRow(JPanel panel, String label, JComponent component) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(120, 25));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyGUI().setVisible(true));
    }
}