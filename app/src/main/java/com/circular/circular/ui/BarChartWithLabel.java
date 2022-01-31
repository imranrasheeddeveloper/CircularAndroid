package com.circular.circular.ui;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BarChartWithLabel extends RelativeLayout {
    private int m_nWidth;
    private int m_nMaxHeight;
    private int m_nValue;
    private int m_nMaxValue;
    private String mStrLabel;
    private int m_nColor;
    private int m_nHorizontalPadding;
    private boolean m_bTopDown;
    private int m_nFontSize = 12;
    private Typeface mTf;

    public BarChartWithLabel(Context context) {
        super(context);
    }

    public BarChartWithLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartWithLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BarChartWithLabel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setWidth(int nWidth){
        m_nWidth = nWidth;
    }

    public void setValue(int nValue){
        m_nValue = nValue;
    }

    public void setMaxValue(int nMaxValue){
        m_nMaxValue = nMaxValue;
    }

    public void setMaxHeight(int nMaxHeight){
        m_nMaxHeight = nMaxHeight;
    }

    public void setLabel(String strLabel){
        mStrLabel = strLabel;
    }

    public void setColor(int color){
        m_nColor = color;
    }

    public void setFontSize(int nFontSize){
        m_nFontSize = nFontSize;
    }

    public void setHorizontalPadding(int nPadding){
        m_nHorizontalPadding = nPadding;
    }

    public void setTypeface(Typeface tf){
        mTf = tf;
    }

    public void init(){
        removeAllViews();
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(m_nWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        setPadding(m_nHorizontalPadding, 0, m_nHorizontalPadding, 0);

        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int width  = getMeasuredWidth();
                int height = getMeasuredHeight();
                RelativeLayout rlChild = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int)(m_nValue * ((float)height / (float)m_nMaxValue)));
                lp1.addRule(ALIGN_PARENT_BOTTOM, 1);
                rlChild.setLayoutParams(lp1);
                rlChild.setBackgroundColor(m_nColor);
                addView(rlChild);

                VerticalTextView tv = new VerticalTextView(getContext());
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.addRule(ALIGN_PARENT_BOTTOM, 1);
                lp2.addRule(CENTER_HORIZONTAL, 1);
                tv.setTopDown(m_bTopDown);
                tv.setTextColor(0xFFFFFFFF);
                tv.setGravity(Gravity.BOTTOM);
                tv.setText(mStrLabel);
                tv.setTag("ChartLabel");
                int nFontSize = (int)((float)(lp1.height) / (float)(mStrLabel.length()));
                if (nFontSize > m_nFontSize){
                    nFontSize = m_nFontSize;
                }
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, nFontSize);
//                tv.setTextSize(nFontSize);
                tv.setLayoutParams(lp2);
                tv.setTypeface(mTf);
                tv.setPadding(10, 0, 0, 0);
                tv.setVisibility(View.GONE);
                addView(tv);
            }
        });
    }

    public void setTopDown(boolean bFlag){
        m_bTopDown = bFlag;
    }
//
//    @Override
//    protected void onDraw(Canvas canvas){
//        canvas.save();
//        Paint paint = new Paint();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            paint.setBlendMode(BlendMode.DIFFERENCE);
//        }else{
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
//        }
//
//        canvas.drawPaint(paint);
//
//        canvas.restore();
//    }
}
