package com.project.cookbook;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Timer extends Activity implements View.OnClickListener {

    private Button startB;
    private TextView text, timeElapsedView;
    private MalibuCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private long timeElapsed;

    private final long startTime =  50 * 1000;
    private final long interval = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startB = (Button) findViewById(R.id.button);
        startB.setOnClickListener(this);

        text = (TextView) findViewById(R.id.timer);
        timeElapsedView = (TextView) findViewById(R.id.timeElapsed);
        countDownTimer = new MalibuCountDownTimer(startTime, interval);

        text.setText(text.getText() + String.valueOf(startTime/1000) + " seconds " +
                String.valueOf(startTime%1000) + " milliseconds");
    }

    @Override
    public void onClick(View v) {
        if(!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("Reset");
        }
        else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("Restart");
        }
    }

    //CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer {
        /**
         * @param startTime    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param interval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MalibuCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime/1000) + " seconds " +
                    String.valueOf(startTime%1000) + " milliseconds");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            text.setText("Time Remaining: " + millisUntilFinished/1000 + " seconds " + (millisUntilFinished%1000) + " milliseconds" );
            timeElapsed = startTime - millisUntilFinished;
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed/1000) + " seconds " +
                    String.valueOf(timeElapsed%1000) + " milliseconds");
        }
    }
}
