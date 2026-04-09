/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author huynh
 */
public class NhanVienGUI extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienGUI
     */
    public NhanVienGUI() {
        initComponents();
    }

    private String maNVDangNhap;

    public NhanVienGUI(String maNV) {
        this.maNVDangNhap = maNV;
        initComponents(); // 1. BẮT BUỘC ĐỂ ĐẦU TIÊN
        xinChaoText.setText("Xin chào " + maNVDangNhap);
        initCustomTabs(); // 2. GỌI HÀM RÁP PANEL SAU CÙNG
    }

   private void initCustomTabs() {
        try {
            // ==========================================
            // 1.KHỔ CHỮ VÀ LẤY TÊN NHÂN VIÊN
            // ==========================================
            this.setSize(1300, 750); 
            this.setLocationRelativeTo(null); 

   
            String tenNhanVien = null; 
            try {
                // Chỉ móc DB nếu mã NV không bị null
                if (this.maNVDangNhap != null && !this.maNVDangNhap.isEmpty()) {
                    java.sql.Connection conn = DAO.database.createConnection();
                    java.sql.PreparedStatement ps = conn.prepareStatement("SELECT HoTen FROM NhanVien WHERE MaNV = ?");
                    ps.setString(1, this.maNVDangNhap);
                    java.sql.ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        tenNhanVien = rs.getString("HoTen"); 
                    }
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println("Lỗi load tên NV: " + ex.getMessage());
            }

           
            String tenHienThi = "NHÂN VIÊN"; // Giá trị mặc định an toàn nhất
            if (tenNhanVien != null && !tenNhanVien.trim().isEmpty()) {
                tenHienThi = tenNhanVien.toUpperCase();
            } else if (this.maNVDangNhap != null && !this.maNVDangNhap.trim().isEmpty()) {
                tenHienThi = this.maNVDangNhap.toUpperCase();
            }

            // ==========================================
            // 3. TẠO BANNER HEADER
            // ==========================================
            JPanel pnlHeader = new JPanel(new BorderLayout());
            pnlHeader.setBackground(new Color(0, 0, 153)); 
            pnlHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20)); 

            JLabel lblLogo = new JLabel("ABC GROUP");
            lblLogo.setFont(new Font("Arial", Font.BOLD, 36)); 
            lblLogo.setForeground(new Color(255, 102, 0)); // Màu cam chuẩn
            pnlHeader.add(lblLogo, BorderLayout.WEST);

            JPanel pnlRight = new JPanel(new java.awt.GridLayout(2, 1, 5, 5));
            pnlRight.setOpaque(false); 

            // Xin chào + TÊN NHÂN VIÊN 
            JLabel lblChao = new JLabel("Xin chào: " + tenHienThi, javax.swing.SwingConstants.RIGHT);
            lblChao.setFont(new Font("Arial", Font.BOLD, 16));
            lblChao.setForeground(Color.WHITE); 

            JButton btnDangXuat = new JButton("Đăng xuất");
            btnDangXuat.setFont(new Font("Arial", Font.BOLD, 12));
            btnDangXuat.addActionListener(e -> {
                this.dispose();
                new GUI.DangNhap().setVisible(true); 
            });

            pnlRight.add(lblChao);
            pnlRight.add(btnDangXuat);
            pnlHeader.add(pnlRight, BorderLayout.EAST);

            // ==========================================
            // 4. KHỞI TẠO 3 TAB
            // ==========================================
            JTabbedPane myTabbedPane = new JTabbedPane();
            myTabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

            // --- Xử lý Tab 1 ---
            try {
                // Nhét thẳng mã nhân viên vào trong ngoặc tròn luôn!
                GUI.NhanVien_component.PanelThongTinCaNhanNV tab1 = new GUI.NhanVien_component.PanelThongTinCaNhanNV(this.maNVDangNhap);
                if (tab1 != null) myTabbedPane.addTab("Thông tin cá nhân", tab1);
            } catch (Exception e) { e.printStackTrace(); }

            // --- Xử lý Tab 2 ---
            try {
                // Nhét thẳng mã nhân viên vào trong ngoặc tròn luôn!
                GUI.NhanVien_component.PanelDuAnDangLamNV tab2 = new GUI.NhanVien_component.PanelDuAnDangLamNV(this.maNVDangNhap);
                if (tab2 != null) myTabbedPane.addTab("Dự án đang làm", tab2);
            } catch (Exception e) { e.printStackTrace(); }

            // --- Xử lý Tab 3 ---
            try {
                // Nhét thẳng mã nhân viên vào trong ngoặc tròn luôn!
                GUI.NhanVien_component.PanelLichSuDuAnNV tab3 = new GUI.NhanVien_component.PanelLichSuDuAnNV(this.maNVDangNhap);
                if (tab3 != null) myTabbedPane.addTab("Lịch sử dự án", tab3);
            } catch (Exception e) { e.printStackTrace(); } {}

            // ==========================================
            // 5. RÁP VÀO JFRAME CHÍNH
            // ==========================================
            this.getContentPane().removeAll();
            this.getContentPane().setLayout(new BorderLayout());
            this.getContentPane().add(pnlHeader, BorderLayout.NORTH); 
            this.getContentPane().add(myTabbedPane, BorderLayout.CENTER);

            this.revalidate();
            this.repaint();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gọi initCustomTabs trong constructor
    {
        initCustomTabs();
    }
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        sepGUI1 = new GUI.SepGUI();
        Header = new javax.swing.JPanel();
        tenCty = new javax.swing.JLabel();
        leftGroupHeader = new javax.swing.JPanel();
        dangXuatBtn = new javax.swing.JButton();
        xinChaoText = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Header.setBackground(new java.awt.Color(0, 0, 153));
        Header.setPreferredSize(new java.awt.Dimension(1280, 100));

        tenCty.setBackground(new java.awt.Color(255, 0, 51));
        tenCty.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        tenCty.setForeground(new java.awt.Color(255, 102, 0));
        tenCty.setText("ABC GROUP");

        leftGroupHeader.setBackground(new java.awt.Color(0, 0, 153));

        dangXuatBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        dangXuatBtn.setText("Đăng xuất");
        dangXuatBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dangXuatBtnActionPerformed(evt);
            }
        });

        xinChaoText.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        xinChaoText.setForeground(new java.awt.Color(255, 255, 255));
        xinChaoText.setText("Xin chào + Tên Nhân Viên");

        javax.swing.GroupLayout leftGroupHeaderLayout = new javax.swing.GroupLayout(leftGroupHeader);
        leftGroupHeader.setLayout(leftGroupHeaderLayout);
        leftGroupHeaderLayout.setHorizontalGroup(
            leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dangXuatBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xinChaoText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        leftGroupHeaderLayout.setVerticalGroup(
            leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(xinChaoText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dangXuatBtn)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenCty, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 311, Short.MAX_VALUE)
                .addComponent(leftGroupHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tenCty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(leftGroupHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 961, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 646, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dangXuatBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dangXuatBtnActionPerformed
        this.dispose(); // Close the current window to log out
    }// GEN-LAST:event_dangXuatBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienGUI("NV01").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JButton dangXuatBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel leftGroupHeader;
    private GUI.SepGUI sepGUI1;
    private javax.swing.JLabel tenCty;
    private javax.swing.JLabel xinChaoText;
    // End of variables declaration//GEN-END:variables
}
