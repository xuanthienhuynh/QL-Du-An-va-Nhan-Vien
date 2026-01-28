package DTO;

public class PhongBan_DTO {
    private String maPB;
    private String tenPB;
    private String maCN;

    public PhongBan_DTO() {
    }

    public PhongBan_DTO(String maPB, String tenPB, String maCN) {
        this.maPB = maPB;
        this.tenPB = tenPB;
        this.maCN = maCN;
    }

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }
}