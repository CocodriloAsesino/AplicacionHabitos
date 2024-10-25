package com.example.hv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hv.DatabaseRoom.HabitoDao;

import java.util.List;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private List<Habito> habitosList;
    private HabitoDao habitoDao;
    Context context;

    public HabitoAdapter(List<Habito> habitosList, HabitoDao habitoDao, Context context) {

        this.habitosList = habitosList;
        this.habitoDao = habitoDao;
        this.context = context;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textrecyclerview, parent, false);
        return new HabitoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = habitosList.get(position);
        holder.txtName.setText(habito.getName());
        holder.txtHora.setText(habito.getHora());
        holder.txtFecha.setText(habito.getFecha());
    }

    @Override
    public int getItemCount() {

        return habitosList.size();
    }

    public void deleteItem(int position){
        Habito habito = habitosList.get(position);
        habitosList.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(context,"Habito Eliminado.", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                habitoDao.delete(habito);
            }
        }).start();
    }

    public void editItem(int position){

        Habito habito = habitosList.get(position);
        Intent intent = new Intent(context, EditarHabito.class);
        intent.putExtra("id", String.valueOf(habito.getId()));
        intent.putExtra("text", String.valueOf(habito.getName()));
        intent.putExtra("hora", String.valueOf(habito.getHora()));
        intent.putExtra("fecha", String.valueOf(habito.getFecha()));

        context.startActivity(intent);

        notifyItemChanged(position);

        new Thread(new Runnable() {
            @Override
            public void run() {
                habitoDao.update(habito);
            }
        }).start();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtHora, txtFecha;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtHora = itemView.findViewById(R.id.txtHora);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
