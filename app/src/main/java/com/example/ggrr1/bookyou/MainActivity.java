package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button go_btn = (Button) findViewById(R.id.button);
        final LinearLayout l_layout = (LinearLayout) findViewById(R.id.layout);
        final Animation animTrans = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                l_layout.startAnimation(animTrans);

            }
        }, 500);
        animTrans.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                go_btn.setVisibility(View.VISIBLE);
                go_btn.startAnimation(animFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Login.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }
}
