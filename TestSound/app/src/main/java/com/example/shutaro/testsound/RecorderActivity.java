package com.example.shutaro.testsound;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class RecorderActivity extends AppCompatActivity {

    private static final String TAG = "myLog";
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.button5)
    Button button5;
    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.toggleButton)
    ToggleButton toggleButton;
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // MediaPlayerの解放処理
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        // MediaRecorderの解放処理
        if (mMediaRecorder != null) {
            if (mMediaRecorder != null) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }
    }

    @OnCheckedChanged(R.id.toggleButton)
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        Log.v("myLog", "" + isChecked);
        if (isChecked) {
            // 録音の開始
            startRecord();
        } else {
            // 録音の停止
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
            }
        }
    }
    @OnClick({R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id
            .button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                test2();
                break;
            case R.id.button3:
                test3();
                break;
            case R.id.button4:
                test4();
                break;
            case R.id.button5:
                test5();
                break;
            case R.id.button6:
                test6();
                break;
        }
    }

    private void test2() {
        // 録音した音声を再生
        startPlay();
    }

    private void test3() {

    }

    private void test4() {

    }

    private void test5() {

    }

    private void test6() {
        textView.setText("");
    }

    @OnClick(R.id.toggleButton)
    public void onClick() {
    }

    /**
     * MediaRecorderを初期化し録音を開始
     */
    private void startRecord() {
        // 再生中であれば再生を停止
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }

        // MediaRecorderの初期化
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        // 入力ソースをマイクに設定
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 保存フォーマットを3gpに設定
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // Audioエンコードをデフォルトに設定
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        // 外部ストレージ（microSDなど）に「hoge.3gp」という名前で保存する
        String fileName = "hoge.3gp";
        //String fileDir = Environment.getExternalStorageDirectory() + "/test_record/";
        String fileDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/test_record/";

        mFilePath = fileDir + fileName;

        // フォルダ作成
        File file = new File(fileDir);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.e("Debug", "Make Dir Error");
            }
        }
        registAndroidDB(mFilePath);

        mMediaRecorder.setOutputFile(mFilePath);
        textView.append("output " + mFilePath + "\n");

        // 録音準備が完了したら録音開始
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString(), e);
        }
    }

    /**
     * MediaPlayerを初期化し音声を再生
     */
    private void startPlay() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            mMediaPlayer.reset();
        }
        try {
            textView.append("play " + mFilePath);
            mMediaPlayer.setDataSource(mFilePath);
            // 音声の再生準備が完了した際に呼び出されるリスナー
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 音声を再生
                    mp.start();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(RecorderActivity.this,
                            R.string.text_complete_recordplay,
                            Toast.LENGTH_SHORT).show();
                }
            });
            mMediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString(), e);
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString(), e);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString(), e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString(), e);
        }
    }

    /**
     * アンドロイドのデータベースへパスを登録
     * @param path 登録するパス
     */
    private void registAndroidDB(String path) {
        // アンドロイドのデータベースへ登録
        // (登録しないとギャラリーなどにすぐに反映されないため)
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        values.put(MediaStore.Audio.Media.MIME_TYPE, "video/3gpp");
        values.put("_data", path);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
