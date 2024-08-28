package com.example.studywithme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studywithme.fragment.BoardFragment;
import com.example.studywithme.fragment.CalendarFragment;


public class MainActivity_memo extends AppCompatActivity {
    MemoDB helper;
    SQLiteDatabase db;
    MyAdapter adapter;
    Cursor cursor;
    ListView list;
    String userEmail;
    String selectedDate;
    ImageView iv_back;
    public static final int REQUEST_UPDATE = 1;  // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memo);

        helper = new MemoDB(this);
        db = helper.getReadableDatabase();

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "");

        String sql = "select * from memo where user_email = ? order by wdate desc";
        Log.d("MainActivity_memo", "SQL: " + sql);
        Log.d("MainActivity_memo", "User email: " + userEmail);
        cursor = db.rawQuery(sql,new String[] {userEmail});

        cursor = db.rawQuery("select * from memo where user_email = ? order by wdate desc",new String[] {userEmail});
        list = findViewById(R.id.list);
        adapter = new MyAdapter(this, cursor);
        list.setAdapter(adapter);

        Log.d("MainActivity_memo", "Cursor count: " + cursor.getCount());
        iv_back = findViewById(R.id.iv_back);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_memo.this, CalendarFragment.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
                finish(); // 현재 액티비티 없애기
            }
        });

    }

    class MyAdapter extends CursorAdapter {
        public MyAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return getLayoutInflater().inflate(R.layout.item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, final Cursor cursor) {
            TextView txtcontent = view.findViewById(R.id.txtcontent);
            txtcontent.setText(cursor.getString(1));
            TextView txtwdate = view.findViewById(R.id.txtwdate);
            txtwdate.setText(cursor.getString(2));

            TextView btndel = view.findViewById(R.id.btndel);
            final String user_email = cursor.getString(3);  // Assuming that the 4th column of the memo table stores the user email
            final int _id = cursor.getInt(0);
            Log.d("MyAdapter", "Content: " + cursor.getString(1) + ", Date: " + cursor.getString(2));
            //삭제버튼
            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user_email != null && user_email.equals(userEmail)) {
                        AlertDialog.Builder box = new AlertDialog.Builder(MainActivity_memo.this);
                        box.setMessage("해당 글을 삭제하시겠습니까?");
                        box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String sql = "delete from memo where _id=" + _id;
                                db.execSQL(sql);

                                onRestart();
                            }
                        });
                        box.setNegativeButton("닫기", null);
                        box.show();
                    }
                    else {
                        Toast.makeText(MainActivity_memo.this, "You can only delete your own memos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //수정하기버튼클릭시
            RelativeLayout btnupdate = view.findViewById(R.id.btnupdate);
            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user_email != null && user_email.equals(userEmail)) {
                        Intent intent = new Intent(MainActivity_memo.this, UpdateActivity.class);
                        intent.putExtra("_id", _id);
                        startActivityForResult(intent, REQUEST_UPDATE);  // Update this line

                    } else {
                        Toast.makeText(MainActivity_memo.this, "You can only edit your own memos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            cursor = db.rawQuery("select * from memo where user_email = ? order by wdate desc", new String[] {userEmail});
            adapter.changeCursor(cursor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor = db.rawQuery("select * from memo where user_email = ? order by wdate desc", new String[] {userEmail});
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onRestart() {

        String sql = "select * from memo where user_email = ? order by wdate desc";
        Log.d("MainActivity_memo", "SQL: " + sql);
        Log.d("MainActivity_memo", "User email: " + userEmail);
        cursor = db.rawQuery(sql,new String[] {userEmail});

        cursor=db.rawQuery("select * from memo where user_email = ? order by wdate desc",new String[] {userEmail});
        adapter.changeCursor(cursor);
        super.onRestart();
    }

}