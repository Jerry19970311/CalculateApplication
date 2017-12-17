package cn.edu.bistu.cs.se.calculateapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by XZL on 2017/10/25.
 */

public class BaseActivity extends AppCompatActivity{
    public static final int BASE2=2;
    public static final int BASE8=8;
    public static final int BASE10=10;
    public static final int BASE16=16;
    private TextView editText;
    private int base=BASE10;
    private long baseNumber=0;
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_base);
        editText=(TextView)findViewById(R.id.bResult);
        editText.setEnabled(false);
        Button base2=(Button)findViewById(R.id.base2);
        base2.setOnClickListener(new BaseListener(BASE2));
        Button base8=(Button)findViewById(R.id.base8);
        base8.setOnClickListener(new BaseListener(BASE8));
        Button base10=(Button)findViewById(R.id.base10);
        base10.setOnClickListener(new BaseListener(BASE10));
        Button base16=(Button)findViewById(R.id.base16);
        base16.setOnClickListener(new BaseListener(BASE16));
        final Button one=(Button)findViewById(R.id.bOne);
        one.setOnClickListener(new NumberListener(1));
        final Button two=(Button)findViewById(R.id.bTwo);
        two.setOnClickListener(new NumberListener(2));
        final Button three=(Button)findViewById(R.id.bThree);
        three.setOnClickListener(new NumberListener(3));
        final Button four=(Button)findViewById(R.id.bFour);
        four.setOnClickListener(new NumberListener(4));
        final Button five=(Button)findViewById(R.id.bFive);
        five.setOnClickListener(new NumberListener(5));
        final Button six=(Button)findViewById(R.id.bSix);
        six.setOnClickListener(new NumberListener(6));
        final Button seven=(Button)findViewById(R.id.bSeven);
        seven.setOnClickListener(new NumberListener(7));
        final Button eight=(Button)findViewById(R.id.bEight);
        eight.setOnClickListener(new NumberListener(8));
        final Button nine=(Button)findViewById(R.id.bNine);
        nine.setOnClickListener(new NumberListener(9));
        final Button zero=(Button)findViewById(R.id.bZero);
        zero.setOnClickListener(new NumberListener(0));
        final Button a=(Button)findViewById(R.id.bA);
        a.setOnClickListener(new NumberListener(10));
        final Button b=(Button)findViewById(R.id.bB);
        b.setOnClickListener(new NumberListener(11));
        final Button c=(Button)findViewById(R.id.bC);
        c.setOnClickListener(new NumberListener(12));
        final Button d=(Button)findViewById(R.id.bD);
        d.setOnClickListener(new NumberListener(13));
        final Button e=(Button)findViewById(R.id.bE);
        e.setOnClickListener(new NumberListener(14));
        final Button f=(Button)findViewById(R.id.bF);
        f.setOnClickListener(new NumberListener(15));
        Button cancel=(Button)findViewById(R.id.bCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button back=(Button)findViewById(R.id.bBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseNumber=baseNumber/base;
            }
        });
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true){
                    switch (base){
                        case BASE16:
                            two.setClickable(true);
                            three.setClickable(true);
                            four.setClickable(true);
                            five.setClickable(true);
                            six.setClickable(true);
                            seven.setClickable(true);
                            eight.setClickable(true);
                            nine.setClickable(true);
                            a.setClickable(true);
                            b.setClickable(true);
                            c.setClickable(true);
                            d.setClickable(true);
                            e.setClickable(true);
                            f.setClickable(true);
                            break;
                        case BASE10:
                            two.setClickable(true);
                            three.setClickable(true);
                            four.setClickable(true);
                            five.setClickable(true);
                            six.setClickable(true);
                            seven.setClickable(true);
                            eight.setClickable(true);
                            nine.setClickable(true);
                            a.setClickable(false);
                            b.setClickable(false);
                            c.setClickable(false);
                            d.setClickable(false);
                            e.setClickable(false);
                            f.setClickable(false);
                            break;
                        case BASE8:
                            two.setClickable(true);
                            three.setClickable(true);
                            four.setClickable(true);
                            five.setClickable(true);
                            six.setClickable(true);
                            seven.setClickable(true);
                            eight.setClickable(false);
                            nine.setClickable(false);
                            a.setClickable(false);
                            b.setClickable(false);
                            c.setClickable(false);
                            d.setClickable(false);
                            e.setClickable(false);
                            f.setClickable(false);
                            break;
                        case BASE2:
                            two.setClickable(false);
                            three.setClickable(false);
                            four.setClickable(false);
                            five.setClickable(false);
                            six.setClickable(false);
                            seven.setClickable(false);
                            eight.setClickable(false);
                            nine.setClickable(false);
                            a.setClickable(false);
                            b.setClickable(false);
                            c.setClickable(false);
                            d.setClickable(false);
                            e.setClickable(false);
                            f.setClickable(false);
                            break;
                        default:
                    }
                }
            }
        };
        new Thread(runnable).start();
        Runnable runnableText=new Runnable() {
            @Override
            public void run() {
                while(true){
                    editText.setText(Long.toString(baseNumber,base).toUpperCase());
                }
            }
        };
        new Thread(runnableText).start();
    }
    public class BaseListener implements View.OnClickListener{
        private int b;
        public BaseListener(int b){
            this.b=b;
        }
        @Override
        public void onClick(View view){
            base=b;
        }
    }
    public class NumberListener implements View.OnClickListener{
        private int number;
        public NumberListener(int n){
            this.number=n;
        }
        @Override
        public void onClick(View view) {
            baseNumber=baseNumber*base+number;
        }
    }
}
