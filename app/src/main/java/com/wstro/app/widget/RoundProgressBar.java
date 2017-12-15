package com.wstro.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wstro.app.common.utils.LogUtil;

/**
 * ClassName: RoundProgressBar
 * Function:
 * Date:     2017/12/15 0015 10:11
 *
 * @author pengl
 * @see
 */
public class RoundProgressBar extends View{
    private final static float DEFAULT_TEXT_SIZE = 35;
    private final static int DEFAULT_WIDTH = 50;

    private Paint textPaint;

    private Paint circlePaint;

    private int radius,strokeRadius;
    private int centerX,centerY;

    private int progressBgColor = Color.parseColor("#A0000000");

    private int progressColor;

    private float textSize;

    private int maxProgress;

    private int progress;

    private String drawTextLabel;

    private String suffix = "%";

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        textSize = DEFAULT_TEXT_SIZE;
        progressColor = Color.WHITE;
        progress = 0;
        maxProgress = 100;
        strokeRadius = 14;

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(textSize);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(progressBgColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measure(widthMeasureSpec,true);
        int height = measure(heightMeasureSpec,true);

        LogUtil.d("measureWidth:"+width+",measureHeight:"+height);
        setMeasuredDimension(width,height);
    }


    private int measure(int measureSpec,boolean isWidth){
        int result;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();

        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;

            if(mode == MeasureSpec.AT_MOST){
                if(isWidth) {
                    result = Math.max(result, size);
                }else{
                    result = Math.min(result, size);
                }
            }
        }


        return result;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return DEFAULT_WIDTH;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawColor(Color.TRANSPARENT);

        radius = Math.min(getWidth() - strokeRadius,getHeight() - strokeRadius) / 2;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        //绘制圆背景
        circlePaint.setColor(progressBgColor);
        canvas.drawCircle(centerX,centerY,radius,circlePaint);

        //绘制进度条
        circlePaint.setColor(progressColor);
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectF, 270, progress * 360 / maxProgress, false, circlePaint);

        //绘制进度数字
        drawTextLabel = String.format("%d",progress * 100 / maxProgress);
        drawTextLabel += suffix;
        float textWidth = textPaint.measureText(drawTextLabel);

        float x = (getWidth() - textWidth) / 2;
        float y = (getHeight() - (textPaint.descent() + textPaint.ascent())) / 2;
        canvas.drawText(drawTextLabel,x,y,textPaint);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;

        invalidate();
    }

    public void setProgress(int progress) {
        if(progress == this.progress)
            return;

        this.progress = progress;

        invalidate();
    }
}
