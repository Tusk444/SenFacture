package com.example.senfacture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
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

    private EditText txtEmail, txtPassword,txtIntitulé,txtMontant,txtNuméro,txtDate;
    private Button btnConnect, btnSignUp,btnSave;
    private String password;
    public static String email;
    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Liaison entre variables et composants
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnConnect = findViewById(R.id.btnConnect);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSave = findViewById(R.id.btnSave);
        txtIntitulé = findViewById(R.id.txtIntitulé);
        txtMontant = findViewById(R.id.txtMontant);
        txtNuméro = findViewById(R.id.txtNuméro);
        txtDate = findViewById(R.id.txtDate);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);

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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inscription();
            }
        });
    }

    private void inscription() {
        Intent intent = new Intent(MainActivity.this, InscriptionActivity.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);
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
                    else {
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        // below two lines will put values for
                        // email and password in shared preferences.
                        editor.putString(EMAIL_KEY, txtEmail.getText().toString());
                        editor.putString(PASSWORD_KEY, txtPassword.getText().toString());

                        // to save our data with key and value.
                        editor.apply();
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
    private boolean CheckAllFields() {
        if (txtIntitulé.length() == 0) {
            txtIntitulé.setError("This field is required");
            return false;
        }

        if (txtMontant.length() == 0) {
            txtMontant.setError("This field is required");
            return false;
        }

        if (txtNuméro.length() == 0) {
            txtNuméro.setError("This field is required");
            return false;
        }

        if (txtDate.length() == 0) {
            txtDate.setError("This field is required");
            return false;
        }
        // after all validation return true.
        return true;
    }
}