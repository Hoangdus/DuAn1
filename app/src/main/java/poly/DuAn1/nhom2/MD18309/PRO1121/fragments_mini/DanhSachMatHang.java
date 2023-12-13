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
import android.widget.Toast;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.MatHangAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.MatHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachMatHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachMatHang extends Fragment implements MatHangAdapter.OnItemClickCallBack, ThemMatHang.FragmentCallBack {

    private RecyclerView recyclerView;
    private ThemMatHang themMatHang;
    private FragmentManager fragmentManager;
    private ConstraintLayout constraintLayout;
    private MatHangDAO matHangDAO;
    private ArrayList<MatHang> listMatHang;
    private MatHangAdapter matHangAdapter;
    private int holderPOS;

    //Gọi ngược lại activity
    FragmentCallBack fragmentCallBack;
    public interface FragmentCallBack{
        void enterAddFragment(String title);
        void exitAddFragment();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachMatHang() {
        // Required empty public constructor
    }

    public DanhSachMatHang(FragmentCallBack fragmentCallBack) {
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachMatHang.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachMatHang newInstance(String param1, String param2) {
        DanhSachMatHang fragment = new DanhSachMatHang();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_mat_hang, container, false);
        //Khai Báo
        recyclerView = view.findViewById(R.id.recyclerView);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        EditText edtSearch = view.findViewById(R.id.edtSearch);
        matHangDAO = new MatHangDAO(getContext());
        ArrayList<MatHang> listTimKiem = new ArrayList<>();
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
                for (MatHang matHang: listMatHang){
                    if(!s.toString().isEmpty()){
                        if (matHang.getTenMatHang().contains(s)){
                            listTimKiem.add(matHang);
                            setAdapter(listTimKiem);
                        }else{
                            setAdapter(listTimKiem);
                        }
                    }else{
                        setAdapter(listMatHang);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Thêm
        btnAdd.setOnClickListener(v -> {
            themMatHang = new ThemMatHang(getContext(), -1,this);
            fragmentManager.beginTransaction().replace(R.id.framelayout, themMatHang).commit();
            constraintLayout.setVisibility(View.INVISIBLE);
            fragmentCallBack.enterAddFragment("Thêm Mặt Hàng");
        });

        //Setup recyclerView
        loadList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        setAdapter(listMatHang);
        return view;
    }

    private void loadList(){
        listMatHang = matHangDAO.getMatHangList();
    }

    private void setAdapter(ArrayList<MatHang> matHangArrayList){
        matHangAdapter = new MatHangAdapter(getContext(), matHangArrayList, this);
        recyclerView.setAdapter(matHangAdapter);
    }

    //Xem Thông Tin
    @Override
    public void onClickListener(int id, int holderPOS) {
//        System.out.println(id);
        this.holderPOS = holderPOS;
//        System.out.println(holderPOS);
//        Toast.makeText(getContext(), "Tưởng Tượng Màn Hình Thông Tin Chi Tiết", Toast.LENGTH_SHORT).show();
        themMatHang = new ThemMatHang(getContext(), id,this);
        fragmentManager.beginTransaction().replace(R.id.framelayout, themMatHang).commit();
        constraintLayout.setVisibility(View.INVISIBLE);
        fragmentCallBack.enterAddFragment("Thông Tin Mặt hàng");
    }

    //Thoát Fragment Thêm
    @Override
    public void finishCall(int result) {
        fragmentManager.beginTransaction().remove(themMatHang).commit();
        constraintLayout.setVisibility(View.VISIBLE);
        fragmentCallBack.exitAddFragment();
        if (result == 1){
            loadList();
            setAdapter(listMatHang);
        } else if (result == 2) {
//            System.out.println(holderPOS);
            matHangAdapter.notifyChange(holderPOS);
        }
    }
}