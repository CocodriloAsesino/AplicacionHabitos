package com.example.hv;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.app.PendingIntent;

import com.example.hv.DatabaseRoom.DatabaseClient;
import com.example.hv.DatabaseRoom.HabitoDao;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class VistaHabito extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Habito> habitosList;
    private HabitoAdapter habitoAdapter;
    private Button inicio, crearNoti;
    private static final int REQUEST_CODE = 1; // Código de solicitud para permisos
    private Button createNotificationButton; // Botón para crear la notificación
    private static final String TAG = "Notify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vista_habito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicio = findViewById(R.id.Inicio);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ir_inicio();
            }
        });

        recyclerView = findViewById(R.id.recyclerViewHabitos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHabitos();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(habitoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    public void loadHabitos(){
        habitosList = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao().getAllHabitos();
        HabitoDao habitoDao = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao();
        habitoAdapter = new HabitoAdapter(habitosList, habitoDao, getApplicationContext());
        recyclerView.setAdapter(habitoAdapter);
    }

    public void ir_inicio(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }




}