package com.example.hv;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Habito {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String hora;
    private String fecha;

    public Habito(String name, String hora, String fecha) {
        this.name = name;
        this.hora = hora;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}