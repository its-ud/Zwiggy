package com.example.zwiggy.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.Data.setUserId;
import com.example.zwiggy.R;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.auth.GoogleAuthType;

public class LoginActivity extends AppCompatActivity {

    EditText editLoginEmail;
    EditText editLoginPassword;
    Button loginButton;
    TextView loginSignup;
    App app;
    String appID = "hackit-qyzey";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);

        editLoginEmail = findViewById(R.id.inputLoginEmail);
        editLoginPassword = findViewById(R.id.inputLoginPassword);
        loginButton = findViewById(R.id.loginButton);
        loginSignup = findViewById(R.id.loginSignup);

        loginSignup.setOnClickListener(LoginSignupClick);
        loginButton.setOnClickListener(LoginButtonClick);
        progressBar = findViewById(R.id.progressLogin);
        app = new App(new AppConfiguration.Builder(appID).build());
        progressBar.setVisibility(View.GONE);


    }
    View.OnClickListener LoginSignupClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();

        }
    };


    View.OnClickListener LoginButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = editLoginEmail.getText().toString();
            String password = editLoginPassword.getText().toString();
            if(email!=null && password!=null){
                hideKeybaord(view);
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        UserDetail.setUser(app.currentUser());
                        UserDetail.setEmailId(email);
                        UserDetail.setmUid(app.currentUser().getId());

                        Log.i("id", app.currentUser().getId());
                        Log.i("EXAMPLE", "Successfully Logged In user.");

                        setUserId.setuserid();

                        Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                        if(UserDetail.getType()==0){
                            Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(LoginActivity.this, OwnerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        loginButton.setEnabled(true);
                        Log.e("EXAMPLE", "Failed to Login user: " + it.getError().getErrorMessage());
                        Toast.makeText(LoginActivity.this,"Login Failed, Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };


    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

    }
}