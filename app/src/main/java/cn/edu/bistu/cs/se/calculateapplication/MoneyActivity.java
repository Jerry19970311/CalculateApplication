package cn.edu.bistu.cs.se.calculateapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by XZL on 2017/11/25.
 */

public class MoneyActivity extends AppCompatActivity{
    private String from="";
    private String goal="";
    private Money money;
    private double rate=0;
    private String update="";
    private TextView fromTe;
    private TextView goalTe;
    private TextView updateTe;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            String s="http://api.k780.com/?app=finance.rate&scur="+from+"&tcur="+goal+"&appkey=30007&sign=7f84287e09d63096e463e66acfe8a917&format=json";
            try {
                URL url=new URL(s);
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String jsonS="";
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    jsonS = jsonS + temp;
                }
                bufferedReader.close();
                Gson gson=new Gson();
                money=gson.fromJson(jsonS,Money.class);
                rate=Double.parseDouble(money.getResult().getRate());
                update=money.getResult().getUpdate();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                rate=0;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_money);
        Button fromButton=(Button)findViewById(R.id.from_money);
        fromButton.setOnClickListener(new fgListener(R.id.from_money));
        Button goalButton=(Button)findViewById(R.id.sign_money);
        goalButton.setOnClickListener(new fgListener(R.id.sign_money));
        final EditText input=(EditText)findViewById(R.id.from_input);
        final TextView output=(TextView)findViewById(R.id.goal_output);
        fromTe=(TextView)findViewById(R.id.from_result);
        goalTe=(TextView)findViewById(R.id.sign_result);
        updateTe=(TextView)findViewById(R.id.update_output);
        Runnable runnable2=new Runnable() {
            @Override
            public void run() {
                while (true){
                    double f;
                    try {
                        f=Double.parseDouble(input.getText().toString());
                    }catch (NumberFormatException e){
                        f=0d;
                    }
                    double g=f*rate;
                    output.setText(String.valueOf(g));
                    updateTe.setText("当前汇率:"+rate+"\n更新时间:"+update);
                }
            }
        };
        new Thread(runnable2).start();
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*Double f;
                try {
                    f=Double.parseDouble(editable.toString());
                }catch (NumberFormatException e){
                    f=0d;
                }
                Double g=f*rate;
                output.setText(String.valueOf(g));*/
            }
        });
    }
    public class fgListener implements View.OnClickListener{
        private int who;
        public fgListener(int w){
            this.who=w;
        }
        @Override
        public void onClick(View view) {
            final PopupMenu popupMenu=new PopupMenu(MoneyActivity.this,view);
            popupMenu.getMenuInflater().inflate(R.menu.dollar,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    /*MenuView.ItemView itemView=(MenuView.ItemView) .findViewById();
                    sign=itemView.getItemData().getTitle().toString();
                    Log.v("sign-------------------------------------------------------------------",sign);*/
                    //Log.v("sign-------------------------------------------------------------------",menuItem.getTitle().toString());
                    String[] str=menuItem.getTitle().toString().split("[（）]");
                    if(who==R.id.from_money){
                        from=str[1];
                        fromTe.setText(str[1]);
                    }
                    if(who==R.id.sign_money){
                        goal=str[1];
                        goalTe.setText(str[1]);
                    }
                    new Thread(runnable).start();
                    return true;
                }
            });
            popupMenu.show();
        }
    }
}
