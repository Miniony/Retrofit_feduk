package com.example.smart_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Recycler_view extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.list);
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(this, states);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }
    private void setInitialData(){

        states.add(new State ("Бразилия", "Бразилиа", R.drawable.home1));
        states.add(new State ("Аргентина", "Буэнос-Айрес", R.drawable.home2));
        states.add(new State ("Колумбия", "Богота", R.drawable.home3));
        states.add(new State ("Уругвай", "Монтевидео", R.drawable.home4));
        states.add(new State ("Чили", "Сантьяго", R.drawable.home5));
    }
}

