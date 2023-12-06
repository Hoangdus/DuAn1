package poly.DuAn1.nhom2.MD18309.PRO1121.fragments_mini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.TaiKhoanDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class DoiMatKhau extends Fragment {

    private FragmentCallBack fragmentCallBack;
    private TaiKhoan taiKhoan;
    private TaiKhoanDAO TKDAO;

    public interface FragmentCallBack{
        void exitFragment();
    }

    public DoiMatKhau() {}

    public DoiMatKhau(FragmentCallBack fragmentCallBack, TaiKhoan taiKhoan) {
        this.fragmentCallBack = fragmentCallBack;
        this.taiKhoan = taiKhoan;
        TKDAO = new TaiKhoanDAO(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimatkhau, container, false);

        TextView TVUN = view.findViewById(R.id.tv_un);
        TVUN.setText(taiKhoan.getUserName());

        EditText EdtOP = view.findViewById(R.id.edt_old_pass),
                EdtNP = view.findViewById(R.id.edt_new_pass),
                EdtRNP = view.findViewById(R.id.edt_re_new_pass);
        Button BtnDONE = view.findViewById(R.id.btn_done),
                BtnCancel = view.findViewById(R.id.btn_cancel);

        //EdtOP.setText(taiKhoan.getPassWord());

        BtnDONE.setOnClickListener(v -> {
            if(Check(EdtOP,EdtNP,EdtRNP)){
                taiKhoan.setPassWord(EdtNP.getText().toString());
                if(TKDAO.updateTaiKhoan(taiKhoan)) {
                    Toast.makeText(getContext(), "Tay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                    fragmentCallBack.exitFragment();
                }
                else
                    Toast.makeText(getContext(), "Thay đổi thất bại, đã xẩy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        BtnCancel.setOnClickListener(v -> {
            EdtOP.setText("");
            EdtNP.setText("");
            EdtRNP.setText("");
            fragmentCallBack.exitFragment();
        });

        return view;
    }

    private boolean Check(EditText edtOP, EditText edtNP, EditText edtRNP) {
        String sOP = edtOP.getText().toString(),
                sNP = edtNP.getText().toString(),
                sRNP = edtRNP.getText().toString();
        if(sOP.isEmpty()|sNP.isEmpty()|sRNP.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!sOP.equals(taiKhoan.getPassWord())){
            Toast.makeText(getContext(), "Mật khẩu cũ chưa đúng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!sNP.equals(sRNP)){
            Toast.makeText(getContext(), "Mật khẩu nhập lại chưa khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
