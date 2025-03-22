package com.example.internalfiles;

import static android.provider.Telephony.Mms.Part.FILENAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView tV;
    EditText eT;
    String textEt;
    private final String FILENAME = "inttest.txt";
    String textFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = (TextView) findViewById(R.id.tV);
        eT = (EditText) findViewById(R.id.eT);

        textFile = getTextFile();
        tV.setText(textFile);
    }

    /**
     * Saves the user input from EditText to the internal file.
     *
     * @param view The view
     */
    public void goSave(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_APPEND);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            textEt = eT.getText().toString();
            bW.write(textEt);
            bW.close();
            oSW.close();
            fOS.close();

            tV.setText(getTextFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads and returns the contents of the internal file.
     *
     * @return The contents of the file as a String.
     */
    public String getTextFile() {
        String text;
        try {
            FileInputStream fIS= openFileInput (FILENAME);
            InputStreamReader iSR = new InputStreamReader (fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            text = sB.toString();

            bR.close();
            iSR.close();
            fIS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    /**
     * Clears the contents of the internal file and resets UI components.
     *
     * @param view The view
     */
    public void goReset(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write("");
            bW.close();
            oSW.close();
            fOS.close();
            eT.setText("");
            tV.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves user input and closes the application.
     *
     * @param view The view
     */
    public void goExit(View view) {
        goSave(view);
        finish();
    }


    /**
     * Inflates the options menu.
     *
     * @param menu The options menu.
     * @return true if the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles selection of menu items.
     *
     * @param item The selected menu item.
     * @return true if the menu item was handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuCred) {
            Intent si = new Intent(this, activity_credits.class);
            startActivity(si);
        }
        return true;
    }
}