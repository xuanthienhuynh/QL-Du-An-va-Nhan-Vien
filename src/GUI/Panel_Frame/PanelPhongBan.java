package GUI.Panel_Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelPhongBan extends JPanel {

    private String maPB;
    private String tenPB;
    private String maCN;

    public PanelPhongBan(String maPB, String tenPB, String maCN) {
        this.maPB = maPB;
        this.tenPB = tenPB;
        this.maCN = maCN;
        initComponents();
    }

    private void initComponents() {
        // Cấu hình panel chính (Dạng Card)
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(1920, 80)); // Ép chiều cao cố định
        setPreferredSize(new Dimension(800, 80));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)), // Kẻ gạch ngang dưới
                new EmptyBorder(10, 20, 10, 20)));

        // --- CỘT TRÁI (THÔNG TIN PHÒNG BAN) ---
        JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
        pnlInfo.setBackground(Color.WHITE);

        JLabel lblTenPB = new JLabel(tenPB.toUpperCase() + " (" + maPB + ")");
        lblTenPB.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTenPB.setForeground(new Color(0, 0, 153));

        JLabel lblChiNhanh = new JLabel("Thuộc: " + maCN);
        lblChiNhanh.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblChiNhanh.setForeground(Color.GRAY);

        pnlInfo.add(lblTenPB);
        pnlInfo.add(lblChiNhanh);
        add(pnlInfo, BorderLayout.CENTER);

        // --- CỘT PHẢI (CÁC NÚT CHỨC NĂNG) ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(Color.WHITE);

        JButton btnXem = taoNut("Xem Chi Tiết", new Color(0, 153, 204));
        JButton btnSua = taoNut("Chỉnh Sửa", new Color(255, 153, 0));
        JButton btnXoa = taoNut("Xóa", new Color(204, 0, 0));

        // Gắn sự kiện (Event) cơ bản cho các nút
        btnXem.addActionListener(e -> {
            // 1. Tạo một cái Popup (JDialog)
            JDialog dialog = new JDialog();
            dialog.setTitle("Chi Tiết Phòng Ban - " + maPB);
            dialog.setModal(true); // Bắt buộc người dùng xem xong phải đóng lại

            // 2. Gọi cái PanelDetailPhongBan mình vừa code ở Bước 2
            GUI.Panel_Frame.PanelDetailPhongBan pnlDetails = new GUI.Panel_Frame.PanelDetailPhongBan(maPB, tenPB, maCN);
            dialog.add(pnlDetails);

            // 3. Hiển thị lên giữa màn hình
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });

        btnSua.addActionListener(e -> {
            // Mở form Popup
            JDialog dialog = new JDialog();
            dialog.setTitle("Chỉnh sửa Phòng Ban - " + maPB);
            dialog.setModal(true);

            // Truyền Full thông tin (mã, tên, chi nhánh) sang form Sửa
            GUI.Panel_Frame.PanelEditPhongBan pnlEdit = new GUI.Panel_Frame.PanelEditPhongBan(dialog, maPB, tenPB,
                    maCN);
            dialog.add(pnlEdit);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            // BÍ QUYẾT: Sau khi tắt Form Sửa, ép giao diện SepGUI tải lại danh sách
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (window instanceof GUI.SepGUI) {
                ((GUI.SepGUI) window).loadDanhSachPhongBan();
            }
        });
        btnXoa.addActionListener(e -> {
            // 1. Hỏi xác nhận cực kỳ rõ ràng
            int ngonTayBam = javax.swing.JOptionPane.showConfirmDialog(this,
                    "BẠN CÓ CHẮC CHẮN MUỐN XÓA?\nPhòng ban: " + tenPB + " (" + maPB + ")\n"
                            + "Lưu ý: Hành động này không thể hoàn tác!",
                    "Cảnh báo nguy hiểm",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.ERROR_MESSAGE); // Hiện icon dấu chấm than đỏ

            // 2. Nếu Sếp chọn YES
            if (ngonTayBam == javax.swing.JOptionPane.YES_OPTION) {
                BUS.PhongBanBUS bus = new BUS.PhongBanBUS();
                String ketQua = bus.xoaPhongBan(maPB);

                if (ketQua.contains("thành công")) {
                    javax.swing.JOptionPane.showMessageDialog(this, ketQua);

                    // 3. LOAD LẠI GIAO DIỆN ĐỂ BIẾN MẤT
                    // Tìm cửa sổ cha (SepGUI) để ra lệnh load lại danh sách
                    java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
                    if (window instanceof GUI.SepGUI) {
                        ((GUI.SepGUI) window).loadDanhSachPhongBan();
                    }
                } else {
                    // Hiện lỗi nếu không xóa được (do còn nhân viên)
                    javax.swing.JOptionPane.showMessageDialog(this, ketQua, "Lỗi xóa dữ liệu",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        pnlButtons.add(btnXem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);

        add(pnlButtons, BorderLayout.EAST);
    }

    // Hàm phụ trợ tạo nút cho đẹp
    private JButton taoNut(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        return btn;
    }
}