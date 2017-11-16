package cn.minminaya.privatetestproject.img.cache;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.minminaya.privatetestproject.R;

/**
 * Created by Niwa on 2017/11/16.
 */

public class ImagCacheRAdapter extends RecyclerView.Adapter<ImagCacheRAdapter.ViewHolderA> {

    private Context context;
    public ImageLoader mImageLoader;
    private int mStartImgIndex;
    private int mEndImgIndex;

    public static String[] URLS;//图片的下载路径集合

    private boolean mFirstOpenAPP;//标记是否首次打开程序

    public ImagCacheRAdapter(Context context, String[] mUrlsdata, RecyclerView recyclerView) {
        URLS = mUrlsdata;
        this.context = context;
        this.mFirstOpenAPP = true;
        mImageLoader = new ImageLoader(recyclerView);


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //不滚动的时候
                    //加载可见项
                    mImageLoader.showImages(mStartImgIndex, mEndImgIndex);
                } else {
                    //滑动的时候取消取消加载
                    mImageLoader.cancelAllTask();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mStartImgIndex = linearLayoutManager.findFirstVisibleItemPosition();
                mEndImgIndex = linearLayoutManager.findLastVisibleItemPosition() + 1 ;
                Log.e("index", "当前开始index：" + mStartImgIndex + "，" + "当前EndIndex:" + mEndImgIndex);
                //首次打开app时加载图片，因为上面的onScrollStateChanged函数里面的逻辑是滚动时候不加载数据，所以这里要加上刚打开的时候加载数据
                if (mFirstOpenAPP && linearLayoutManager.getChildCount() > 0) {
                    // 加载图片(序号从mStartImgIndex-->mEndImgIndex)
                    mImageLoader.showImages(mStartImgIndex, mEndImgIndex);
                    mFirstOpenAPP = false;
                }
            }
        });

    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_img_cache, parent, false);
        return new ViewHolderA(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {
        if (URLS != null) {
            holder.img.setImageResource(R.mipmap.ic_launcher);
            holder.img.setTag(URLS[position]);

        }
    }

    @Override
    public int getItemCount() {
        return URLS.length > 0 ? URLS.length : 0;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
