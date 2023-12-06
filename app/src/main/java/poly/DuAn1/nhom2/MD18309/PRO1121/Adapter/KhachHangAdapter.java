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

import java.sql.Array;
import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.KhachHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.KhachHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewFucker> {

    private final Context context;
    private final KhachHangDAO khachHangDAO;
    private ArrayList<KhachHang> khachHangArrayList;
    private static OnItemClickCallBack onItemClickCallBack;

    public interface OnItemClickCallBack{
        void onClickListener(int id, String ten);

        void onLongClickListenter(int id, int holderPOS);
    }

    public KhachHangAdapter(Context context, ArrayList<KhachHang> khachHangArrayList, OnItemClickCallBack onItemClickCallBack) {
        this.context = context;
        this.khachHangDAO = new KhachHangDAO(context);
        this.khachHangArrayList = khachHangArrayList;
        KhachHangAdapter.onItemClickCallBack = onItemClickCallBack;
    }

    private void getData(){
        khachHangArrayList = khachHangDAO.getKhachHangList();
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
        holder.txtHoTen.setText(khachHangArrayList.get(holder.getAdapterPosition()).getHoTenKH());
        holder.txtMa.setText("Mã: "+khachHangArrayList.get(holder.getAdapterPosition()).getIdKH());
        holder.txtSDT.setText("SĐT: "+khachHangArrayList.get(holder.getAdapterPosition()).getPhoneKH());
        holder.txtEmail.setText("Email: "+khachHangArrayList.get(holder.getAdapterPosition()).getAddressKH());

        if (khachHangArrayList.get(holder.getAdapterPosition()).getTrangThai() == 1){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            params.topMargin = -14;
            holder.itemView.setLayoutParams(params);
        }
    }

    public void notifyChange(int holderPOS){
        khachHangArrayList.clear();
        getData();
        notifyItemChanged(holderPOS);
    }

    @Override
    public int getItemCount() {
        return khachHangArrayList.size();
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
            onItemClickCallBack.onClickListener(Integer.parseInt(txtMa.getText().toString().replaceAll("[^0-9]", "")), txtHoTen.getText().toString());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickCallBack.onLongClickListenter(Integer.parseInt(txtMa.getText().toString().replaceAll("[^0-9]", "")), holderPOS);
            return true;
        }
    }
}
