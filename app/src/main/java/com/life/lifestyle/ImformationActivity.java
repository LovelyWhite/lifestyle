package com.life.lifestyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.life.lifestyle.Data.DataUtli;
import com.life.lifestyle.Data.StaticData;
import com.life.lifestyle.Data.User;

public class ImformationActivity extends AppCompatActivity {

    EditText edit_name,edit_tel,edit_adress,edit_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imformation);
        edit_name= findViewById(R.id.edit_name);
        edit_tel= findViewById(R.id.edit_tel);
        edit_adress= findViewById(R.id.edit_adress);
        edit_date= findViewById(R.id.edit_date);
        edit_name.setText(StaticData.getUser().getUsername());
        edit_tel.setText(StaticData.getUser().getUserphone());
        edit_date.setText(StaticData.getUser().getBirthDate());
        edit_adress.setText(StaticData.getUser().getUseraddress());
    }
    public void commit(View view)
    {
        User user = new User();
        user.setUsername(edit_name.getText().toString());
        user.setUserphone(edit_tel.getText().toString());
        user.setBirthDate(edit_date.getText().toString());
        user.setUseraddress(edit_adress.getText().toString());
        if(DataUtli.updateUsers(user))
        {
            StaticData.setUsers(user);
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show();
        }
    }
}
