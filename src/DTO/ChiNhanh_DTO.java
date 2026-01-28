package DTO;

public class ChiNhanh_DTO {
    private String maCN;
    private String tenCN;
    private String diaChi;

    public ChiNhanh_DTO() {
    }

    public ChiNhanh_DTO(String maCN, String tenCN, String diaChi) {
        this.maCN = maCN;
        this.tenCN = tenCN;
        this.diaChi = diaChi;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}