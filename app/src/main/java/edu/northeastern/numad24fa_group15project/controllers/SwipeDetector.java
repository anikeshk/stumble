package edu.northeastern.numad24fa_group15project.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SwipeDetector extends FrameLayout {
    private float startX;
    private OnSwipeListener listener;

    public interface OnSwipeListener {
        void onRightSwipe();
        void onLeftSwipe();
    }

    public SwipeDetector(@NonNull Context context) {
        super(context);
    }

    public SwipeDetector(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeListener(OnSwipeListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                return true;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float deltaX = endX - startX;
                if (Math.abs(deltaX) > 200) { // Adjust this threshold as needed
                    if (deltaX > 0) {
                        listener.onRightSwipe();
                    } else {
                        listener.onLeftSwipe();
                    }
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
}
