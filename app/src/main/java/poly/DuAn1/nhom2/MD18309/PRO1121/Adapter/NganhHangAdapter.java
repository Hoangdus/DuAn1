package poly.DuAn1.nhom2.MD18309.PRO1121.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NganhHangDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NganhHang;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class NganhHangAdapter extends RecyclerView.Adapter<NganhHangAdapter.ViewFucker>{
    private final Context context;
    private ArrayList<NganhHang> nganhHangArrayList;
    private NganhHangDAO nganhHangDAO;
    private static OnItemClickCallBack onItemClickCallBack;

    public interface OnItemClickCallBack{
        void onClickListener(int id, String ten);

        void onLongClickListenter(int id, int holderPOS);
    }



    public NganhHangAdapter(Context context, ArrayList<NganhHang> nganhHangArrayList, OnItemClickCallBack onItemClickCallBack) {
        this.context = context;
        this.nganhHangArrayList = nganhHangArrayList;
        this.nganhHangDAO = new NganhHangDAO(context);
        NganhHangAdapter.onItemClickCallBack = onItemClickCallBack;
    }

    private void GetData(){
        nganhHangArrayList = nganhHangDAO.getNganhHangList();
    }
    @NonNull
    @Override
    public ViewFucker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.nganh_hang_item, parent, false);
        return new ViewFucker(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewFucker holder, int position) {
        holder.txtTenNganhHang.setText(nganhHangArrayList.get(holder.getAdapterPosition()).getTenNganhHang());
        holder.txtMaHangHang.setText("Mã: "+nganhHangArrayList.get(holder.getAdapterPosition()).getIdNganhHang());
        holder.holderPOS=holder.getAdapterPosition();
        if (nganhHangArrayList.get(holder.getAdapterPosition()).getTrangThai() == 1){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            params.topMargin = -14;
            holder.itemView.setLayoutParams(params);
        }
    }

    public void notifyChange(int holderPOS){
        nganhHangArrayList.clear();
        GetData();
        notifyItemChanged(holderPOS);
    }

    @Override
    public int getItemCount() {
        return nganhHangArrayList.size();
    }

    public static class ViewFucker extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView txtTenNganhHang, txtMaHangHang;
        int holderPOS;

        public ViewFucker(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            txtTenNganhHang = itemView.findViewById(R.id.txtTenNganhHang);
            txtMaHangHang = itemView.findViewById(R.id.txtMaNganhHang);
        }

        @Override
        public void onClick(View v) {
            onItemClickCallBack.onClickListener(Integer.parseInt(txtMaHangHang.getText().toString().replaceAll("[^0-9]", "")), txtTenNganhHang.getText().toString());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickCallBack.onLongClickListenter(Integer.parseInt(txtMaHangHang.getText().toString().replaceAll("[^0-9]", "")), holderPOS);
            return true;
        }
    }
}
