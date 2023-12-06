package poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass;

public class KhachHang {
    private int idKH;
    private String HoTenKH;
    private String phoneKH;
    private String addressKH;
    private int trangThai;

    public int getTrangThai() {
        return trangThai;
    }

    public int getIdKH() {
        return idKH;
    }

    public String getHoTenKH() {
        return HoTenKH;
    }

    public String getPhoneKH() {
        return phoneKH;
    }

    public String getAddressKH() {
        return addressKH;
    }

    public KhachHang(int idKH, String hoTenKH, String phoneKH, String addressKH, int trangThai) {
        this.idKH = idKH;
        HoTenKH = hoTenKH;
        this.phoneKH = phoneKH;
        this.addressKH = addressKH;
        this.trangThai = trangThai;
    }
}
