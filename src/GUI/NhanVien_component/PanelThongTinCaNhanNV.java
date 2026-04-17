package GUI.NhanVien_component;

import DTO.NhanVien_DTO;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class PanelThongTinCaNhanNV extends JPanel {

    private JLabel lblHoTenTitle, lblAvatar;
    private JTextField txtMaNV, txtGioiTinh, txtDiaChi, txtLuongCoBan, txtChucVu, txtPhongBan, txtChiNhanh;

    public PanelThongTinCaNhanNV(String maNVLoggedIn) {
        initComponents();
        loadDuLieuThucTe(maNVLoggedIn);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // =======================================================
        // 1. CỘT TRÁI (PANEL XANH ĐẬM CHỨA TÊN VÀ AVATAR)
        // =======================================================
        JPanel pnlWest = new JPanel();
        pnlWest.setBackground(new Color(0, 0, 153));
        pnlWest.setPreferredSize(new Dimension(300, 0));
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
        pnlWest.setBorder(new EmptyBorder(60, 20, 40, 20)); // Tăng đệm trên để đẩy avatar xuống giữa

        lblHoTenTitle = new JLabel("ĐANG TẢI...");
        lblHoTenTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHoTenTitle.setForeground(Color.WHITE);
        lblHoTenTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlWest.add(lblHoTenTitle);
        pnlWest.add(Box.createVerticalStrut(30));

        lblAvatar = new JLabel();
        try {
            java.net.URL imgURL = getClass().getResource("/img/default_avatar.jpg");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // CẬP NHẬT: Kích thước 200x200 chuẩn vuông
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(scaledImage));
                lblAvatar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
            } else {
                lblAvatar.setText("ẢNH ĐẠI DIỆN");
                lblAvatar.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                lblAvatar.setForeground(Color.WHITE);
                lblAvatar.setPreferredSize(new Dimension(200, 200));
                lblAvatar.setMaximumSize(new Dimension(200, 200));
                lblAvatar.setBorder(BorderFactory.createDashedBorder(Color.WHITE));
                lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
        }

        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlWest.add(lblAvatar);

        // Đã loại bỏ nút Đổi mật khẩu tại đây

        pnlWest.add(Box.createVerticalGlue());
        add(pnlWest, BorderLayout.WEST);

        // =======================================================
        // 2. CỘT PHẢI (PANEL TRẮNG CHỨA THÔNG TIN)
        // =======================================================
        JPanel pnlWrapper = new JPanel(new GridBagLayout());
        pnlWrapper.setBackground(Color.WHITE);

        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 25, 15, 25);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 15);
        Font fontText = new Font("Segoe UI", Font.PLAIN, 15);

        Border borderText = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(10, 15, 10, 15));

        int row = 0;
        txtMaNV = taoDongThongTin(pnlCenter, gbc, "Mã Nhân Viên:", row++, fontLabel, fontText, borderText);
        txtGioiTinh = taoDongThongTin(pnlCenter, gbc, "Giới Tính:", row++, fontLabel, fontText, borderText);
        txtDiaChi = taoDongThongTin(pnlCenter, gbc, "Địa Chỉ:", row++, fontLabel, fontText, borderText);
        txtLuongCoBan = taoDongThongTin(pnlCenter, gbc, "Mức Lương:", row++, fontLabel, fontText, borderText);
        txtChucVu = taoDongThongTin(pnlCenter, gbc, "Chức Vụ:", row++, fontLabel, fontText, borderText);
        txtPhongBan = taoDongThongTin(pnlCenter, gbc, "Phòng Ban:", row++, fontLabel, fontText, borderText);
        txtChiNhanh = taoDongThongTin(pnlCenter, gbc, "Chi Nhánh:", row++, fontLabel, fontText, borderText);

        pnlWrapper.add(pnlCenter);
        add(pnlWrapper, BorderLayout.CENTER);
    }

    private JTextField taoDongThongTin(JPanel panel, GridBagConstraints gbc, String label, int yPos, Font fLabel,
            Font fText, Border border) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lbl = new JLabel(label);
        lbl.setFont(fLabel);
        lbl.setForeground(new Color(70, 70, 70));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txt = new JTextField("");
        txt.setPreferredSize(new Dimension(400, 45));
        txt.setFont(fText);
        txt.setBorder(border);
        txt.setEditable(false);
        txt.setDisabledTextColor(new Color(40, 40, 40));
        txt.setBackground(new Color(245, 246, 247));
        panel.add(txt, gbc);

        return txt;
    }

    private void loadDuLieuThucTe(String maNV) {
        DAO.NhanVienDAO dao = new DAO.NhanVienDAO();
        NhanVien_DTO nv = dao.layThongTinNhanVien(maNV);

        if (nv != null) {
            lblHoTenTitle.setText(nv.getHoTen().toUpperCase());
            txtMaNV.setText(nv.getMaNV());
            txtGioiTinh.setText(nv.getGioiTinh());

            String diaChiFull = nv.getDcSoNha() + ", " + nv.getDcPhuong() + ", " + nv.getDcTinh();
            txtDiaChi.setText(diaChiFull.replace("null, ", "").replace("null", "Trống"));

            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            txtLuongCoBan.setText(format.format(nv.getLuong()));

            txtChucVu.setText(nv.getVaiTro());
            txtPhongBan.setText(nv.getMaPB());
            txtChiNhanh.setText(nv.getMaCN());
        }
    }
}