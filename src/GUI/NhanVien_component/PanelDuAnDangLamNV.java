package GUI.NhanVien_component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class PanelDuAnDangLamNV extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private TableRowSorter<DefaultTableModel> sorter;

    public PanelDuAnDangLamNV(String maNV) {
        initComponents();
        loadData(maNV);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- 1. THANH TÌM KIẾM (NORTH) ---
        JPanel pnSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnSearch.setBackground(Color.WHITE);
        pnSearch.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel lblSearch = new JLabel("Tìm kiếm dự án: ");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(300, 35));

        pnSearch.add(lblSearch);
        pnSearch.add(txtSearch);

        // --- 2. BẢNG DỮ LIỆU (CENTER) ---
       String[] columns = {"Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Trạng Thái", "Vai Trò", "Chi Tiết"};
        
       
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return Icon.class; 
                }
                return super.getColumnClass(columnIndex);
            }
        };
        table = new JTable(model);
        table.setRowHeight(60); // FIX: Nới chiều cao hàng lên 60 để chứa vừa cái ảnh 48px
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(102, 204, 255));

       
        table.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.TableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JButton btnEye = new JButton();
                
                
                try {
                    java.net.URL imgURL = getClass().getResource("/img/icons8-eye-48.png");
                    if (imgURL != null) {
                        btnEye.setIcon(new ImageIcon(imgURL));
                    } else {
                        btnEye.setText("Xem"); // Nếu lỗi đường dẫn thì hiện tạm chữ "Xem"
                    }
                } catch (Exception e) {}

                // Trang trí cho cái nút nó đẹp, tệp màu với bảng
                btnEye.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                btnEye.setBorderPainted(false); // Xóa viền nút
                btnEye.setContentAreaFilled(false); 
                btnEye.setOpaque(true);
                btnEye.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Đưa chuột vào biến thành hình bàn tay
                
                return btnEye;
            }
        });
        // ==========================================

        // Khởi tạo Sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(15, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // --- 3. LOGIC TÌM KIẾM REAL-TIME ---
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(DocumentEvent e) { search(); }
            @Override
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) giúp tìm kiếm không phân biệt hoa thường
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        add(pnSearch, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. BẮT SỰ KIỆN CLICK VÀO BẢNG DỰ ÁN ĐANG LÀM ---
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                // Nếu click đúng vào CỘT SỐ 5 (Cột Xem Chi Tiết)
                if (row >= 0 && col == 5) {
                    String maDA = table.getValueAt(row, 0).toString(); // Lấy mã Dự Án
                    
                    // 1. Tạo cái Khung Popup (JDialog)
                    JDialog dialog = new JDialog();
                    dialog.setTitle("Chi Tiết Dự Án - " + maDA);
                    dialog.setModal(true); // Bắt buộc người dùng xem xong phải đóng mới làm việc tiếp được
                    
                    // 2. Lấy cái Panel màu xanh của bạn ông nhét vào khung
                    // Lưu ý: Cấu trúc thư mục của ông là GUI.Panel_Frame nên gọi như này là chuẩn
                    GUI.Panel_Frame.PanelDetailsDuAn pnlDetails = new GUI.Panel_Frame.PanelDetailsDuAn(maDA);
                    dialog.add(pnlDetails);
                    
                    // 3. Hiện lên giữa màn hình
                    dialog.pack(); 
                    dialog.setLocationRelativeTo(null); 
                    dialog.setVisible(true); 
                }
            }
        });
    }

    private void loadData(String maNV) {
        model.setRowCount(0); // Xóa data cũ trên bảng

        // Gọi ông BUS ra làm việc (KHÔNG CÓ MỘT CHỮ SQL NÀO Ở ĐÂY NỮA!)
        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<Object[]> listDA = bus.getDuAnDangLamCuaNhanVien(maNV);

        // Duyệt danh sách và đắp lên JTable
        for (Object[] row : listDA) {
            // Đổ vào bảng. Cột cuối cùng ném đại chữ "Button" vào, 
            // bộ vẽ (Renderer) ở trên của ông sẽ tự động đè hình con mắt lên!
            model.addRow(new Object[]{
                row[0], // Mã DA
                row[1], // Tên DA
                row[2], // Ngày Bắt Đầu (Đã được DAO format)
                row[3], // Trạng Thái
                row[4], // Vai Trò
                "Button" // Nút xem chi tiết
            });
        }
    }
}
