package bbitb.com.donateplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        /*if(getIntent().hasExtra("InfoTitle")){
            Bundle bundle = getIntent().getExtras();
            String info = bundle.getString("InfoTitle");
            if(info.length() > 0){
                Toast.makeText(MainActivity.this,info,Toast.LENGTH_SHORT).show();
            }
        }*/

        Bundle bundle = getIntent().getExtras();
        String info = bundle.getString("InfoTitle");
        if(info.length() > 0){
            Toast.makeText(Test.this,info,Toast.LENGTH_SHORT).show();
        }
    }
}
