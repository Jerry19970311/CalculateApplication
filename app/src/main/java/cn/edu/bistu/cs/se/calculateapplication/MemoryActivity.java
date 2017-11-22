package cn.edu.bistu.cs.se.calculateapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by XZL on 2017/10/25.
 */

public class MemoryActivity extends AppCompatActivity{
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("tag","25---------------------------------------------------------");
        setContentView(R.layout.activity_memory);
        final TextView textView=(TextView)findViewById(R.id.memoryText);
        textView.setText(load());
        Button back=(Button)findViewById(R.id.MemoryBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button clear=(Button)findViewById(R.id.MemoryClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FileProcess.writeStringContentToFile("memory2","蛤");
                try {
                    FileOutputStream fileOutputStream=openFileOutput("memory2",MODE_PRIVATE);
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    bufferedWriter.write("");
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView.setText("");
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.v("tag","23---------------------------------------------------------");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.v("tag","24---------------------------------------------------------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.v("tag","22---------------------------------------------------------");
        Configuration configuration=this.getResources().getConfiguration();
        if(configuration.orientation==configuration.ORIENTATION_LANDSCAPE){
            Log.v("tag","26---------------------------------------------------------");
        }
        if(configuration.orientation==configuration.ORIENTATION_PORTRAIT){
            Log.v("tag","27---------------------------------------------------------");
        }
        /*setContentView(R.layout.activity_memory);
        final TextView textView=(TextView)findViewById(R.id.memoryText);
        textView.setText(load());
        Button back=(Button)findViewById(R.id.MemoryBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button clear=(Button)findViewById(R.id.MemoryClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FileProcess.writeStringContentToFile("memory2","蛤");
                try {
                    FileOutputStream fileOutputStream=openFileOutput("memory2",MODE_PRIVATE);
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    bufferedWriter.write("");
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textView.setText("");
            }
        });*/
    }
    public String load(){
        Log.v("tag","21");
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("memory2");
            reader=new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line=reader.readLine())!=null){
                content.append(line+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
