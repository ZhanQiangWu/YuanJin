package net.yuanjin.tencentxg.receiver;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 *  腾讯信鸽 - Receiver
 *  Created by WuZhanQiang on 2017/2/14.
 */

public class MessageReceiver extends XGPushBaseReceiver{

    public static final String LogTag = "TPushReceiver";

    /**注册结果**/
    @Override
    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult xgPushRegisterResult) {
        Log.d(LogTag, "----------------------------- > onRegisterResult");
        if (context == null || xgPushRegisterResult == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = xgPushRegisterResult + "注册成功";
            // 在这里拿token
            String token = xgPushRegisterResult.getToken();
        } else {
            text = xgPushRegisterResult + "注册失败，错误码：" + errorCode;
        }
        Log.d(LogTag, text);

    }

    /**反注册结果**/
    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        Log.d(LogTag, "----------------------------- > onUnregisterResult");
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        Log.d(LogTag, text);
    }

    /**设置标签结果**/
    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        Log.d(LogTag, "----------------------------- > onSetTagResult");
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
    }

    /**删除标签结果**/
    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        Log.d(LogTag, "----------------------------- > onDeleteTagResult");
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        Log.d(LogTag, text);
    }

    /**收到消息**/
    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        Log.d(LogTag, "----------------------------- > onTextMessage");
    }

    /**通知被打开触发的结果**/
    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        Log.d(LogTag, "----------------------------- > onNotifactionClickedResult");
    }

    /**通知被展示在通知栏触发的结果，可以在此保存APP收到的通知**/
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        if (context == null || xgPushShowedResult == null) {
            return;
        }





        Log.d(LogTag, "----------------------------- > onNotifactionShowedResult");
    }
}
