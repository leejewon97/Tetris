package com.android.ljw.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Stage.Control {
    FrameLayout stageLayout, previewLayout;
    Stage stage;
    Preview preview;
    float stageWidth, unit;
    float gridCount;
    boolean runFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stage = new Stage(this, this);
        preview = new Preview(this);
        stageLayout = findViewById(R.id.stageLayout);
        previewLayout = findViewById(R.id.previewLayout);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //게임판 사이즈
        gridCount = stage.map[0].length + preview.preview_map[0].length;
        stageWidth = metrics.widthPixels;
        //한칸 사이즈
        unit = stageWidth / gridCount;
        stage.setUnit(unit);
        preview.setUnit(unit);
        // 새로운 블럭을 세팅
        setNewBlock();
        // 프리뷰에 세팅된 블럭을 스테이지로 이동
        moveBlockToStage();
        stageLayout.addView(stage);
        previewLayout.addView(preview);

        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_down).setOnClickListener(this);
        findViewById(R.id.btn_downToEnd).setOnClickListener(this);
        findViewById(R.id.btn_rotate).setOnClickListener(this);

        runThread();
    }

    private void runThread() {
        new Thread() {
            @Override
            public void run() {
                while (runFlag) {
                    try {
                        Thread.sleep(1000);
                        stage.down();
                        stage.invalidate();
                        preview.invalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void setNewBlock() {
        Block newBlock = BlockFactory.newBlock();
        preview.setBlock(newBlock);
    }

    @Override
    public void moveBlockToStage() {
        Block block = preview.getBlock();
        stage.setBlock(block);
        setNewBlock();
        stage.invalidate();
        preview.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                stage.left();
                break;
            case R.id.btn_right:
                stage.right();
                break;
            case R.id.btn_down:
                stage.down();
                break;
            case R.id.btn_downToEnd:
                stage.downToEnd();
                break;
            case R.id.btn_rotate:
                stage.rotate();
                break;
        }
        stage.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runFlag = false;
    }
}
