package cn.minminaya.privatetestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.minminaya.privatetestproject.img.cache.ImagCacheActivity;
import cn.minminaya.privatetestproject.view.zhihu.ZhihuAdActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_zhihu_ad)
    Button btnZhihuAd;
    @BindView(R.id.btn_lru)
    Button btnNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_zhihu_ad, R.id.btn_lru})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_zhihu_ad:
                Toast.makeText(this, "知乎滑动广告", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, ZhihuAdActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_lru:
                Intent intent2 = new Intent(this, ImagCacheActivity.class);
                startActivity(intent2);
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
