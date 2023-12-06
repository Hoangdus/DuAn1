package poly.DuAn1.nhom2.MD18309.PRO1121.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.DAO.NhaCungCapDAO;
import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.NhaCungCap;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class NhaCungCapAdapter extends RecyclerView.Adapter<NhaCungCapAdapter.ViewFucker> {

    private final Context context;
    private ArrayList<NhaCungCap> nhaCungCapArrayList;
    private NhaCungCapDAO nhaCungCapDAO;
    private static OnItemClickCallBack onItemClickCallBack;

    public interface OnItemClickCallBack{
        void onItemClick(int id, String ten);

        void onLongItemClick(int id, int holderPOS);
    }

    public NhaCungCapAdapter(Context context, ArrayList<NhaCungCap> nhaCungCapArrayList, OnItemClickCallBack onItemClickCallBack) {
        this.context = context;
        this.nhaCungCapArrayList = nhaCungCapArrayList;
        this.nhaCungCapDAO = new NhaCungCapDAO(context);
        NhaCungCapAdapter.onItemClickCallBack = onItemClickCallBack;
    }

    private void GetData(){
        nhaCungCapArrayList = nhaCungCapDAO.getNhaCungCapList();
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
        holder.txtTenNCC.setText(nhaCungCapArrayList.get(holder.getAdapterPosition()).getTenNhaCungCap());
        holder.txtMaNCC.setText("Mã: "+nhaCungCapArrayList.get(holder.getAdapterPosition()).getIdNhaCungCap());
        holder.holderPOS = holder.getAdapterPosition();

        if (nhaCungCapArrayList.get(holder.getAdapterPosition()).getTrangThai() == 1){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.height = 0;
            params.topMargin = -14;
            holder.itemView.setLayoutParams(params);
        }
    }

    public void notifyChange(int holderPOS){
        nhaCungCapArrayList.clear();
        GetData();
        notifyItemChanged(holderPOS);
    }

    @Override
    public int getItemCount() {
        return nhaCungCapArrayList.size();
    }

    public static class ViewFucker extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView txtTenNCC, txtMaNCC;
        int holderPOS;
        public ViewFucker(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            txtTenNCC = itemView.findViewById(R.id.txtTenNganhHang);
            txtMaNCC = itemView.findViewById(R.id.txtMaNganhHang);
        }

        @Override
        public void onClick(View v) {
            onItemClickCallBack.onItemClick(Integer.parseInt(txtMaNCC.getText().toString().replaceAll("[^0-9]", "")), txtTenNCC.getText().toString());
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickCallBack.onLongItemClick(Integer.parseInt(txtMaNCC.getText().toString().replaceAll("[^0-9]", "")), holderPOS);
            return true;
        }
    }
}
