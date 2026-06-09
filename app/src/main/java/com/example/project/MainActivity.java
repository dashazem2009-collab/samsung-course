package com.example.project;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "notes_prefs";
    private static final String KEY_NOTES = "notes";

    EditText editTextNote;
    Button buttonAdd;
    ListView listViewNotes;

    ArrayList<String> notes;
    ArrayAdapter<String> adapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.editTextNote);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewNotes = findViewById(R.id.listViewNotes);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Загрузка заметок
        Set<String> savedSet = prefs.getStringSet(KEY_NOTES, null);
        notes = new ArrayList<>();
        if (savedSet != null) {
            notes.addAll(savedSet);
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );
        listViewNotes.setAdapter(adapter);

        listViewNotes.setOnItemClickListener((parent, view, position, id) -> {
            notes.remove(position);
            adapter.notifyDataSetChanged();
            saveNotes();
        });

        buttonAdd.setOnClickListener(v -> {
            String text = editTextNote.getText().toString().trim();

            if (!text.isEmpty()) {
                notes.add(text);
                adapter.notifyDataSetChanged();
                editTextNote.setText("");
                saveNotes();
            }
        });
    }

    private void saveNotes() {
        // Преобразуем в Set для SharedPreferences
        Set<String> set = new HashSet<>(notes);
        prefs.edit().putStringSet(KEY_NOTES, set).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNotes();
    }
}
