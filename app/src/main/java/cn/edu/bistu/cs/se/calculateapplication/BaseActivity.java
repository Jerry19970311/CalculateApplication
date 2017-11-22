package cn.edu.bistu.cs.se.calculateapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by XZL on 2017/10/25.
 */

public class BaseActivity extends AppCompatActivity{
    public static final int BASE2=2;
    public static final int BASE8=8;
    public static final int BASE10=10;
    public static final int BASE16=16;
    private EditText editText;
    private Editable editable;
    private int base=BASE10;
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_base);
        editText=(EditText)findViewById(R.id.bResult);
        editable=editText.getText();
        editText.setEnabled(false);
        Button base2=(Button)findViewById(R.id.base2);
        Button base8=(Button)findViewById(R.id.base8);
        Button base10=(Button)findViewById(R.id.base10);
        Button base16=(Button)findViewById(R.id.base16);
        Button one=(Button)findViewById(R.id.bOne);
        Button two=(Button)findViewById(R.id.bTwo);
        Button three=(Button)findViewById(R.id.bThree);
        Button four=(Button)findViewById(R.id.bFour);
        Button five=(Button)findViewById(R.id.bFive);
        Button six=(Button)findViewById(R.id.bSix);
        Button seven=(Button)findViewById(R.id.bSeven);
        Button eight=(Button)findViewById(R.id.bEight);
        Button nine=(Button)findViewById(R.id.bNine);
        Button zero=(Button)findViewById(R.id.bZero);
        Button a=(Button)findViewById(R.id.bA);
        Button b=(Button)findViewById(R.id.bB);
        Button c=(Button)findViewById(R.id.bC);
        Button d=(Button)findViewById(R.id.bD);
        Button e=(Button)findViewById(R.id.bE);
        Button f=(Button)findViewById(R.id.bF);
    }
    public class BaseListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
        }
    }
}
