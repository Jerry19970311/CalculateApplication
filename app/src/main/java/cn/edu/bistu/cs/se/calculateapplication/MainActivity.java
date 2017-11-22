package cn.edu.bistu.cs.se.calculateapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> precal=new ArrayList<String>();//准备队列：输入阶段时对其进行操作，便于输入或者撤销。
    private Queue<String> cal;//运算队列：参与运算时使用的队列，在输入准备阶段不直接对其操作，待准备队列需要运算时再创建该队列的实例送入运算程序。
    private StringBuffer now=new StringBuffer();
    private boolean haveFinished=false;
    private EditText editText;
    private Editable editable;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("precal",precal);
        outState.putSerializable("now",now);
        outState.putBoolean("haveFinished",haveFinished);
        outState.putString("result",editable.toString());
        Log.v("tag","23---------------------------------------------------------");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        editText=(EditText)findViewById(R.id.Result);
        editable=editText.getText();
        precal=(ArrayList<String>)savedInstanceState.getSerializable("precal");
        now=(StringBuffer)savedInstanceState.getSerializable("now");
        haveFinished=savedInstanceState.getBoolean("haveFinished");
        editable.insert(editText.getSelectionStart(),savedInstanceState.getString("result"));
        Log.v("tag","24---------------------------------------------------------"+editable.toString());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        /*outPersistentState.putStringArray("precal",(String[])precal.toArray());
        outPersistentState.putString("now",now.toString());
        outPersistentState.putBoolean("haveFinished",haveFinished);*/
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        /*String[] tempPrecal=persistentState.getStringArray("precal");
        precal=new ArrayList<String>();
        for(int i=0;i<tempPrecal.length;i++){
            precal.add(tempPrecal[i]);
        }
        now=new StringBuffer(persistentState.getString("now"));
        haveFinished=persistentState.getBoolean("haveFinished");*/
        Log.v("edittest",editable.toString());
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Memory:
                Log.v("tag","11");
                Intent intent1=new Intent(MainActivity.this,MemoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.baseturn:
                Intent intent2=new Intent(MainActivity.this,BaseActivity.class);
                startActivity(intent2);
                break;
            case R.id.pi:
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    //cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append(String.valueOf(Math.PI));
                editable.insert(editText.getSelectionStart(),"π");
                break;
            case R.id.ee:
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    //cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append(String.valueOf(Math.E));
                editable.insert(editText.getSelectionStart(),"e");
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.Result);
        editable=editText.getText();
        editText.setEnabled(false);
        editText.setTextColor(Color.rgb(0,0,0));
        Button add=(Button)findViewById(R.id.add);
        Button minus=(Button)findViewById(R.id.minus);
        Button muliiply=(Button)findViewById(R.id.multiply);
        Button divide=(Button)findViewById(R.id.divide);
        Button one=(Button)findViewById(R.id.one);
        Button two=(Button)findViewById(R.id.two);
        Button three=(Button)findViewById(R.id.three);
        Button four=(Button)findViewById(R.id.four);
        Button five=(Button)findViewById(R.id.five);
        Button six=(Button)findViewById(R.id.six);
        Button seven=(Button)findViewById(R.id.seven);
        Button eight=(Button)findViewById(R.id.eight);
        Button nine=(Button)findViewById(R.id.nine);
        Button zero=(Button)findViewById(R.id.zero);
        Button getResult=(Button)findViewById(R.id.getResult);
        Button cancel=(Button)findViewById(R.id.Cancel);
        Button back=(Button)findViewById(R.id.Back);
        Button madd=(Button)findViewById(R.id.maddButton);
        Button mread=(Button)findViewById(R.id.mrButton);
        Button mclear=(Button)findViewById(R.id.mcButton);
        Button point=(Button)findViewById(R.id.point);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("1");
                //editable.insert(editText.getSelectionStart(),"1");
                editable.append("1");
                /*String temp=editable.toString()+"1";
                editText.setText(temp);*/
                Log.d("test:",editText.getSelectionStart()+"Cross--------------------------------------------------------------------------------------------------------"+editText.getSelectionEnd());
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("2");
                editable.insert(editText.getSelectionStart(),"2");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("3");
                editable.insert(editText.getSelectionStart(),"3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("4");
                editable.insert(editText.getSelectionStart(),"4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("5");
                editable.insert(editText.getSelectionStart(),"5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("6");
                editable.insert(editText.getSelectionStart(),"6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("7");
                editable.insert(editText.getSelectionStart(),"7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("8");
                editable.insert(editText.getSelectionStart(),"8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("9");
                editable.insert(editText.getSelectionStart(),"9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                if(!editable.toString().equals("0")){
                    now.append("0");
                    editable.insert(editText.getSelectionStart(),"0");
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    Log.v("test","1111111111111111111111111111111111111111111111111");
                    editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                    //cal.clear();
                    precal.clear();
                    //precal.add(now.toString());
                    haveFinished=false;
                }
                if(!now.toString().isEmpty()) {
                    Log.v("test","2222222222222222222222222222222222222222222222222");
                    precal.add(now.toString());
                }
                Log.v("test","3333333333333333333333333333333333333333333333333");
                now=new StringBuffer();
                precal.add("+");
                editable.insert(editText.getSelectionStart(),"+");
                //editable.append("+");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                    //cal.clear();
                    precal.clear();
                    haveFinished=false;
                }
                if(!now.toString().isEmpty()) {
                    precal.add(now.toString());
                }
                now=new StringBuffer();
                precal.add("-");
                editable.insert(editText.getSelectionStart(),"-");
            }
        });
        muliiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                    //cal.clear();
                    precal.clear();
                    haveFinished=false;
                }
                if(!now.toString().isEmpty()) {
                    precal.add(now.toString());
                }
                now=new StringBuffer();
                precal.add("*");
                editable.insert(editText.getSelectionStart(),"*");
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                    //cal.clear();
                    precal.clear();
                    haveFinished=false;
                }
                if(!now.toString().isEmpty()) {
                    precal.add(now.toString());
                }
                now=new StringBuffer();
                precal.add("/");
                editable.insert(editText.getSelectionStart(),"/");
            }
        });
        getResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //由于运算结果不能重复运算，因此在haveFinished置否之前按下等号无需做任何相应
                if(haveFinished){
                    return;
                }
                if(!now.toString().isEmpty()){
                    precal.add(now.toString());
                }
                Log.v("BeforeQueue",precal.toString());
                try {
                    cal=new LinkedBlockingQueue<String>(precal);
                    double result=BaseCalculate.count2(cal);
                    String res=String.valueOf(result);
                    if(res.endsWith(".0")){
                        res=cutZero(res);
                    }
                    editable.insert(editText.getSelectionStart(),"\n"+res);
                    now=new StringBuffer(res);
                } catch (IllegalInputException e) {
                    Log.e("Exception",e.toString());
                }finally{
                    Log.v("LastQueue", precal.toString());
                    haveFinished = true;
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                }
                editable.clear();
                precal.clear();
                //cal.clear();
                now=new StringBuffer();
                haveFinished=false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("?????","------------------------------");
                if(now.toString().isEmpty()){
                    try {
                        String temp = precal.remove(precal.size() - 1);
                        if (temp == null) {
                            return;
                        }
                        if (BaseCalculate.ALL.contains(temp)) {
                            int i = editText.getSelectionStart();
                            editable.delete(i - temp.length(), i);
                            return;
                        }
                        now.append(temp);
                    }catch (ArrayIndexOutOfBoundsException e){
                    }catch (StringIndexOutOfBoundsException e){
                    }
                }
                try {
                    now = now.deleteCharAt(now.length() - 1);
                    Log.v("afterbacknow", now.toString());
                    int index = editText.getSelectionStart();
                    editable.delete(index - 1, index);
                }catch (StringIndexOutOfBoundsException e){
                }
            }
        });
        madd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                    //cal.clear();
                    precal.clear();
                    haveFinished=false;
                }
                //如果队列为空，则查看字符串缓冲区是否有非运算符的字符串，没有，则可以直接退出此方法。
                if(precal.isEmpty()){
                    if(now.toString().isEmpty()||BaseCalculate.ALL.contains(now.toString())){
                        return;
                    }
                    try {
                        saveNumber(now.toString());
                        editable.insert(editText.getSelectionStart(),"\nM="+now.toString()+"\n");
                        haveFinished=true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    //如果队列非空，则先进行常规运算后再存储。
                    if(!now.toString().isEmpty()){
                        precal.add(now.toString());
                        haveFinished = true;
                    }
                    Log.v("BeforeQueue",precal.toString());
                    try {
                        cal=new LinkedBlockingQueue<String>(precal);
                        double result=BaseCalculate.count2(cal);
                        String res=String.valueOf(result);
                        if(res.endsWith(".0")){
                            res=cutZero(res);
                        }
                        editable.insert(editText.getSelectionStart(),"\nM="+res+"\n");
                        now=new StringBuffer(res);
                    } catch (IllegalInputException e) {
                        Log.e("Exception",e.toString());
                    }finally{
                        Log.v("LastQueue", precal.toString());
                        try {
                            saveNumber(now.toString());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        haveFinished = true;
                    }
                }
            }
        });
        mread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editable.insert(editText.getSelectionStart(),"\n"+getNumber());
                now=new StringBuffer(getNumber());
            }
        });
        mclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveNumber("0");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveFinished){
                    save(editable.toString()+"\n");
                    editable.clear();
                    cal.clear();
                    precal.clear();
                    now=new StringBuffer();
                    haveFinished=false;
                }
                now.append("8");
                editable.insert(editText.getSelectionStart(),".");
            }
        });
        Configuration configuration=this.getResources().getConfiguration();
        if(configuration.orientation==configuration.ORIENTATION_LANDSCAPE){
            Log.v("tag","26---------------------------------------------------------");
            Button sin=(Button)findViewById(R.id.sin);
            sin.setOnClickListener(new SingleCalculate("sin"));
            Button cos=(Button)findViewById(R.id.cos);
            cos.setOnClickListener(new SingleCalculate("cos"));
            Button tan=(Button)findViewById(R.id.tan);
            tan.setOnClickListener(new SingleCalculate("tan"));
            Button lg=(Button)findViewById(R.id.lg);
            lg.setOnClickListener(new SingleCalculate("lg"));
            Button ln=(Button)findViewById(R.id.ln);
            ln.setOnClickListener(new SingleCalculate("ln"));
            Button left=(Button)findViewById(R.id.left);
            left.setOnClickListener(new SingleCalculate("("));
            Button right=(Button)findViewById(R.id.right);
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(haveFinished){
                        editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                        //cal.clear();
                        precal.clear();
                        haveFinished=false;
                    }
                    if(!now.toString().isEmpty()) {
                        precal.add(now.toString());
                    }
                    now=new StringBuffer();
                    precal.add(")");
                    editable.insert(editText.getSelectionStart(),")");
                }
            });
            Button pow=(Button)findViewById(R.id.pow);
            pow.setOnClickListener(new HighCalculate("^"));
            Button sqrt=(Button)findViewById(R.id.sqrt);
            sqrt.setOnClickListener(
                    new HighCalculate("√")/*new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(haveFinished){
                        editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                        //cal.clear();
                        precal.clear();
                        haveFinished=false;
                    }
                    if(!now.toString().isEmpty()) {
                        precal.add(now.toString());
                        if(BaseCalculate.ALL.contains(precal.get(precal.size()))){
                            Log.v("test","0000000000000000000000000000000000000000000000000000000000000");
                            precal.add("2");
                        }
                    }
                    if(BaseCalculate.ALL.contains(now.toString())){
                        Log.v("test",now.toString());
                        editable.insert(editText.getSelectionStart(),"2");
                    }
                    now=new StringBuffer();
                    precal.add("√");
                    editable.insert(editText.getSelectionStart(),"√");
                }
            }*/);
        }
    }
    public String cutZero(String t){
        return t.replace(".0","");
    }
    public void saveNumber(String number) throws FileNotFoundException {
        /*FileOutputStream out=null;
        BufferedWriter writer=null;
        out=openFileOutput("SaveNumber", Context.MODE_PRIVATE);
        writer=new BufferedWriter(new OutputStreamWriter(out));
        writer.write();*/
        SharedPreferences.Editor editor=getSharedPreferences("SaveNumber",MODE_PRIVATE).edit();
        editor.putString("number",number);
        editor.apply();
    }
    public String getNumber(){
        SharedPreferences sharedPreferences=getSharedPreferences("SaveNumber",MODE_PRIVATE);
        return sharedPreferences.getString("number","0");
    }
    public void save(String data){
        Log.v("tag","0");
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("memory2",MODE_APPEND);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            //writer.write(data+"\n");
            String[] temp=data.split("\n");
            for(int i=0;i<temp.length;i++){
                writer.write(temp[i]);
                writer.newLine();
                writer.flush();
            }
            Log.v("tag","1");
        } catch (FileNotFoundException e) {
            Log.v("tag","4");
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("tag","5");
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                Log.v("tag","2");
            } catch (IOException e) {
                Log.v("tag","3");
                e.printStackTrace();
            }
        }
    }
    public class SingleCalculate implements View.OnClickListener{
        String sign;
        public SingleCalculate(String sign){
            this.sign=sign;
        }
        @Override
        public void onClick(View view) {
            if(haveFinished){
                editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                //cal.clear();
                precal.clear();
                haveFinished=false;
            }
            if(!now.toString().isEmpty()) {
                precal.add(now.toString());
                if(!BaseCalculate.ALL.contains(now.toString())){
                    precal.add("*");
                }
            }
            if(!BaseCalculate.ALL.contains(now.toString())){
                editable.insert(editText.getSelectionStart(),"*");
            }
            now=new StringBuffer();
            precal.add(sign);
            editable.insert(editText.getSelectionStart(),sign);
        }
    }
    public class HighCalculate implements View.OnClickListener{
        String sign;
        public HighCalculate(String sign){
            this.sign=sign;
        }
        @Override
        public void onClick(View view) {
            if(haveFinished){
                editable.insert(editText.getSelectionStart(),"\n"+now.toString());
                //cal.clear();
                precal.clear();
                haveFinished=false;
            }
            if(!now.toString().isEmpty()) {
                precal.add(now.toString());
            }
            now=new StringBuffer();
            precal.add(sign);
            editable.insert(editText.getSelectionStart(),sign);
        }
    }
}
