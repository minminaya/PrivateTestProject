package cn.minminaya.privatetestproject;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Niwa on 2017/11/5.
 */

public class App extends Application {
    private static App INSTANCE;

    public static App getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Utils.init(this);
    }
}
