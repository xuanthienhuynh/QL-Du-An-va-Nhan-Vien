package DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DuAn_DTO {
    private String maDA;
    private String tenDA;
    private double kinhPhi;
    private double DoanhThu; // moi them
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai; // "Kết thúc", "Đang chạy"
    // private String maCN; // Khóa ngoại

    private List<String> danhSachMaCN = new ArrayList<>();

    public DuAn_DTO() {
    }

    public DuAn_DTO(String maDA, String tenDA, double kinhPhi, double DoanhThu, Date ngayBatDau, Date ngayKetThuc,
            String trangThai, List<String> danhSachMaCN) {
        this.maDA = maDA;
        this.tenDA = tenDA;
        this.kinhPhi = kinhPhi;
        this.DoanhThu = DoanhThu;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
        this.danhSachMaCN = danhSachMaCN;
    }

    public double getDoanhThu() {
        return DoanhThu;
    }

    public double getKinhPhi() {
        return kinhPhi;
    }

    public List<String> getDanhSachMaCN() {
        return danhSachMaCN;
    }

    public void setDanhSachMaCN(List<String> danhSachMaCN) {
        this.danhSachMaCN = danhSachMaCN;
    }

    public String getMaDA() {
        return maDA;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public String getTenDA() {
        return tenDA;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setDoanhThu(double DoanhThu) {
        this.DoanhThu = DoanhThu;
    }

    public void setKinhPhi(double kinhPhi) {
        this.kinhPhi = kinhPhi;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setTenDA(String tenDA) {
        this.tenDA = tenDA;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void chuyenSangTrangThaiKetThuc() {
        this.trangThai = "Đã kết thúc";
    }

}