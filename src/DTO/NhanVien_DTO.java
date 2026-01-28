package DTO;

import java.util.Date;

public class NhanVien_DTO {
    private String maNV;
    private String hoTen;
    private String gioiTinh;
    private String dcTinh;
    private String dcPhuong;
    private String dcSoNha;
    private Date ngaySinh;
    private double luong;
    private String tinhTrang; // "Đang làm việc", "Đã nghỉ việc"
    private String vaiTro;    // "NhanVien", "QuanLy", "Sep"
    private String matKhau;
    private String maPB;      // Khóa ngoại
    private String maCN;      // Khóa ngoại

   
    public NhanVien_DTO() {
    }

    public NhanVien_DTO(String maNV, String hoTen, String gioiTinh, String dcTinh, String dcPhuong, String dcSoNha, Date ngaySinh, double luong, String tinhTrang, String vaiTro, String matKhau, String maPB, String maCN) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.dcTinh = dcTinh;
        this.dcPhuong = dcPhuong;
        this.dcSoNha = dcSoNha;
        this.ngaySinh = ngaySinh;
        this.luong = luong;
        this.tinhTrang = tinhTrang;
        this.vaiTro = vaiTro;
        this.matKhau = matKhau;
        this.maPB = maPB;
        this.maCN = maCN;
    }

    // Getter và Setter
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDcTinh() { return dcTinh; }
    public void setDcTinh(String dcTinh) { this.dcTinh = dcTinh; }

    public String getDcPhuong() { return dcPhuong; }
    public void setDcPhuong(String dcPhuong) { this.dcPhuong = dcPhuong; }

    public String getDcSoNha() { return dcSoNha; }
    public void setDcSoNha(String dcSoNha) { this.dcSoNha = dcSoNha; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getMaPB() { return maPB; }
    public void setMaPB(String maPB) { this.maPB = maPB; }

    public String getMaCN() { return maCN; }
    public void setMaCN(String maCN) { this.maCN = maCN; }
}