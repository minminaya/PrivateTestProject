package cn.minminaya.privatetestproject.view.zhihu;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.minminaya.privatetestproject.R;

public class ZhihuAdActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_zhihu)
    RecyclerView recyclerViewZhihu;
    @BindView(R.id.img_zhihu)
    ImageView imgZhihu;
    private List<String> lists = new ArrayList<>();
    private ZhihuAdAdapter adAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_ad);
        ButterKnife.bind(this);

        recyclerViewZhihu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewZhihu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewZhihu.getLayoutManager();
        adAdapter = new ZhihuAdAdapter(imgZhihu, layoutManager);
        recyclerViewZhihu.setAdapter(adAdapter);
        for (int i = 0; i < 30; i++) {
            lists.add("item" + i);
        }

        adAdapter.setList(lists);
        adAdapter.notifyDataSetChanged();

        recyclerViewZhihu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("ZhihuAdActivity", "dy:" + dy);

                imgZhihu.offsetTopAndBottom(dy / 12);

            }
        });
    }

}
