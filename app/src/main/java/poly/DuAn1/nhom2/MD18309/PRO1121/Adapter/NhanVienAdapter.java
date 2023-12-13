package poly.DuAn1.nhom2.MD18309.PRO1121.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.TaiKhoanDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.TaiKhoan;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewFucker> {

    private final Context context;
    private final TaiKhoanDAO taiKhoanDAO;
    private ArrayList<TaiKhoan> taiKhoanArrayList;
    private static OnItemClickCallBack onItemClickCallBack;

    public interface OnItemClickCallBack{
        void onClickListener(String userName, String ten);

        void onLongClickListenter(String userName, int holderPOS);
    }

    public NhanVienAdapter(Context context, ArrayList<TaiKhoan> taiKhoanArrayList, OnItemClickCallBack onItemClickCallBack) {
        this.context = context;
        this.taiKhoanDAO = new TaiKhoanDAO(context);
        this.taiKhoanArrayList = taiKhoanArrayList;
        NhanVienAdapter.onItemClickCallBack = onItemClickCallBack;
    }

    private void getData(){
        taiKhoanArrayList = taiKhoanDAO.getListTaiKhoan();
    }

    @NonNull
    @Override
    public ViewFucker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khach_hang_nhan_vien, parent, false);
        return new ViewFucker(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewFucker holder, int position) {
        holder.holderPOS = holder.getAdapterPosition();
        holder.txtHoTen.setText(taiKhoanArrayList.get(holder.getAdapterPosition()).getHoTen());
        holder.txtMa.setText("Username: "+taiKhoanArrayList.get(holder.getAdapterPosition()).getUserName());
        holder.txtSDT.setText("SĐT: "+taiKhoanArrayList.get(holder.getAdapterPosition()).getPhone());
        holder.txtEmail.setText("Email: "+taiKhoanArrayList.get(holder.getAdapterPosition()).getEmail());

        if (!taiKhoanArrayList.get(holder.getAdapterPosition()).getRole().equalsIgnoreCase("nhanvien")){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            params.topMargin = -14;
            holder.itemView.setLayoutParams(params);
        }

        if (taiKhoanArrayList.get(holder.getAdapterPosition()).getStatus() == 1){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            params.topMargin = -14;
            holder.itemView.setLayoutParams(params);
        }
    }

    public void notifyChange(int holderPOS){
        taiKhoanArrayList.clear();
        getData();
        notifyItemChanged(holderPOS);
    }


    @Override
    public int getItemCount() {
        return taiKhoanArrayList.size();
    }

    public static class ViewFucker extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        int holderPOS;
        TextView txtHoTen, txtMa, txtSDT, txtEmail;
        public ViewFucker(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtMa = itemView.findViewById(R.id.txtMa);
            txtSDT = itemView.findViewById(R.id.txtSDT);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }

        @Override
        public void onClick(View v) {
            onItemClickCallBack.onClickListener(txtMa.getText().toString().substring(10), txtHoTen.getText().toString());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickCallBack.onLongClickListenter(txtMa.getText().toString().substring(10), holderPOS);
            return true;
        }
    }
}
