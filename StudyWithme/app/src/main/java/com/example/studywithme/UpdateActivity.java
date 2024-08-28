package com.example.studywithme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studywithme.fragment.CalendarFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class UpdateActivity extends AppCompatActivity {
    int _id;
    MemoDB helper;
    SQLiteDatabase db;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent=getIntent();
        _id=intent.getIntExtra("_id",0);

        helper=new MemoDB(this);
        db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from memo where _id="+_id, null);
        if(cursor.moveToNext()){
            TextView txtwdate=findViewById(R.id.txtwdate);
            txtwdate.setText("작성일 : "+cursor.getString(2));
            TextView edtcontent=findViewById(R.id.edtcontent);
            edtcontent.setText(cursor.getString(1));
        }

        iv_back = findViewById(R.id.iv_back);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity_memo.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
                finish(); // 현재 액티비티 없애기
            }
        });

        FloatingActionButton btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(UpdateActivity.this);
                box.setMessage("수정하시겠습니까?");
                box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edtcontent = findViewById(R.id.edtcontent);
                        String strcontent = edtcontent.getText().toString();
                        String sql = "update memo set content=? where _id=?";
                        db.execSQL(sql, new String[] { strcontent, String.valueOf(_id) });

                        setResult(RESULT_OK);
                        finish();
                    }
                });
                box.setNegativeButton("닫기", null);
                box.show();
            }
        });
    }


}
