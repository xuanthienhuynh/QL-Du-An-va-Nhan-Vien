package GUI.NhanVien_component;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
        setBackground(new Color(245, 246, 247)); // Nền tổng xám siêu nhạt cho hiện đại

        // ==========================================
        // 1. THANH TÌM KIẾM (NORTH) - LÀM ĐẸP
        // ==========================================
        JPanel pnSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pnSearch.setBackground(new Color(245, 246, 247));

        JLabel lblSearch = new JLabel("Tìm kiếm dự án:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSearch.setForeground(new Color(50, 50, 50));

        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtSearch.setPreferredSize(new Dimension(300, 40));
        // Viền Textbox tinh tế
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(5, 10, 5, 10)));

        pnSearch.add(lblSearch);
        pnSearch.add(txtSearch);

        // ==========================================
        // 2. BẢNG DỮ LIỆU (CENTER) - LỘT XÁC
        // ==========================================
        String[] columns = { "Mã Dự Án", "Tên Dự Án", "Ngày Bắt Đầu", "Trạng Thái", "Vai Trò", "Chi Tiết" };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5)
                    return Icon.class;
                return super.getColumnClass(columnIndex);
            }
        };
        table = new JTable(model);

        // --- STYLE CHO BẢNG ---
        table.setRowHeight(60);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Tăng size chữ lên 15
        table.setForeground(new Color(20, 20, 20)); // Ép chữ màu Đen đậm để cực dễ đọc
        table.setSelectionBackground(new Color(153, 204, 255)); // Màu xanh lơ đậm hơn một chút khi click chọn
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(200, 200, 200)); // Kẻ ngang rõ hơn một chút

        // --- TRỊ LỖI NIMBUS: ÉP HEADER PHẢI LÀM MÀU XANH ĐẬM ---
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0, 0, 153)); // Xanh đậm của công ty
        headerRenderer.setForeground(Color.WHITE); // Chữ trắng nổi bật
        headerRenderer.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font to và in đậm
        headerRenderer.setHorizontalAlignment(JLabel.CENTER); // Căn giữa

        // Bôi viền trắng nhẹ giữa các cột trên Header cho đẹp
        headerRenderer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 255, 255, 80)),
                new EmptyBorder(0, 5, 0, 5)));

        // Áp dụng bộ Renderer mới cứng này vào Header của bảng
        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.getTableHeader().setPreferredSize(new Dimension(100, 50)); // Nới cao Header

        // --- CĂN GIỮA DỮ LIỆU CÁC CỘT (GIỮ NGUYÊN HOẶC CHỈNH LẠI THEO FILE CỦA BẠN)
        // ---
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Tuỳ file mà bạn set căn giữa cho đúng cột nhé:
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã DA
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Ngày

        // --- STYLE CHO HEADER TỆP MÀU CÔNG TY ---
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0, 0, 153)); // Xanh đậm giống Header
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(100, 45));
        table.getTableHeader().setOpaque(false);
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // --- CĂN GIỮA DỮ LIỆU CÁC CỘT (Trừ cột Tên) ---
        // DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Mã DA
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Ngày
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Trạng thái
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Vai trò

        // --- RENDERER NÚT XEM CHI TIẾT CỦA BẠN ---
        table.getColumnModel().getColumn(5).setCellRenderer(new javax.swing.table.TableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JButton btnEye = new JButton();
                try {
                    java.net.URL imgURL = getClass().getResource("/img/icons8-eye-48.png");
                    if (imgURL != null) {
                        btnEye.setIcon(new ImageIcon(imgURL));
                    } else {
                        btnEye.setText("Xem");
                    }
                } catch (Exception e) {
                }

                btnEye.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                btnEye.setBorderPainted(false);
                btnEye.setContentAreaFilled(false);
                btnEye.setOpaque(true);
                btnEye.setCursor(new Cursor(Cursor.HAND_CURSOR));
                return btnEye;
            }
        });

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Xóa viền bao quanh bảng
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Tạo viền bọc ngoài JScrollPane cho đẹp
        JPanel pnlTableWrapper = new JPanel(new BorderLayout());
        pnlTableWrapper.setBackground(Color.WHITE);
        pnlTableWrapper.setBorder(new EmptyBorder(0, 20, 20, 20)); // Margin cách 2 bên
        pnlTableWrapper.add(scrollPane, BorderLayout.CENTER);

        // ==========================================
        // 3. LOGIC TÌM KIẾM REAL-TIME
        // ==========================================
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search();
            }

            private void search() {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        add(pnSearch, BorderLayout.NORTH);
        add(pnlTableWrapper, BorderLayout.CENTER);

        // ==========================================
        // 4. BẮT SỰ KIỆN CLICK VÀO NÚT
        // ==========================================
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());

                if (row >= 0 && col == 5) {
                    String maDA = table.getValueAt(row, 0).toString();

                    JDialog dialog = new JDialog();
                    dialog.setTitle("Chi Tiết Dự Án - " + maDA);
                    dialog.setModal(true);

                    GUI.Panel_Frame.PanelDetailsDuAn pnlDetails = new GUI.Panel_Frame.PanelDetailsDuAn(maDA);
                    dialog.add(pnlDetails);

                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            }
        });
    }

    private void loadData(String maNV) {
        model.setRowCount(0);

        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<Object[]> listDA = bus.getDuAnDangLamCuaNhanVien(maNV);

        for (Object[] row : listDA) {
            model.addRow(new Object[] {
                    row[0], row[1], row[2], row[3], row[4], "Button"
            });
        }
    }
}