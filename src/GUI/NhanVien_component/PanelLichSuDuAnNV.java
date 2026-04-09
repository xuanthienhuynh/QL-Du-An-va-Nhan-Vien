package GUI.NhanVien_component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PanelLichSuDuAnNV extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTimKiem;
    private TableRowSorter<DefaultTableModel> sorter;

    public PanelLichSuDuAnNV(String maNV) {
        initComponents();
        loadData(maNV);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        // ==========================================
        // 1. THANH TÌM KIẾM
        // ==========================================
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTop.setBackground(new Color(245, 245, 245));

        JLabel lblTimKiem = new JLabel("Tìm kiếm lịch sử dự án:");
        lblTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
        
        txtTimKiem = new JTextField(25);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 14));

        pnlTop.add(lblTimKiem);
        pnlTop.add(txtTimKiem);
        this.add(pnlTop, BorderLayout.NORTH);

        // ==========================================
        // 2. KHỞI TẠO BẢNG (THÊM CỘT CHI TIẾT SỐ 6)
        // ==========================================
        String[] columns = {"Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Vai Trò", "Đánh Giá", "Chi Tiết"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table = new JTable(model);
        table.setRowHeight(60); // Nới cao 60px để nhét vừa ảnh 48px
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(102, 204, 255));

        // ÉP CỘT 6 (CỘT CHI TIẾT) VẼ RA CÁI NÚT CHỨA CON MẮT
        table.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.TableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton btnEye = new JButton();
                try {
                    java.net.URL imgURL = getClass().getResource("/img/icons8-eye-48.png");
                    if (imgURL != null) {
                        btnEye.setIcon(new ImageIcon(imgURL));
                    } else {
                        btnEye.setText("Xem");
                    }
                } catch (Exception e) {}

                btnEye.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                btnEye.setBorderPainted(false); 
                btnEye.setContentAreaFilled(false); 
                btnEye.setOpaque(true);
                btnEye.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
                return btnEye;
            }
        });

        // BẮT SỰ KIỆN CLICK
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                // Nếu click đúng vào CỘT SỐ 6
                if (row >= 0 && col == 6) {
                    String maDA = table.getValueAt(row, 0).toString(); // Lấy mã DA
                    
                    // Bật cửa sổ Popup màu xanh
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Chi Tiết Dự Án Lịch Sử - " + maDA);
                    dialog.setModal(true); 
                    
                    GUI.Panel_Frame.PanelDetailsDuAn pnlDetails = new GUI.Panel_Frame.PanelDetailsDuAn(maDA);
                    dialog.add(pnlDetails);
                    
                    dialog.pack(); 
                    dialog.setLocationRelativeTo(null); 
                    dialog.setVisible(true); 
                }
            }
        });

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 3. TÌM KIẾM REAL-TIME
        // ==========================================
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                String text = txtTimKiem.getText().trim();
                if (text.length() == 0) {
                    sorter.setRowFilter(null); 
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    // ==========================================
    // 4. HÀM LOAD DATA 
    // ==========================================
    private void loadData(String maNV) {
        // Thay tblLichSu bằng tên biến JTable Tab 3 của ông nha
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        model.setRowCount(0); // Dọn sạch rác cũ

        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<Object[]> listLichSu = bus.getLichSuDuAn(maNV);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        for (Object[] row : listLichSu) {
            // Format ngày tháng cho gọn gàng
            String ngayBD = (row[2] != null) ? sdf.format((java.util.Date) row[2]) : "Chưa rõ";
            String ngayKT = (row[3] != null) ? sdf.format((java.util.Date) row[3]) : "Chưa rõ";

            // SẮP XẾP LẠI ĐÚNG THỨ TỰ 7 CỘT TRÊN GIAO DIỆN
            model.addRow(new Object[]{
                row[0],     // 1. Mã DA
                row[1],     // 2. Tên DA
                ngayBD,     // 3. Ngày Bắt Đầu
                ngayKT,     // 4. Ngày Kết Thúc
                row[4],     // 5. Vai trò
                row[5],     // 6. Đánh giá (Sẽ hiện 10, 9, 8 hoặc "Chưa chấm")
                "Button"    // 7. Chi Tiết (Hiển thị icon con mắt)
            });
        }
    }
}