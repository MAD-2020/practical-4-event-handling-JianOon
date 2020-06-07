package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private TextView Scores;
    private int ran;

    private int score;
    private static final String TAG = "ButtonActivity";

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Whack-A-Mole");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonLeft = (Button) findViewById(R.id.LeftButton);
        ButtonRight = (Button) findViewById(R.id.RightButton);
        ButtonMiddle = (Button) findViewById(R.id.MiddleButton);
        Scores = (TextView) findViewById(R.id.scores);
        score = 0;
        ran = setNewMole();
        Scores.setText(Integer.toString(score));
        doCheck(ButtonLeft);
        doCheck(ButtonMiddle);
        doCheck(ButtonRight);

    }
    private int setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        if(randomLocation == 0){
            ButtonLeft.setText("*");
            ButtonMiddle.setText("O");
            ButtonRight.setText("O");
        }
        else if(randomLocation == 1){
            ButtonMiddle.setText("*");
            ButtonLeft.setText("O");
            ButtonRight.setText("O");
        }
        else{
            ButtonRight.setText("*");
            ButtonMiddle.setText("O");
            ButtonLeft.setText("O");
        }
        return randomLocation;

    }


    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(final Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkButton.getText()=="*") {
                    score = score + 1;
                    Log.v(TAG, "Hit, score added!");
                } else {
                    score = score - 1;
                    Log.v(TAG, "Missed, score deducted!");
                }
                Scores.setText(Integer.toString(score));
                if (score%10 == 0 && score != 0) {
                    nextLevelQuery(score);
                } else {
                    ran = setNewMole();
                }


            }
        });
    }

    private void nextLevelQuery(final Integer score) {
        /*
        Builds dialog box here.
        */

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        builder.setMessage("Would you like to advance to advanced mole?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel(score);
                Log.v(TAG, "Advance option given to user!");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.v(TAG, "User decline!");

            }
        });
        builder.show();

    }

    private void nextLevel(Integer score) {
        /* Launch advanced page */
        Intent nextlvl = new Intent(MainActivity.this, Main2Activity.class);
        nextlvl.putExtra("Score", Integer.toString(score));
        startActivity(nextlvl);

    }

}