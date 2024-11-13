package com.example.hv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

        //Cambio de color al recyclerView

        holder.switchHabito.setOnCheckedChangeListener((buttonView, isChecked)-> {
            if (isChecked) { holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.newColor));
            }
            else {
                holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.defaultColor));
            }
        });
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

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtHora, txtFecha;
        Switch switchHabito;
        LinearLayout linearLayout;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtHora = itemView.findViewById(R.id.txtHora);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            switchHabito = itemView.findViewById(R.id.switchHabito);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
