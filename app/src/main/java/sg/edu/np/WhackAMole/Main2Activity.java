package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    int score;
    CountDownTimer countDownTimer;
    CountDownTimer moletimer;
    TextView displayscore;


    private static final String TAG = "AdvanceMole";

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        countDownTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                Toast.makeText(getApplicationContext(), "Get ready " +millisUntilFinished/ 1000+ " seconds !", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_LONG).show();

                countDownTimer.cancel();
                placeMoleTimer();

            }
        };
        countDownTimer.start();


    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        Log.v(TAG,"Mole Timer running");
        moletimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){

            }

            public void onFinish(){
                setNewMole();
                Log.v(TAG, "New Mole Location!");
                moletimer.cancel();
                moletimer.start();

            }
        };
        moletimer.start();
        setNewMole();

    }
    private static final int[] BUTTON_IDS = {
        R.id.hole1,R.id.hole2,R.id.hole3,R.id.hole4,R.id.hole5,R.id.hole6,R.id.hole7,R.id.hole8,R.id.hole9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receivingEnd = getIntent();
        String advancedScore = receivingEnd.getStringExtra("Score");
        score = Integer.parseInt(advancedScore);
        displayscore = findViewById(R.id.score);
        displayscore.setText(advancedScore);
        Log.v(TAG, "Current User Score: " + String.valueOf(score));
        readyTimer();



        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button temp3 = findViewById(id);
            Log.v("WHAT", (String) temp3.getText());

            temp3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.v("WHAT"," Before Button:" + temp3.getText());
                    int check = doCheck(temp3);
                    Log.v("WHAT", "check: " + String.valueOf(check));
                    if(check == 1){
                        Log.v(TAG, "Hit, score added!");
                        score += 1;
                        displayscore.setText(Integer.toString(score));
                        Log.v("WHAT", "Current User Score: " + String.valueOf(score));
                    }
                    else if(check==2) {
                        Log.v(TAG, "Missed, point deducted!");
                        score-=1;
                        displayscore.setText(Integer.toString(score));
                        Log.v("WHAT", "Current User Score: " + String.valueOf(score));


                    }




                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private int doCheck(Button checkButton) {

        Log.v("WHAT","Button:" + checkButton.getText());
        if (checkButton.getText().equals("*") ) {
            return 1;
        }
        else if (checkButton.getText() == "O") {
            return 2;
        }
        else{
            return 3;
        }
    }



    public void setNewMole() {
    /* Hint:
        Clears the previous mole location and gets a new random location of the next mole location.
        Sets the new location of the mole.
     */

        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i = BUTTON_IDS.length - 1; i >= 0; i--) {
            Button temp = findViewById(BUTTON_IDS[i]);

            if (i != randomLocation) {
                temp.setText("O");
            } else {
                temp.setText("*");
                Log.v("WHAT","* = " + Integer.toString(BUTTON_IDS[i]));
                Log.v("WHAT","textset: " + temp.getText());
            }
        }
    }
}

