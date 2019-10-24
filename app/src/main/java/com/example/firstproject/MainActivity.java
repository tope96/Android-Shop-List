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

    private SharedPreferences sp;
    private TextView tvFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvFontSize = findViewById(R.id.tvTest);
        sp = getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int fontSize = preferences.getInt("etFontSize", 10);
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


}
