package com.life.lifestyle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.life.lifestyle.Data.BillAdapter;
import com.life.lifestyle.Data.Bill;
import com.life.lifestyle.Data.DataUtli;
import com.life.lifestyle.Data.StaticData;
import com.life.lifestyle.Data.Tuple;

import java.util.Vector;

public class billFrag extends Fragment {
    ListView listView;
    Vector<Bill> vector;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view =inflater.inflate(R.layout.billfrag,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.billList);
        if(StaticData.isLogin())
        {
            vector = DataUtli.getBill(new Tuple("UserID",StaticData.getUser().getUserID()));
            final BillAdapter adapter =  new BillAdapter(view.getContext(),vector);
            listView.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(view.getContext(),"请先登陆",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(StaticData.isLogin()) {
             vector = DataUtli.getBill(new Tuple("UserID", StaticData.getUser().getUserID()));
        }
    }
}