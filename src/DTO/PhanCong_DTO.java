package DTO;

import java.util.Date;

public class PhanCong_DTO {
    private String maNV;
    private String maDA;
    private Date ngayThamGia;
    private Date ngayKetThuc; // Có thể null nếu chưa xong
    private String vaiTroDuAn;
    private int danhGia; // Điểm 1-10

    public PhanCong_DTO() {
    }

    public PhanCong_DTO(String maNV, String maDA, Date ngayThamGia, Date ngayKetThuc, String vaiTroDuAn, int danhGia) {
        this.maNV = maNV;
        this.maDA = maDA;
        this.ngayThamGia = ngayThamGia;
        this.ngayKetThuc = ngayKetThuc;
        this.vaiTroDuAn = vaiTroDuAn;
        this.danhGia = danhGia;
    }

    // Getter và Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaDA() {
        return maDA;
    }

    public void setMaDA(String maDA) {
        this.maDA = maDA;
    }

    public Date getNgayThamGia() {
        return ngayThamGia;
    }

    public void setNgayThamGia(Date ngayThamGia) {
        this.ngayThamGia = ngayThamGia;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getVaiTroDuAn() {
        return vaiTroDuAn;
    }

    public void setVaiTroDuAn(String vaiTroDuAn) {
        this.vaiTroDuAn = vaiTroDuAn;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }
}