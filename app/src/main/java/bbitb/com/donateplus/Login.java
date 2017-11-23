package bbitb.com.donateplus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //Home list fragment here since user is already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

        progressDialog = new ProgressDialog(this);


        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        editTextEmail =(EditText) findViewById(R.id.Login_email);
        editTextPassword =(EditText) findViewById(R.id.Login_password);

        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);

       buttonLogin.setOnClickListener(this);
       textViewSignUp.setOnClickListener(this);
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        //if validations are ok
        //we show the progress dialog

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //user is successfully registered and logged in
                            // we will start the profile activity here
                            //right no display toast only
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Could not log in. Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            userLogin();
        }

        if(v == textViewSignUp){
            finish();
            startActivity(new Intent(this, SignUp.class));
        }
    }
}
