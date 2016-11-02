package com.sunsunsoft.shutaro.testview;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * 出力を一括スイッチングできるLog
 * タグ毎のON/OFFを設定できる
 */
public class MyLog {
    private static HashMap<String,Boolean> enableMap = new HashMap<>();

    public static void setEnable(String tag, boolean enable) {
        enableMap.put(tag, enable);
    }

    public static void init() {
        setEnable("viewtouch", false);
        setEnable("MyView9", false);
    }

    public static void print(String tag, String msg) {
        // 有効無効判定
        Boolean enable = enableMap.get(tag);
        if (enable != null && !enable) {
            // 出力しない
        } else {
            Log.v(tag, msg);
        }
    }
}
