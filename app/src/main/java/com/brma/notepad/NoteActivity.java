package com.brma.notepad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static final String NOTE_POSITION = "com.brma.notepad.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private boolean mIsNewNote;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private Spinner mSpinnerCourses;
    private int mNotePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get a reference to our spinner for code interaction with it
        mSpinnerCourses = findViewById(R.id.spinner_courses);

        List <CourseInfo> courses = DataManager.getInstance().getCourses(); //This how to get the List of courses

        //creating an adapter that will associate that list with the spinner, in this case ArrayAdapter
        ArrayAdapter <CourseInfo> adapterCourses = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courses);

        //gives the dropdown list of courses from the arrow
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(adapterCourses); //associating the adapter to the spinner

        readDisplayStateValues ();

        mTextNoteTitle = findViewById(R.id.text_note_title);
        mTextNoteText = findViewById(R.id.text_note_text);

        if (!mIsNewNote)
                displayNote (mSpinnerCourses, mTextNoteTitle, mTextNoteText);

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNote ();
    }

    private void saveNote() {
        mNote.setCourse((CourseInfo) mSpinnerCourses.getSelectedItem());
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());
    }

    private void displayNote(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List <CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        int position = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mIsNewNote = position == POSITION_NOT_SET;
        if (mIsNewNote) {
            createNewNote ();
        } else {
            mNote = DataManager.getInstance().getNotes().get(position);
        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        mNote = dm.getNotes().get(mNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail ();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String text = "Checkout what I learned in the Pluralsight course \"" + course.getTitle() +
                "\"\n" + mTextNoteText.getText();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);

    }
}
