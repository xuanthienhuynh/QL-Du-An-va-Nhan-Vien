package GUI.Panel_Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelItemNhanVien extends JPanel {

    // Khai báo biến ẩn để giữ Mã NV cho các nút bấm hoạt động
    private JLabel lblMaNV;

    // Hàm tạo nhận dữ liệu từ vòng lặp ở SepGUI
    public PanelItemNhanVien(String ma, String ten, String vaiTro) {
        initComponentsCustom(ma, ten, vaiTro);
    }

    private void initComponentsCustom(String ma, String ten, String vaiTro) {
        // Cấu hình panel chính (Dạng Card giống hệt Phòng Ban)
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(1920, 80)); // Ép chiều cao cố định
        setPreferredSize(new Dimension(800, 80));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)), // Kẻ gạch ngang xám nhạt
                new EmptyBorder(10, 20, 10, 20) // Đệm lề
        ));

        // Lưu tạm mã NV vào biến ẩn để 2 hàm Click ở dưới lấy ra dùng
        lblMaNV = new JLabel(ma);

        // --- CỘT TRÁI (THÔNG TIN NHÂN VIÊN) ---
        JPanel pnlInfo = new JPanel(new GridLayout(2, 1));
        pnlInfo.setBackground(Color.WHITE);

        JLabel lblTenNVTitle = new JLabel(ten.toUpperCase() + " (" + ma + ")");
        lblTenNVTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTenNVTitle.setForeground(new Color(0, 51, 153)); // Xanh đậm giống form kia

        JLabel lblVaiTroTitle = new JLabel("Vai trò / Chức vụ: " + vaiTro);
        lblVaiTroTitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblVaiTroTitle.setForeground(Color.GRAY);

        pnlInfo.add(lblTenNVTitle);
        pnlInfo.add(lblVaiTroTitle);
        add(pnlInfo, BorderLayout.CENTER);

        // --- CỘT PHẢI (CÁC NÚT CHỨC NĂNG) ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        pnlButtons.setBackground(Color.WHITE);

        // Tạo nút bấm phẳng (kết hợp cả icon của bạn và màu sắc xịn sò)
        JButton btnXem = taoNut("Xem Chi Tiết", new Color(0, 153, 204), "/img/icons8-eye-30.png");
        JButton btnSua = taoNut("Chỉnh Sửa", new Color(255, 153, 0), "/img/icons8-edit-30.png");

        // Gắn nguyên si logic Click cũ của bạn vào 2 nút mới
        btnXem.addActionListener(e -> detailNhanVien_btnActionPerformed(e));
        btnSua.addActionListener(e -> btnEditActionPerformed(e));

        pnlButtons.add(btnXem);
        pnlButtons.add(btnSua);

        add(pnlButtons, BorderLayout.EAST);
    }

    // Hàm tạo Nút bấm Đẹp + Gắn Icon
    private JButton taoNut(String text, Color bgColor, String iconPath) {
        JButton btn = new JButton(text);
        try {
            java.net.URL imgURL = getClass().getResource(iconPath);
            if (imgURL != null) {
                // Thu nhỏ icon lại một xíu (20x20) để đi kèm với text cho cân đối
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
        }

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); // Xóa khung viền gạch chấm khi click
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        return btn;
    }

    // ========================================================
    // GIỮ NGUYÊN 100% LOGIC XỬ LÝ SỰ KIỆN CŨ CỦA BẠN
    // ========================================================
    private void detailNhanVien_btnActionPerformed(java.awt.event.ActionEvent evt) {
        String maNV = lblMaNV.getText(); // Vẫn lấy được mã bình thường
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) parentWindow, "Thông Tin Chi Tiết", true);

        PanelDetailsNhanVien panelDetails = new PanelDetailsNhanVien(maNV);
        dialog.getContentPane().add(panelDetails);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        String maNV = lblMaNV.getText();
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) parentWindow, "Chỉnh Sửa Nhân Viên",
                true);

        PanelEditNhanVien panelEdit = new PanelEditNhanVien(dialog, maNV);
        dialog.getContentPane().add(panelEdit);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}