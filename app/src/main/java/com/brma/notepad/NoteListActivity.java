package com.brma.notepad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

//    private ArrayAdapter<NoteInfo> mAdapterNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            //This will send the Intent that shows our NoteActivity
            public void onClick(View v) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent (); //create a method to hold the list view creation code

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mAdapterNotes.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
//         //Inside the ListView add a list of all the notes. Inorder to do that we need a reference
//        // to the ListView. So we'll declare a variable as below:
//        final ListView listNotes = findViewById(R.id.list_notes);
//
//         //Will get the list of notes by declaring a variable of notes of type List with a generic
//        // argument of <NoteInfo>. So we can get those notes and put them in an array adapter
//        List<NoteInfo> notes = DataManager.getInstance().getNotes();
//        mAdapterNotes = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, notes);
//        listNotes.setAdapter(mAdapterNotes); //to associate the adapter with the ListView
//
//        //anonymous class creation and AS putting the relevant body in it.
//        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            //handling list view selection that takes you into NoteActivity
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
////                NoteInfo note = (NoteInfo) listNotes.getItemAtPosition(position);
//                intent.putExtra(NoteActivity.NOTE_POSITION, position);
//                startActivity(intent);
//            }
//        });
        final RecyclerView recyclerNotes = (RecyclerView) findViewById(R.id.list_notes);
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        recyclerNotes.setLayoutManager(notesLayoutManager);

        List <NoteInfo> notes = DataManager.getInstance().getNotes();
        final NoteRecyclerAdapter noteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        recyclerNotes.setAdapter(noteRecyclerAdapter);
    }

}
