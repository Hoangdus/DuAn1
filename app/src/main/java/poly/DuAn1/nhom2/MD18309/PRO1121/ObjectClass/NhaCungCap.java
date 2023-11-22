package poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass;

public class NhaCungCap {
    private int idNhaCungCap;
    private String tenNhaCungCap;
    private String sdtNhaCungCap;
    private String hoTenNguoiDaiDien;
    private String diaChiNhaCungCap;

    public int getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public String getSdtNhaCungCap() {
        return sdtNhaCungCap;
    }

    public String getHoTenNguoiDaiDien() {
        return hoTenNguoiDaiDien;
    }

    public String getDiaChiNhaCungCap() {
        return diaChiNhaCungCap;
    }

    public NhaCungCap(int idNhaCungCap, String tenNhaCungCap, String sdtNhaCungCap, String hoTenNguoiDaiDien, String diaChiNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.sdtNhaCungCap = sdtNhaCungCap;
        this.hoTenNguoiDaiDien = hoTenNguoiDaiDien;
        this.diaChiNhaCungCap = diaChiNhaCungCap;
    }
}
