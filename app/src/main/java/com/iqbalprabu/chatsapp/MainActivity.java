package com.iqbalprabu.chatsapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    public static final String TAG = "SendMessage";
    private RecyclerView mRecycler;
    private EditText edtMessage;
    private Button btnSend;

    private AppPreference mAppPreference;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Message, ChatViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMessage = (EditText) findViewById(R.id.edt_message);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        mRecycler = (RecyclerView) findViewById(R.id.rv_chat);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mAppPreference = new AppPreference(MainActivity.this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();



    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new FirebaseRecyclerAdapter<Message, ChatViewHolder>(Message.class,
                R.layout.item_row_chat,
                ChatViewHolder.class,
                mDatabaseReference.child("chat")) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, Message model, int position) {
                viewHolder.tvEmail.setText(model.getSender());
                viewHolder.tvMessage.setText(model.getMessage());
            }
        };
        mRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_send) {
            String message = edtMessage.getText().toString();

            if (!message.isEmpty()) {
                Map<String, Object> param = new HashMap<>();
                param.put("sender", mAppPreference.getEmail());
                param.put("message", message);

                mDatabaseReference.child("chat")
                .push()
                .setValue(param)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               edtMessage.setText("");
                                if(task.isSuccessful())
                                {
                                    Log.d(TAG, "Success");
                                }else{
                                    Log.d("SendMessage", "Failed");
                                }
                            }
                        });

            }

        }

    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView tvEmail, tvMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);

            tvEmail = (TextView) itemView.findViewById(R.id.tv_sender);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);

        }
    }
}

