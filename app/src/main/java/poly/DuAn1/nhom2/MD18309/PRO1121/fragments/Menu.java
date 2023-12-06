package poly.DuAn1.nhom2.MD18309.PRO1121.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini.BaoCao;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini.DanhSachKhachHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini.DoiMatKhau;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini.ThongKe;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini.DanhSachNhanVien;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment implements DanhSachKhachHang.FragmentCallBack, DanhSachNhanVien.FragmentCallBack, ThongKe.FragmentCallBack, DoiMatKhau.FragmentCallBack, BaoCao.FragmentCallBack {

    private TaiKhoan taiKhoan;
    private FragmentManager fragmentManager;
    private LinearLayout parentLayout;

    //Fragment
    private DanhSachNhanVien danhSachNhanVien;
    private DanhSachKhachHang danhSachKhachHang;
    private DoiMatKhau doiMatKhau;
    private BaoCao baoCao;
    private ThongKe thongKe;

    private FragmentCallBack fragmentCallBack;

    public interface FragmentCallBack{
        void logout();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu() {
        // Required empty public constructor
    }

    public Menu(TaiKhoan taiKhoan, FragmentCallBack fragmentCallBack) {
        this.taiKhoan = taiKhoan;
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        //Khai Báo
        TextView txtHoTen = view.findViewById(R.id.txtHoTen);
        TextView txtChucVu = view.findViewById(R.id.txtRole);
        ImageView imgProfile = view.findViewById(R.id.imgProfile);
        parentLayout = view.findViewById(R.id.linearLayout);
        LinearLayout btnChangePass = view.findViewById(R.id.btnChangePass);
        LinearLayout btnQLNhanVien = view.findViewById(R.id.btnQLNhanVien);
        LinearLayout btnQLKhachHang = view.findViewById(R.id.btnQLKhachHang);
        LinearLayout btnThongKe = view.findViewById(R.id.btnThongKe);
        LinearLayout btnBaoCao = view.findViewById(R.id.btnBaoCao);
        LinearLayout btnLogOut = view.findViewById(R.id.btnLogOut);
        fragmentManager = getChildFragmentManager();
        danhSachNhanVien = new DanhSachNhanVien(this);
        danhSachKhachHang = new DanhSachKhachHang(this);
        thongKe = new ThongKe(this);
        baoCao = new BaoCao(this);
        doiMatKhau = new DoiMatKhau(this, taiKhoan);

        //Hiện Thị Thông Tin
        txtHoTen.setText(taiKhoan.getHoTen());

        if (taiKhoan.getRole().equalsIgnoreCase("QUANLY")){
            imgProfile.setImageResource(R.drawable.user_admin);
            txtChucVu.setText("Quản Lý");
        } else if (taiKhoan.getRole().equalsIgnoreCase("NhanVien")) {
            imgProfile.setImageResource(R.drawable.user);
            txtChucVu.setText("Nhân Viên");
            btnQLNhanVien.setVisibility(View.GONE);
        }else {
            imgProfile.setImageResource(R.drawable.user);
            txtChucVu.setText("Người Dùng");
        }

        btnQLNhanVien.setOnClickListener(v -> {
            openQLNhanVien();
        });

        btnThongKe.setOnClickListener(v -> {
            openThongKe();
        });

        btnQLKhachHang.setOnClickListener(v -> {
            openQLKhachHang();
        });

        btnBaoCao.setOnClickListener(v -> {
            parentLayout.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction().replace(R.id.framelayout, baoCao).commit();
        });

        btnChangePass.setOnClickListener(v -> {
            parentLayout.setVisibility(View.INVISIBLE);
            fragmentManager.beginTransaction().replace(R.id.framelayout, doiMatKhau).commit();
        });

        btnLogOut.setOnClickListener(v -> fragmentCallBack.logout());

        return view;
    }

    public void openThongKe(){
        parentLayout.setVisibility(View.INVISIBLE);
        fragmentManager.beginTransaction().replace(R.id.framelayout, thongKe).commit();
    }

    public void openQLNhanVien(){
        parentLayout.setVisibility(View.INVISIBLE);
        fragmentManager.beginTransaction().replace(R.id.framelayout, danhSachNhanVien).commit();
    }

    public void openQLKhachHang(){
        parentLayout.setVisibility(View.INVISIBLE);
        fragmentManager.beginTransaction().replace(R.id.framelayout, danhSachKhachHang).commit();
    }

    @Override
    public void exitFragment() {
        fragmentManager.beginTransaction().remove(danhSachNhanVien).commit();
        fragmentManager.beginTransaction().remove(danhSachKhachHang).commit();
        fragmentManager.beginTransaction().remove(thongKe).commit();
        fragmentManager.beginTransaction().remove(baoCao).commit();
        fragmentManager.beginTransaction().remove(doiMatKhau).commit();
        parentLayout.setVisibility(View.VISIBLE);
    }
}