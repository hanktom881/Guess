package com.tom.guess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends Activity {

    private int secret = -1;
    private boolean match;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateSecret();

    }

    private void generateSecret() {
        if (secret>0) {
            new AlertDialog.Builder(this)
                    .setMessage("是否還要繼續玩?")
                    .setPositiveButton("YES", null)
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
        Random r = new Random();
        secret = r.nextInt(10)+1;
        Log.d("SECRET", secret + "");
        Toast.makeText(this, "已產生祕密數字", Toast.LENGTH_LONG).show();
        count = 0;
    }

    public void guess(View v){
        EditText edNumber = (EditText) findViewById(R.id.number);
        int n = Integer.parseInt(edNumber.getText().toString());
        String msg = null;
        match = false;
        if (n>secret){
            msg = "小一點";
        }else if (n<secret){
            msg = "大一點";
        }else {
            if (count==0){
                msg = "太神了，您一次就答對了";
            }else {
                msg = "猜對了";
            }
            match = true;
        }
        count++;
        if (count==4 && !match){
            new AlertDialog.Builder(this)
                    .setMessage("猜4次了,不對,祕密數字為"+secret)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            generateSecret();
                        }
                    })
                    .show();
        }else {
            new AlertDialog.Builder(this)
                    .setMessage(msg + "(" + count + ")")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (match) {
                                generateSecret();
                            }
                        }
                    })
                    .show();
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
}
