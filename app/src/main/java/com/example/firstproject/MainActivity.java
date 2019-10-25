package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvFontSize;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tvFontSize = findViewById(R.id.tvTest);

    }

    @Override
    protected void onStart() {
        boolean darkMode = preferences.getBoolean("darkTheme", true);
        changeTheme(darkMode);
        super.onStart();
        int fontSize = preferences.getInt("etFontSize", 25);
        tvFontSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }


    public void listShow(View view) {
        Intent in = new Intent(this, list.class);
        startActivity(in);
    }

    public void optionsShow(View view) {
        Intent in = new Intent(this, options.class);
        startActivity(in);
    }


    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }


}
