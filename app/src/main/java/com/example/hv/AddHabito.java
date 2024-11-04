package com.example.hv;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hv.DatabaseRoom.DatabaseClient;

import java.util.Calendar;
import java.util.TimeZone;

public class AddHabito extends AppCompatActivity {

    private EditText editDate, editTime, editText;
    private TextView introducirHabito;
    private Button inicio, add_habito, buttonSave;
    private static final int REQUEST_CODE = 1; // Código de solicitud para permisos
    private static final String TAG = "Notify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_habito);

        inicio = findViewById(R.id.Inicio);
        inicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                irInicio();
            }
        });

        editText = findViewById(R.id.editText);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
        buttonSave = findViewById(R.id.addHabito);

        buttonSave.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  saveHabito();

              }
          });

    }

    private void saveHabito(){
        String hora = editTime.getText().toString();
        String fecha = editDate.getText().toString();
        String texto = editText.getText().toString();

        if(hora.isEmpty() || fecha.isEmpty() || texto.isEmpty()){
            Toast.makeText(this,"Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Habito habitoOBJ = new Habito(texto,hora,fecha);

        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao().insert(habitoOBJ);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 y superior
            if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, REQUEST_CODE);
            } else {
                createNotificationChannel(); // Crea el canal de notificaciones
                scheduleDailyNotification(); // Programa una notificación diaria
            }
        } else {
            createNotificationChannel(); // Crea el canal de notificaciones
            scheduleDailyNotification(); // Programa una notificación diaria
        }

        Toast.makeText(this, "Habito Añadido", Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(3000);
            startActivity(new Intent(this,VistaHabito.class));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public void irInicio (){
        Intent intentAdd = new Intent(this, MainActivity.class);

        startActivity(intentAdd);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Solo para Android 8.0 y superior
            CharSequence name = "DailyReminderChannel";
            String description = "Channel for Daily Reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("daily_reminder_channel",
                    name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Canal de notificaciones creado");
        }
    }

    private void scheduleDailyNotification() {
        // Obtiene una instancia del calendario
        Calendar calendar = Calendar.getInstance();
        // Establece la zona horaria predeterminada del dispositivo
        calendar.setTimeZone(TimeZone.getDefault());
        // Establece la hora actual
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND,5);
        // Configura la hora de la notificación (9:00:00 en este ejemplo)
//        Integer.parseInt(editTime.getText().toString())
//        calendar.set(Calendar.HOUR_OF_DAY, 9);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);

        // Si la hora configurada ya pasó, programa para el siguiente día
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Crea un intent para el receptor de la notificación
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("fecha",editDate.getText().toString());
        intent.putExtra("hora",editTime.getText().toString());
        intent.putExtra("texto",editText.getText().toString());

        //DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao().getAllHabitos().toString())
        // Agrega el flag FLAG_IMMUTABLE al PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Programa la notificación diaria utilizando AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d(TAG, "Notificación diaria programada a las " + calendar.getTime());
    }

    // Manejo de los resultados de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createNotificationChannel(); // Crea el canal de notificaciones
                scheduleDailyNotification(); // Programa una notificación diaria
            }
        }
    }
}