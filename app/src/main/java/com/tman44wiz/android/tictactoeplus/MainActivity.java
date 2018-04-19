package com.tman44wiz.android.tictactoeplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button[][] boxButtons  = new Button[3][3];

    private String[][] boxContents = new String[3][3];

    private boolean firstPlayerTurn = true;

    private int firstPlayerPoint, secondPlayerPoint;
    private int counterVariable;

    private TextView firstPlayerTextView, secondPlayerTextView, playerTurnTextView;

    private Button resetBoardButton, resetGameButton;

    private String playerOnePoint;
    private String playerTwoPoint;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterVariable = 9;

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        initializer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("firstPlayerPoint", firstPlayerPoint);
        outState.putInt("secondPlayerTextView", secondPlayerPoint);
        outState.putBoolean("firstPlayerTurn", firstPlayerTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        firstPlayerPoint = savedInstanceState.getInt("firstPlayerPoint");
        secondPlayerPoint = savedInstanceState.getInt("secondPlayerTextView");
        firstPlayerTurn = savedInstanceState.getBoolean("firstPlayerTurn");

        //boxContents[3][3] = savedInstanceState.getString("boxContents");
    }

    public void initializer() {
        firstPlayerTextView = findViewById(R.id.player1_textView);
        secondPlayerTextView = findViewById(R.id.player2_textView);
        playerTurnTextView = findViewById(R.id.playerTurn_TextView);
        playerTurnTextView.setText("First Player Turn");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                boxButtons[i][j] = findViewById(resourceID);
                boxButtons[i][j].setOnClickListener(this);
            }
        }

        resetBoardButton = findViewById(R.id.resetBoard_Button);
        resetBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetBoard();
            }
        });

        resetGameButton = findViewById(R.id.resetGame_Button);
        resetGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }


    @Override
    public void onClick(View view) {

        //To check if the clicked box is empty.
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        //Now lets start First Players turn.
        if (firstPlayerTurn) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#01fc2f"));
            playerTurnTextView.setText("O Turn");
            firstPlayerTurn = false;
        }
        else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#de37f4"));
            playerTurnTextView.setText("X Turn");
            firstPlayerTurn = true;
        }

        counterVariable++;

        checkForResult();

    }

    private void checkForResult() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boxContents[i][j] = boxButtons[i][j].getText().toString();
            }
        }


        //This checks if the game is draw.
        Boolean boxEmpty = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boxContents[i][j].isEmpty()) {
                    boxEmpty = true;
                    break;
                }
            }
        }
        if (counterVariable == 9 && !boxEmpty) {
            drawGame();
        }


        //Let's check for the Horizontal Lines.
        for (int i = 0; i < 3; i++) {
            if (boxContents[i][0] == boxContents[i][1]
                    && boxContents[i][0] == boxContents[i][2]
                    && !(boxContents[i][0] == (""))
                    && boxContents[i][0].equals("X")) {
                firstPlayerScores();
            }
            if (boxContents[i][0] == boxContents[i][1]
                    && boxContents[i][0] == boxContents[i][2]
                    && !(boxContents[i][0] == (""))
                    && boxContents[i][0].equals("O")) {
                secondPlayerScores();
            }
        }


        //Let's check for the Vertical Lines.
        for (int i = 0; i < 3; i++) {
            if (boxContents[0][i] == boxContents[1][i]
                    && boxContents[0][i] == boxContents[2][i]
                    && !(boxContents[0][i] == (""))
                    && boxContents[0][i].equals("X")) {
                firstPlayerScores();
            }
            if (boxContents[0][i] == boxContents[1][i]
                    && boxContents[0][i] == boxContents[2][i]
                    && !(boxContents[0][i] == (""))
                    && boxContents[0][i].equals("O")) {
                secondPlayerScores();
            }
        }


        //Let's check for the Diagonals.
        if (boxContents[0][0] == boxContents[1][1]
                && boxContents[0][0] == boxContents[2][2]
                && !(boxContents[0][0] == (""))
                && boxContents[0][0].equals("X")) {
            firstPlayerScores();
        }
        if (boxContents[0][0] == boxContents[1][1]
                && boxContents[0][0] == boxContents[2][2]
                && !(boxContents[0][0] == (""))
                && boxContents[0][0].equals("O")) {
            secondPlayerScores();
        }


        if (boxContents[0][2] == boxContents[1][1]
                && boxContents[0][2] == boxContents[2][0]
                && !(boxContents[0][2] == (""))
                && boxContents[0][2].equals("X")) {
            firstPlayerScores();
        }
        if (boxContents[0][2] == boxContents[1][1]
                && boxContents[0][2] == boxContents[2][0]
                && !(boxContents[0][2] == (""))
                && boxContents[0][2].equals("O")) {
            secondPlayerScores();
        }

    }


    private void firstPlayerScores() {
        firstPlayerPoint++;
        firstPlayerDisplayScores();
        AlertDialog.Builder firstPlayerAlertBuilder = new AlertDialog.Builder(this);
        firstPlayerAlertBuilder.setIcon(R.drawable.trophy);
        firstPlayerAlertBuilder.setTitle("Winner!!!");
        firstPlayerAlertBuilder.setMessage("Congratulation, First Player Won this Game... \nPress Ok and Reset Game.");
        firstPlayerAlertBuilder.setCancelable(true);
        firstPlayerAlertBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo List
                        //Now Reset the boardTable.
                        resetBoard();
                    }
                });
        firstPlayerAlertBuilder.create().show();
    }
    public void firstPlayerDisplayScores(){
        firstPlayerTextView.setText("Player 1: " + firstPlayerPoint);
    }

    private void secondPlayerScores() {
        secondPlayerPoint++;
        secondPlayerDisplayScores();
        AlertDialog.Builder secondPlayerAlertBuilder = new AlertDialog.Builder(this);
        secondPlayerAlertBuilder.setIcon(R.drawable.trophy);
        secondPlayerAlertBuilder.setTitle("Winner!!!");
        secondPlayerAlertBuilder.setMessage("Congratulation, Second Player Won this Game... \nPress Ok and Reset Game.");
        secondPlayerAlertBuilder.setCancelable(true);
        secondPlayerAlertBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo List
                        //Now Reset the boardTable.
                        resetBoard();
                    }
                });
        secondPlayerAlertBuilder.create().show();
    }
    public void secondPlayerDisplayScores(){
        secondPlayerTextView.setText("Player 2: " + secondPlayerPoint);
    }

    private void drawGame() {
        AlertDialog.Builder drawPlayerAlertBuilder = new AlertDialog.Builder(this);
        drawPlayerAlertBuilder.setIcon(R.drawable.draw);
        drawPlayerAlertBuilder.setTitle("Ooops!!!");
        drawPlayerAlertBuilder.setMessage("This is a Draw Game... \nPress Ok and Reset Game.");
        drawPlayerAlertBuilder.setCancelable(true);
        drawPlayerAlertBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo List
                        //Now Reset the boardTable.
                        resetBoard();
                    }
                });
        drawPlayerAlertBuilder.create().show();
    }


    private void resetBoard() {
        vibrator.vibrate(100);

        for (int i = 0; i != 3; i++) {
            for (int j = 0; j != 3; j++) {
                boxButtons[i][j].setText("");
            }
        }
        counterVariable = 0;
        playerTurnTextView.setText("First Player Turn");
        firstPlayerTurn = true;
    }


    private void resetGame() {
        vibrator.vibrate(100);
        AlertDialog.Builder resetAlertBuilder = new AlertDialog.Builder(this);
        resetAlertBuilder.setIcon(R.drawable.reset);
        resetAlertBuilder.setTitle("Reset Board?");
        resetAlertBuilder.setMessage("Reset game board...?");
        resetAlertBuilder.setCancelable(true);
        resetAlertBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        firstPlayerPoint = 0;
                        secondPlayerPoint = 0;
                        firstPlayerDisplayScores();
                        secondPlayerDisplayScores();
                        playerTurnTextView.setText("First Player Turn");
                        resetBoard();
                    }
                });
        resetAlertBuilder.create().show();
    }
}
