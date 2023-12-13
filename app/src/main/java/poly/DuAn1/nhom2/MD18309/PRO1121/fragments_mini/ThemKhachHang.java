package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicBoolean;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.KhachHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.KhachHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemKhachHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemKhachHang extends Fragment {

    private KhachHangDAO khachHangDAO;
    private int idKH;
    private String title;

    private TextInputLayout edtHoTenLayout,
            edtSDTLayout,
            edtEmailLayout;
    FragmentCallBack fragmentCallBack;

    public interface FragmentCallBack{
        void finishCall(int result);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThemKhachHang() {
        // Required empty public constructor
    }

    public ThemKhachHang(int idKH, String title, FragmentCallBack fragmentCallBack) {
        this.idKH = idKH;
        this.title = title;
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThemKhachHang.
     */
    // TODO: Rename and change types and number of parameters
    public static ThemKhachHang newInstance(String param1, String param2) {
        ThemKhachHang fragment = new ThemKhachHang();
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
        View view = inflater.inflate(R.layout.fragment_them_khach_hang, container, false);

        //khai báo
        khachHangDAO = new KhachHangDAO(getContext());
        TextView txtTitle = view.findViewById(R.id.title);
        AtomicBoolean editMode = new AtomicBoolean(false);
        Button btnAdd = view.findViewById(R.id.btnAdd),
               btnCancel = view.findViewById(R.id.btnCancel),
               btnDelete = view.findViewById(R.id.btnDelete);
        edtHoTenLayout = view.findViewById(R.id.edtHotenLayout);
        edtSDTLayout = view.findViewById(R.id.edtSodienthoaiLayout);
        edtEmailLayout = view.findViewById(R.id.edtEmailLayout);

        TextInputEditText edtHoTen = view.findViewById(R.id.edtHoten),
                          edtSDT = view.findViewById(R.id.edtSodienthoai),
                          edtEmail = view.findViewById(R.id.edtEmail);

        txtTitle.setText(title);

        if (idKH != -1){
            KhachHang khachHang = khachHangDAO.getKhachHangbyID(idKH);
            edtSDT.setEnabled(false);
            edtEmail.setEnabled(false);
            edtHoTen.setEnabled(false);
            btnDelete.setVisibility(View.VISIBLE);
            btnAdd.setText("Chỉnh sửa");
            edtEmail.setText(khachHang.getAddressKH());
            edtSDT.setText(khachHang.getPhoneKH());
            edtHoTen.setText(khachHang.getHoTenKH());
        }else{
            btnDelete.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(v -> {
            String hoTen = edtHoTen.getText().toString();
            String email = edtEmail.getText().toString();
            String sDT = edtSDT.getText().toString();
            if(idKH != -1){
                if (editMode.get()){
                    if(email.isEmpty() || hoTen.isEmpty() || sDT.isEmpty()){
                        setError();
                    }else{
                        if (khachHangDAO.updateKhachHang(new KhachHang(idKH, hoTen, sDT, email, 0))){
                            Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            fragmentCallBack.finishCall(2);
                        }else{
                            Toast.makeText(getContext(), "Sửa Thành Bại", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), "Thêm Thành Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    btnAdd.setText("Xong");
                    edtSDT.setEnabled(true);
                    edtEmail.setEnabled(true);
                    edtHoTen.setEnabled(true);
                    edtHoTen.requestFocus();
                }
                editMode.set(true);
            }else{
                if (email.isEmpty() || hoTen.isEmpty() || sDT.isEmpty()){
                    setError();
                }else{
                    if (khachHangDAO.AddKhachHang(new KhachHang(0, hoTen, sDT, email, 0))){
                        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        fragmentCallBack.finishCall(1);
                    }else{
                        Toast.makeText(getContext(), "Thêm Thành Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Xác Nhận Xóa");
            builder.setMessage("Chắc Không Đấy Bạn?");
            builder.setPositiveButton("Chắc", (dialog, which) -> {
                if (khachHangDAO.DeleteKhachHang(idKH)){
                    Toast.makeText(getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    fragmentCallBack.finishCall(2);
                }else{
                    Toast.makeText(getContext(), "Xóa Thành Bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Không", (dialog, which) -> {

            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        btnCancel.setOnClickListener(v -> {
            fragmentCallBack.finishCall(0);
        });

        return view;
    }

    private void setError(){
        edtEmailLayout.setError("Trống");
        edtHoTenLayout.setError("Trống");
        edtSDTLayout.setError("Trống");
        Runnable runnable = () -> {
            edtEmailLayout.setErrorEnabled(false);
            edtHoTenLayout.setErrorEnabled(false);
            edtSDTLayout.setErrorEnabled(false);
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1200);
    }
}