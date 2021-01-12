package com.example.loginapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    EditText editName, editEmail, editPassword, editConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
    }

    public void signup(View view) {
        if(TextUtils.isEmpty(editName.getText())) {
            editName.setError("Name is required!");
        }else if(TextUtils.isEmpty((editEmail.getText()))){
            editEmail.setError("Email is required");
        }else if(TextUtils.isEmpty(editPassword.getText())){
            editPassword.setError("Password is required!");
        }else if(TextUtils.isEmpty(editConfirmPassword.getText())) {
            editConfirmPassword.setError("Confirm Password is required!");
        }else if(!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())){
            Toast.makeText(SignupActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();
        }else{
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Loading ...");
            progress.show();
            ParseUser user = new ParseUser();
            // Set the user's username and password, which can be obtained by a forms
            user.setUsername(editEmail.getText().toString().trim());
            user.setEmail(editEmail.getText().toString().trim());
            user.setPassword(editPassword.getText().toString().trim());
            user.put("name", editName.getText().toString().trim());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    progress.dismiss();
                    if (e == null) {
                        Toast.makeText(SignupActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}