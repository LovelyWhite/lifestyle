package com.life.lifestyle;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.life.lifestyle.Data.DataUtli;
import com.life.lifestyle.Data.MessAdapter;
import com.life.lifestyle.Data.Message;
import com.life.lifestyle.Data.StaticData;
import com.life.lifestyle.Data.Tuple;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

public class modFrag extends Fragment {
    ListView listView;
    Button submit ,update;
    EditText textM;
    Handler handler;
    ProgressBar progressBar;
    Vector<Message> vector;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modfrag, container, false);
        return view;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list2);
        final MessAdapter adapter = new MessAdapter(view.getContext(), vector);
        listView.setAdapter(adapter);
        submit = view.findViewById(R.id.submitM);
        textM = view.findViewById(R.id.textM);
        update= view.findViewById(R.id.updateM);
        progressBar= view.findViewById(R.id.modProgress);
        vector = new Vector<Message>();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        Toast.makeText(view.getContext(), "发表成功", Toast.LENGTH_SHORT).show();break;
                    case -1:
                        Toast.makeText(view.getContext(), "发表失败", Toast.LENGTH_SHORT).show();
                    case 3:
                        adapter.notifyDataSetChanged();
                }
            }
        };
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Thread()
               {
                   @Override
                   public void run() {
                       super.run();
                       int ch=0;
                       if(vector==null||vector.size()==0)
                       {
                           ch = 0;
                       }
                       vector = DataUtli.getMessage((Tuple[]) null);
                       if(ch!=vector.size())
                       {
                           android.os.Message  m= new android.os.Message();
                           m.what = 3;
                           handler.sendMessage(m);
                       }
                   }
               }.start();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!StaticData.isLogin()) {
                    Toast.makeText(view.getContext(), "未登录账号", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Message message = new Message();
                            message.setUserId(StaticData.getUser().getUserID());
                            message.setInformation(textM.getText().toString());
                            message.setMessID(String.valueOf(new Random(System.currentTimeMillis()).nextInt()));
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            message.setSendTime(df.format(new java.util.Date()));
                            android.os.Message m = new android.os.Message();
                            if (DataUtli.addMessage(message)) {
                                vector.add(message);
                                m.what = 1;
                            } else {
                                m.what = -1;
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            handler.sendMessage(m);
                        }
                    }.start();
                }
            }
        });
        update.callOnClick();
    }

}