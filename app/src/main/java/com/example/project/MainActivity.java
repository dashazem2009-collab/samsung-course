package com.example.project;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextNote;
    Button buttonAdd;
    ListView listViewNotes;

    ArrayList<String> notes;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.editTextNote);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewNotes = findViewById(R.id.listViewNotes);

        notes = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );
        listViewNotes.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            String text = editTextNote.getText().toString();

            if (!text.isEmpty()) {
                notes.add(text);
                adapter.notifyDataSetChanged();
                editTextNote.setText("");
            }
        });
    }
}