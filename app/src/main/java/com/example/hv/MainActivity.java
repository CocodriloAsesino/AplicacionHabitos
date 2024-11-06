package com.example.hv;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Switch aSwitch;
    private Button editarHabitos;
    private static final int REQUEST_CODE = 1; // Código de solicitud para permisos
    private Button createNotificationButton; // Botón para crear la notificación
    private static final String TAG = "Notify";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        editarHabitos = findViewById(R.id.editarHabitos);
        editarHabitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar_Habitos();
            }
        });
        EditText autoD8 = (EditText)findViewById(R.id.FechaMain);
        EditText autoTime = (EditText)findViewById(R.id.HoraMain);

        SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        autoD8.setText(date);
        autoTime.setText(time);

        aSwitch = findViewById(R.id.switch1);
        int mode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == 32)
            aSwitch.setText("LIGHT MODE");
        else
            aSwitch.setText("DDARK MODE");
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambioNigga();
            }
        });

    }

    public void editar_Habitos() {
        Intent intentAdd = new Intent(this, AddHabito.class);
        startActivity(intentAdd);
    }

    public void cambioNigga() {
        if (aSwitch.isChecked()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            aSwitch.setText("DARK MODE");
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setText("LIGHT MODE");
        }
    }
}