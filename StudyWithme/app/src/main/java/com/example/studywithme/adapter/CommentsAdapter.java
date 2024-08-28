package com.example.studywithme.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studywithme.R;
import com.example.studywithme.models.Comments;
import com.example.studywithme.utility.FirebaseID;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<Comments> datas;

    // Alt + Insert Consturctor
    public CommentsAdapter(List<Comments> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments data = datas.get(position);
        holder.comments.setText(data.getComments());
        //holder.nickname.setText(data.getNickname());
        holder.time.setText(data.getTime());
        holder.documentid.setText(data.getDocumentID());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView comments;
        private TextView time;
        //private TextView nickname;
        private TextView documentid;
        private ImageView btn_comments_delete;


        // 댓글 삭제 기능 추가해야함 intent로 documentid 가져와서 if문으로 비교 후 x 표시

        //생성자
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            comments = (TextView) itemView.findViewById(R.id.item_comments_comments);
            time = (TextView) itemView.findViewById(R.id.item_comments_timestamp);
            //nickname = (TextView) itemView.findViewById(R.id.item_comments_nickname);
            documentid = (TextView) itemView.findViewById(R.id.item_comments_documentid);

            Log.d("commentsAdapter", "uid : " + documentid.getText().toString());
            //Toast.makeText(comments.getContext(), "uid : " + documentid.getText().toString(), Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onClick(View view) {

        }
    }
}
