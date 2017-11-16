package cn.minminaya.privatetestproject.img.cache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.minminaya.privatetestproject.R;

public class ImagCacheActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ImagCacheRAdapter imagCacheRAdapter = null;
    private String[] urlStrings = new String[]{
            "http://upload.jianshu.io/users/upload_avatars/3515789/cc80e295-dc84-4afa-bb85-039c146df0a9.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240", "http://img0.imgtn.bdimg.com/it/u=2764371306,3467823016&fm=27&gp=0.jpg",
            "http://img.dongqiudi.com/uploads/avatar/2015/07/25/QM387nh7As_thumb_1437790672318.jpg",
            "http://img5.imgtn.bdimg.com/it/u=547138142,3998729701&fm=27&gp=0.jpg",
            "http://www.qqzhi.com/uploadpic/2014-09-14/070503273.jpg",
            "http://img5q.duitang.com/uploads/item/201501/07/20150107220508_BsnLB.thumb.224_0.jpeg",
            "http://diy.qqjay.com/u2/2014/1130/6272576897a2e42385ddbcf41435d938.jpg",
            "http://www.18183.com/uploads/allimg/140616/61-140616111040.jpg",
            "http://up.qqjia.com/z/24/tu29253_9.jpg",
            "http://cdn.duitang.com/uploads/item/201409/07/20140907193547_4YF8e.thumb.224_0.jpeg",
            "http://img3.imgtn.bdimg.com/it/u=1535299223,1773821976&fm=214&gp=0.jpg",
            "http://www.feizl.com/upload2007/2013_07/130731204792789.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1712317116,2031280619&fm=214&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1215764257,2244678340&fm=214&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2396930774,866518571&fm=214&gp=0.jpg",
            "http://www.qqzhi.com/uploadpic/2014-05-12/080230804.jpg",
            "http://www.qqzhi.com/uploadpic/2014-09-18/120331604.jpg",
            "http://img.jgzyw.com:8000/d/img/touxiang/2015/01/01/2015010109283525306.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imag_cache);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        imagCacheRAdapter = new ImagCacheRAdapter(this, urlStrings, recyclerView);
        recyclerView.setAdapter(imagCacheRAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imagCacheRAdapter.mImageLoader.cancelAllTask();
    }
}
