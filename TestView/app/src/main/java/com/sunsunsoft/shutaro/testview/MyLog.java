package com.sunsunsoft.shutaro.testview;

import android.util.Log;
import java.util.HashMap;

/**
 * 出力を一括スイッチングできるLog
 * タグ毎のON/OFFを設定できる
 */
public class MyLog {
    // タグ毎のON/OFF情報をMap(Dictionary)で持つ
    private static HashMap<String,Boolean> enables = new HashMap<>();
    private static HashMap<String,Integer> counters = new HashMap<>();

    // タグのON/OFFを設定する
    public static void setEnable(String tag, boolean enable) {
        enables.put(tag, enable);
    }

    // 初期化、アプリ起動時に１回だけ呼ぶ
    public static void init() {
        setEnable("viewtouch", false);
        setEnable("MyView9", false);
        setEnable("MenuBar", false);
        setEnable(MyScrollBar.TAG, false);
        setEnable(IconWindow.TAG, true);
    }

    // ログ出力
    public static void print(String tag, String msg) {
        // 有効無効判定
        Boolean enable = enables.get(tag);
        if (enable != null && !enable) {
            // 出力しない
        } else {
            Log.v(tag, msg);
        }
    }

    /**
     * カウントする
     * start - count ... - end
     */
    public static void startCount(String tag) {
        counters.put(tag, 0);
    }
    public static void count(String tag) {
        Integer count = counters.get(tag);
        if (count == null) {
            count = 0;
        }
        count = count + 1;
        counters.put(tag, count);
    }
    public static void endCount(String tag) {
        Log.d(tag, "count:" + counters.get(tag));
    }
}
