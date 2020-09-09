package com.brma.notepad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initializeDisplayContent (); //create a method to hold the list view creation code
    }

    private void initializeDisplayContent() {
         //Inside the ListView add a list of all the notes. Inorder to do that we need a reference
        // to the ListView. So we'll declare a variable as below:
        ListView listNotes = findViewById(R.id.list_notes);

         //Will get the list of notes by declaring a variable of notes of type List with a generic
        // argument of <NoteInfo>. So we can get those notes and put them in an array adapter
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        ArrayAdapter<NoteInfo> adapterNotes = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);
        listNotes.setAdapter(adapterNotes); //to associate the adapter with the ListView

        //anonymous class creation and AS putting the relevant body in it.
        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //handling list view selection that takes you into NoteActivity
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }

}
