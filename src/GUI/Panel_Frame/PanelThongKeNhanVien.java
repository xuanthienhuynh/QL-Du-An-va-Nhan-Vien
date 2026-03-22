package GUI.Panel_Frame;

import javax.swing.JDialog;

public class PanelThongKeNhanVien extends javax.swing.JPanel {

    // 1. TẠO 2 BIẾN TOÀN CỤC ĐỂ LƯU MÃ VÀ TÊN CHO NÚT BẤM SỬ DỤNG
    private String maNV_global;
    private String tenNV_global;

    public PanelThongKeNhanVien(String maNV, String tenNV, String maPB, String chiSo) {
        initComponents();

        // 2. Lưu thông tin vào biến toàn cục
        this.maNV_global = maNV;
        this.tenNV_global = tenNV;

        // 3. Gán dữ liệu lên nhãn
        lblMaNV.setText(maNV);
        lblTenNV.setText(tenNV);
        lblMaPB.setText(maPB);
        lblChiSo.setText(chiSo);

        // 4. Bố cục lại thành 5 cột
        this.removeAll();
        this.setLayout(new java.awt.GridLayout(1, 5));

        // 5. Gắn thành phần lên Panel
        this.add(lblMaNV);
        this.add(lblTenNV);
        this.add(lblMaPB);
        this.add(lblChiSo);
        this.add(jButton1);

        // 6. Căn chỉnh lề, kích thước
        this.setMaximumSize(new java.awt.Dimension(32767, 40));
        this.setPreferredSize(new java.awt.Dimension(10, 40));
        this.setMinimumSize(new java.awt.Dimension(0, 40));

        lblTenNV.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        lblChiSo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 15));

        this.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(200, 200, 200)));
        this.setBackground(java.awt.Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblMaPB = new javax.swing.JLabel();
        lblChiSo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(700, 40));

        jPanel1.setBackground(new java.awt.Color(153, 102, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 40));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        lblMaNV.setText("Tên Nhân viên");
        jPanel1.add(lblMaNV);

        lblTenNV.setText("jLabel1");
        jPanel1.add(lblTenNV);

        lblMaPB.setText("jLabel1");
        jPanel1.add(lblMaPB);

        lblChiSo.setText("jLabel1");
        jPanel1.add(lblChiSo);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-eye-24.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    // ---> SỰ KIỆN CLICK VÀO NÚT CON MẮT ĐÃ ĐƯỢC FIX SẠCH LỖI <---
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // 1. Tạo một cửa sổ Dialog mới (Cửa sổ nổi)
        JDialog dialog = new JDialog();
        dialog.setTitle("Chi Tiết Nhân Viên: " + tenNV_global);
        dialog.setModal(true); // Khóa cửa sổ chính lại khi bảng chi tiết đang mở

        // 2. Gọi Panel Details và truyền mã NV vào
        GUI.Panel_Frame.PanelDetailsNhanVienThongKe panelDetails = new GUI.Panel_Frame.PanelDetailsNhanVienThongKe(
                maNV_global);
        dialog.getContentPane().add(panelDetails);

        // 3. Hiển thị lên giữa màn hình
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }// GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChiSo;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaPB;
    private javax.swing.JLabel lblTenNV;
    // End of variables declaration//GEN-END:variables
}