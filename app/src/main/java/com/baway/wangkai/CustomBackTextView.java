package com.baway.wangkai;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import static android.R.attr.drawablePadding;

/**
 * Created by 王锴 on 2017/8/7.
 * @描述 自定义TextView继承TextView
 * @Date 2017年8月7日08:53:54
 */

public class CustomBackTextView extends TextView {

    private String text;
    private int textSize;
    private int textColor;
    private Paint paint;
    private Rect rect;

    public CustomBackTextView(Context context) {
        this(context,null);
    }

    public CustomBackTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomBackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CustomBackTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //读取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.custom_textview,defStyleAttr,defStyleRes);
        //读取Text
        text = typedArray.getString(R.styleable.custom_textview_customText);
        //读取字符大小
        textSize = typedArray.getDimensionPixelSize(R.styleable.custom_textview_customTextSize,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,17,getResources().getDisplayMetrics()));
        //读取颜色
        textColor = typedArray.getColor(R.styleable.custom_textview_customColor, Color.BLACK);

        typedArray.recycle();

        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);

        rect = new Rect();
        paint.getTextBounds(text,0,text.length(),rect);

    }
    int viewWidth;
    int viewHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode =   MeasureSpec.getMode(widthMeasureSpec);
        int heightMode =  MeasureSpec.getMode(heightMeasureSpec) ;
        int width =  MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            viewWidth = width ;
        } else {
            //计算出来内容的宽度 :  文件的宽度 ＋ 左右内间距
            viewWidth = rect.width() + getPaddingLeft() + getPaddingRight() ;
        }

        if(heightMode == MeasureSpec.EXACTLY){
            viewHeight = height ;
        } else {
            viewHeight = rect.height() + getPaddingTop() + getPaddingBottom();
        }
        //设置当前view的尺寸
        setMeasuredDimension(viewWidth,viewHeight);

        if(widthMode == MeasureSpec.EXACTLY){
            System.out.println("widthMode = " + widthMode);
        }
        if(heightMode == MeasureSpec.AT_MOST){
            System.out.println("heightMode = " + heightMode);
        }

        System.out.println("widthMeasureSpec = " + width);
        System.out.println("heightMeasureSpec = " + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setGravity(Gravity.CENTER);
        canvas.drawText(text, 0, getHeight() / 2 + rect.height() / 2, paint);

    }
}
