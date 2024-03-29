package com.lexivip.lexi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.R;

import java.util.List;


public class RangeSeekBarView extends View {
    private static final float SEEK_BG_SCALE = 1F / 4;
    private static final float SEEK_TEXT_SCALE = 2F / 3F;
    private static final int DEF_HEIGHT = 80; //控件高度

    private int DEF_PADDING; //左边间距

    private int BG_HEIGHT; //进度条高度

    private static final int SEEK_STROKE_SIZE = 1;

    private int viewWidth;
    private int viewHeight;

    private int seekBgColor; //进度条背景颜色

    private int seekPbColor; //进度条前景颜色

    private int seekBallSolidColor;
    private int seekBallStrokeColor;
    private int seekTextColor;
    private int seekTextSelectedColor;
    private int seekTextSize;

    private Paint seekBgPaint; //画进度条背景
    private Paint seekBallPaint;
    private Paint seekBallEndPaint;
    private Paint seekBallStrokePaint;
    private Paint seekPbPaint;
    private Paint seekTextPaint;
    private RectF seekBGRectF;
    private RectF seekPbRectF;

    private int seekBallRadio;
    private int seekBallY;
    private int leftSeekBallX;
    private int rightSeekBallX;

    private List<String> data;
    private int seekTextY;
    private int currentMovingType;
    private OnDragFinishedListener dragFinishedListener;
    private int leftPosition, rightPosition;
    private int downX;

    public RangeSeekBarView(Context context) {
        this(context, null);
    }

