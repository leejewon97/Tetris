package com.android.ljw.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Block {
    float unit;
    int index;
    int x, y;
    int rotation;
    int rotation_limit;
    int[][][] block;
    Paint paint;
    public static int colors[] = {
            Color.RED,
            Color.parseColor("#FFA500"),
            Color.YELLOW,
            Color.GREEN,
            Color.parseColor("#0000CD"),
            Color.parseColor("#000080"),
            Color.MAGENTA
    };

    public void up() {
        y--;
    }

    public void down() {
        y++;
    }

    public void right() {
        x++;
    }

    public void left() {
        x--;
    }

    public void rotate() {
        rotation++;
        if (rotation == rotation_limit)
            rotation = 0;
    }

    public void backRotate() {
        rotation--;
        if (rotation < 0)
            rotation = rotation_limit - 1;
    }

    public Block(int block[][][], int index) {
        this.block = block;
        this.index = index;
        this.rotation_limit = block.length;
        paint = new Paint();
        paint.setColor(colors[index]);
    }

    public void setUnit(float unit) {
        this.unit = unit;
    }

    public int[][] currentBlock() {
        return block[rotation];
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < currentBlock().length; i++) {
            for (int j = 0; j < currentBlock()[i].length; j++) {
                if (currentBlock()[i][j] != 0) {
                    canvas.drawRect(
                            (j + x) * unit,
                            (i + y) * unit,
                            (j + x) * unit + unit,
                            (i + y) * unit + unit,
                            paint
                    );
                }
            }
        }
    }
}
