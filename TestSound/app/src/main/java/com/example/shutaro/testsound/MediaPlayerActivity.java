package com.example.shutaro.testsound;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.R.attr.duration;

public class MediaPlayerActivity extends AppCompatActivity implements OnCompletionListener, OnPreparedListener {
    @InjectView(R.id.seekBar)
    SeekBar seekBar;

    private static final int ACCELERATION_VALUE = 5000;
    private static final int THREAD_RUNNING_INTERVAL = 500;

    ScheduledExecutorService scheduledEx;
    ScheduledFuture scheduledFuture;

    private MediaPlayer mMediaPlayer;
    private static final int[] soundIDs = new int[]{
            R.raw.bgm_healing02,
            R.raw.bgm_healing03
    };

    @InjectView(R.id.button)
    Button button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ButterKnife.inject(this);

        scheduledEx =  Executors.newSingleThreadScheduledExecutor();

        // メディアプレイヤーの初期化
        mMediaPlayer = new MediaPlayer();
        // メディアの再生準備完了の通知を受け取るリスナーの設定
        mMediaPlayer.setOnPreparedListener(this);
        // メディアの再生完了の通知を受け取るリスナーの設定
        mMediaPlayer.setOnCompletionListener(this);

        // メディアファイルを指すパスを作成
        loadSound(R.raw.bgm_healing02);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // メディアプレイヤーが再生中なら停止
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        // メディアプレイヤーを解放
        mMediaPlayer.release();
    }

    @Override
    protected void onStart() {
        super.onStart();

        scheduledFuture = scheduledEx.scheduleAtFixedRate(new SeekRunner("seek task"), 0, THREAD_RUNNING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        scheduledFuture.cancel(true);
    }

    /**
     * サウンドファイルの読み込みが完了して再生準備が整った
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        // メディアプレイヤーが再生可能になったので再生ボタンを有効にする
        button.setEnabled(true);
        setButtonText(mp);
        textView.append("onPrepared\n");

        seekBar.setMax(mMediaPlayer.getDuration());
        textView.append("duration " + mMediaPlayer.getDuration() + "\n");
    }

    /**
     * サウンドの再生が完了した
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        // メディアプレイヤーの再生が終わったのでボタンの状態を変更
        setButtonText(mp);
        textView.append("onCompletion\n");
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                test1();
                break;
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


    private void setButtonText(MediaPlayer mp) {
        if (mp.isPlaying()) {
            // 再生中なら停止を表示
            button.setText(getString(R.string.text_pause));
        } else {
            // 停止中なら再生を表示
            button.setText(getString(R.string.text_play));
        }
    }

    /**
     * 再生＆停止
     */
    private void test1() {
        if (mMediaPlayer.isPlaying()) {
            // メディアプレイヤーが再生中なら停止
            mMediaPlayer.pause();
            setButtonText(mMediaPlayer);
            textView.append("pause \n");
        } else {
            // メディアプレイヤーが再生中でないなら再生
            mMediaPlayer.start();
            setButtonText(mMediaPlayer);
            textView.append("play \n");
        }
    }

    private void test2() {
        loadSound(R.raw.bgm_healing02);
    }

    private void test3() {
        loadSound(R.raw.bgm_healing03);
    }

    /**
     * 停止（完全に停止していない。一時停止して再生位置を先頭に戻す
     */
    private void test4() {
        mMediaPlayer.pause();
        mMediaPlayer.seekTo(0);

        seekBar.setProgress(0);
        setButtonText(mMediaPlayer);
    }

    /**
     * 完全に停止。読み込んだサウンドデータも解放する
     */
    private void test5() {
        mMediaPlayer.stop();
    }

    private void test6() {
        textView.setText("");
    }

    /**
     * サウンドファイルを読み込む
     *
     * @param soundFileName
     */
    private void loadSound(int soundFileName) {
        mMediaPlayer.stop();
        // リセット
        mMediaPlayer.reset();


        String fileName = "android.resource://" + getPackageName() + "/"
                + soundFileName;
        try {
            // メディアファイルをMediaPlayerに設定
            mMediaPlayer.setDataSource(this, Uri.parse(fileName));
            // メディアファイルを非同期で読み込む
            mMediaPlayer.prepareAsync();
            setButtonText(mMediaPlayer);
            textView.append("load " + fileName + "\n");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class SeekRunner implements Runnable {
        private String name;

        public SeekRunner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            int currentPosition = mMediaPlayer.getCurrentPosition();
            seekBar.setProgress(currentPosition);
            Log.v("myLog", "run " + currentPosition);
        }
    }
}

