package com.android.ljw.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Stage extends View {
    Control control;
    Paint boxPaint, gridPaint, tempPaint;
    Block block;
    float unit;

    int map[][] = {
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9},
            {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}
    };

    public Stage(Context context, Control control) {
        super(context);
        this.control = control;
        gridPaint = new Paint();
        boxPaint = new Paint();

        gridPaint.setColor(Color.CYAN); // 사각형의 색
        gridPaint.setStyle(Paint.Style.STROKE); // 사각형의 스타일
        gridPaint.setStrokeWidth(1); // 선 두께
        boxPaint.setColor(Color.BLUE);
    }

    public void setUnit(float unit) {
        this.unit = unit;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        block.onDraw(canvas);
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) {
                    tempPaint = gridPaint;
                } else if (map[y][x] == 9) {
                    tempPaint = boxPaint;
                } else {
                    tempPaint = new Paint();
                    tempPaint.setColor(Block.colors[map[y][x] - 1]);
                }
                canvas.drawRect(
                        x * unit,
                        y * unit,
                        x * unit + unit,
                        y * unit + unit,
                        tempPaint);
            }
        }
    }

    public void setBlock(Block block) {
        this.block = block;
        this.block.x = 4;
        this.block.y = 0;
    }

    public void downToEnd() {
        while (!collisionCheck()) {
            block.down();
        }
        block.up();
        pushBlockToStage();
        control.moveBlockToStage();
    }
    
    public void down() {
        block.down();
        if (collisionCheck()) {
            block.up();
            pushBlockToStage();
            control.moveBlockToStage();
        }
    }

    public void left() {
        block.left();
        if (collisionCheck())
            block.right();
    }

    public void right() {
        block.right();
        if (collisionCheck())
            block.left();
    }

    public void rotate() {
        block.rotate();
        if (collisionCheck())
            block.backRotate();
    }

    private void pushBlockToStage() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (block.currentBlock()[y][x] > 0)
                    map[y + block.y][x + block.x] = block.currentBlock()[y][x];
            }
        }
    }

    public boolean collisionCheck() {
        // 스테이지에서 블럭이 다음에 이동할 곳의 값들을 꺼내서 담아둔다
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int target_x = x + block.x;
                int target_y = y + block.y;
                // 셀의 값이 둘다 0보다 큰경우 충돌
                if (target_x >= 0 && target_x < map[0].length && target_y >= 0 && target_y < map.length) {
                    if (map[target_y][target_x] > 0 && block.currentBlock()[y][x] > 0)
                        return true;
                }
            }
        }
        return false;
    }

    public interface Control {
        void moveBlockToStage();
    }
}
