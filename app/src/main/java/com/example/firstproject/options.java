package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class options extends AppCompatActivity {

    private EditText etFontSize;
    private TextView tvColor;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tvFontSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        etFontSize = findViewById(R.id.etFontSize);
        tvColor = findViewById(R.id.tvColor);
        tvFontSize = findViewById(R.id.tvFontSize);
        sp = getPreferences(Context.MODE_PRIVATE);
        editor = sp.edit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        int fontSize = sp.getInt("etFontSize", 10);
        etFontSize.setText(""+fontSize);
        setSizes(fontSize);
    }


    public void saveOptions(View view) {
        int fontSizeValue = Integer.parseInt(etFontSize.getText().toString());
        editor.putInt("etFontSize", fontSizeValue);
        editor.apply();
        editor.commit();
        setSizes(fontSizeValue);
        Toast.makeText(this, R.string.saveSuccess, Toast.LENGTH_SHORT).show();
    }

    private void setSizes(int size){
        tvFontSize.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        tvColor.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}
