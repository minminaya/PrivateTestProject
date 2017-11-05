package cn.minminaya.privatetestproject.view.zhihu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.minminaya.privatetestproject.R;

/**
 * Created by Niwa on 2017/11/4.
 */

public class ZhihuAdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageView imgZhihu;
    private LinearLayoutManager linearLayoutManager;
    private List<String> list = null;


    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public ZhihuAdAdapter(ImageView imgZhihu, LinearLayoutManager layoutManager) {
        this.imgZhihu = imgZhihu;
        this.linearLayoutManager = layoutManager;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 1_0111:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_zhihu_transparent, parent, false);
                viewHolder = new ViewHolderB(view);
                break;
            case 1_0001:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_zhihu, parent, false);
                viewHolder = new ViewHolderA(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
        Log.d("ZhihuAdAdapter", "lastVisiblePosition:" + lastVisiblePosition);

        if (list != null) {
            if (holder instanceof ViewHolderA) {
                ViewHolderA viewHolderA = (ViewHolderA) holder;
                viewHolderA.tvRecyclerViewItem.setText(list.get(position));
            }
        }

        if (linearLayoutManager.findLastVisibleItemPosition() > 8 && linearLayoutManager.findLastVisibleItemPosition() <= 15) {
            imgZhihu.setVisibility(View.VISIBLE);
        } else {
            imgZhihu.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 10) {
            //代表透明区域
            return 1_0111;
        } else {
            return 1_0001;
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recycler_view_item)
        TextView tvRecyclerViewItem;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderB extends RecyclerView.ViewHolder {
        @BindView(R.id.img_recycler_view_item)
        ImageView imgRecyclerViewItem;

        ViewHolderB(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
