package com.example.hv;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hv.DatabaseRoom.DatabaseClient;

import java.util.List;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private HabitoAdapter habitoAdapter;
    private List<Habito> habitosList;

    public SwipeToDeleteCallback(HabitoAdapter habitoAdapter){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.habitoAdapter = habitoAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT)
            habitoAdapter.deleteItem(position);
        if(direction == ItemTouchHelper.RIGHT){
            habitoAdapter.editItem(position);
//            Intent intent = new Intent(context, EditarHabito.class);
//
//            intent.putExtra("posicion", position);
//
//            context.startActivity(intent);
//
//            habitoAdapter.deleteItem(position);
        }

    }

}
