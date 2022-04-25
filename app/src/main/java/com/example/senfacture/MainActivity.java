package com.example.senfacture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnConnect, btnSignUp;
    private String password;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Liaison entre variables et composants
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnConnect = findViewById(R.id.btnConnect);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtEmail.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    String message = getString(R.string.error_field);
                    //Message d'alerte
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
                    authentification();

//                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                    startActivity(intent);
                }
            }
        });
    }

    public void authentification(){
        String url = "http://192.168.1.5/senfacture/connexion.php?email="+email+"&password="+password;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}