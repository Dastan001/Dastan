package com.example.dastan.dastan_slidemenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Dastan on 2015/11/4.
 */
public class Dastan_SlidingMenu extends HorizontalScrollView {
    private int widthPixel;
    private ViewGroup menu;
    private ViewGroup content;
    private int menuWidth;
    private int contentWidth;
    private int downX;

    public Dastan_SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕的宽度
        //上下文拿到系统窗体管理器
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //拿到屏幕显示器
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        widthPixel = metrics.widthPixels;
    }
    //重写OnMEASure方法
//确定姿势图的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LinearLayout ll = (LinearLayout) this.getChildAt(0);//获取子视图
        menu = (ViewGroup) ll.getChildAt(0);
        content = (ViewGroup) ll.getChildAt(1);
        //设置menu的宽度
        menu.getLayoutParams().width = (int) (widthPixel*0.8);
        menuWidth = (int) (widthPixel*0.8);
        content.getLayoutParams().width = widthPixel;
        contentWidth = widthPixel;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
//    设置子视图的位置

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(menuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                 downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if(computeHorizontalScrollOffset () > menuWidth/2)
                {
                    //隐藏菜单
                    this.scrollTo(menuWidth, 0);
                }else
                {
                    //显示菜单
                    this.scrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
    //重写onScrollChanged方法

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        l的范围0到menu的宽度
        float scale = (float)l/menuWidth;
        float leftScale = 1-scale*0.3f;
        //menu缩放比例
        ViewHelper.setScaleX(menu,leftScale);
        ViewHelper.setScaleY(menu, leftScale);
        //透明度的变化
        float leftAlpha = (float) (1-scale*0.7);
        ViewHelper.setAlpha(menu, leftAlpha);
        //位移变化
        ViewHelper.setTranslationX(menu,l*0.7f);
        //content 主视图有缩放的功能
        float rightScala = (float) (0.8f + scale*0.2);
        ViewHelper.setScaleX(content,rightScala);
        ViewHelper.setScaleY(content,rightScala);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
