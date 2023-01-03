package com.example.mvvm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mvvm.R;

public class AddNoteActivity extends AppCompatActivity {
    // Biến constant được dùng để định danh dữ liệu được truyền giữa các Activity
    public static final String EXTRA_TITLE = "com.example.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DES = "com.example.mvvm.EXTRA_DES";
    public static final String EXTRA_PRIORITY = "com.example.mvvm.EXTRA_PRIORITY";

    private EditText edt_title;
    private EditText edt_des;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edt_title = findViewById(R.id.edt_title);
        edt_des = findViewById(R.id.edt_des);
        numberPicker = findViewById(R.id.number_picker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        setTitle("add Note");
    }

    private void saveNote(){
        String title = edt_title.getText().toString();
        String des = edt_des.getText().toString();
        int priority = numberPicker.getValue();

        if(title.trim().isEmpty() || des.trim().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập không để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        //Tạo 1 intent để chứa dữ liệu trả về
        Intent data = new Intent();
        //truyền data vào intent
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DES, des);
        data.putExtra(EXTRA_PRIORITY, priority);

        // Đặt resultCode là RESULT_OK thể hiện đã thành công và có chứa kết quả trả về
        setResult(RESULT_OK, data);
        //gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}