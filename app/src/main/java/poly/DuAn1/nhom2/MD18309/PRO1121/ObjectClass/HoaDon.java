package poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass;

public class HoaDon {
    private int idHD;
    private String idNguoiTao;
    private String idNguoiMua;
    private String ngayTao;
    private String loai;
    private int trangThai;
    private int status;

    public HoaDon() {}

    public HoaDon(int idHD, String idNguoiTao, String idNguoiMua, String ngayTao, String loai, int trangThai, int status) {
        this.idHD = idHD;
        this.idNguoiTao = idNguoiTao;
        this.idNguoiMua = idNguoiMua;
        this.ngayTao = ngayTao;
        this.loai = loai;
        this.trangThai = trangThai;
        this.status = status;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public String getIdNguoiTao() {
        return idNguoiTao;
    }

    public void setIdNguoiTao(String idNguoiTao) {
        this.idNguoiTao = idNguoiTao;
    }

    public String getIdNguoiMua() {
        return idNguoiMua;
    }

    public void setIdNguoiMua(String idNguoiMua) {
        this.idNguoiMua = idNguoiMua;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoai() {
        return loai;
    }

    public int getStatus() {
        return status;
    }
}
