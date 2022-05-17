package com.example.senfacture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddBillActivity extends AppCompatActivity {

    private EditText txtIntitule,txtMontant,txtNumero, txtDate,txtEntreprise;
    private String intitule,montant,numero,date,entreprise,email;
    private int id;
    private Button btnAdd,btnCancel;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"SENELEC", "SONATEL", "SDE","CANAL +"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        txtIntitule = findViewById(R.id.txtIntitulé);
        txtMontant = findViewById(R.id.txtMontant);
        txtNumero = findViewById(R.id.txtNuméro);
        txtDate = findViewById(R.id.txtDate);
        btnCancel= findViewById(R.id.btnCancel);
        btnAdd= findViewById(R.id.btnAdd);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBillActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intitule = txtIntitule.getText().toString().trim();
                montant = txtMontant.getText().toString().trim();
                numero = txtNumero.getText().toString().trim();
                date = txtDate.getText().toString().trim();
                entreprise = dropdown.getSelectedItem().toString().trim();
                if(intitule.isEmpty() || montant.isEmpty() || numero.isEmpty() || date.isEmpty() || entreprise.isEmpty()) {
                    String message = getString(R.string.error_field);
                    Toast.makeText(AddBillActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else {
                    getIdByEmail();
                    addBill();
                }
            }
        });
    }
    public void getIdByEmail(){
        String url = "http://"+BuildConfig.IP_ADDRESS+"/senfacture/id.php?email="+email;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                AddBillActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddBillActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");

                    if(!status.equals("0")){
                        id = Integer.parseInt(jo.getString("status"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void addBill(){
        getIdByEmail();
        String url = "http://"+BuildConfig.IP_ADDRESS+"/senfacture/add_bill.php?intitule="+intitule+"&entreprise="+entreprise+"&montant="+montant+"&numero="+numero+"&date="+date+"&id="+id;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddBillActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        final String message =  getString(R.string.error_parameters);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddBillActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(AddBillActivity.this, HomeActivity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddBillActivity.this, R.string.successfully_added_bill, Toast.LENGTH_SHORT).show();
                            }
                        });
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