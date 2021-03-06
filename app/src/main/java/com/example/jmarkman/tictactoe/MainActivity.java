/*
CSC 240
Homework #1 - Tic Tac Toe
Jonathan Markman
N00755695
1/25/2018
 */
package com.example.jmarkman.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button[][] buttons;
    private boolean lastSelectionWasX;
    private int numTurns = 0;
    private String winningSide;

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

        // Get a reference to the spinner's location in the action bar
        MenuItem spinnerItem = menu.findItem(R.id.spinner_colors);

        // Get a reference to the actual spinner
        Spinner spinner = (Spinner) spinnerItem.getActionView();

        // Put a listener on it
        spinner.setOnItemSelectedListener(this); //for now, ignore the error you get

        // Create an array that will take the array we made in the XML and package it
        // so that the spinner can read from it and populate itself using it
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);

        // Set how the spinner dropdown will look
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Associate the array with the spinner so the spinner will populate
        spinner.setAdapter(adapter);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        else if (id == R.id.reset_board)
        {
            resetBoard();
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
            //Toast.makeText(this, btn.getResources().getResourceName(btn.getId()), Toast.LENGTH_SHORT).show();
            btn.setText(getString(R.string.x_mark));
            lastSelectionWasX = true;
            btn.setClickable(false);
        }
        else if (btnText.contains("") && lastSelectionWasX)
        {
            //Toast.makeText(this, btn.getResources().getResourceName(btn.getId()), Toast.LENGTH_SHORT).show();
            btn.setText(getString(R.string.o_mark));
            lastSelectionWasX = false;
            btn.setClickable(false);
        }

        numTurns++;
        checkForVictory();
    }

    /**
     * Resets the board by setting the text value of all buttons in the table to empty strings
     */
    public void resetBoard()
    {
        // For each button in our button array, if the button isn't enabled, reenable it and clear
        // the text within it
        for (Button[] btnArray : buttons)
        {
            for (Button button : btnArray)
            {
                if (button.isEnabled() == false)
                {
                    button.setClickable(true);
                    button.setText("");
                }
            }
        }

        // To make sure that after reset, the first mark during a new game is an "X",
        // set the boolean flag to false
        lastSelectionWasX = false;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        String spinnerItem = adapterView.getItemAtPosition(i).toString();
        changeSymbolColor(spinnerItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {
        return;
    }

    /**
     * Based on user selection from the spinner, change the color of the symbols
     * using the setColor method
     * @param colorFromSpinner the desired color as a string from the spinner
     */
    private void changeSymbolColor(String colorFromSpinner)
    {
        switch (colorFromSpinner)
        {
            case "Red":
                setColor(Color.RED);
                break;
            case "Blue":
                setColor(Color.BLUE);
                break;
            case "Yellow":
                setColor(Color.YELLOW);
                break;
            case "Green":
                setColor(Color.GREEN);
                break;
            default:
                setColor(Color.BLACK);
                break;
        }
    }

    /**
     * Based on the selection from changeSymbolColor, change the color of the symbol based on
     * which side went last
     * @param colorAsInt the desired color as an int via the Color class
     */
    private void setColor(int colorAsInt)
    {
        for (Button[] btnArray : buttons)
        {
            for (Button button : btnArray)
            {
                if (lastSelectionWasX)
                {
                    if (button.getText().toString().equals("O"))
                    {
                        button.setTextColor(colorAsInt);
                    }
                }
                else
                {
                    if (button.getText().toString().equals("X"))
                    {
                        button.setTextColor(colorAsInt);
                    }
                }

            }
        }
    }

    /**
     * Logic for checking for a winning side in tic tac toe. Tic Tac Toe can end in 5 turns (or 3
     * moves, if we implement a forfeit function) and shouldn't go on for more than 8.
     */
    private void checkForVictory()
    {
        if (numTurns >= 4)
        {
            if (checkRowsForWin() || checkColsForWin() || checkDiagsForWin())
            {
                for (Button[] btnArray : buttons)
                {
                    for (Button button : btnArray)
                    {
                        button.setClickable(false);
                    }
                }

                Toast winToast = Toast.makeText(this, winningSide + " is the victor!\nPress reset from the menu to play again!", Toast.LENGTH_LONG );
                TextView winView = (TextView) winToast.getView().findViewById(android.R.id.message);
                if (winView != null)
                    winView.setGravity(Gravity.CENTER);

                winToast.show();
            }
            else if (numTurns >= 8)
            {
                Toast drawToast = Toast.makeText(this, "Draw! Everybody loses!\nPress reset from the menu to play again!", Toast.LENGTH_LONG);
                TextView drawView = (TextView) drawToast.getView().findViewById(android.R.id.message);
                if (drawView != null)
                    drawView.setGravity(Gravity.CENTER);
                drawToast.show();

                resetBoard();
            }
        }
        else
        {
            return;
        }
    }

    /*
    * According to The Internet (tm), the most common way to solve tic tac toe is to make a 2D array
    * to represent the grid. What the 2D array does for us is that it allows us to have a guarantee
    * of what's going to be in our operational space.
    *
    * http://www.javaprogrammingforums.com/loops-control-statements/18747-tic-tac-toe-array-win-check.html#post79896
    * This user postulates the general idea of statically accessing (i.e., buttons[0][1]) each box and
    * running it through an equivalence method.
    *
    * The current limitation of these methods is that they're made for the traditional tic-tac-toe grid.
    * Some people on The Internet (tm) like to play "code golf" where they account for grids that have
    * dimensions that are multiples of 3.
    */

    /**
     * Checks the rows of the buttons 2D array for victory conditions
     * @return true if the characters in a row match
     */
    private boolean checkRowsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[i][0].getText(), buttons[i][1].getText(), buttons[i][2].getText()) == true)
            {
                winningSide = buttons[i][0].getText().toString();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the columns of the buttons 2D array for victory conditions
     * @return true if the characters in a column match
     */
    private boolean checkColsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[0][i].getText(), buttons[1][i].getText(), buttons[2][i].getText()) == true)
            {
                winningSide = buttons[0][i].getText().toString();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the diagonals of the buttons 2D array for victory conditions
     * @return true if the characters in either diagonal match
     */
    private boolean checkDiagsForWin()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            if (checkRowCol(buttons[0][0].getText(), buttons[1][1].getText(), buttons[2][2].getText()) == true ||
                    checkRowCol(buttons[0][2].getText(), buttons[1][1].getText(), buttons[2][0].getText()) == true)
            {
                winningSide = buttons[i][0].getText().toString();
                return true;
            }

        }
        return false;
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
