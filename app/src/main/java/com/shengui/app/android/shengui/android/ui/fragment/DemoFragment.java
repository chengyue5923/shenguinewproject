package com.shengui.app.android.shengui.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.DividerItemDecoration;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author FT
 * @time 2016-07-18 16:49
 */

// JUMP 
public class DemoFragment extends Fragment {
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private Context context;
    private int mPage;
    public static final String MERCHANT_DETAILS_PAGE = "MERCHANT_DETAILS_PAGE";
    private DemoAdapter mAdapter;

    List<ProductModel> mData;

    public static DemoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MERCHANT_DETAILS_PAGE, page);
        DemoFragment tripFragment = new DemoFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(MERCHANT_DETAILS_PAGE);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this, view);
        mData = new ArrayList<>();
        for(int i=0; i<30;i++){
            mData.add(new ProductModel());
        }
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new DemoAdapter(getActivity(),mData);
        initAdapter();
        return view;
    }
    /**
     * 设置RecyclerView属性
     */
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);//设置adapter
        //设置item点击事件
//        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
    }
    /**
     * 商家详情adapter
     */
    class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {
        int mLayoutId;
        List<ProductModel> list;
        Context wcontext;
        public DemoAdapter(Context context, List<ProductModel> str) {
            wcontext = context;
            list=str;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layout = -1;
//            switch (viewType) {
//                case Message.TYPE_MESSAGE:
//                    layout = R.layout.item_message;
//                    break;
//                case Message.TYPE_LOG:
//                    layout = R.layout.item_log;
//                    break;
//                case Message.TYPE_ACTION:
//                    layout = R.layout.item_action;
//                    break;
                layout=R.layout.view_activity_tie_zi_item;


//            }
            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(layout, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ProductModel message = list.get(position);
//            viewHolder.setMessage(message.getMessage());
//            viewHolder.setUsername(message.getUsername());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

//        @Override
//        public int getItemViewType(int position) {
//            return list.get(position).getType();
//        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView priductTimeText;
            private RelativeLayout imagesListView;
            private ImageView shareText;

            public ViewHolder(View itemView) {
                super(itemView);
               priductTimeText = (TextView)itemView.findViewById(R.id.priductTimeText);
               imagesListView=(RelativeLayout)itemView.findViewById(R.id.imagesListView);
               shareText=(ImageView)itemView.findViewById(R.id.shareText);
            }

            public void setUsername(String username) {
            }

            public void setMessage(String message) {
                if (null == shareText) return;
            }

        }
    }

}
