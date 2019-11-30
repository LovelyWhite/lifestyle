package com.life.lifestyle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.life.lifestyle.Data.DataUtli;
import com.life.lifestyle.Data.Schedule;
import com.life.lifestyle.Data.StaticData;
import com.life.lifestyle.Data.Tuple;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

public class calenFrag extends Fragment {
    CalendarView calendarView;
    TextView dateT;
    EditText messageD;
    Button clearD, submitD, updateD;
    ProgressBar progressBar;
    Vector<Schedule> vector = null;
    static Handler handler;
    android.os.Message m;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calenfrag, container, false);
        return view;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView = view.findViewById(R.id.calendarView);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateT = view.findViewById(R.id.dateT);
        messageD = view.findViewById(R.id.messageD);
        clearD = view.findViewById(R.id.clearD);
        submitD = view.findViewById(R.id.submitD);
        progressBar = view.findViewById(R.id.calendarProgress);
        updateD = view.findViewById(R.id.updateD);
        dateT.setText(sdf.format(calendarView.getDate()));
        m = new android.os.Message();
        handler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Toast.makeText(view.getContext(), "清空成功", Toast.LENGTH_SHORT).show();
                        messageD.setText("");
                        break;
                    case -1:
                        Toast.makeText(view.getContext(), "清空失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(view.getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        break;
                    case -2:
                        Toast.makeText(view.getContext(), "更新失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(view.getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                        break;
                    case -3:
                        Toast.makeText(view.getContext(), "发表失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                boolean d = false;
                dateT.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                if (!StaticData.isLogin()) {
                    Toast.makeText(view.getContext(), "请先登陆", Toast.LENGTH_LONG).show();
                } else {
                   if(vector!=null&&vector.size()!=0)
                   {

                       for (Schedule i : vector) {
                           Log.i("a",i.getDate());
                               Log.i("b",year + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",dayOfMonth));

                           if (i.getDate().contains(year + "-" + String.format("%02d",month+1) + "-" + String.format("%02d",dayOfMonth))) {
                               messageD.setText(i.getInformation());
                               d = true;
                               break;
                           }
                       }
                       if (!d) {
                           messageD.setText("");
                       }
                   }
                }
            }
        });
        clearD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        for (Schedule i : vector) {
                            if (i.getDate().equals(dateT.getText().toString())) {
                                android.os.Message m = new android.os.Message();
                                if (DataUtli.removeSchedule(i)) {
                                    vector.remove(i);
                                    m.what = 1;//succ
                                } else
                                    m.what = -1;//fal
                                progressBar.setVisibility(View.INVISIBLE);
                                handler.sendMessage(m);
                                break;
                            }
                        }
                    }
                }.start();
            }
        });
        submitD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        boolean x = false;
                        for (Schedule i : vector) {
                            if (i.getDate().equals(dateT.getText().toString())) {
                                android.os.Message m = new android.os.Message();
                                x = true;
                                i.setInformation(messageD.getText().toString());
                                if (DataUtli.updateSchedule(i))
                                    m.what = 2;
                                else
                                    m.what = -2;
                                handler.sendMessage(m);
                                break;
                            }
                        }
                        if (!x) {
                            Schedule s = new Schedule();
                            s.setInformation(messageD.getText().toString());
                            s.setDate(dateT.getText().toString());
                            s.setUserID(StaticData.getUser().getUserID());
                            s.setScheID(String.valueOf(new Random(System.currentTimeMillis()).nextInt()));
                            android.os.Message m = new android.os.Message();
                            if (DataUtli.addSchedule(s)) {

                                vector.add(s);
                                m.what = 3;
                            } else {
                                m.what = -3;
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            handler.sendMessage(m);
                        }
                    }
                }.start();
            }
        });
        updateD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticData.isLogin()) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread() {
                        @Override
                        public void run() {
                            long  future = System.currentTimeMillis()+2000;///
                            super.run();
                            vector = DataUtli.getSchedule(new Tuple("userID", StaticData.getUser().getUserID()));
                            while (future-System.currentTimeMillis()>0) { }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                }
            }
        });
        updateD.callOnClick();
    }
}
