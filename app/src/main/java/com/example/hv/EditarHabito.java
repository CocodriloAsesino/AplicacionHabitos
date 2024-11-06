package com.example.hv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hv.DatabaseRoom.DatabaseClient;
import com.example.hv.DatabaseRoom.HabitoDao;

import java.util.List;

public class EditarHabito extends AppCompatActivity {

    private EditText editText;
    private TimePicker editTime;
    private DatePicker editDate;
    private Button buttonSave;
    private List<Habito> habitosList;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_habito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonSave = findViewById(R.id.addHabito);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHabito();
            }
        });

        Intent intent = getIntent();

        editText = findViewById(R.id.editText);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);

        editText.setText(intent.getStringExtra("text"));

        position = intent.getIntExtra("position", -1);

        editTime.setIs24HourView(true);

        }

    private void saveHabito() {
        String hora = String.format("%02d:%02d",editTime.getCurrentHour(),editTime.getCurrentMinute());
        String fecha = String.format("%04d-%02d-%02d",editDate.getYear(),editDate.getMonth() + 1,editDate.getDayOfMonth());
        String texto = editText.getText().toString();

        if (hora.isEmpty() || fecha.isEmpty() || texto.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        HabitoDao habitoDao = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao();

        habitosList = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao().getAllHabitos();
        habitosList.get(position).setHora(hora);
        habitosList.get(position).setFecha(fecha);
        habitosList.get(position).setName(texto);

        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitoDao().update(habitosList.get(position));

        Toast.makeText(this, "Habito editado", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this,VistaHabito.class));
    }

}


