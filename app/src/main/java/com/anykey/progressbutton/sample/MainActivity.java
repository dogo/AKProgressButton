package com.anykey.progressbutton.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.anykey.library.progressbutton.ProgressButton;
import com.anykey.progressbutton.sample.utils.ProgressGenerator;

public class MainActivity extends Activity implements ProgressGenerator.OnCompleteListener {

    private ProgressButton mStdButton;
    private ProgressButton mCustomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        mStdButton = (ProgressButton) findViewById(R.id.btn_standard);
        mStdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start();
            }
        });

        mCustomButton = (ProgressButton) findViewById(R.id.btn_custom);
        mCustomButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start();
            }
        });
    }

    @Override
    public void onComplete() {
        mStdButton.stopLoadingAnimation();
        mCustomButton.stopLoadingAnimation();
    }
}
