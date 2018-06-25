package com.android.ljw.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.params.BlackLevelPattern;
import android.view.View;

public class Preview extends View {
    Paint boxPaint, gridPaint, tempPaint;
    Block block;
    float unit;
    int preview_map[][] = {
            {8, 8, 8, 8, 8, 8, 8, 8, 8},
            {8, 9, 9, 9, 9, 9, 9, 9, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 0, 0, 0, 0, 0, 0, 9},
            {8, 9, 9, 9, 9, 9, 9, 9, 9}
    };

    public Preview(Context context) {
        super(context);
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
        for (int y = 0; y < preview_map.length; y++) {
            for (int x = 0; x < preview_map[y].length; x++) {
                if (preview_map[y][x] == 0) {
                    tempPaint = gridPaint;
                } else if (preview_map[y][x] == 9) {
                    tempPaint = boxPaint;
                } else
                    tempPaint = new Paint();
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
        this.block.x = 3;
        this.block.y = 3;
        block.setUnit(unit);
    }

    public Block getBlock() {
        return block;
    }
}
