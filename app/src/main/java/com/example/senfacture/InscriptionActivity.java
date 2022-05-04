package com.example.senfacture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InscriptionActivity extends AppCompatActivity {

    private EditText txtFirstName,txtLastName,txtEmail, txtPassword;
    private String firstName,lastName,email, password;
    private Button btnSignUp,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnBack= findViewById(R.id.btnBack);
        btnSignUp= findViewById(R.id.btnSignUp);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    String message = getString(R.string.error_field);
                    Toast.makeText(InscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    inscription();
                }
            }
        });

    }

    public void inscription(){
        String url = "http://"+BuildConfig.IP_ADDRESS+"/senfacture/inscription.php?first_name="+firstName+"&last_name="+lastName+"&email="+email+"&password="+password;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");

                    if(status.equals("KO")){
                        final String message = getString(R.string.error_parameters);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InscriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(InscriptionActivity.this, R.string.successfully_signed_up, Toast.LENGTH_SHORT).show();
                                          }
                                      });
                        intent.putExtra("EMAIL", email);
                        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                                new Runnable() {
                                    public void run() {
                                        startActivity(intent);                                    }
                                },
                                3000);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}