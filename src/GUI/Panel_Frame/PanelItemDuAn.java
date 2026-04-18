package GUI.Panel_Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class PanelItemDuAn extends JPanel {

    private String maDA;
    private String tenDA;
    private String trangThai;
    private Date ngayKT;

    private JLabel lblTenDA_UI;
    private JButton btnDetail, btnEdit, btnEnd;
    private JPanel pnlInfo, pnlButtons;

    public PanelItemDuAn(String maDA, String tenDA, double DoanhThu, double chiPhi, Date ngayBD, Date ngayKT,
            String trangThai) {
        this.maDA = maDA;
        this.tenDA = tenDA;
        this.ngayKT = ngayKT;
        this.trangThai = trangThai;

        // BƯỚC 1: KIỂM TRA TRẠNG THÁI THỰC TẾ
        Date hienTai = new Date();
        // Nếu trạng thái là "KetThuc" HOẶC ngày kết thúc đã qua rồi
        boolean isDaKetThuc = "KetThuc".equalsIgnoreCase(trangThai) || (ngayKT != null && ngayKT.before(hienTai));

        initComponentsCustom(maDA, tenDA, DoanhThu, chiPhi, ngayBD, isDaKetThuc);
    }

    private void initComponentsCustom(String maDA, String tenDA, double DoanhThu, double chiPhi, Date ngayBD,
            boolean isKetThuc) {
        setLayout(new BorderLayout());
        setBackground(isKetThuc ? new Color(245, 246, 247) : Color.WHITE); // Nền xám nhạt nếu đã kết thúc
        setMaximumSize(new Dimension(1920, 80));
        setPreferredSize(new Dimension(800, 80));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(10, 20, 10, 20)));

        // --- CỘT TRÁI (THÔNG TIN) ---
        pnlInfo = new JPanel(new GridLayout(2, 1));
        pnlInfo.setOpaque(false);

        String hienThiTen = tenDA.toUpperCase() + " (" + maDA + ")";
        if (isKetThuc) {
            hienThiTen = "<html>" + tenDA.toUpperCase() + " <font color='red'>(Đã kết thúc)</font></html>";
        }

        lblTenDA_UI = new JLabel(hienThiTen);
        lblTenDA_UI.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTenDA_UI.setForeground(isKetThuc ? new Color(120, 120, 120) : new Color(0, 51, 153));

        // Tính toán tài chính
        double loiNhuan = DoanhThu - chiPhi;
        String loiNhuanText = String.format("%,.0f VNĐ", loiNhuan);
        String financialStatus = (loiNhuan > 0) ? "<font color='blue'>Lãi: " + loiNhuanText + "</font>"
                : (loiNhuan < 0) ? "<font color='red'>Lỗ: " + Math.abs(loiNhuan) + " VNĐ</font>" : "Hòa vốn";

        String ngayBDText = (ngayBD != null) ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayBD) : "...";
        JLabel lblSubInfo = new JLabel("<html>Ngày bắt đầu: <b>" + ngayBDText
                + "</b> &nbsp;&nbsp;|&nbsp;&nbsp; Tài chính: " + financialStatus + "</html>");
        lblSubInfo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSubInfo.setForeground(new Color(100, 100, 100));

        pnlInfo.add(lblTenDA_UI);
        pnlInfo.add(lblSubInfo);
        add(pnlInfo, BorderLayout.CENTER);

        // --- CỘT PHẢI (NÚT BẤM) ---
        pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlButtons.setOpaque(false);

        btnDetail = taoNut("Xem", new Color(0, 153, 204), "/img/icons8-eye-30.png");
        btnEdit = taoNut("Sửa", new Color(255, 153, 0), "/img/icons8-edit-30.png");
        btnEnd = taoNut("Kết thúc", new Color(204, 0, 0), "/img/icons8-delete-30.png");

        // BƯỚC 2: BLOCK CÁC NÚT DỰA TRÊN TRẠNG THÁI
        if (isKetThuc) {
            btnEdit.setVisible(false); // Ẩn nút Sửa
            btnEnd.setVisible(false); // Ẩn nút Kết thúc
        }

        btnDetail.addActionListener(e -> btnDetailActionPerformed(e));
        btnEdit.addActionListener(e -> btnEdit1ActionPerformed(e));
        btnEnd.addActionListener(e -> btnDeleteActionPerformed(e));

        pnlButtons.add(btnDetail);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnEnd);

        add(pnlButtons, BorderLayout.EAST);
    }

    private JButton taoNut(String text, Color bgColor, String iconPath) {
        JButton btn = new JButton(text);
        try {
            java.net.URL imgURL = getClass().getResource(iconPath);
            if (imgURL != null) {
                Image img = new ImageIcon(imgURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
        }
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        return btn;
    }

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN CHI TIẾT/SỬA/XÓA (GIỮ NGUYÊN NHƯ FILE TRƯỚC) ---
    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) parentWindow, "Thông Tin Chi Tiết", true);

        GUI.Panel_Frame.PanelDetailsDuAn panelDetails = new GUI.Panel_Frame.PanelDetailsDuAn(this.maDA);

        dialog.getContentPane().add(panelDetails);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) parentWindow, "Chỉnh Sửa Dự Án", true);

        GUI.Panel_Frame.PanelEditDuAn panelEdit = new GUI.Panel_Frame.PanelEditDuAn(dialog, this.maDA);

        dialog.getContentPane().add(panelEdit);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "Chuyển dự án:\n[" + this.maDA + "] - " + this.tenDA + "\nsang trạng thái 'Đã kết thúc'?",
                "Xác nhận kết thúc dự án",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            BUS.DuAnBUS bus = new BUS.DuAnBUS();
            DTO.DuAn_DTO da = bus.getChiTietDuAn(this.maDA);

            if (da != null) {
                if ("Đã kết thúc".equalsIgnoreCase(da.getTrangThai())
                        || "KetThuc".equalsIgnoreCase(da.getTrangThai())) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Dự án này đã kết thúc từ trước rồi!", "Cảnh báo",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    return;
                }

                da.chuyenSangTrangThaiKetThuc();
                boolean isUpdated = bus.capNhatTrangThaiDuAn(da);

                if (isUpdated) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "Đã cập nhật trạng thái xuống Database thành công!");

                    // --- CẬP NHẬT GIAO DIỆN (Logic xịn sò của Thiện) ---
                    btnEdit.setVisible(false); // Ẩn nút sửa
                    btnEnd.setVisible(false); // Ẩn nút xóa
                    lblTenDA_UI.setText(
                            "<html>" + this.tenDA.toUpperCase() + " <font color='red'>(Đã kết thúc)</font></html>");
                    lblTenDA_UI.setForeground(new java.awt.Color(120, 120, 120)); // Đổi màu chữ xám
                    this.setBackground(new java.awt.Color(245, 246, 247)); // Làm mờ nền thẻ

                    // Repaint lại thẻ để nhận màu nền mới
                    this.revalidate();
                    this.repaint();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Lỗi: Không thể lưu xuống Database!", "Lỗi",
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu dự án trên hệ thống!");
            }
        }
    }
}