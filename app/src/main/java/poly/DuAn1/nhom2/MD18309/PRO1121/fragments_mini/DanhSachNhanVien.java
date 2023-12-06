package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.KhachHangAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.NhanVienAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.KhachHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.TaiKhoanDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.KhachHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachNhanVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachNhanVien extends Fragment implements NhanVienAdapter.OnItemClickCallBack {

    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private ConstraintLayout constraintLayout;
    private TaiKhoanDAO taiKhoanDAO;
    private ArrayList<TaiKhoan> listTaiKhoan;
    private NhanVienAdapter nhanVienAdapter;
    private int holderPOS;
    private FragmentCallBack fragmentCallBack;

    public interface FragmentCallBack{
        void exitFragment();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachNhanVien() {
        // Required empty public constructor
    }

    public DanhSachNhanVien(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachNhanVien.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachNhanVien newInstance(String param1, String param2) {
        DanhSachNhanVien fragment = new DanhSachNhanVien();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_nhan_vien, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        EditText edtSearch = view.findViewById(R.id.edtSearch);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        ArrayList<TaiKhoan> listTimKiem = new ArrayList<>();
        fragmentManager = getChildFragmentManager();
        constraintLayout = view.findViewById(R.id.constraintLayout);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listTimKiem.clear();
                for (TaiKhoan taiKhoan: listTaiKhoan){
                    if(!s.toString().isEmpty()){
                        if (taiKhoan.getHoTen().contains(s)){
                            listTimKiem.add(taiKhoan);
                            setAdapter(listTimKiem);
                        }else{
                            setAdapter(listTimKiem);
                        }
                    }else{
                        setAdapter(listTaiKhoan);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter(listTaiKhoan);

        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(v -> fragmentCallBack.exitFragment());
        return view;
    }

    private void loadList(){
        listTaiKhoan = taiKhoanDAO.getListTaiKhoan();
    }

    private void setAdapter(ArrayList<TaiKhoan> arrayList){
        nhanVienAdapter = new NhanVienAdapter(getContext(), arrayList, this);
        recyclerView.setAdapter(nhanVienAdapter);
    }

    @Override
    public void onClickListener(int id, String ten) {

    }

    @Override
    public void onLongClickListenter(int id, int holderPOS) {

    }
}