package com.jianyuyouhun.kotlin.kotlinjmvp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jianyuyouhun.kotlin.library.app.broadcast.LightBroadCast;
import com.jianyuyouhun.kotlin.library.mvp.common.ThemeModel;

/**
 *
 * Created by wangyu on 2017/8/11.
 */

public class AppUtils {
    /**
     * 重启整个APP
     * @param context
     * @param Delayed 延迟多少毫秒
     */
    public static void restartAPP(Context context, long Delayed){
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 200, restartIntent); // 重启应用
        LightBroadCast.Companion.getInstance().sendEmptyMsgDelayed(ThemeModel.Companion.getMSG_WHAT_ALL_ACTIVITY_CLOSE_SELF()
                , Delayed);
    }

}
