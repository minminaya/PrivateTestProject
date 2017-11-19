package cn.minminaya.privatetestproject.img.load;

import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.minminaya.privatetestproject.R;

public class LoadCompressImgActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_compress_img);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.sample);
        imageView.setImageBitmap(bitmap);

    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {
        Log.e("LoadCompressImgActivity","点击ImageView");
        imageView.setImageBitmap(CompressUtil.decodeSampleBitmapFromResource(getResources(),R.mipmap.sample,400,400));
    }
}
