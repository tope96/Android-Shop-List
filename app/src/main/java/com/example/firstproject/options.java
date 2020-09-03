package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class options extends AppCompatActivity {

    private TextView tvColor;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tvFontSize, tvFontUnit;
    private Spinner dropDown;
    private Switch swTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean darkMode = sp.getBoolean("darkTheme", true);
        changeTheme(darkMode);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);
        swTheme = findViewById(R.id.swColor);
        swTheme.setChecked(darkMode);
        dropDown = findViewById(R.id.spinner);
        tvColor = findViewById(R.id.tvColor);
        tvFontUnit = findViewById(R.id.tvFontUnit);
        tvFontSize = findViewById(R.id.tvFontSize);

        editor = sp.edit();

        Integer[] sizes = new Integer[26];
        for (int i=0; i<sizes.length; i++)
        {
            sizes[i] = i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        dropDown.setAdapter(adapter);

    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(options.this, MainActivity.class));

    }


    @Override
    protected void onStart() {
        super.onStart();
        int fontSize = sp.getInt("etFontSize",25);
        dropDown.setSelection(fontSize);
        setSizes(fontSize);
    }


    public void saveOptions(View view) {
        int selectedPosition = dropDown.getSelectedItemPosition();
        editor.putInt("etFontSize", selectedPosition);
        editor.apply();
        setSizes(selectedPosition);
        Toast.makeText(this, R.string.saveSuccess, Toast.LENGTH_SHORT).show();

        if(swTheme.isChecked()){
            editor.putBoolean("darkTheme", true);
            editor.apply();
            changeTheme( true);
            restartApp();
        }else{
            editor.putBoolean("darkTheme", false);
            editor.apply();
            changeTheme( false);
            restartApp();
        }

    }

    private void setSizes(int size){
        tvFontSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tvColor.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tvFontUnit.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    private void changeTheme(boolean dark){
        if(dark){
            setTheme(R.style.AppTheme2);
        }else{
            setTheme(R.style.AppTheme);
        }

    }

    private void restartApp(){
        Intent i = new Intent(getApplicationContext(), options.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(options.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}