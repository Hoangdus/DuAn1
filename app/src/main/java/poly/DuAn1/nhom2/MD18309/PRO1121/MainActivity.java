package poly.DuAn1.nhom2.MD18309.PRO1121;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.GridItemAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments.HoaDon;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments.KhoHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments.Menu;
import poly.DuAn1.nhom2.MD18309.PRO1121.fragments.TrangChu;

public class MainActivity extends AppCompatActivity implements TrangChu.FragmentCallback, Menu.FragmentCallBack {
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private KhoHang khoHang = null;
    private HoaDon hoaDon = null;
    private Menu menu = null;
    private TrangChu trangChu = null;
    private TaiKhoan taiKhoan = null;
    private Activity activity;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        fragmentManager = getSupportFragmentManager();
        Window window = activity.getWindow();
//
//        if(savedInstanceState != null){
//            taiKhoan = (TaiKhoan) savedInstanceState.getSerializable(login_screen.KEY_TK);
//        }else {
//        }
        taiKhoan = (TaiKhoan) getIntent().getSerializableExtra(login_screen.KEY_TK);
//        trangChu = new TrangChu(taiKhoan);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setItemRippleColor(null);
        bottomNavigationView.setSelectedItemId(R.id.trangchu);
        fragmentManager.beginTransaction().replace(R.id.framelayout, new TrangChu(taiKhoan, this)).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.trangchu) {
                trangChu = new TrangChu(taiKhoan, this);
                fragmentManager.beginTransaction().replace(R.id.framelayout, trangChu).commit();
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_color));
            } else if (item.getItemId() == R.id.hoadon) {
                hoaDon = new HoaDon();
                fragmentManager.beginTransaction().replace(R.id.framelayout, hoaDon).commit();
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_color));
            } else if (item.getItemId() == R.id.khohang) {
                khoHang = new KhoHang();
                fragmentManager.beginTransaction().replace(R.id.framelayout, khoHang).commit();
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_color));
            } else if (item.getItemId() == R.id.menu) {
                menu = new Menu(taiKhoan, this);
                fragmentManager.beginTransaction().replace(R.id.framelayout, menu).commit();
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.primary_color));
            }
            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println(taiKhoan.getHoTen());
        outState.putSerializable(login_screen.KEY_TK, taiKhoan);
    }

    //Tắt Bàn Phím Khi Ấn Chỗ Khác
    //https://dev.to/ahmmedrejowan/hide-the-soft-keyboard-and-remove-focus-from-edittext-in-android-ehp
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if (v instanceof EditText){
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())){
                    v.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemClicked(int position) {
        System.out.println(position);
        if (position == 0) {
            bottomNavigationView.setSelectedItemId(R.id.menu);
            Runnable delay = () -> menu.openThongKe();
            handler.postDelayed(delay, 1);
        }else if (position == 1) {
            bottomNavigationView.setSelectedItemId(R.id.hoadon);
        }else if (position == 2){
            bottomNavigationView.setSelectedItemId(R.id.khohang);
            Runnable delay = () -> khoHang.openDanhSachMatHang();
            handler.postDelayed(delay, 1);
        }else if(position == 3){
            bottomNavigationView.setSelectedItemId(R.id.khohang);
            Runnable delay = () -> khoHang.openNCC();
            handler.postDelayed(delay, 1);
        }else if(position == 4){
            bottomNavigationView.setSelectedItemId(R.id.menu);
            Runnable delay = () -> menu.openQLNhanVien();
            handler.postDelayed(delay, 1);
        }
    }

    @Override
    public void logout() {
        onBackPressed();
    }
}