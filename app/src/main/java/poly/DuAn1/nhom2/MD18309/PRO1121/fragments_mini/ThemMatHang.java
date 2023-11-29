package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.atomic.AtomicBoolean;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.MatHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NganhHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NhaCungCapDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.MatHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NganhHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NhaCungCap;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemMatHang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemMatHang extends Fragment {

    private Context context;
    private MatHangDAO matHangDAO;
    private NhaCungCapDAO nhaCungCapDAO;
    private NganhHangDAO nganhHangDAO;
    private int idMH;
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

    public ThemMatHang() {
        // Required empty public constructor
    }

    public ThemMatHang(Context context, int idMH, FragmentCallBack fragmentCallBack) {
        this.context = context;
        this.matHangDAO = new MatHangDAO(context);
        this.nganhHangDAO = new NganhHangDAO(context);
        this.nhaCungCapDAO = new NhaCungCapDAO(context);
        this.idMH = idMH;
        this.fragmentCallBack = fragmentCallBack;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThemMatHang.
     */
    // TODO: Rename and change types and number of parameters
    public static ThemMatHang newInstance(String param1, String param2) {
        ThemMatHang fragment = new ThemMatHang();
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
        View view = inflater.inflate(R.layout.fragment_them_mat_hang, container, false);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        AtomicBoolean editMode = new AtomicBoolean(false);

        TextInputLayout edtTenMatHangLayout = view.findViewById(R.id.edtTenmathangLayout);
        TextInputLayout edtNhaCungCapLayout = view.findViewById(R.id.edtNhaCungCapLayout);
        TextInputLayout edtNganhHangLayout = view.findViewById(R.id.edtNganhhangLayout);
        TextInputLayout edtGiaNhapLayout = view.findViewById(R.id.edtGianhapLayout);
        TextInputLayout edtGiaBanLayout = view.findViewById(R.id.edtGiabanLayout);
        TextInputLayout edtSoLuongLayout = view.findViewById(R.id.edtSoluongLayout);
        TextInputLayout edtDVTLayout = view.findViewById(R.id.edtDVTLayout);

        TextInputEditText edtTenMatHang = view.findViewById(R.id.edtTenmathang);
        TextInputEditText edtNhaCungCap = view.findViewById(R.id.edtNhacungcap);
        TextInputEditText edtNganhHang = view.findViewById(R.id.edtNganhhang);
        TextInputEditText edtGiaNhap = view.findViewById(R.id.edtGianhap);
        TextInputEditText edtGiaBan = view.findViewById(R.id.edtGiaban);
        TextInputEditText edtSoLuong = view.findViewById(R.id.edtSoluong);
        TextInputEditText edtDVT = view.findViewById(R.id.edtDVT);

        //Chuyển chế độ dựa trên tham số
        if (idMH != -1){
            btnAdd.setText("Chỉnh sửa");
            btnCancel.setText("Thoát");
            btnCancel.setBackground(ContextCompat.getDrawable(context, R.drawable.button_background));
            MatHang matHang = matHangDAO.getMatHangByID(idMH);
            NganhHang nganhHang = nganhHangDAO.getNganhHangByID(matHang.getIdNganhHang());
            NhaCungCap nhaCungCap = nhaCungCapDAO.getNhaCungCapByID(matHang.getIdNhaCungCap());

            edtNhaCungCap.setText(nhaCungCap.getTenNhaCungCap());
            edtTenMatHang.setText(matHang.getTenMatHang());
            edtNganhHang.setText(nganhHang.getTenNganhHang());
            edtGiaNhap.setText(matHang.getGiaNhapMatHang()+"");
            edtGiaBan.setText(matHang.getGiaMatHang()+"");
            edtSoLuong.setText(matHang.getSoLuongMatHang()+"");
            edtDVT.setText(matHang.getDonViTinh());

            edtNhaCungCap.setEnabled(false);
            edtTenMatHang.setEnabled(false);
            edtSoLuong.setEnabled(false);
            edtDVT.setEnabled(false);
            edtNganhHang.setEnabled(false);
            edtGiaBan.setEnabled(false);
            edtGiaNhap.setEnabled(false);
        }else{
            btnDelete.setVisibility(View.INVISIBLE);
        }

        btnCancel.setOnClickListener(v -> fragmentCallBack.finishCall(0));

        btnAdd.setOnClickListener(v -> {
            String nhaCungCap = edtNhaCungCap.getText().toString();
            String tenMatHang = edtTenMatHang.getText().toString();
            String nganhHang = edtNganhHang.getText().toString();
            String giaNhap = edtGiaNhap.getText().toString();
            String giaBan = edtGiaBan.getText().toString();
            String soLuong = edtSoLuong.getText().toString();
            String dVT = edtDVT.getText().toString();
            if(idMH != -1){
                if (editMode.get()){
                    if(nhaCungCap.isEmpty() || tenMatHang.isEmpty() || nganhHang.isEmpty() || giaBan.isEmpty() || giaNhap.isEmpty() || soLuong.isEmpty() || dVT.isEmpty()){
                        edtNhaCungCapLayout.setError("Trống");
                        edtTenMatHangLayout.setError("Trống");
                        edtNganhHangLayout.setError("Trống");
                        edtGiaNhapLayout.setError("Trống");
                        edtGiaBanLayout.setError("Trống");
                        edtSoLuongLayout.setError("Trống");
                        edtDVTLayout.setError("Trống");
                        Runnable runnable = () -> {
                            edtNhaCungCapLayout.setErrorEnabled(false);
                            edtTenMatHangLayout.setErrorEnabled(false);
                            edtNganhHangLayout.setErrorEnabled(false);
                            edtGiaNhapLayout.setErrorEnabled(false);
                            edtGiaBanLayout.setErrorEnabled(false);
                            edtSoLuongLayout.setErrorEnabled(false);
                            edtDVTLayout.setErrorEnabled(false);

                        };
                        Handler handler = new Handler();
                        handler.postDelayed(runnable, 1200);
                    }else{
                        if (matHangDAO.updateMatHang(new MatHang(idMH, 1, 1, tenMatHang, Float.parseFloat(soLuong), dVT, Integer.parseInt(giaNhap), Integer.parseInt(giaBan), 0))){
                            Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            fragmentCallBack.finishCall(2);
                        }else{
                            Toast.makeText(getContext(), "Sửa Thành Bại", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getContext(), "Thêm Thành Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    btnAdd.setText("Xong");
                    edtNhaCungCap.setEnabled(true);
                    edtTenMatHang.setEnabled(true);
                    edtSoLuong.setEnabled(true);
                    edtDVT.setEnabled(true);
                    edtNganhHang.setEnabled(true);
                    edtGiaBan.setEnabled(true);
                    edtGiaNhap.setEnabled(true);
                    edtTenMatHang.requestFocus();
                }
                editMode.set(true);
            }else{
                if(nhaCungCap.isEmpty() || tenMatHang.isEmpty() || nganhHang.isEmpty() || giaBan.isEmpty() || giaNhap.isEmpty() || soLuong.isEmpty() || dVT.isEmpty()){
                    edtNhaCungCapLayout.setError("Trống");
                    edtTenMatHangLayout.setError("Trống");
                    edtNganhHangLayout.setError("Trống");
                    edtGiaNhapLayout.setError("Trống");
                    edtGiaBanLayout.setError("Trống");
                    edtSoLuongLayout.setError("Trống");
                    edtDVTLayout.setError("Trống");
                    Runnable runnable = () -> {
                        edtNhaCungCapLayout.setErrorEnabled(false);
                        edtTenMatHangLayout.setErrorEnabled(false);
                        edtNganhHangLayout.setErrorEnabled(false);
                        edtGiaNhapLayout.setErrorEnabled(false);
                        edtGiaBanLayout.setErrorEnabled(false);
                        edtSoLuongLayout.setErrorEnabled(false);
                        edtDVTLayout.setErrorEnabled(false);

                    };
                    Handler handler = new Handler();
                    handler.postDelayed(runnable, 1200);
                }else{
                    if (matHangDAO.AddMatHang(new MatHang(0, 1, 1, tenMatHang, Float.parseFloat(soLuong), dVT, Integer.parseInt(giaNhap), Integer.parseInt(giaBan), 0))){
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
                if (matHangDAO.DeleteMatHang(idMH)){
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
        return view;
    }
}