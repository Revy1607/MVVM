package com.example.mvvm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm.Note.adapter.NoteAdapter;
import com.example.mvvm.Note.model.Note;
import com.example.mvvm.Note.viewmodel.NoteViewModel;
import com.example.mvvm.Note.activities.AddNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;

    ActivityResultLauncher<Intent> activityResultLauncher;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.add_note);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            // Khi kết quả được trả về từ Activity khác, hàm onActivityResult sẽ được gọi
            @Override
            public void onActivityResult(ActivityResult result) {
                // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng, RESULT_OK chỉ ra rằng kết quả này đã thành công
                //if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK)
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    // Nhận dữ liệu từ Intent trả về
                    String title = result.getData().getStringExtra(AddNoteActivity.EXTRA_TITLE);
                    String des = result.getData().getStringExtra(AddNoteActivity.EXTRA_DES);
                    int priority = result.getData().getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

                    Note note = new Note(title,des,priority);
                    noteViewModel.insert(note);

                    Toast.makeText(MainActivity.this, "Note đã lưu", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Note chưa lưu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//                startActivityForResult(intent, ADD_NOTE_REQUEST);
                activityResultLauncher.launch(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.note_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

//        new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//                return 0;
//            }
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//
//            }
//        })
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng, RESULT_OK chỉ ra rằng kết quả này đã thành công
//        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
//            // Nhận dữ liệu từ Intent trả về
//            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
//            String des = data.getStringExtra(AddNoteActivity.EXTRA_DES);
//            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);
//
//            Note note = new Note(title,des,priority);
//            noteViewModel.insert(note);
//
//            Toast.makeText(this, "Note đã lưu", Toast.LENGTH_SHORT).show();
//        } else{
//            Toast.makeText(this, "Note chưa lưu", Toast.LENGTH_SHORT).show();
//        }
//    }
}