    public RangeSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RangeSeekBarView, defStyleAttr, R.style.rangeBarStyle);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.RangeSeekBarView_seek_bg_color:
                    seekBgColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_pb_color:
                    seekPbColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_ball_solid_color:
                    seekBallSolidColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_ball_stroke_color:
                    seekBallStrokeColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_bar_text_selected_color:
                    seekTextSelectedColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_text_color:
                    seekTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.RangeSeekBarView_seek_text_size:
                    seekTextSize = typedArray.getDimensionPixelSize(attr, 0);
                    break;

                case R.styleable.RangeSeekBarView_seek_bar_height:
                    BG_HEIGHT = typedArray.getDimensionPixelSize(attr, DimenUtil.getDimensionPixelSize(R.dimen.dp4));
                    break;
                case R.styleable.RangeSeekBarView_seek_view_padding:
                    DEF_PADDING = typedArray.getDimensionPixelSize(attr, DimenUtil.getDimensionPixelSize(R.dimen.dp15));
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        currentMovingType = BallType.LEFT;
        seekTextPaint = creatPaint(seekTextColor, seekTextSize, Paint.Style.FILL, 0);
        seekBgPaint = creatPaint(seekBgColor, 0, Paint.Style.FILL, 0);
        seekBallPaint = creatPaint(seekBallSolidColor, 0, Paint.Style.FILL, 0);
        seekPbPaint = creatPaint(seekPbColor, 0, Paint.Style.FILL, 0);
        seekBallEndPaint = creatPaint(seekPbColor, 0, Paint.Style.FILL, 0);
        seekBallStrokePaint = creatPaint(seekBallStrokeColor, 0, Paint.Style.FILL, 0);
        seekBallStrokePaint.setShadowLayer(5, 2, 2, seekBallStrokeColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

        seekBallRadio = DimenUtil.dp2px(10.0);
        seekBallY = (int) (viewHeight * SEEK_BG_SCALE);
        seekTextY = (int) (viewHeight * SEEK_TEXT_SCALE);
        leftSeekBallX = seekBallRadio + DEF_PADDING;
        rightSeekBallX = viewWidth - seekBallRadio - DEF_PADDING;

        seekBGRectF = new RectF(seekBallRadio + DEF_PADDING, viewHeight * SEEK_BG_SCALE, viewWidth - seekBallRadio - DEF_PADDING, viewHeight * SEEK_BG_SCALE + BG_HEIGHT);

        seekPbRectF = new RectF(leftSeekBallX, viewHeight * SEEK_BG_SCALE, rightSeekBallX, viewHeight * SEEK_BG_SCALE + BG_HEIGHT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight;
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getContext().getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTexts(canvas);
        drawSeekBG(canvas);
        drawSeekPB(canvas);
        drawLeftCircle(canvas);
        drawRightCircle(canvas);
    }

    private void drawTexts(Canvas canvas) {
        if (null == data) return;
        int size = data.size();
        int unitWidth = getUnitWidth(size - 1);
        for (int i = 0; i < size; i++) {
            String tempDesc = data.get(i);
            float measureTextWidth = seekTextPaint.measureText(tempDesc);
            if (i == leftPosition || i == rightPosition) {
                seekTextPaint.setColor(seekTextSelectedColor);
            } else {
                seekTextPaint.setColor(seekTextColor);
            }
            canvas.drawText(tempDesc, DEF_PADDING + seekBallRadio + unitWidth * i - measureTextWidth * 0.5f, seekTextY, seekTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                currentMovingType = getMovingLeftOrRight(downX);
                if (BallType.LEFT == currentMovingType) {
                    leftSeekBallX = downX;
                } else if (BallType.RIGHT == currentMovingType) {
                    rightSeekBallX = downX;
                }
                seekPbRectF = new RectF(leftSeekBallX, viewHeight * SEEK_BG_SCALE, rightSeekBallX, viewHeight * SEEK_BG_SCALE + BG_HEIGHT);

                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                if (leftSeekBallX == rightSeekBallX) {
                    if (moveX - downX > 0) {
                        currentMovingType = BallType.RIGHT;
                        rightSeekBallX = moveX;
                    } else {
                        currentMovingType = BallType.LEFT;
                        leftSeekBallX = moveX;
                    }
                } else {
                    if (BallType.LEFT == currentMovingType) {
                        leftSeekBallX = leftSeekBallX - rightSeekBallX >= 0 ? rightSeekBallX : moveX;
                    } else if (BallType.RIGHT == currentMovingType) {
                        rightSeekBallX = rightSeekBallX - leftSeekBallX <= 0 ? leftSeekBallX : moveX;
                    }
                }
                seekPbRectF = new RectF(leftSeekBallX, viewHeight * SEEK_BG_SCALE, rightSeekBallX, viewHeight * SEEK_BG_SCALE + BG_HEIGHT);
                break;
            case MotionEvent.ACTION_UP:
                if (BallType.LEFT == currentMovingType) {
                    leftPosition = getDataPosition((int) event.getX());
                    leftSeekBallX = leftSeekBallX - rightSeekBallX >= 0 ? rightSeekBallX : getCurrentSeekX((int) event.getX()) + DEF_PADDING + seekBallRadio;
                } else if (BallType.RIGHT == currentMovingType) {
                    rightPosition = getDataPosition((int) event.getX());
                    rightSeekBallX = rightSeekBallX - leftSeekBallX <= 0 ? leftSeekBallX : getCurrentSeekX((int) event.getX()) + DEF_PADDING + seekBallRadio;
                }
                if (null != dragFinishedListener) {
                    if (rightPosition == data.size()) rightPosition = data.size() - 1;
                    dragFinishedListener.dragFinished(leftPosition, rightPosition);
                }
                break;
        }

        if (BallType.LEFT == currentMovingType) {
            if (leftSeekBallX < seekBallRadio + DEF_PADDING) {
                leftSeekBallX = seekBallRadio + DEF_PADDING;
            }
            if (leftSeekBallX > viewWidth - seekBallRadio - DEF_PADDING) {
                leftSeekBallX = viewWidth - seekBallRadio - DEF_PADDING;
            }
        } else if (BallType.RIGHT == currentMovingType) {
            if (rightSeekBallX < seekBallRadio + DEF_PADDING) {
                rightSeekBallX = seekBallRadio + DEF_PADDING;
            }
            if (rightSeekBallX > viewWidth - seekBallRadio - DEF_PADDING) {
                rightSeekBallX = viewWidth - seekBallRadio - DEF_PADDING;
            }
        }
        seekPbRectF = new RectF(leftSeekBallX, viewHeight * SEEK_BG_SCALE, rightSeekBallX, viewHeight * SEEK_BG_SCALE + BG_HEIGHT);
        invalidate();
        return true;
    }

    private void drawSeekPB(Canvas canvas) { //画选中范围颜色
        canvas.drawRoundRect(seekPbRectF, 5, 5, seekPbPaint);
    }

    private void drawSeekBG(Canvas canvas) { //画Bar未选中背景颜色
        canvas.drawRoundRect(seekBGRectF, 5, 5, seekBgPaint);
    }

    private void drawRightCircle(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        Bitmap girlBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.icon_range_drag_button)).getBitmap();
        int girlBitWidth = girlBitmap.getWidth();
        int girlBitHeight = girlBitmap.getHeight();
        canvas.drawBitmap(girlBitmap, rightSeekBallX - girlBitWidth * 0.5f, seekBallY - girlBitHeight * 0.5f, seekBallEndPaint);
    }

    private void drawLeftCircle(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        Bitmap girlBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.icon_range_drag_button)).getBitmap();
        int girlBitWidth = girlBitmap.getWidth();
        int girlBitHeight = girlBitmap.getHeight();
        canvas.drawBitmap(girlBitmap, leftSeekBallX - girlBitWidth * 0.5f, seekBallY - girlBitHeight * 0.5f, seekBallPaint);
    }


    private Paint creatPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private int getUnitWidth(int count) {
        return (viewWidth - 2 * DEF_PADDING - 2 * seekBallRadio) / count;
    }

    private int getCurrentSeekX(int upX) {
        if (null == data) {
            return 0;
        }
        int unitWidth = getUnitWidth(data.size() - 1);
        return unitWidth * (upX / unitWidth);
    }

    private int getDataPosition(int upX) {
        if (null == data) {
            return 0;
        }
        int unitWidth = getUnitWidth(data.size() - 1);
        return upX / unitWidth;
    }

    public void setData(List<String> data, OnDragFinishedListener dragFinishedListener) {
        this.dragFinishedListener = dragFinishedListener;
        this.data = data;
        leftPosition = 0;
        if (null != data && data.size() != 0) {
            rightPosition = data.size() - 1;
        }
    }

    private int getMovingLeftOrRight(int actionX) {
        return Math.abs(leftSeekBallX - actionX) - Math.abs(rightSeekBallX - actionX) > 0 ? BallType.RIGHT : BallType.LEFT;
    }

    private static class BallType {
        private static final int LEFT = 99;
        private static final int RIGHT = 98;
    }

    public interface OnDragFinishedListener {
        void dragFinished(int leftPostion, int rightPostion);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        invalidate();
    }
}
