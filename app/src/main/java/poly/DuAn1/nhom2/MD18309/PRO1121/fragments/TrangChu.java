package poly.DuAn1.nhom2.MD18309.PRO1121.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import poly.DuAn1.nhom2.MD18309.PRO1121.Adapter.GridItemAdapter;
import poly.DuAn1.nhom2.MD18309.PRO1121.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu newInstance(String param1, String param2) {
        TrangChu fragment = new TrangChu();
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
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        CustomGridLayout customGridLayout = new CustomGridLayout(getContext(), 2, false);
        customGridLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(customGridLayout);

        recyclerView.setAdapter(new GridItemAdapter(getContext(), null));
        return view;
    }

    public static class CustomGridLayout extends GridLayoutManager{
        private final boolean flag;
        public CustomGridLayout(Context context, int spanCount, boolean flag) {
            super(context, spanCount);
            this.flag = flag;
        }
        @Override
        public boolean canScrollVertically() {
            return flag && super.canScrollVertically();
        }
    }
}