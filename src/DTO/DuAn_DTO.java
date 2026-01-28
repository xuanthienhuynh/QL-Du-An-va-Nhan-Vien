package DTO;

import java.util.Date;

public class DuAn_DTO {
    private String maDA;
    private String tenDA;
    private double kinhPhi;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai; // "Kết thúc", "Đang chạy"
    private String maCN; // Khóa ngoại

    public DuAn_DTO() {
    }

    public DuAn_DTO(String maDA, String tenDA, double kinhPhi, Date ngayBatDau, Date ngayKetThuc, String trangThai,
            String maCN) {
        this.maDA = maDA;
        this.tenDA = tenDA;
        this.kinhPhi = kinhPhi;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.maCN = maCN;
    }

    // Getter và Setter
    public String getMaDA() {
        return maDA;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public String getTenDA() {
        return tenDA;
    }

    public void setTenDA(String tenDA) {
        this.tenDA = tenDA;
    }

    public double getKinhPhi() {
        return kinhPhi;
    }

    public void setKinhPhi(double kinhPhi) {
        this.kinhPhi = kinhPhi;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }
}