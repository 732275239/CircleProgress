package com.CircleProgress.Circle_progress_bar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by 赵政 on 2018/3/19.
 */

public class CircleProgressBar extends View {

    private int maxValue = 100;//最大进度
    private int backgroundcolor;//圆弧的初始颜色
    private int circlewidth;//圆环宽度
    private float currentValue = 0;//当前进度
    private float alphaAngle;//圆心角度
    private Paint circlePaint;
    private Paint textPaint;
    private int[] colorArray = new int[]{Color.parseColor("#27B197"), Color.parseColor("#00A6D5")};
    private int shadowColor = Color.parseColor("#E2E0DE");

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        //底色亮灰色
        backgroundcolor = typedArray.getColor(R.styleable.CircleProgressBar_backgroundColor, Color.parseColor("#C6C7C9"));
        //默认圆弧宽度6dp
        circlewidth = typedArray.getDimensionPixelOffset(R.styleable.CircleProgressBar_circleWidth, 6);
        //进度
        currentValue = typedArray.getInteger(R.styleable.CircleProgressBar_circleAngle, 0);

        typedArray.recycle();

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true); // 抗锯齿
        circlePaint.setDither(true); // 防抖动
        circlePaint.setStrokeWidth(circlewidth);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 分别获取期望的宽度和高度，并取其中较小的尺寸作为该控件的宽和高
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = this.getWidth() / 2;
        int radius = center - circlewidth / 2;//得到半径
        drawCircle(canvas, center, radius);//画圆弧
        drawText(canvas, center, radius);
    }

    //画圆弧
    private void drawCircle(Canvas canvas, int center, int radius) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        circlePaint.setShader(null);//清除上一次的状态
        circlePaint.setColor(backgroundcolor);
        circlePaint.setStyle(Paint.Style.STROKE);//空心圆
        canvas.drawCircle(center, center, radius - paddingLeft, circlePaint);
        RectF oval = new RectF(); // 圆的外接正方形
        oval.left = center - radius + paddingLeft;
        oval.top = center - radius + paddingTop;
        oval.right = center + radius - paddingRight;
        oval.bottom = center + radius - paddingBottom;
        //绘制颜色的渐变圆环
        //Shader 是一个着色器
        LinearGradient linearGradient = new LinearGradient(circlewidth, circlewidth,
                getMeasuredWidth() - circlewidth, getMeasuredHeight() - circlewidth, colorArray, null, Shader.TileMode.REPEAT);
        circlePaint.setShader(linearGradient);
        circlePaint.setShadowLayer(10, 0, 0, shadowColor);//阴影色
        circlePaint.setColor(backgroundcolor); // 设置圆弧背景的颜色
        circlePaint.setStrokeCap(Paint.Cap.ROUND); // 圆弧头尾改成圆角的
        alphaAngle = currentValue * 360.0f / maxValue * 1.0f;//计算圆弧角度（进度）
        canvas.drawArc(oval, -90, alphaAngle, false, circlePaint);
    }

    private void drawText(Canvas canvas, int center, int radius) {
        float result = currentValue * 100.0f / maxValue * 1.0f;
        String percent = String.format("%.1f", result) + "%";
        textPaint.setTextAlign(Paint.Align.CENTER); // 设置文字居中，文字的x坐标要注意
        textPaint.setColor(Color.BLACK); // 设置文字颜色
        textPaint.setTextSize(30); // 设置要绘制的文字大小
        textPaint.setStrokeWidth(0); // 注意此处一定要重新设置宽度为0,否则绘制的文字会重叠
        Rect bounds = new Rect(); // 文字边框
        textPaint.getTextBounds(percent, 0, percent.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt(); // 获取绘制Text时的四条线
        int baseline = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        canvas.drawText(percent, center, baseline, textPaint); // 绘制表示进度的文字
    }

    /**
     * 设置圆环的宽度
     *
     * @param width
     */
    public void setCircleWidth(int width) {
        this.circlewidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources()
                .getDisplayMetrics());
        circlePaint.setStrokeWidth(circlewidth);
        invalidate();
    }


    /**
     * 设置进度条渐变色颜色数组（镜像）
     * @param colors 颜色数组，类型为int[]
     * private int[] colorArray = new int[]{Color.parseColor("#27B197"), Color.parseColor("#00A6D5")};
     */
    public void setColorArray(int[] colors) {
        if (colors.length==1){
            this.colorArray=new int[]{colors[0],colors[0]};
        }else {
            this.colorArray = colors;
        }
        invalidate();
    }

    /**
     *  设置圆环周围的阴影颜色
     * @param color
     */

    public void setShadowColor(int color){
        this.shadowColor = color;
        invalidate();
    }
    /**
     * 按进度显示百分比
     *
     * @param progress 进度，值通常为0到100
     */
    public void setProgress(int progress) {
        int percent = progress * maxValue / 100;
        if (percent < 0) {
            percent = 0;
        }
        if (percent > 100) {
            percent = 100;
        }
        this.currentValue = percent;
        invalidate();
    }

    /**
     * 按进度显示百分比，可选择是否启用数字动画
     *
     * @param progress     进度，值通常为0到100
     * @param useAnimation 是否启用动画，true为启用
     */
    public void setProgress(int progress, boolean useAnimation) {
        int percent = progress * maxValue / 100;
        if (percent < 0) {
            percent = 0;
        }
        if (percent > 100) {
            percent = 100;
        }
        if (useAnimation) {// 使用动画
            ValueAnimator animator = ValueAnimator.ofFloat(currentValue * 1.0f, percent * 1.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            //设置一个减速的插值器
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(1000);
            animator.start();
        } else {
            setProgress(progress);
        }
    }

}
