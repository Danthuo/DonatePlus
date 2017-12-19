package bbitb.com.donateplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonateForm extends AppCompatActivity {

    private static final String MAKEDONATION = "https://avrajsingh.000webhostapp.com/donateplus/makedonation_android.php";
    private static final String HOMES = "https://avrajsingh.000webhostapp.com/donateplus/homeslist_form_android.php";
    private Spinner txt_Form_Home;
    private Spinner txt_Form_Type;
    private EditText txt_Form_Amount;
    private TextView txt_Form_DropOff;
    private Button btn_Form_Cancel;
    private Button btn_Form_MD;
    private String homeSelected;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    List<String> homesList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_form);

        progressDialog = new ProgressDialog(this);

        String title = this.getIntent().getExtras().getString("title");


        txt_Form_Home = (Spinner) findViewById(R.id.txt_Form_Home);
        txt_Form_Type = (Spinner) findViewById(R.id.txt_Form_Type);
        txt_Form_Amount = (EditText) findViewById(R.id.txt_Form_Amount);
        txt_Form_DropOff = (TextView) findViewById(R.id.txt_Form_DropOff);

        txt_Form_DropOff.setText(title);
        getHomes();
        styleTypes();

        addListeners();
    }

    public void getHomes(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HOMES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray JSONhomes = new JSONArray(response);

                            for (int i = 0; i < JSONhomes.length(); i++) {
                                JSONObject homeOBJ = JSONhomes.getJSONObject(i);
                                homesList.add(homeOBJ.getString("profileName"));
                            }

                            txt_Form_Home.setAdapter(styleHomes(homesList));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void makeDonation()
    {
        final String home = String.valueOf(txt_Form_Home.getSelectedItem());
        final String type = String.valueOf(txt_Form_Type.getSelectedItem());
        final String amount = txt_Form_Amount.getText().toString().trim();
        final String dropOff = txt_Form_DropOff.getText().toString().trim();
        final String userEmail = getUrl();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAKEDONATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userEmail", userEmail);
                params.put("homeName", home);
                params.put("donationType", type);
                params.put("donationAmount", amount);
                params.put("dropOff", dropOff);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void styleTypes(){
        txt_Form_Type.setPrompt("Select a Type of Donation");
        ArrayAdapter adapter_types = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.spinner_item);
        adapter_types.setDropDownViewResource(R.layout.spinner_dropdown_item);
        txt_Form_Type.setAdapter(adapter_types);
    }

    public ArrayAdapter styleHomes(List list){
        ArrayAdapter<String> adapter_homes = new ArrayAdapter<>(this, R.layout.spinner_item, list);
        adapter_homes.setDropDownViewResource(R.layout.spinner_dropdown_item);
        return adapter_homes;
    }

    public void addListeners(){
        btn_Form_Cancel = (Button) findViewById(R.id.btn_Form_Cancel);
        btn_Form_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        btn_Form_MD = (Button) findViewById(R.id.btn_Form_MD);
        btn_Form_MD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Making Donation ...");
                progressDialog.show();
                makeDonation();
                Toast.makeText(DonateForm.this, "Donated Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    public String getUrl() {
        firebaseAuth = FirebaseAuth.getInstance();
        String userURL = "";

        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity here if user is not logged in
            startActivity(new Intent(getApplicationContext(), Login.class));
        }else{
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String displayEmail = user.getEmail();
            userURL = displayEmail;
        }
        return userURL;
    }
}
