package bbitb.com.donateplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Daniel Thuo on 15/11/2017.
 */

public class HomeDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeprofile);

        //1
        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");

        //2
        setTitle(title);

        //3
        //mWebView = (WebView) findViewById(R.id.detail_web_view);

        //4
        //mWebView.loadUrl(url);

    }



}
