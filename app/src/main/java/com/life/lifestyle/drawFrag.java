package com.life.lifestyle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class drawFrag extends Fragment {
    private PaletteView mPaletteView;
    private Button mErazer;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view =inflater.inflate(R.layout.drawfrag,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPaletteView = (PaletteView)view.findViewById(R.id.draw);
        mErazer = (Button) view.findViewById(R.id.erazer);
        super.onViewCreated(view, savedInstanceState);
    }
    public void undo()
    {
        mPaletteView.undo();
    }
    public void redo()
    {
        mPaletteView.redo();
    }
    public void clear()
    {
        mPaletteView.clear();
    }
    public void save() {

        Bitmap bitmap = mPaletteView.buildBitmap();
        FileOutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory()+"/pic/", UUID.randomUUID().toString() + ".png");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this.getContext(), "图片已保存"+ Environment.getExternalStorageDirectory() + "/pic下", Toast.LENGTH_SHORT).show();
    }
    public void erazer() {
        if (mPaletteView.getMode() == PaletteView.Mode.DRAW) {
            mErazer.setText("Draw");
            mPaletteView.setMode(PaletteView.Mode.ERASER);
        } else {
            mErazer.setText("Erazer");
            mPaletteView.setMode(PaletteView.Mode.DRAW);
        }
    }
}
