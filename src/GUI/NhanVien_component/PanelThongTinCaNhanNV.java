package GUI.NhanVien_component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import DTO.NhanVien_DTO;

public class PanelThongTinCaNhanNV extends JPanel {

    // Khai báo các thành phần giao diện
    private JLabel lblHoTenTitle, lblAvatar;
    private JTextField txtMaNV, txtGioiTinh, txtDiaChi, txtLuongCoBan, txtChucVu, txtPhongBan, txtChiNhanh;

    // BƯỚC 1: Constructor giờ sẽ nhận Mã Nhân Viên đang đăng nhập vào
    public PanelThongTinCaNhanNV(String maNVLoggedIn) {
        initComponents();
        loadDuLieuThucTe(maNVLoggedIn); // Gọi hàm kéo data từ DB lên
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- CỘT TRÁI (PANEL XANH ĐẬM CHỨA TÊN VÀ AVATAR) ---
        JPanel pnlWest = new JPanel();
        pnlWest.setBackground(new Color(0, 0, 204)); 
        pnlWest.setPreferredSize(new Dimension(280, 0)); 
        pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS)); 
        pnlWest.setBorder(new EmptyBorder(30, 10, 30, 10)); 

        // Tiêu đề tên (Sẽ được set lại khi có data thật)
        lblHoTenTitle = new JLabel("ĐANG TẢI...");
        lblHoTenTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblHoTenTitle.setForeground(Color.WHITE);
        lblHoTenTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlWest.add(lblHoTenTitle);
        pnlWest.add(Box.createVerticalStrut(20)); 

        // Ảnh đại diện
        lblAvatar = new JLabel();
        try {
            java.net.URL imgURL = getClass().getResource("/img/default_avatar.jpg");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image scaledImage = icon.getImage().getScaledInstance(180, 220, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(scaledImage));
                lblAvatar.setBorder(new LineBorder(Color.WHITE, 1));
            } else {
                lblAvatar.setText("Lỗi ảnh");
                lblAvatar.setForeground(Color.WHITE);
            }
        } catch (Exception e) {}
        
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlWest.add(lblAvatar);
        pnlWest.add(Box.createVerticalGlue()); 

        // Nút đăng xuất
        JButton btnDangXuatLeft = new JButton("ĐĂNG XUẤT");
        btnDangXuatLeft.setFont(new Font("Arial", Font.BOLD, 14));
        btnDangXuatLeft.setBackground(Color.WHITE);
        btnDangXuatLeft.setForeground(Color.BLACK);
        btnDangXuatLeft.setFocusPainted(false);
        btnDangXuatLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlWest.add(btnDangXuatLeft);

        add(pnlWest, BorderLayout.WEST);

        // --- CỘT PHẢI (PANEL TRẮNG CHỨA CÁC TRƯỜNG THÔNG TIN) ---
        JPanel pnlCenter = new JPanel();
        pnlCenter.setBackground(Color.WHITE); 
        pnlCenter.setLayout(new GridBagLayout()); 
        pnlCenter.setBorder(new EmptyBorder(50, 60, 50, 60)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.WEST; 

        Font fontLabel = new Font("Arial", Font.BOLD, 16);
        Font fontText = new Font("Arial", Font.PLAIN, 16);
        Border borderText = new LineBorder(Color.BLACK, 1);

        // Hàm tạo nhanh một dòng dữ liệu để code đỡ dài
        int row = 0;
        txtMaNV = taoDongThongTin(pnlCenter, gbc, "MÃ NHÂN VIÊN :", row++, fontLabel, fontText, borderText);
        txtGioiTinh = taoDongThongTin(pnlCenter, gbc, "GIỚI TÍNH :", row++, fontLabel, fontText, borderText);
        txtDiaChi = taoDongThongTin(pnlCenter, gbc, "ĐỊA CHỈ :", row++, fontLabel, fontText, borderText);
        txtLuongCoBan = taoDongThongTin(pnlCenter, gbc, "LƯƠNG CƠ BẢN :", row++, fontLabel, fontText, borderText);
        txtChucVu = taoDongThongTin(pnlCenter, gbc, "CHỨC VỤ :", row++, fontLabel, fontText, borderText);
        txtPhongBan = taoDongThongTin(pnlCenter, gbc, "PHÒNG BAN :", row++, fontLabel, fontText, borderText);
        txtChiNhanh = taoDongThongTin(pnlCenter, gbc, "CHI NHÁNH :", row++, fontLabel, fontText, borderText);

        add(pnlCenter, BorderLayout.CENTER);
    }

    // Hàm phụ trợ giúp tạo các ô Textfield ngắn gọn hơn
    private JTextField taoDongThongTin(JPanel panel, GridBagConstraints gbc, String label, int yPos, Font fLabel, Font fText, Border border) {
        gbc.gridx = 0; gbc.gridy = yPos;
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        JLabel lbl = new JLabel(label);
        lbl.setFont(fLabel);
        panel.add(lbl, gbc);

        gbc.gridx = 1; gbc.gridy = yPos;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField txt = new JTextField("");
        txt.setFont(fText);
        txt.setBorder(border);
        txt.setEditable(false);
        txt.setDisabledTextColor(Color.BLACK);
        txt.setBackground(Color.WHITE);
        panel.add(txt, gbc);
        return txt;
    }

    // ====================================================================
    // BƯỚC 2: HÀM LẤY DỮ LIỆU TỪ DATABASE VÀ ĐẬP VÀO GIAO DIỆN
    // ====================================================================
    private void loadDuLieuThucTe(String maNV) {
        // Dùng thẳng DAO của bạn ông viết (hoặc gọi qua BUS nếu ông có NhanVienBUS)
        DAO.NhanVienDAO dao = new DAO.NhanVienDAO();
        NhanVien_DTO nv = dao.layThongTinNhanVien(maNV);

        if (nv != null) {
            // Đổ dữ liệu thật vào các ô TextBox
            lblHoTenTitle.setText(nv.getHoTen().toUpperCase());
            txtMaNV.setText(nv.getMaNV());
            txtGioiTinh.setText(nv.getGioiTinh());
            
            // Nối 3 trường địa chỉ lại
            String diaChiFull = nv.getDcSoNha() + ", " + nv.getDcPhuong() + ", " + nv.getDcTinh();
            txtDiaChi.setText(diaChiFull.replace("null, ", "").replace("null", "Chưa cập nhật"));

            // Format tiền tệ
            Locale locale = new Locale("vi", "VN");
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            txtLuongCoBan.setText(format.format(nv.getLuong()));

            txtChucVu.setText(nv.getVaiTro());
            txtPhongBan.setText(nv.getMaPB());
            txtChiNhanh.setText(nv.getMaCN());
        }
    }
}