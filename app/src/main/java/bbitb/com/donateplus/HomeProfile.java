package bbitb.com.donateplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Daniel Thuo on 16/11/2017.
 */

public class HomeProfile extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeprofile);

        TextView profileEmail = (TextView) findViewById(R.id.profileEmail);
        TextView profilePhone = (TextView) findViewById(R.id.profilePhone);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        String email = this.getIntent().getExtras().getString("email");
        String phone = this.getIntent().getExtras().getString("phone");


        setTitle(title);
        profileEmail.setText(email);
        profilePhone.setText(phone);

    }
}
