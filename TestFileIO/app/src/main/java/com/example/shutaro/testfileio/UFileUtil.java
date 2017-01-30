package com.example.shutaro.testfileio;

/**
 * Created by shutaro on 2017/01/20.
 */

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * enum
 */
// ファイルの保存先の種類
enum DirType{
    AppStorage,     // アプリの永続化ストレージ
    AppCache,       // アプリのキャッシュ（一時的に使用する）領域
    AppExternal,    // アプリの外部
    //        Data,           // Androidのデータ保存領域
    ExternalStorage,        // 外部ストレージ
    ExternalDocument,       // 外部ストレージ(共有ドキュメント)
    ExternalDownload,       // 外部ストレージ(共有ダウンロード)
}

public class UFileUtil {

    /**
     * dirTypeで指定したいろいろなフォルダのパスを取得する
     * @return
     */
    public static File getPath(Context context, DirType dirType) {
        switch (dirType) {
            case AppStorage:
                return context.getFilesDir();
            case AppCache:
                return context.getCacheDir();
            case AppExternal:
            {
                File[] dirs = context.getExternalFilesDirs(null);
                StringBuffer buf = new StringBuffer();
                if (dirs != null && dirs.length > 0) {
                    return dirs[0];
                }
            }
            case ExternalStorage:
                return Environment.getExternalStorageDirectory();
            case ExternalDocument:
                return Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOCUMENTS);
            case ExternalDownload:
                return Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS);
        }
        return null;
    }
}
