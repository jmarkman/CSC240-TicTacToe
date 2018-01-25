/*
CSC 240
Homework #1 - Tic Tac Toe
Jonathan Markman
N00755695
1/25/2018
 */
package com.example.jmarkman.tictactoe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private boolean lastSelectionWasX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            btn.setText(getString(R.string.x_mark));
            lastSelectionWasX = true;
        }
        else
        {
            btn.setText(getString(R.string.o_mark));
            lastSelectionWasX = false;
        }
    }

    /**
     * Resets the board by setting the text value of all buttons in the table to empty strings
     * @param view The current view
     */
    public void resetBoard(View view)
    {
        // Get a reference to the table layout that's currently holding all of our buttons
        TableLayout gameBoard = findViewById(R.id.game_board);
        // Create a list of our board buttons
        ArrayList<View> boardButtons = gameBoard.getTouchables();

        // For each button in the list, if the object is actually a button, set its
        // internal text to a blank string
        for (View btn : boardButtons)
        {
            if (btn instanceof Button)
            {
                ((Button) btn).setText("");
            }
        }

        // To make sure that after reset, the first mark during a new game is an "X",
        // set the boolean flag to false
        lastSelectionWasX = false;
    }
}
