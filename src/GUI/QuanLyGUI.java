package GUI;

import GUI.QuanLy_component.PhanCongForm;
import GUI.QuanLy_component.QuanLyDuAnForm;
import java.awt.BorderLayout;
import com.formdev.flatlaf.FlatLightLaf;

public class QuanLyGUI extends javax.swing.JFrame {

        /**
         * Creates new form QuanLyGUI
         */
        public QuanLyGUI() {
                initComponents();
                initCustomTabs();
        }

        private void initCustomTabs() {
                // Tab 1: Phân Công
                PhanCongForm pnlPhanCong = new PhanCongForm();
                jPanel1.setLayout(new BorderLayout());
                jPanel1.add(pnlPhanCong, BorderLayout.CENTER);

                // Tab 2: Dự Án (Thêm phần này)
                QuanLyDuAnForm pnlDuAn = new QuanLyDuAnForm();
                jPanel2.setLayout(new BorderLayout());
                jPanel2.add(pnlDuAn, BorderLayout.CENTER);

                // Đặt tên Tab
                jTabbedPane1.setTitleAt(0, "Phân Công Nhân Sự");
                jTabbedPane1.setTitleAt(1, "Danh Mục Dự Án");
                jTabbedPane1.setTitleAt(2, "Quản Lý Nhân Viên");

                this.revalidate();
                this.repaint();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

                Header = new javax.swing.JPanel();
                leftGroupHeader = new javax.swing.JPanel();
                dangXuatBtn = new javax.swing.JButton();
                xinChaoText = new javax.swing.JLabel();
                tenCty = new javax.swing.JLabel();
                jTabbedPane1 = new javax.swing.JTabbedPane();
                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jPanel3 = new javax.swing.JPanel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Hệ thống quản lý ABC Group");

                Header.setBackground(new java.awt.Color(0, 0, 153));
                Header.setPreferredSize(new java.awt.Dimension(1280, 100));

                leftGroupHeader.setBackground(new java.awt.Color(0, 0, 153));

                dangXuatBtn.setBackground(new java.awt.Color(255, 255, 255));
                dangXuatBtn.setFont(new java.awt.Font("Dialog", 1, 14));
                dangXuatBtn.setText("Đăng xuất");
                dangXuatBtn.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                dangXuatBtnActionPerformed(evt);
                        }
                });

                xinChaoText.setFont(new java.awt.Font("Dialog", 1, 14));
                xinChaoText.setForeground(new java.awt.Color(255, 255, 255));
                xinChaoText.setText("Xin chào Quản Trị Viên");

                javax.swing.GroupLayout leftGroupHeaderLayout = new javax.swing.GroupLayout(leftGroupHeader);
                leftGroupHeader.setLayout(leftGroupHeaderLayout);
                leftGroupHeaderLayout.setHorizontalGroup(
                                leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                                                                .addContainerGap(56, Short.MAX_VALUE)
                                                                .addGroup(leftGroupHeaderLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(dangXuatBtn,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                158,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(xinChaoText,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                275,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
                leftGroupHeaderLayout.setVerticalGroup(
                                leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                                                                .addGap(5, 5, 5)
                                                                .addComponent(xinChaoText,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                37,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(dangXuatBtn)
                                                                .addContainerGap(14, Short.MAX_VALUE)));

                tenCty.setFont(new java.awt.Font("Arial", 1, 48));
                tenCty.setForeground(new java.awt.Color(255, 102, 0));
                tenCty.setText("ABC GROUP");

                javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
                Header.setLayout(HeaderLayout);
                HeaderLayout.setHorizontalGroup(
                                HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(HeaderLayout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(tenCty,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                319,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                675,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(leftGroupHeader,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                HeaderLayout.setVerticalGroup(
                                HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(HeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(HeaderLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(tenCty,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                HeaderLayout.createSequentialGroup()
                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                .addComponent(leftGroupHeader,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));

                // jPanel1 là Tab Phân Công
                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 1287, Short.MAX_VALUE));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 582, Short.MAX_VALUE));
                jTabbedPane1.addTab("Phân Công", jPanel1);

                // jPanel2 và jPanel3 (Dự phòng cho Dự án và Nhân viên)
                jTabbedPane1.addTab("Dự Án", jPanel2);
                jTabbedPane1.addTab("Nhân Viên", jPanel3);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 1292,
                                                                Short.MAX_VALUE)
                                                .addComponent(jTabbedPane1));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(Header,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTabbedPane1)));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>

        private void dangXuatBtnActionPerformed(java.awt.event.ActionEvent evt) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?");
                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        this.dispose();
                        new DangNhap().setVisible(true);
                }
        }

        public static void main(String args[]) {
                try {
                        com.formdev.flatlaf.FlatLightLaf.setup();
                } catch (Exception ex) {
                        System.err.println("Failed to initialize FlatLaf");
                }

                java.awt.EventQueue.invokeLater(() -> {
                        new QuanLyGUI().setVisible(true);
                });
        }

        // Variables declaration - do not modify
        private javax.swing.JPanel Header;
        private javax.swing.JButton dangXuatBtn;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JPanel leftGroupHeader;
        private javax.swing.JLabel tenCty;
        private javax.swing.JLabel xinChaoText;
        // End of variables declaration
}