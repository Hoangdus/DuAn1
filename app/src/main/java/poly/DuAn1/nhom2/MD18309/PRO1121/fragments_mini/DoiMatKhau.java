package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.TaiKhoanDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class DoiMatKhau extends Fragment {

    private FragmentCallBack fragmentCallBack;
    private TextInputLayout edtOldPasswordLayout,edtNewPasswordLayout,edtNewRetypePasswordLayout;
    private TaiKhoan taiKhoan;
    private TaiKhoanDAO taiKhoanDAO;

    public interface FragmentCallBack{
        void exitFragment();
    }

    public DoiMatKhau() {}

    public DoiMatKhau(FragmentCallBack fragmentCallBack, TaiKhoan taiKhoan) {
        this.fragmentCallBack = fragmentCallBack;
        this.taiKhoan = taiKhoan;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimatkhau, container, false);

        String userName = taiKhoan.getUserName();
        TextView TVUN = view.findViewById(R.id.tv_un);
        TVUN.setText(userName);
        taiKhoanDAO = new TaiKhoanDAO(getContext());
        edtNewPasswordLayout = view.findViewById(R.id.edt_new_pass_layout);
        edtNewRetypePasswordLayout = view.findViewById(R.id.edt_re_new_pass_layout);
        edtOldPasswordLayout = view.findViewById(R.id.edt_old_pass_layout);

        TextInputEditText edtOP = view.findViewById(R.id.edt_old_pass),
                edtNP = view.findViewById(R.id.edt_new_pass),
                edtRNP = view.findViewById(R.id.edt_re_new_pass);
        Button btnDONE = view.findViewById(R.id.btn_done),
                btnCancel = view.findViewById(R.id.btn_cancel);

        //EdtOP.setText(taiKhoan.getPassWord());

        btnDONE.setOnClickListener(v -> {
            String sOP = edtOP.getText().toString(),
                    sNP = edtNP.getText().toString(),
                    sRNP = edtRNP.getText().toString();
            if (sNP.isEmpty() || sOP.isEmpty() || sRNP.isEmpty()){
                setError(0);
            }else {
                if (sNP.equals(sRNP)){
                    if(taiKhoanDAO.login(userName, sOP) != null){
                        if (taiKhoanDAO.changePass(userName, sNP)){
                            Toast.makeText(getContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            setError(3);
                        }
                    }else{
                        setError(2);
                    }
                }else{
                    setError(1);
                }
            }
        });

        btnCancel.setOnClickListener(v -> {
            fragmentCallBack.exitFragment();
        });

        return view;
    }

    private void setError(int mode){
        if (mode == 0){
            edtOldPasswordLayout.setError("Trống");
            edtNewRetypePasswordLayout.setError("Trống");
            edtNewPasswordLayout.setError("Trống");
        }else if (mode == 1) {
            edtNewRetypePasswordLayout.setError("Không Khớp");
            edtNewPasswordLayout.setError("Không Khớp");
        }else if (mode == 2){
            edtOldPasswordLayout.setError("Mật Khẩu Cũ Không ");
        }else if (mode == 3) {
            edtOldPasswordLayout.setError("Thành Bại");
            edtNewRetypePasswordLayout.setError("Thành Bại");
            edtNewPasswordLayout.setError("Thành Bại");
        }
        Runnable runnable = () -> {
            edtOldPasswordLayout.setErrorEnabled(false);
            edtNewPasswordLayout.setErrorEnabled(false);
            edtNewRetypePasswordLayout.setErrorEnabled(false);
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1200);
    }

//    private boolean Check(EditText edtOP, EditText edtNP, EditText edtRNP) {
//
//        if(sOP.isEmpty()|sNP.isEmpty()|sRNP.isEmpty()){
//            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(!sOP.equals(taiKhoan.getPassWord())){
//            Toast.makeText(getContext(), "Mật khẩu cũ chưa đúng", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if(!sNP.equals(sRNP)){
//            Toast.makeText(getContext(), "Mật khẩu nhập lại chưa khớp", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }


}
