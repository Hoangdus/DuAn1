package poly.DuAn1.nhom2.MD18309.PRO1121.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import poly.DuAn1.nhom2.MD18309.PRO1121.ObjectClass.GridItem;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

public class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.ViewFucker>{
    private final Context context;
    private final ArrayList<GridItem> gridItemArrayList;
    private static OnClickCallBack onClickCallBack;

    public interface OnClickCallBack{
        void onCLickListener(int position);
    }

    public GridItemAdapter(Context context, ArrayList<GridItem> gridItemArrayList, OnClickCallBack onClickCallBack) {
        this.context = context;
        this.gridItemArrayList = gridItemArrayList;
        GridItemAdapter.onClickCallBack = onClickCallBack;
    }

    @NonNull
    @Override
    public ViewFucker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        return new ViewFucker(view);
    }

    @Override
    public int getItemCount() {
        return gridItemArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFucker holder, int position) {
        holder.gridName.setText(gridItemArrayList.get(holder.getAdapterPosition()).getGridName());
        holder.gridName.setSelected(true);
        holder.gridIcon.setImageResource(gridItemArrayList.get(holder.getAdapterPosition()).getGridIcon());
        if (gridItemArrayList.get(holder.getAdapterPosition()).getGridDes().isEmpty()){
            holder.gridName.setY(20);
            holder.gridDes.setVisibility(View.INVISIBLE);
        }else{
            holder.gridDes.setText(gridItemArrayList.get(holder.getAdapterPosition()).getGridDes());
            holder.gridDes.setSelected(true);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null){
//
//                }
//            }
//        });
    }

    public static class ViewFucker extends RecyclerView.ViewHolder implements View.OnClickListener {
        ConstraintLayout gridLayout;
        TextView gridName, gridDes;
        ImageView gridIcon;
        public ViewFucker(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            gridName = itemView.findViewById(R.id.gridname);
            gridDes = itemView.findViewById(R.id.griddescription);
            gridIcon = itemView.findViewById(R.id.gridicon);
            gridLayout = itemView.findViewById(R.id.girdLayout);
        }

        @Override
        public void onClick(View v) {
            onClickCallBack.onCLickListener(getAdapterPosition());
        }
    }
}
