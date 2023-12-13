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

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.TaiKhoanDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemNhanVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemNhanVien extends Fragment {

    private TaiKhoanDAO taiKhoanDAO;
    private String userName = "";
    private String title;
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

    public ThemNhanVien() {
        // Required empty public constructor
    }

    public ThemNhanVien(String title, String userName,FragmentCallBack fragmentCallBack) {
        this.title = title;
        this.userName = userName;
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThemNhanVien.
     */
    // TODO: Rename and change types and number of parameters
    public static ThemNhanVien newInstance(String param1, String param2) {
        ThemNhanVien fragment = new ThemNhanVien();
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
        View view = inflater.inflate(R.layout.fragment_them_nhan_vien, container, false);

        //khai báo
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        TextView txtTitle = view.findViewById(R.id.title);
        AtomicBoolean editMode = new AtomicBoolean(false);
        Button btnCancel = view.findViewById(R.id.btnCancel),
               btnAdd = view.findViewById(R.id.btnAdd),
               btnDelete = view.findViewById(R.id.btnDelete);
        TextInputLayout edtHoTenLayout = view.findViewById(R.id.edtHotenLayout),
                        edtSDTLayout = view.findViewById(R.id.edtSodienthoaiLayout),
                        edtEmailLayout = view.findViewById(R.id.edtEmailLayout),
                        edtUsernameLayout = view.findViewById(R.id.edtUsernameLayout),
                        edtMatKhauLayout = view.findViewById(R.id.edtMatkhauLayout);

        TextInputEditText edtHoTen = view.findViewById(R.id.edtHoten),
                          edtSDT = view.findViewById(R.id.edtSodienthoai),
                          edtEmail = view.findViewById(R.id.edtEmail),
                          edtUsername = view.findViewById(R.id.edtUsername),
                          edtMatKhau = view.findViewById(R.id.edtMatkhau);

        txtTitle.setText(title);

        if (!userName.isEmpty()){

            TaiKhoan taiKhoan = taiKhoanDAO.getTaiKhoan(userName);

            edtHoTen.setEnabled(false);
            edtEmail.setEnabled(false);
            edtSDT.setEnabled(false);
            edtUsername.setEnabled(false);
            edtMatKhau.setEnabled(false);
            btnDelete.setVisibility(View.VISIBLE);
            edtUsername.setVisibility(View.GONE);
            edtUsernameLayout.setVisibility(View.GONE);
            btnAdd.setText("Chỉnh Sửa");
            edtHoTen.setText(taiKhoan.getHoTen());
            edtEmail.setText(taiKhoan.getEmail());
            edtSDT.setText(taiKhoan.getPhone());
            edtMatKhau.setText(taiKhoan.getPassWord());

        }else{
            btnDelete.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(v -> {
            String hoTen = edtHoTen.getText().toString();
            String email = edtEmail.getText().toString();
            String sDT = edtSDT.getText().toString();
            String newUserName = edtUsername.getText().toString();
            String matKhau = edtMatKhau.getText().toString();
            if (!userName.isEmpty()){
                if (editMode.get()){
                    if (hoTen.isEmpty() || email.isEmpty() || sDT.isEmpty() || matKhau.isEmpty()){
                        edtHoTenLayout.setError("Trống");
                        edtEmailLayout.setError("Trống");
                        edtMatKhauLayout.setError("Trống");
                        edtSDTLayout.setError("Trống");
                        Runnable runnable = () -> {
                            edtHoTenLayout.setErrorEnabled(false);
                            edtEmailLayout.setErrorEnabled(false);
                            edtMatKhauLayout.setErrorEnabled(false);
                            edtSDTLayout.setErrorEnabled(false);
                        };
                        Handler handler = new Handler();
                        handler.postDelayed(runnable, 1200);
                    } else {
                        if (taiKhoanDAO.updateTaiKhoan(new TaiKhoan(userName, matKhau, "nhanvien", hoTen, sDT, email, 0))){
                            Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            fragmentCallBack.finishCall(2);
                        }else{
                            Toast.makeText(getContext(), "Sửa Thành Bại", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), "Thêm Thành Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    edtHoTen.setEnabled(true);
                    edtEmail.setEnabled(true);
                    edtSDT.setEnabled(true);
                    edtMatKhau.setEnabled(true);
                    btnAdd.setText("Xong");
                    edtHoTen.requestFocus();
                }
                editMode.set(true);
            }else{
                if (hoTen.isEmpty() || email.isEmpty() || sDT.isEmpty() || newUserName.isEmpty() || matKhau.isEmpty()){
                    edtHoTenLayout.setError("Trống");
                    edtEmailLayout.setError("Trống");
                    edtMatKhauLayout.setError("Trống");
                    edtSDTLayout.setError("Trống");
                    edtUsernameLayout.setError("Trống");
                    Runnable runnable = () -> {
                        edtHoTenLayout.setErrorEnabled(false);
                        edtEmailLayout.setErrorEnabled(false);
                        edtMatKhauLayout.setErrorEnabled(false);
                        edtSDTLayout.setErrorEnabled(false);
                        edtUsernameLayout.setErrorEnabled(false);
                    };
                    Handler handler = new Handler();
                    handler.postDelayed(runnable, 1200);
                }else{
                    if (taiKhoanDAO.checkUserName(newUserName)){
                        edtUsernameLayout.setError("Username đã tồn tại :V");
                        Runnable runnable = () -> {
                            edtUsernameLayout.setErrorEnabled(false);
                        };
                        Handler handler = new Handler();
                        handler.postDelayed(runnable, 1200);
                    }else{
                        if (taiKhoanDAO.AddTaiKhoan(new TaiKhoan(newUserName, matKhau, "nhanvien", hoTen, sDT, email, 0))){
                            Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            fragmentCallBack.finishCall(1);
                        }else{
                            Toast.makeText(getContext(), "Thêm Thành Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Xác Nhận Xóa");
            builder.setMessage("Chắc Không Đấy Bạn?");
            builder.setPositiveButton("Chắc", (dialog, which) -> {
                if (taiKhoanDAO.DeleteTaiKhoan(userName)){
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

        btnCancel.setOnClickListener(v -> fragmentCallBack.finishCall(0));

        return view;
    }
}