package bbitb.com.donateplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
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
        TextView aboutUs = (TextView) findViewById(R.id.about_us);
        ImageButton imgLocation = (ImageButton) findViewById(R.id.imgLocation);

        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        String email = this.getIntent().getExtras().getString("email");
        String phone = this.getIntent().getExtras().getString("phone");
        String description = this.getIntent().getExtras().getString("description");
        final Double longitude = this.getIntent().getExtras().getDouble("longitude");
        final Double latitude = this.getIntent().getExtras().getDouble("latitude");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);

        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomeMap.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
            }
        });


        setTitle(title);
        profileEmail.setText(email);
        profilePhone.setText(phone);
        aboutUs.setText(description);

    }
}
