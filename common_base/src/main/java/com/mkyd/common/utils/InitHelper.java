package com.mkyd.common.utils;

import android.app.Application;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mkyd.common_base.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

/**
 * Description: 第三方初始化工具
 * Data：2020/3/9-18:53
 * Author: ly
 */
public class InitHelper {

    private static final String TAG = "initHelper";
    private static Application mApplication;
    private static InitHelper instance;

    private InitHelper() {

    }

    public static InitHelper getInstance(Application application) {
        if (instance==null){
            synchronized (InitHelper.class){
                if (instance==null){
                    instance = new InitHelper();
                    mApplication = application ;
                }
            }
        }
        return instance ;
    }

    public InitHelper initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(mApplication)) {
            return this;
        }
        LeakCanary.install(mApplication);
        return this;
    }

    public InitHelper initRouter() {

        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApplication); // 尽可能早，推荐在Application中初始化
        return this;
    }

}
