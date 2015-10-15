package com.anykey.progressbutton.sample.utils;

import android.os.Handler;

import com.anykey.library.progressbutton.ProgressButton;

import java.util.Random;

public class ProgressGenerator {

    public interface OnCompleteListener {
        void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                if (mProgress <= 110) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    mListener.onComplete();
                    mProgress = 0;
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
