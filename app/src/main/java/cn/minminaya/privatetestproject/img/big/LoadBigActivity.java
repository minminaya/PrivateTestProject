package cn.minminaya.privatetestproject.img.big;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.minminaya.privatetestproject.R;

public class LoadBigActivity extends AppCompatActivity {

    @BindView(R.id.img_big)
    LargeImageView imgBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_big);
        ButterKnife.bind(this);
        try {
            InputStream inputStream = getAssets().open("big_picture.jpg");
            imgBig.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
