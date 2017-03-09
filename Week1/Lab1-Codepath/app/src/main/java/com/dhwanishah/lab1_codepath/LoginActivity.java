package com.dhwanishah.lab1_codepath;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButton (View view) {
        Toast.makeText(this, "Login button clicked.", Toast.LENGTH_LONG).show();
    }
    public void signUpButton (View view) {
        Toast.makeText(this, "Sign up button clicked.", Toast.LENGTH_LONG).show();
    }
    public void helpButton (View view) {
        Toast.makeText(this, "Help button clicked.", Toast.LENGTH_LONG).show();
    }
    public void forgotPassButton (View view) {
        Toast.makeText(this, "Change password button clicked.", Toast.LENGTH_LONG).show();
    }
    public void changeButton (View view) {
        Toast.makeText(this, "Change Language button clicked.", Toast.LENGTH_LONG).show();
    }

}
