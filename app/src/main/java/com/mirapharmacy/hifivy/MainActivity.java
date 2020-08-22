package com.mirapharmacy.hifivy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    EditText emailET;
    EditText passwordET;
    Button loginBtn;
    boolean value;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("logIn", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        value = sharedPreferences.getBoolean("value", false);


        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);

        loginBtn = findViewById(R.id.logInET);
        constraintLayout = findViewById(R.id.constraintLayout);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                userLogin();
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
    }

    private void userLogin() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (loginResponse.isStatus()) {
                    editor.putBoolean("value", true);
                    editor.putString("token", loginResponse.getToken());
                    editor.apply();
                    progressDialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                    intent.putExtra("token", loginResponse.getToken());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, loginResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (value) {
            startActivity(new Intent(getApplicationContext(), ShowDataActivity.class));
            finish();
        }
    }
}