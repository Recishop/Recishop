package com.example.recishop;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import com.example.recishop.databinding.ActivityLoginBindingImpl;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Login Activity is the entry point into the application.
 * If the user is logged in, they will automatically be forwarded to
 * the Main Activity.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * Handle to UI Login screen
     */
    ActivityLoginBindingImpl activityLoginBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity();
        }

        activityLoginBinding.btnLogin.setOnClickListener(this);
        activityLoginBinding.btnSignUp.setOnClickListener(this);
    }

    private void createUser(String username, String password) {
        Log.i(TAG, "Attempting to create new user:" + username);
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        // TODO: Ensure the username does not already exist in the database when making a new account

        user.signUpInBackground(e -> {
            if (e == null) {
                goToMainActivity();
                Toast.makeText(LoginActivity.this, "Successfully created account!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null) {
                    goToMainActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username/password", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        String username = activityLoginBinding.etUsername.getText().toString();
        String password = activityLoginBinding.etPassword.getText().toString();

        switch (v.getId()) {
            case R.id.btnLogin:
                Log.i(TAG, "onClick login button");
                loginUser(username, password);
                break;
            case R.id.btnSignUp:
                Log.i(TAG, "onClick sign up button");
                createUser(username,password);
                break;
        }
    }
}
