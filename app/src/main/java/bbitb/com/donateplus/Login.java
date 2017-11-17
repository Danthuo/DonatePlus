package bbitb.com.donateplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button sign_in = findViewById(R.id.Login_sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_mainmenu = new Intent(Login.this, MainActivity.class);
                startActivity(go_to_mainmenu);
            }
        });

        Button sign_up = findViewById(R.id.Login_sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_to_sign_up = new Intent(Login.this, sign_up.class);
                startActivity(go_to_sign_up);
            }
        });
    }

}
