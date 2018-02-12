/*
CSC 240
Homework #1 - Tic Tac Toe
Jonathan Markman
N00755695
1/25/2018
 */
package com.example.jmarkman.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private class Results
    {
        private boolean victory;
        private String side;

        private Results(boolean v, String s)
        {
            victory = v;
            side = s;
        }
    }

    private Button[][] buttons;
    private boolean lastSelectionWasX;
    private int numTurns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Implement array to hold references to buttons in the tic-tac-toe board
        buttons = new Button[3][3];
        int buttonId = R.id.tttBtn1;

        for (int i = 0; i < buttons.length; i++)
        {
            for (int j = 0; j < buttons[i].length; j++)
            {
                buttons[i][j] = findViewById(buttonId);
                buttonId++;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Append a sign to the button based on its state.
     * @param view The current view
     */
    public void appendSign(View view)
    {
        // Get the numeric id of the current view that triggered this method via onClick
        int btnId = view.getId();
        // Use findViewById to store a reference to the current button
        Button btn = findViewById(btnId);

        // Based on the text in the button, change it to an "X" or an "O" based on its initial
        // state and the boolean flag that checks if the last selection resulted in an "X" mark
        String btnText = (String) btn.getText();
        if (btnText.contains("") && !lastSelectionWasX)
        {
            Toast.makeText(this, btn.getResources().getResourceName(btn.getId()), Toast.LENGTH_SHORT).show();
            btn.setText(getString(R.string.x_mark));
            lastSelectionWasX = true;
            btn.setEnabled(false);
        }
        else if (btnText.contains("") && lastSelectionWasX)
        {
            Toast.makeText(this, btn.getResources().getResourceName(btn.getId()), Toast.LENGTH_SHORT).show();
            btn.setText(getString(R.string.o_mark));
            lastSelectionWasX = false;
            btn.setEnabled(false);
        }

        numTurns++;
        checkForVictory();
    }

    /**
     * Resets the board by setting the text value of all buttons in the table to empty strings
     * @param view The current view
     */
    public void resetBoard(View view)
    {
        // For each button in our button array, if the button isn't enabled, reenable it and clear
        // the text within it
        for (Button[] btnArray : buttons)
        {
            for (Button button : btnArray)
            {
                if (button.isEnabled() == false)
                {
                    button.setEnabled(true);
                    button.setText("");
                }
            }
        }

        // To make sure that after reset, the first mark during a new game is an "X",
        // set the boolean flag to false
        lastSelectionWasX = false;
    }

    private void checkForVictory()
    {
        if (numTurns >= 3)
        {
            Results rows;
            Results cols;
            Results diag;


        }
        else
        {
            return;
        }
    }

    private Results checkRowsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()) == true)
                return new Results(true, buttons[i][0].getText().toString());
        }
        return new Results(false,"");
    }

    private Results checkColsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText()) == true)
                return new Results(true, buttons[i][0].getText().toString());
        }
        return new Results(false,"");
    }

    private Results checkDiagsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()) == true)
                return new Results(true, buttons[i][0].getText().toString());
        }
        return new Results(false,"");
    }

    /**
     * Checks if the actual contents of a row/col/diagonal are exactly the same
     * @param cell_1 The contents of a given cell
     * @param cell_2 The contents of a given cell
     * @param cell_3 The contents of a given cell
     * @return true if all three cells are equal and not blank, false otherwise
     */
    private boolean checkRowCol(CharSequence cell_1, CharSequence cell_2, CharSequence cell_3)
    {
        boolean areEqual = false;

        if ((cell_1 != "") && cell_1 == cell_2 && cell_2 == cell_3)
        {
            areEqual = true;
        }

        return areEqual;
    }

}
