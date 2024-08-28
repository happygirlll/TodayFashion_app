package com.example.studywithme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studywithme.fragment.BoardFragment;
import com.example.studywithme.fragment.CalendarFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class InsertActivity extends AppCompatActivity {
    MemoDB helper;
    SQLiteDatabase db;
    String userEmail;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        final String selectedDate = getIntent().getStringExtra("selectedDate");  // Retrieve the selected date


        helper = new MemoDB(this);
        db = helper.getWritableDatabase();
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");
        iv_back = findViewById(R.id.iv_back);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, CalendarFragment.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
                finish(); // 현재 액티비티 없애기
            }
        });


        FloatingActionButton btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = getIntent().getStringExtra("selectedDate");
                if (selectedDate != null) {
                    selectedDate = selectedDate.replace("년 ", "/").replace("월 ", "/").replace("일 선택", "");
                } else {
                    // handle the case when selectedDate is null
                    // for example, use the current date as a fallback
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                    sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                    selectedDate = sdf.format(new Date());
                }
                selectedDate = selectedDate.replace("년 ", "/").replace("월 ", "/").replace("일 선택", "");
                EditText edtcontent = findViewById(R.id.edtcontent);
                String strcontent = edtcontent.getText().toString();

                String sql = "insert into memo(user_email, content, wdate) values(?,?,?)";
                db.execSQL(sql, new String[] { userEmail, strcontent, selectedDate });

                Toast.makeText(InsertActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InsertActivity.this, CalendarFragment.class);
                startActivity(intent);
                finish();
            }
        });


    }


}