package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.os.Bundle;

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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.NganhHangAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NganhHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NhaCungCapDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NganhHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachNganhHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachNganhHang extends Fragment implements NganhHangAdapter.OnItemClickCallBack, ThemNganhHang.FragmentCallBack {

    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private NganhHangDAO nganhHangDAO;
    private ConstraintLayout constraintLayout;
    private ThemNganhHang themNganhHang;
    private NganhHangAdapter nganhHangAdapter;
    private ArrayList<NganhHang> nganhHangArrayList;
    private int holderPOS;
    private int mode;
    FragmentCallBack fragmentCallBack;



    public interface FragmentCallBack{
        void enterAddFragment(String title);
        void exitAddFragment();
        void returnNHData(int id, String tenNH);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachNganhHang() {
        // Required empty public constructor
    }

    public DanhSachNganhHang(int mode,FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
        this.mode = mode;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachNganhHangf.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachNganhHang newInstance(String param1, String param2) {
        DanhSachNganhHang fragment = new DanhSachNganhHang();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_nganh_hang, container, false);

        //Khai Báo
        recyclerView = view.findViewById(R.id.recyclerView);
        EditText edtSearch = view.findViewById(R.id.edtSearch);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        nganhHangDAO = new NganhHangDAO(getContext());
        ArrayList<NganhHang> listTimKiem = new ArrayList<>();
        constraintLayout = view.findViewById(R.id.constraintLayout);
        fragmentManager = getChildFragmentManager();

        //Tìm Kiếm Theo Tên
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listTimKiem.clear();
                for (NganhHang nganhHang: nganhHangArrayList){
                    if(!s.toString().isEmpty()){
                        if (nganhHang.getTenNganhHang().contains(s)){
                            listTimKiem.add(nganhHang);
                            setAdapter(listTimKiem);
                        }else{
                            setAdapter(listTimKiem);
                        }
                    }else{
                        setAdapter(nganhHangArrayList);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Setup RecyclerView
        loadList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter(nganhHangArrayList);

        //Thêm Ngành Hàng
        btnAdd.setOnClickListener(v -> {
            themNganhHang = new ThemNganhHang(getContext(), -1, this);
            fragmentManager.beginTransaction().replace(R.id.framelayout, themNganhHang).commit();
            constraintLayout.setVisibility(View.INVISIBLE);
            fragmentCallBack.enterAddFragment("Thêm Ngành Hàng");
        });

        return view;
    }

    private void loadList(){
        nganhHangArrayList = nganhHangDAO.getNganhHangList();

    }

    private void setAdapter(ArrayList<NganhHang> nganhHangArrayList){
        nganhHangAdapter = new NganhHangAdapter(getContext(), nganhHangArrayList, this);
        recyclerView.setAdapter(nganhHangAdapter);
    }

    //Xem Thông Tin Ngành Hàng
    @Override
    public void onLongClickListenter(int id, int holderPOS) {
        System.out.println(id);
        this.holderPOS = holderPOS;
        themNganhHang = new ThemNganhHang(getContext(), id, this);
        fragmentManager.beginTransaction().replace(R.id.framelayout, themNganhHang).commit();
        constraintLayout.setVisibility(View.INVISIBLE);
        fragmentCallBack.enterAddFragment("Thông Tin Ngành Hàng");
    }

    //Lấy thông tin cho việc thêm mặt hàng
    @Override
    public void onClickListener(int id, String ten) {
        if (mode == 1){
            fragmentCallBack.returnNHData(id, ten);
            fragmentCallBack.exitAddFragment();
        }
    }


    @Override
    public void finishCall(int result) {
        fragmentManager.beginTransaction().remove(themNganhHang).commit();
        constraintLayout.setVisibility(View.VISIBLE);
        fragmentCallBack.exitAddFragment();
        if (result == 1){
            setAdapter(nganhHangDAO.getNganhHangList());
        }else if (result == 2){
            loadList();
            nganhHangAdapter.notifyChange(holderPOS);
        }
    }
}