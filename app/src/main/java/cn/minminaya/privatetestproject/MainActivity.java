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
import cn.minminaya.privatetestproject.img.load.LoadCompressImgActivity;
import cn.minminaya.privatetestproject.view.zhihu.ZhihuAdActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_zhihu_ad)
    Button btnZhihuAd;
    @BindView(R.id.btn_lru)
    Button btnNull;
    @BindView(R.id.btn_img_load)
    Button btnImgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_zhihu_ad, R.id.btn_lru,R.id.btn_img_load})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_zhihu_ad:
                Toast.makeText(this, "知乎滑动广告", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ZhihuAdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_lru:
                intent = new Intent(this, ImagCacheActivity.class);
                startActivity(intent);
                Toast.makeText(this, "LruCache", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_img_load:
                intent = new Intent(this, LoadCompressImgActivity.class);
                startActivity(intent);
                Toast.makeText(this, "图片压缩", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
