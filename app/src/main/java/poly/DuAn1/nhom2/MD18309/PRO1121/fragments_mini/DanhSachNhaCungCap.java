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

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.NhaCungCapAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NhaCungCapDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NhaCungCap;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachNhaCungCap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachNhaCungCap extends Fragment implements NhaCungCapAdapter.OnItemClickCallBack, ThemNhaCungCap.FragmentCallBack {

    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    private NhaCungCapDAO nhaCungCapDAO;
    private ThemNhaCungCap themNhaCungCap;
    private ConstraintLayout constraintLayout;
    private ArrayList<NhaCungCap> nhaCungCapArrayList;
    private NhaCungCapAdapter nhaCungCapAdapter;
    private int holderPOS;
    private int mode;
    FragmentCallBack fragmentCallBack;

    public interface FragmentCallBack{
        void enterAddFragment(String title);
        void exitAddFragment();
        void returnNCCData(int id, String tenNCC);
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachNhaCungCap() {
        // Required empty public constructor
    }

    public DanhSachNhaCungCap(int mode, FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
        this.mode = mode;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachNhaCungCap.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachNhaCungCap newInstance(String param1, String param2) {
        DanhSachNhaCungCap fragment = new DanhSachNhaCungCap();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_nha_cung_cap, container, false);

        //Khai Báo
        recyclerView = view.findViewById(R.id.recyclerView);
        EditText edtSearch = view.findViewById(R.id.edtSearch);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        nhaCungCapDAO = new NhaCungCapDAO(getContext());
        ArrayList<NhaCungCap> listTimKiem = new ArrayList<>();
        fragmentManager = getChildFragmentManager();
        constraintLayout = view.findViewById(R.id.constraintLayout);

        //Tìm Kiếm Theo Tên
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listTimKiem.clear();
                for (NhaCungCap nhaCungCap: nhaCungCapArrayList){
                    if(!s.toString().isEmpty()){
                        if (nhaCungCap.getTenNhaCungCap().contains(s)){
                            listTimKiem.add(nhaCungCap);
                            setAdapter(listTimKiem);
                        }else{
                            setAdapter(listTimKiem);
                        }
                    }else{
                        setAdapter(nhaCungCapArrayList);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //setup RecyclerView
        loadList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter(nhaCungCapArrayList);

        btnAdd.setOnClickListener(v -> {
            themNhaCungCap = new ThemNhaCungCap(getContext(), -1, this);
            fragmentManager.beginTransaction().replace(R.id.framelayout, themNhaCungCap).commit();
            constraintLayout.setVisibility(View.INVISIBLE);
            fragmentCallBack.enterAddFragment("Thêm N.C.C");
//            Toast.makeText(getContext(), "Thêm Thành Công(Chắc Thế)", Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    private void loadList(){
        nhaCungCapArrayList = nhaCungCapDAO.getNhaCungCapList();
    }

    private void setAdapter(ArrayList<NhaCungCap> nhaCungCapArrayList){
        nhaCungCapAdapter = new NhaCungCapAdapter(getContext(), nhaCungCapArrayList, this);
        recyclerView.setAdapter(nhaCungCapAdapter);
    }

    @Override
    public void onItemClick(int id, String ten) {
        if (mode == 1){
            fragmentCallBack.returnNCCData(id, ten);
            fragmentCallBack.exitAddFragment();
        }
    }

    @Override
    public void onLongItemClick(int id, int holderPOS) {
        System.out.println(id);
        this.holderPOS = holderPOS;
        themNhaCungCap = new ThemNhaCungCap(getContext(), id, this);
        fragmentManager.beginTransaction().replace(R.id.framelayout, themNhaCungCap).commit();
        constraintLayout.setVisibility(View.INVISIBLE);
        fragmentCallBack.enterAddFragment("Thông Tin N.C.C");
    }

    @Override
    public void finishCall(int result) {
        fragmentManager.beginTransaction().remove(themNhaCungCap).commit();
        constraintLayout.setVisibility(View.VISIBLE);
        fragmentCallBack.exitAddFragment();
        if (result == 1){
            loadList();
            setAdapter(nhaCungCapArrayList);
        } else if (result == 2) {
            nhaCungCapAdapter.notifyChange(holderPOS);
        }
    }
}