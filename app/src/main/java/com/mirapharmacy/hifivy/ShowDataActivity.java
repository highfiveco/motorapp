package com.mirapharmacy.hifivy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowDataActivity extends AppCompatActivity {

    String token, id;
    ConstraintLayout constraintLayout;
    AutoCompleteTextView username;
    List<User> users;
    ArrayList<String> names;
    TextView nameTv, priceTv, balanceTv, differenceTv;
    EditText currentReadingTv, previousReadingTv;
    Button updateDataBtn;

    int currentValueAPI, lastValueAPI, differenceValueAPI;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        token = getIntent().getStringExtra("token");
        SharedPreferences sharedPreferences = getSharedPreferences("logIn", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        names = new ArrayList<>();
        nameTv = findViewById(R.id.nameTv);
        priceTv = findViewById(R.id.priceTv);
        balanceTv = findViewById(R.id.balanceTv);
        previousReadingTv = findViewById(R.id.previousReadingTv);
        currentReadingTv = findViewById(R.id.currentReadingTv);
        differenceTv = findViewById(R.id.differenceTv);
        updateDataBtn = findViewById(R.id.updateDataBtn);

        users = new ArrayList<>();
        username = findViewById(R.id.searchAutoCompleteTv);
        final String url = UrlClass.urlgetData;


        constraintLayout = findViewById(R.id.constraintLayout2);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    users = extractUser(url, charSequence.toString());
                    if (users.size() > 0) {
                        User user = users.get(0);
                        nameTv.setText(user.getName());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        username.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                showData(UrlClass.urlgetData, s);
                closeKeyboard();

            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });

        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(ShowDataActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                updateData();

            }
        });

        previousReadingTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String number = charSequence.toString();
                    if (!number.equals(lastValueAPI + "")) {
                        int result = Integer.parseInt(currentReadingTv.getText().toString()) - Integer.parseInt(number);
                        differenceTv.setText(result + "");
                    } else {
                        if (differenceValueAPI >= 0) {
                            differenceTv.setText(differenceValueAPI + "");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        currentReadingTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    String number = charSequence.toString();
                    if (!number.equals(currentValueAPI + "")) {
                        int result = Integer.parseInt(number) - Integer.parseInt(previousReadingTv.getText().toString());
                        differenceTv.setText(result + "");
                    } else {
                        differenceTv.setText(differenceValueAPI + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showData(String url, String name) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiClass apiClass = retrofit.create(ApiClass.class);

        Call<TokenBody> call = apiClass.sendToken("token", token, name);
        call.enqueue(new Callback<TokenBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TokenBody> call, Response<TokenBody> response) {
                if (response.isSuccessful()) {

                    TokenBody tokenBody = response.body();
                    assert tokenBody != null;
                    if (tokenBody.isStatus()) {
                        List<User> users = tokenBody.getData();
                        User user = users.get(0);
                        currentValueAPI = user.getCurnt_value();
                        lastValueAPI = user.getLast_value();
                        differenceValueAPI = user.getDiff();
                        id = user.getId();
                        nameTv.setText(user.getName());
                        priceTv.setText(user.getPrice() + "");
                        balanceTv.setText(user.getMin_amount() + "");
                        currentReadingTv.setText(user.getCurnt_value() + "");
                        previousReadingTv.setText(user.getLast_value() + "");
                        differenceTv.setText(user.getDiff() + "");
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenBody> call, Throwable t) {

            }
        });
    }

    private ArrayList<User> extractUser(String url, String name) {

        final ArrayList<User> list = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiClass apiClass = retrofit.create(ApiClass.class);

        Call<TokenBody> call = apiClass.sendToken("token", token, name);

        call.enqueue(new Callback<TokenBody>() {
            @Override
            public void onResponse(Call<TokenBody> call, Response<TokenBody> response) {

                if (response.isSuccessful()) {

                    TokenBody tokenBody = response.body();
                    assert tokenBody != null;
                    if (tokenBody.isStatus()) {
                        List<User> users = tokenBody.getData();
                        if (users != null && users.size() > 0) {
                            list.clear();
                            names.clear();
                            for (User user : users) {
                                names.add(user.getName());
                                list.add(user);
                            }

                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ShowDataActivity.this,
                                    android.R.layout.simple_dropdown_item_1line, names);
                            username.setThreshold(3);
                            username.setAdapter(adapter1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenBody> call, Throwable t) {

            }
        });
        return list;
    }

    private void updateData() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(UrlClass.urlAddRead)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        ApiClass apiClass = retrofit.create(ApiClass.class);
        if (!currentReadingTv.getText().equals("") && currentReadingTv.getText().length() > 0
                && !previousReadingTv.getText().equals("") && previousReadingTv.getText().length() > 0
                && !id.equals("") && id.length() > 0) {
            Call<UpdateData> call = apiClass.updateData(token, token, id, currentReadingTv.getText().toString(), previousReadingTv.getText().toString());
            call.enqueue(new Callback<UpdateData>() {
                @Override
                public void onResponse(Call<UpdateData> call, Response<UpdateData> response) {
                    if (response.isSuccessful()) {
                        UpdateData data = response.body();
                        assert data != null;
                        if (data.isStatus()) {
                            progressDialog.dismiss();
                            Toast.makeText(ShowDataActivity.this, data.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Log.d("failure", "Update is Failure 1");
                            Toast.makeText(ShowDataActivity.this, "Update is Failure", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.dismiss();
                        Log.d("failure", "Update is Failure 2");
                        Log.d("updateDate", "code:" + response.code() + ",message:" + response.message());
                        Toast.makeText(ShowDataActivity.this, "Update is Failure", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateData> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("failure", t.getMessage());
                    Toast.makeText(ShowDataActivity.this, "Update is Failure", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "There is no data to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logOut:

                SharedPreferences sharedPreferences = getSharedPreferences("logIn", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("value", false);
                editor.putString("token", "");
                editor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

                break;

            case R.id.clean:
                username.setText("");
                nameTv.setText("");
                priceTv.setText("");
                balanceTv.setText("");
                currentReadingTv.setText("");
                previousReadingTv.setText("");
                differenceTv.setText("");
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}