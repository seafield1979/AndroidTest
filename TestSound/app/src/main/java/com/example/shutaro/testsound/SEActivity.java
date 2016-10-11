package com.example.shutaro.testsound;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.media.SoundPool.OnLoadCompleteListener;

public class SEActivity extends AppCompatActivity implements OnLoadCompleteListener {
    private SoundPool mSoundPool;
    private int mSoundID;
    private static final int[] sounds = new int[]{
            R.raw.wav01,
            R.raw.wav02,
            R.raw.wav03,
            R.raw.wav04
    };
    private static final int[] buttonIDs = new int[]{
            R.id.button,
            R.id.button2,
            R.id.button3,
            R.id.button4
    };
    private Button buttons[] = new Button[buttonIDs.length];
    private int[] mSoundIDs = new int[sounds.length];

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
        setContentView(R.layout.activity_se);
        ButterKnife.inject(this);

        // ボタンの初期化(IDとメンバ変数の関連付け)
        for (int i=0; i<buttonIDs.length; i++) {
            buttons[i] = (Button)findViewById(buttonIDs[i]);
        }

        final int SOUND_POOL_MAX = 6;

        // SoundPoolの初期化
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }        // 音声データの読み込み開始

        // 音声データの読み込み完了を検知するリスナーを設定
        mSoundPool.setOnLoadCompleteListener(this);

        for (int i=0; i<mSoundIDs.length; i++) {
            buttons[i].setEnabled(false);
            mSoundIDs[i] = mSoundPool.load(this, sounds[i], 1);
            textView.append("mSoundsIDs " + mSoundIDs[i]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // SoundPoolの解放
        mSoundPool.release();
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

    private void test1(){
        playSound(0);
    }
    private void test2(){
        playSound(1);
    }
    private void test3(){
        playSound(2);
    }
    private void test4(){
        playSound(3);
    }
    private void test5(){

    }
    private void test6( ){
        textView.setText("");
    }

    private void playSound(int id) {
        // 音声の再生
        mSoundPool.play(mSoundIDs[id],   // ロードした時のID
                1.0F,   // 左側の音量
                1.0F,   // 右側の音量
                0,      // 優先度（デフォルトは0）
                0,      // ループ指定(-1：無限ループ / 0：一回再生)
                1.0F);  // 再生速度(0.5～2.0まで / 1.0で普通の速度)
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        int index = -1;
        for (int i=0; i<mSoundIDs.length; i++) {
            if (sampleId == mSoundIDs[i]) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        // 音声が読み込み完了になったため再生ボタンを有効にする
        buttons[index].setEnabled(true);
        textView.append("onLoadComplate " + sampleId + "\n");
    }
}
