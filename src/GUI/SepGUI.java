/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DTO.NhanVien_DTO;
import GUI.Panel_Frame.PanelItemDuAn;
import GUI.Panel_Frame.PanelItemNhanVien;

public class SepGUI extends javax.swing.JFrame {

    private NhanVien_DTO user;

    // Sửa constructor để nhận tham số nv
    public SepGUI(NhanVien_DTO nv) {
        initComponents();
        this.user = nv; // Lưu lại

        // Setup giao diện cơ bản
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);

        // Hiển thị tên lên Header
        if (nv != null) {
            xinChaoText.setText("Xin chào: " + nv.getHoTen());
            loadThongTinCaNhan();
        }
        loadDanhSachNV();
        loadDanhSachDuAn();
        loadComboBoxThongKe();
        loadComboBoxChiNhanh_TabNhanVien();

        loadDataCombobox();
        loadBoLocDuAn();
    }

    public SepGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Header = new javax.swing.JPanel();
        tenCty = new javax.swing.JLabel();
        leftGroupHeader = new javax.swing.JPanel();
        dangXuatBtn = new javax.swing.JButton();
        xinChaoText = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        nhanVien = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        thanhTimKiem = new javax.swing.JTextField();
        reload_btn = new javax.swing.JButton();
        chiNhanh_cbb = new javax.swing.JComboBox<>();
        add_btn = new javax.swing.JButton();
        timKiem_btn1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelDanhSach = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        cmbNBD = new de.wannawork.jcalendar.JCalendarComboBox();
        cmbNKT = new de.wannawork.jcalendar.JCalendarComboBox();
        cmbChiNhanh = new javax.swing.JComboBox<>();
        btnReload = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        txtTimkiem = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelDanhSachDuAn = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        nutXNthongke = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        thongTinNhanVien = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        bangThongKe = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        nutXNthongke1 = new javax.swing.JButton();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox<>();
        jCalendarComboBox1 = new de.wannawork.jcalendar.JCalendarComboBox();
        jCalendarComboBox2 = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PanelThongKeDuAn = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        PanelSoDoDuAn = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Header.setBackground(new java.awt.Color(0, 0, 153));
        Header.setPreferredSize(new java.awt.Dimension(1280, 100));

        tenCty.setBackground(new java.awt.Color(255, 0, 51));
        tenCty.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        tenCty.setForeground(new java.awt.Color(255, 102, 0));
        tenCty.setText("ABC GROUP");

        leftGroupHeader.setBackground(new java.awt.Color(0, 0, 153));

        dangXuatBtn.setBackground(new java.awt.Color(255, 255, 255));
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
                    .addComponent(xinChaoText, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(dangXuatBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        leftGroupHeaderLayout.setVerticalGroup(
            leftGroupHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftGroupHeaderLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(xinChaoText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dangXuatBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenCty, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(leftGroupHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenCty, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(leftGroupHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));

        jPanel10.setBackground(new java.awt.Color(0, 0, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HUYNH XUAN THIEN");

        jPanel11.setBackground(new java.awt.Color(102, 204, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jButton1.setText("ĐĂNG XUẤT");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("GIỚI TÍNH : ");

        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField1.setText("Nam ");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("MÃ NHÂN VIÊN : ");

        jTextField2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField2.setText("NV01");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setText("ĐỊA CHỈ : ");

        jTextField3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField3.setText("số nhà , phường , thành phố ");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("PHÒNG BAN : ");

        jTextField4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField4.setText("IT");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setText("LƯƠNG CƠ BẢN : ");

        jTextField5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField5.setText("10 000 000 VND");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setText("CHỨC VỤ : ");

        jTextField6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField6.setText("Sếp");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("CHI NHÁNH : ");

        jTextField7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jTextField7.setText("HCM");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(424, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(688, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(162, 162, 162))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("THÔNG TIN ", jPanel8);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        thanhTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                thanhTimKiemKeyReleased(evt);
            }
        });

        reload_btn.setText("Reload");
        reload_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reload_btnActionPerformed(evt);
            }
        });

        chiNhanh_cbb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cơ sở 1", "Cơ sở 2 ", "Cơ sở 3" }));
        chiNhanh_cbb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chiNhanh_cbbActionPerformed(evt);
            }
        });

        add_btn.setText("Thêm");
        add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btnActionPerformed(evt);
            }
        });

        timKiem_btn1.setText("Tìm kiếm");
        timKiem_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timKiem_btn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(thanhTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timKiem_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(chiNhanh_cbb, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 684, Short.MAX_VALUE)
                .addComponent(reload_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thanhTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(reload_btn)
                    .addComponent(chiNhanh_cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_btn)
                    .addComponent(timKiem_btn1))
                .addContainerGap())
        );

        panelDanhSach.setLayout(new javax.swing.BoxLayout(panelDanhSach, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelDanhSach);

        javax.swing.GroupLayout nhanVienLayout = new javax.swing.GroupLayout(nhanVien);
        nhanVien.setLayout(nhanVienLayout);
        nhanVienLayout.setHorizontalGroup(
            nhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        nhanVienLayout.setVerticalGroup(
            nhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nhanVienLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("XEM NHÂN VIÊN", nhanVien);

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        cmbChiNhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbChiNhanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChiNhanhActionPerformed(evt);
            }
        });

        btnReload.setText("Reload");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtTimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimkiemActionPerformed(evt);
            }
        });
        txtTimkiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimkiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 637, Short.MAX_VALUE)
                .addComponent(cmbNBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(cmbNKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(cmbChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem)
                        .addComponent(txtTimkiem))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem)
                        .addComponent(btnReload)
                        .addComponent(cmbChiNhanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbNBD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbNKT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelDanhSachDuAn.setLayout(new javax.swing.BoxLayout(panelDanhSachDuAn, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(panelDanhSachDuAn);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("XEM DỰ ÁN", jPanel2);

        jPanel5.setBackground(new java.awt.Color(0, 0, 153));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Top Nhân viên tham gia nhiều dự án ", "Top Nhân viên lương cao", "Top Nhân viên " }));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kiểu thống kê :");

        nutXNthongke.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-search-40.png"))); // NOI18N
        nutXNthongke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nutXNthongkeActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phòng ban" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chi Nhánh" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Chọn chi nhánh : ");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Chọn phòng ban");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn--", "Sắp xếp tăng ", "Sắp xếp giảm " }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(nutXNthongke, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(888, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nutXNthongke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout thongTinNhanVienLayout = new javax.swing.GroupLayout(thongTinNhanVien);
        thongTinNhanVien.setLayout(thongTinNhanVienLayout);
        thongTinNhanVienLayout.setHorizontalGroup(
            thongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        thongTinNhanVienLayout.setVerticalGroup(
            thongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(thongTinNhanVien);

        javax.swing.GroupLayout bangThongKeLayout = new javax.swing.GroupLayout(bangThongKe);
        bangThongKe.setLayout(bangThongKeLayout);
        bangThongKeLayout.setHorizontalGroup(
            bangThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1364, Short.MAX_VALUE)
        );
        bangThongKeLayout.setVerticalGroup(
            bangThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(bangThongKe);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1382, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("THỐNG KÊ NHÂN VIÊN", jPanel4);

        jPanel7.setBackground(new java.awt.Color(0, 0, 153));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Top Dự án có doanh thu hiệu quả", "Top Dự án hao tổn nhiều kinh phí ", "Top Dự án có thời gian triển khai lâu nhất ", "Top Dự án sắp đến hạn Deadline", "Top Dự án quy mô nhân sự lớn nhất" }));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Kiểu thống kê :");

        nutXNthongke1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-search-40.png"))); // NOI18N
        nutXNthongke1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nutXNthongke1ActionPerformed(evt);
            }
        });

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chi Nhánh" }));
        jComboBox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox8ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Chi nhánh chủ trì : ");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ngày BĐ :");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn--", "Sắp xếp tăng ", "Sắp xếp giảm " }));
        jComboBox9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox9ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Ngày KT :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCalendarComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCalendarComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nutXNthongke1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nutXNthongke1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCalendarComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox6)
                                .addComponent(jComboBox8)
                                .addComponent(jCalendarComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout PanelThongKeDuAnLayout = new javax.swing.GroupLayout(PanelThongKeDuAn);
        PanelThongKeDuAn.setLayout(PanelThongKeDuAnLayout);
        PanelThongKeDuAnLayout.setHorizontalGroup(
            PanelThongKeDuAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
        );
        PanelThongKeDuAnLayout.setVerticalGroup(
            PanelThongKeDuAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(PanelThongKeDuAn);

        javax.swing.GroupLayout PanelSoDoDuAnLayout = new javax.swing.GroupLayout(PanelSoDoDuAn);
        PanelSoDoDuAn.setLayout(PanelSoDoDuAnLayout);
        PanelSoDoDuAnLayout.setHorizontalGroup(
            PanelSoDoDuAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1379, Short.MAX_VALUE)
        );
        PanelSoDoDuAnLayout.setVerticalGroup(
            PanelSoDoDuAnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );

        jScrollPane6.setViewportView(PanelSoDoDuAn);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6)))
        );

        jTabbedPane1.addTab("THỐNG KÊ DỰ ÁN ", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, 1993, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 749, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField2ActionPerformed

    private void reload_btnActionPerformed(java.awt.event.ActionEvent evt) {
        // Xóa trắng thanh tìm kiếm (nếu có)
        // thanhTimKiem.setText("");

        // Gọi lại hàm load danh sách thần thánh
        loadDanhSachNV();
    }

    private void thanhTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_thanhTimKiemKeyReleased
        // TODO add your handling code here:
        capNhatDanhSachNhanVien();
    }// GEN-LAST:event_thanhTimKiemKeyReleased

    private void cmbChiNhanhActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbChiNhanhActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cmbChiNhanhActionPerformed

    private void timKiem_btn1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_timKiem_btn1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_timKiem_btn1ActionPerformed

    private void txtTimkiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimkiemKeyReleased
        String tuKhoa = txtTimkiem.getText().trim();
        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<DTO.DuAn_DTO> ketQua = bus.timKiem(tuKhoa);

        panelDanhSachDuAn.removeAll();

        if (ketQua != null) {
            for (DTO.DuAn_DTO da : ketQua) {
                GUI.Panel_Frame.PanelItemDuAn item = new GUI.Panel_Frame.PanelItemDuAn(
                        da.getMaDA(), da.getTenDA(), da.getDoanhThu(), da.getKinhPhi(), da.getNgayBatDau());
                panelDanhSachDuAn.add(item);
                panelDanhSachDuAn.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
            }
        }
        panelDanhSachDuAn.revalidate();
        panelDanhSachDuAn.repaint();
    }// GEN-LAST:event_txtTimkiemKeyReleased

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnReloadActionPerformed
        txtTimkiem.setText("");
        loadBoLocDuAn();
        loadDanhSachDuAn();
    }// GEN-LAST:event_btnReloadActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
        String tuKhoa = txtTimkiem.getText();
        java.util.Date tuNgay = cmbNBD.getDate();
        java.util.Date denNgay = cmbNKT.getDate();
        String chiNhanh = (String) cmbChiNhanh.getSelectedItem();

        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<DTO.DuAn_DTO> ketQua = bus.timKiemNangCao(tuKhoa, tuNgay, denNgay, chiNhanh);

        panelDanhSachDuAn.removeAll();

        if (ketQua != null) {
            for (DTO.DuAn_DTO da : ketQua) {
                GUI.Panel_Frame.PanelItemDuAn item = new GUI.Panel_Frame.PanelItemDuAn(
                        da.getMaDA(), da.getTenDA(), da.getDoanhThu(), da.getKinhPhi(), da.getNgayBatDau());
                panelDanhSachDuAn.add(item);
                panelDanhSachDuAn.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
            }
        }
        panelDanhSachDuAn.add(javax.swing.Box.createVerticalGlue());
        panelDanhSachDuAn.revalidate();
        panelDanhSachDuAn.repaint();
    }// GEN-LAST:event_btnTimKiemActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
        java.awt.Window parentWindow = javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog dialog = new javax.swing.JDialog((java.awt.Frame) parentWindow, "Thêm Dự Án Mới", true);

        // Khai báo import thẳng class của Nam Thịnh
        GUI.Panel_Frame.PanelAddDuAn panelAdd = new GUI.Panel_Frame.PanelAddDuAn(dialog);

        dialog.setContentPane(panelAdd);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        loadDanhSachDuAn();
    }// GEN-LAST:event_btnThemActionPerformed

    private void txtTimkiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimkiemActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtTimkiemActionPerformed

    // thong ke du an
    private void nutXNthongke1ActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Tắt thanh cuộn ngang danh sách bên trái
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 2. Lấy dữ liệu từ các bộ lọc
        String kieuThongKe = jComboBox6.getSelectedItem().toString().trim();
        String chiNhanh = jComboBox8.getSelectedItem().toString();
        String sapXep = jComboBox9.getSelectedItem().toString();

        java.util.Date ngayBD = null;
        java.util.Date ngayKT = null;
        if (jCalendarComboBox1.getDate() != null) {
            ngayBD = jCalendarComboBox1.getDate();
        }
        if (jCalendarComboBox2.getDate() != null) {
            ngayKT = jCalendarComboBox2.getDate();
        }

        // 3. Gọi BUS xử lý
        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<DTO.DuAn_DTO> list = bus.thongKeDacBietDuAn(kieuThongKe, chiNhanh, ngayBD, ngayKT,
                sapXep);

        // Chuẩn bị Dataset cho biểu đồ
        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();
        String trucY = "Chỉ số";
        if (kieuThongKe.contains("doanh thu") || kieuThongKe.contains("kinh phí")) {
            trucY = "VNĐ";
        } else if (kieuThongKe.contains("lâu nhất") || kieuThongKe.contains("Deadline")) {
            trucY = "Số Ngày";
        } else if (kieuThongKe.contains("quy mô")) {
            trucY = "Nhân sự";
        }

        // 4. Reset và Thiết lập Panel Danh sách bên trái
        PanelThongKeDuAn.removeAll();
        PanelThongKeDuAn.setLayout(new javax.swing.BoxLayout(PanelThongKeDuAn, javax.swing.BoxLayout.Y_AXIS));
        java.text.NumberFormat vnCurrency = java.text.NumberFormat
                .getCurrencyInstance(new java.util.Locale("vi", "VN"));

        // ==========================================
        // VÒNG LẶP NẠP DỮ LIỆU BẠN BỊ THIẾU ĐÂY NHÉ:
        // ==========================================
        if (list != null) {
            for (DTO.DuAn_DTO da : list) {
                String maDuAnChuan = da.getMaDA().trim();
                String chiSoHienThi = "";
                double giaTri = da.getDoanhThu();

                if (trucY.equals("VNĐ")) {
                    chiSoHienThi = vnCurrency.format(giaTri);
                } else if (trucY.equals("Số Ngày")) {
                    chiSoHienThi = (int) giaTri + " Ngày";
                } else {
                    chiSoHienThi = (int) giaTri + " Người";
                }

                // Đưa vào danh sách bên trái
                GUI.Panel_Frame.PanelThongKeDuAn item = new GUI.Panel_Frame.PanelThongKeDuAn(
                        maDuAnChuan, da.getTenDA(), da.getMaCN(), chiSoHienThi);
                PanelThongKeDuAn.add(item);

                // Nạp số liệu vào Dataset cho biểu đồ
                dataset.addValue(giaTri, trucY, maDuAnChuan);
            }
        }

        PanelThongKeDuAn.add(javax.swing.Box.createVerticalGlue());
        PanelThongKeDuAn.revalidate();
        PanelThongKeDuAn.repaint();

        // ==========================================
        // VẼ BIỂU ĐỒ - KHÓA CỨNG KÍCH THƯỚC, KHÔNG CHO BÓP MÉO
        // ==========================================
        jScrollPane6.setHorizontalScrollBarPolicy(
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (list != null && !list.isEmpty()) {
            org.jfree.chart.JFreeChart barChart = org.jfree.chart.ChartFactory.createBarChart(
                    kieuThongKe.toUpperCase(), "Mã Dự Án", trucY, dataset,
                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);

            // 1. TĂNG LỀ ĐÁY LÊN 100PX
            barChart.setPadding(new org.jfree.ui.RectangleInsets(10, 10, 100, 10));

            org.jfree.chart.plot.CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(java.awt.Color.WHITE);
            plot.setRangeGridlinePaint(java.awt.Color.GRAY);

            org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setTickLabelsVisible(true);
            domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.UP_90);
            domainAxis.setMaximumCategoryLabelWidthRatio(10.0f);
            domainAxis.setTickLabelInsets(new org.jfree.ui.RectangleInsets(5, 5, 5, 5));

            domainAxis.setLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            domainAxis.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));

            org.jfree.chart.axis.NumberAxis yAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
            yAxis.setLowerBound(0.0);
            yAxis.setNumberFormatOverride(
                    java.text.NumberFormat.getNumberInstance(new java.util.Locale("vi", "VN")));
            if (!trucY.equals("VNĐ")) {
                yAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
            }

            // Tooltip (Lia chuột)
            org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot
                    .getRenderer();
            if (trucY.equals("VNĐ")) {
                renderer.setBaseToolTipGenerator(
                        new org.jfree.chart.labels.StandardCategoryToolTipGenerator(
                                "Mã {1} | Giá trị: {2}",
                                java.text.NumberFormat.getCurrencyInstance(
                                        new java.util.Locale("vi", "VN"))));
            } else if (trucY.equals("Số Ngày")) {
                renderer.setBaseToolTipGenerator(
                        new org.jfree.chart.labels.StandardCategoryToolTipGenerator(
                                "Mã {1} | Thời gian: {2} Ngày",
                                java.text.NumberFormat.getInstance(
                                        new java.util.Locale("vi", "VN"))));
            } else {
                renderer.setBaseToolTipGenerator(
                        new org.jfree.chart.labels.StandardCategoryToolTipGenerator(
                                "Mã {1} | Quy mô: {2} Người",
                                java.text.NumberFormat.getInstance(
                                        new java.util.Locale("vi", "VN"))));
            }

            org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(barChart);
            chartPanel.setMinimumDrawWidth(10);
            chartPanel.setMinimumDrawHeight(10);
            chartPanel.setMaximumDrawWidth(9999);
            chartPanel.setMaximumDrawHeight(9999);

            // 2. KHÓA CỨNG KÍCH THƯỚC BẰNG WRAPPER PANEL
            int chartWidth = Math.max(800, list.size() * 60);
            java.awt.Dimension fixedSize = new java.awt.Dimension(chartWidth, 650);

            javax.swing.JPanel wrapperPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
            wrapperPanel.setPreferredSize(fixedSize);
            wrapperPanel.setMinimumSize(fixedSize);
            wrapperPanel.add(chartPanel, java.awt.BorderLayout.CENTER);

            jScrollPane6.setViewportView(wrapperPanel);
        } else {
            jScrollPane6.setViewportView(new javax.swing.JPanel());
        }

        jScrollPane6.revalidate();
        jScrollPane6.repaint();
    }

    private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox8ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox8ActionPerformed

    private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox9ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox9ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
        if (jComboBox4.getSelectedItem() != null) {
            String selectedCN = jComboBox4.getSelectedItem().toString();
            // Gọi hàm nạp lại Phòng ban
            loadPhongBanTheoChiNhanh(selectedCN);
        }
    }// GEN-LAST:event_jComboBox4ActionPerformed

    private void nutXNthongkeActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Cấu hình thanh cuộn danh sách bên trái
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 2. Lấy dữ liệu từ bộ lọc
        String kieuThongKe = jComboBox2.getSelectedItem().toString().trim();
        String chiNhanh = jComboBox4.getSelectedItem().toString();
        String phongBan = jComboBox3.getSelectedItem().toString();
        String sapXep = jComboBox5.getSelectedItem().toString();

        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
        java.util.ArrayList<DTO.NhanVien_DTO> list = null;

        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();
        String tenBieuDo = "";
        String trucY = "";

        // 3. Reset danh sách bên trái
        thongTinNhanVien.removeAll();
        thongTinNhanVien.setLayout(new javax.swing.BoxLayout(thongTinNhanVien, javax.swing.BoxLayout.Y_AXIS));
        java.text.NumberFormat vnCurrency = java.text.NumberFormat
                .getCurrencyInstance(new java.util.Locale("vi", "VN"));

        // 4. Lấy dữ liệu và nạp vào Dataset + Danh sách trái
        if (kieuThongKe.equals("Top Nhân viên lương cao")) {
            list = bus.thongKeLuong(chiNhanh, phongBan, sapXep);
            tenBieuDo = "THỐNG KÊ LƯƠNG NHÂN VIÊN";
            trucY = "Mức Lương (VNĐ)";

            if (list != null) {
                for (DTO.NhanVien_DTO nv : list) {
                    String maNVChuan = nv.getMaNV().trim();
                    String luongStr = vnCurrency.format(nv.getLuong());

                    GUI.Panel_Frame.PanelThongKeNhanVien item = new GUI.Panel_Frame.PanelThongKeNhanVien(
                            maNVChuan, nv.getHoTen(), nv.getMaPB(), luongStr);
                    thongTinNhanVien.add(item);
                    dataset.addValue(nv.getLuong(), "Lương", maNVChuan);
                }
            }
        } else if (kieuThongKe.equals("Top Nhân viên tham gia nhiều dự án")) {
            list = bus.thongKeTopDuAn(chiNhanh, phongBan, sapXep);
            tenBieuDo = "THỐNG KÊ SỐ LƯỢNG DỰ ÁN";
            trucY = "Số Dự Án";

            if (list != null) {
                for (DTO.NhanVien_DTO nv : list) {
                    String maNVChuan = nv.getMaNV().trim();
                    int soDuAn = (int) nv.getLuong();
                    String chiSoStr = soDuAn + " Dự án";

                    GUI.Panel_Frame.PanelThongKeNhanVien item = new GUI.Panel_Frame.PanelThongKeNhanVien(
                            maNVChuan, nv.getHoTen(), nv.getMaPB(), chiSoStr);
                    thongTinNhanVien.add(item);
                    dataset.addValue(soDuAn, "Số lượng", maNVChuan);
                }
            }
        }

        thongTinNhanVien.add(javax.swing.Box.createVerticalGlue());
        thongTinNhanVien.revalidate();
        thongTinNhanVien.repaint();

        // ==========================================
        // 5. VẼ BIỂU ĐỒ - TỰ ĐỘNG CO GIÃN THÔNG MINH
        // ==========================================
        jScrollPane4.setHorizontalScrollBarPolicy(
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Tắt thanh trượt dọc để ép biểu đồ phải lùn xuống vừa khít màn hình hiện tại
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        if (list != null && !list.isEmpty()) {
            org.jfree.chart.JFreeChart barChart = org.jfree.chart.ChartFactory.createBarChart(
                    tenBieuDo, "Mã Nhân Viên", trucY, dataset,
                    org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);

            // Tăng lề đáy 100px chống cắt chữ
            barChart.setPadding(new org.jfree.ui.RectangleInsets(10, 10, 100, 10));

            org.jfree.chart.plot.CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(java.awt.Color.WHITE);
            plot.setRangeGridlinePaint(java.awt.Color.GRAY);

            // Cấu hình chữ trục X đứng 90 độ
            org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setTickLabelsVisible(true);
            domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.UP_90);
            domainAxis.setMaximumCategoryLabelWidthRatio(10.0f);
            domainAxis.setTickLabelInsets(new org.jfree.ui.RectangleInsets(5, 5, 5, 5));
            domainAxis.setLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            domainAxis.setTickLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));

            // Cấu hình trục Y
            org.jfree.chart.axis.NumberAxis yAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
            yAxis.setLowerBound(0.0);
            yAxis.setNumberFormatOverride(
                    java.text.NumberFormat.getNumberInstance(new java.util.Locale("vi", "VN")));
            if (!trucY.equals("Mức Lương (VNĐ)")) {
                yAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
            }

            // Tooltip (Lia chuột)
            org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot
                    .getRenderer();
            if (trucY.equals("Mức Lương (VNĐ)")) {
                renderer.setBaseToolTipGenerator(
                        new org.jfree.chart.labels.StandardCategoryToolTipGenerator(
                                "Mã {1} | Lương: {2}",
                                java.text.NumberFormat.getCurrencyInstance(
                                        new java.util.Locale("vi", "VN"))));
            } else {
                renderer.setBaseToolTipGenerator(
                        new org.jfree.chart.labels.StandardCategoryToolTipGenerator(
                                "Mã {1} | Tham gia: {2} Dự án",
                                java.text.NumberFormat.getInstance(
                                        new java.util.Locale("vi", "VN"))));
            }

            org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(barChart);
            chartPanel.setMinimumDrawWidth(10);
            chartPanel.setMinimumDrawHeight(10);
            chartPanel.setMaximumDrawWidth(9999);
            chartPanel.setMaximumDrawHeight(9999);

            // BẢN FIX CUỐI: TẠO WRAPPER PANEL THÔNG MINH
            final int soLuongCot = dataset.getColumnCount(); // Lấy số lượng cột thực tế

            javax.swing.JPanel wrapperPanel = new javax.swing.JPanel(new java.awt.BorderLayout()) {
                @Override
                public java.awt.Dimension getPreferredSize() {
                    // Chiều ngang co giãn theo số lượng cột (để hiện thanh cuộn ngang nếu cần)
                    int w = Math.max(800, soLuongCot * 60);
                    // Chiều cao tự động bám theo đúng khoảng trống hiện có của màn hình
                    int h = jScrollPane4.getViewport().getHeight();
                    if (h <= 0)
                        h = 400; // Chiều cao dự phòng
                    return new java.awt.Dimension(w, h);
                }
            };

            wrapperPanel.add(chartPanel, java.awt.BorderLayout.CENTER);
            jScrollPane4.setViewportView(wrapperPanel);

        } else {
            jScrollPane4.setViewportView(new javax.swing.JPanel());
        }

        jScrollPane4.revalidate();
        jScrollPane4.repaint();
    }

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jComboBox5ActionPerformed

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Tạo một cửa sổ Dialog mới (Cửa sổ con)
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Thêm Nhân Viên Mới", true);

        // 2. Tạo PanelAddNhanVien và bỏ vào Dialog
        GUI.Panel_Frame.PanelAddNhanVien panel = new GUI.Panel_Frame.PanelAddNhanVien(dialog);
        dialog.getContentPane().add(panel);

        // 3. Setup kích thước và hiển thị
        dialog.pack(); // Tự động co giãn theo kích thước Panel
        dialog.setLocationRelativeTo(null); // Ra giữa màn hình
        dialog.setVisible(true);

        // 4. Khi Dialog đóng lại thì load lại danh sách bên ngoài
        loadDanhSachNV();
    }

    private void timKiem_btn1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_timKiem_btn1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_timKiem_btn1ActionPerformed

    private void chiNhanh_cbb1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chiNhanh_cbb1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_chiNhanh_cbb1ActionPerformed

    private void chiNhanh_cbbActionPerformed(java.awt.event.ActionEvent evt) {
        // CHỈ CẦN GỌI ĐÚNG 1 DÒNG NÀY THÔI, XÓA HẾT CODE CŨ ĐI BẠN NHÉ!
        capNhatDanhSachNhanVien();
    }

    // dang xuat
    private void dangXuatBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Hiện bảng hỏi: "Bạn có chắc không?"
        int luaChon = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                javax.swing.JOptionPane.YES_NO_OPTION);

        // 2. Xử lý kết quả
        if (luaChon == javax.swing.JOptionPane.YES_OPTION) {
            // Đóng cửa sổ Sếp lại (Giải phóng Ram)
            this.dispose();

            // Mở lại màn hình Đăng Nhập
            new DangNhap().setVisible(true);
        }
    }

    // public void loadDanhSachNV() {
    // panelDanhSach.removeAll(); // Xóa sạch các item cũ trên giao diện

    // BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
    // // Đảm bảo lấy FULL danh sách, không truyền điều kiện lọc nhầm lẫn
    // java.util.ArrayList<DTO.NhanVien_DTO> list = bus.timKiem("");

    // for (DTO.NhanVien_DTO nv : list) {
    // // Tạo item mới và add vào panel
    // PanelItemNhanVien item = new PanelItemNhanVien(nv.getMaNV(), nv.getHoTen(),
    // nv.getMaCN());
    // panelDanhSach.add(item);
    // }

    // panelDanhSach.revalidate();
    // panelDanhSach.repaint();
    // }

    public void loadDanhSachNV() {
        panelDanhSach.removeAll();
        // Luôn khởi tạo BUS mới để quét lại Database
        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
        java.util.ArrayList<DTO.NhanVien_DTO> list = bus.timKiem("");

        for (DTO.NhanVien_DTO nv : list) {
            PanelItemNhanVien item = new PanelItemNhanVien(nv.getMaNV(), nv.getHoTen(), nv.getVaiTro());
            panelDanhSach.add(item);
        }

        panelDanhSach.revalidate();
        panelDanhSach.repaint();
    }

    private void loadDanhSachDuAn() {
        // 1. Gọi BUS lấy dữ liệu (Đảm bảo bạn đã viết DuAnBUS và DuAnDAO nhé)
        BUS.DuAnBUS bus = new BUS.DuAnBUS();
        java.util.ArrayList<DTO.DuAn_DTO> list = bus.layToanBoDuAn();

        if (panelDanhSachDuAn == null)
            return; // Đề phòng lỗi chưa tạo UI

        // 2. Xóa trắng danh sách cũ
        panelDanhSachDuAn.removeAll();

        // 3. Đổ dữ liệu vào
        for (DTO.DuAn_DTO da : list) {
            // Truyền dữ liệu vào khuôn đúc
            PanelItemDuAn item = new PanelItemDuAn(
                    da.getMaDA(),
                    da.getTenDA(),
                    da.getDoanhThu(),
                    da.getKinhPhi(),
                    da.getNgayBatDau());

            panelDanhSachDuAn.add(item);
            panelDanhSachDuAn.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        }

        // 4. Vẽ lại giao diện
        panelDanhSachDuAn.revalidate();
        panelDanhSachDuAn.repaint();
    }

    private void timKiem_btnActionPerformed(java.awt.event.ActionEvent evt) {
        // CHỈ CẦN GỌI ĐÚNG 1 DÒNG NÀY THÔI
        capNhatDanhSachNhanVien();
    }

    private void loadComboBoxThongKe() {
        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();

        // Nạp Chi nhánh
        jComboBox4.removeAllItems();
        jComboBox4.addItem("Tất cả Chi Nhánh");
        jComboBox8.removeAllItems();
        jComboBox8.addItem("Tất cả Chi Nhánh");
        for (String cn : bus.getListMaCN()) {
            jComboBox4.addItem(cn);
            jComboBox8.addItem(cn);
        }

        // Mặc định nạp chữ Tất cả cho Phòng Ban
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Tất cả Phòng Ban");
    }

    private void loadPhongBanTheoChiNhanh(String maCN) {
        jComboBox3.removeAllItems();
        jComboBox3.addItem("Tất cả Phòng Ban");

        if (maCN != null && !maCN.equals("Tất cả Chi Nhánh")) {
            BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
            for (String pb : bus.getListMaPB(maCN)) {
                jComboBox3.addItem(pb);
            }
        }
    }

    private void loadComboBoxChiNhanh_TabNhanVien() {

        chiNhanh_cbb.removeAllItems();
        chiNhanh_cbb.addItem("Tất cả Chi Nhánh");

        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
        java.util.ArrayList<String> dsCN = bus.getListMaCN(); // Lấy danh sách mã chi nhánh từ Database

        if (dsCN != null) {
            for (String cn : dsCN) {
                chiNhanh_cbb.addItem(cn);
            }
        }
    }

    // HÀM TỔNG HỢP NÀY ĐỂ Ở TRONG FILE SepGUI.java
    private void capNhatDanhSachNhanVien() {
        String tuKhoa = thanhTimKiem.getText().trim();
        String maCN = "";
        if (chiNhanh_cbb.getSelectedItem() != null) {
            maCN = chiNhanh_cbb.getSelectedItem().toString();
        }

        // Gọi BUS xử lý song kiếm hợp bích
        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();
        java.util.ArrayList<DTO.NhanVien_DTO> list = bus.timKiemVaLoc(tuKhoa, maCN);

        // Reset màn hình
        panelDanhSach.removeAll();
        panelDanhSach.setLayout(new javax.swing.BoxLayout(panelDanhSach, javax.swing.BoxLayout.Y_AXIS));

        // Vẽ danh sách mới
        if (list != null) {
            for (DTO.NhanVien_DTO nv : list) {
                // ---> ĐÃ FIX LỖI ĐỎ: Trả lại đúng 3 tham số (Mã, Tên, Vai Trò) <---
                GUI.Panel_Frame.PanelItemNhanVien item = new GUI.Panel_Frame.PanelItemNhanVien(
                        nv.getMaNV().trim(),
                        nv.getHoTen(),
                        nv.getVaiTro());
                panelDanhSach.add(item);
                panelDanhSach.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
            }
        }

        panelDanhSach.add(javax.swing.Box.createVerticalGlue());
        panelDanhSach.revalidate();
        panelDanhSach.repaint();
    }

    private void addduan_btn1ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    // new
    private void loadBoLocDuAn() {
        try {
            BUS.DuAnBUS bus = new BUS.DuAnBUS();
            java.util.ArrayList<DTO.DuAn_DTO> list = bus.layToanBoDuAn();

            if (list == null || list.isEmpty())
                return;

            java.util.Date minDate = null;
            java.util.Date maxDate = null;
            java.util.Set<String> dsChiNhanh = new java.util.HashSet<>();

            for (DTO.DuAn_DTO da : list) {
                java.util.Date nbd = da.getNgayBatDau();
                if (nbd != null) {
                    if (minDate == null || nbd.before(minDate))
                        minDate = nbd;
                }

                java.util.Date nkt = da.getNgayKetThuc();
                if (nkt != null) {
                    if (maxDate == null || nkt.after(maxDate))
                        maxDate = nkt;
                }

                String maCN = da.getMaCN();
                if (maCN != null && !maCN.trim().isEmpty()) {
                    dsChiNhanh.add(maCN);
                }
            }

            if (minDate != null)
                cmbNBD.setDate(minDate);
            if (maxDate != null)
                cmbNKT.setDate(maxDate);

            cmbChiNhanh.removeAllItems();
            cmbChiNhanh.addItem("Tất cả chi nhánh");
            for (String cn : dsChiNhanh) {
                cmbChiNhanh.addItem(cn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadThongTinCaNhan() {
        // Nếu chưa đăng nhập thì bỏ qua
        if (this.user == null)
            return;

        // 1. ĐỔ DỮ LIỆU VÀO CÁC Ô TEXTFIELD
        // Tên dưới ảnh đại diện (In hoa)
        jLabel1.setText(user.getHoTen().toUpperCase());

        // Các trường thông tin (Theo thứ tự giao diện của bạn)
        jTextField2.setText(user.getMaNV()); // Mã NV
        jTextField1.setText(user.getGioiTinh() != null ? user.getGioiTinh() : "Chưa cập nhật"); // Giới tính

        // Xử lý địa chỉ (nối Số nhà, Phường, Tỉnh lại)
        String dcSoNha = user.getDcSoNha() != null ? user.getDcSoNha() + ", " : "";
        String dcPhuong = user.getDcPhuong() != null ? user.getDcPhuong() + ", " : "";
        String dcTinh = user.getDcTinh() != null ? user.getDcTinh() : "";
        String diaChi = dcSoNha + dcPhuong + dcTinh;
        if (diaChi.endsWith(", "))
            diaChi = diaChi.substring(0, diaChi.length() - 2); // Xóa dấu phẩy thừa
        jTextField3.setText(diaChi.isEmpty() ? "Chưa cập nhật" : diaChi); // Địa chỉ

        // Format tiền tệ cho Lương
        java.text.NumberFormat vnCurrency = java.text.NumberFormat
                .getCurrencyInstance(new java.util.Locale("vi", "VN"));
        jTextField5.setText(vnCurrency.format(user.getLuong())); // Lương cơ bản

        jTextField6.setText(user.getVaiTro()); // Chức vụ
        jTextField4.setText(user.getMaPB()); // Phòng ban
        jTextField7.setText(user.getMaCN()); // Chi nhánh

        // Khóa các ô lại không cho người dùng sửa bậy bạ
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        jTextField5.setEditable(false);
        jTextField6.setEditable(false);
        jTextField7.setEditable(false);

        // 2. XỬ LÝ ẢNH ĐẠI DIỆN VÀO jPanel11
        jPanel11.removeAll(); // Xóa màu nền hoặc nội dung cũ
        jPanel11.setLayout(new java.awt.BorderLayout());
        javax.swing.JLabel lblAvatar = new javax.swing.JLabel();
        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        try {
            String maNV = user.getMaNV().trim();
            // Tìm trong thư mục src/img/ (Thử file .jpg trước, nếu không có tìm .png)
            java.io.File fileImg = new java.io.File("src/img/" + maNV + ".jpg");
            if (!fileImg.exists()) {
                fileImg = new java.io.File("src/img/" + maNV + ".png");
            }

            if (fileImg.exists()) {
                // Đọc ảnh và co giãn (Scale) vừa khít với kích thước của jPanel11 (200x200)
                java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(fileImg);
                java.awt.Image dimg = img.getScaledInstance(jPanel11.getWidth(), jPanel11.getHeight(),
                        java.awt.Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new javax.swing.ImageIcon(dimg));
            } else {
                lblAvatar.setText("Không có ảnh (" + maNV + ")");
                lblAvatar.setForeground(java.awt.Color.WHITE);
            }
        } catch (Exception e) {
            lblAvatar.setText("Lỗi tải ảnh");
            System.out.println("Lỗi load ảnh: " + e.getMessage());
        }

        jPanel11.add(lblAvatar, java.awt.BorderLayout.CENTER);
        jPanel11.revalidate();
        jPanel11.repaint();
    }

    private void loadDataCombobox() {
        BUS.NhanVienBUS bus = new BUS.NhanVienBUS();

        cmbChiNhanh.removeAllItems();
        cmbChiNhanh.addItem("Tất cả chi nhánh");

        for (String cn : bus.getListMaCN()) {
            cmbChiNhanh.addItem(cn);
        }
    }

    // new

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SepGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SepGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SepGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SepGUI.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SepGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JPanel PanelSoDoDuAn;
    private javax.swing.JPanel PanelThongKeDuAn;
    private javax.swing.JButton add_btn;
    private javax.swing.JPanel bangThongKe;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> chiNhanh_cbb;
    private javax.swing.JComboBox<String> cmbChiNhanh;
    private de.wannawork.jcalendar.JCalendarComboBox cmbNBD;
    private de.wannawork.jcalendar.JCalendarComboBox cmbNKT;
    private javax.swing.JButton dangXuatBtn;
    private javax.swing.JButton jButton1;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBox1;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBox2;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JPanel leftGroupHeader;
    private javax.swing.JPanel nhanVien;
    private javax.swing.JButton nutXNthongke;
    private javax.swing.JButton nutXNthongke1;
    private javax.swing.JPanel panelDanhSach;
    private javax.swing.JPanel panelDanhSachDuAn;
    private javax.swing.JButton reload_btn;
    private javax.swing.JLabel tenCty;
    private javax.swing.JTextField thanhTimKiem;
    private javax.swing.JPanel thongTinNhanVien;
    private javax.swing.JButton timKiem_btn1;
    private javax.swing.JTextField txtTimkiem;
    private javax.swing.JLabel xinChaoText;
    // End of variables declaration//GEN-END:variables
}
