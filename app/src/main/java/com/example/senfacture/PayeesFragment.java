package com.example.senfacture;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PayeesFragment extends Fragment {
    private Button btnBills;
    private TextView tvBills,tvEmail;
    private int id = 0;
    private String bills,email;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_impayees, container, false);

        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);
        btnBills = view.findViewById(R.id.btnBills);
        tvBills = view.findViewById(R.id.tvBills);



        btnBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBills();
            }
        });
        return view;
    }

    public void getIdByEmail(){
        String url = "http://"+BuildConfig.IP_ADDRESS+"/senfacture/id.php?email="+email;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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

    public void getBills(){
        getIdByEmail();
        if (id==0) throw new IllegalArgumentException("L'utilisateur n'existe pas");
        try {
            String url = "http://"+BuildConfig.IP_ADDRESS+"/senfacture/bills.php?id="+id;
            bills = "";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String message = getString(R.string.error_connection);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        JSONObject jo = new JSONObject(result);
                        JSONArray ja = jo.getJSONArray("bills");

                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject element = ja.getJSONObject(i);
                            String intitule = element.getString("intitule");
                            String entreprise = element.getString("entreprise");
                            String numero = element.getString("numero");
                            String date_echeance = element.getString("date_echeance");
                            bills+= intitule+": "+entreprise+numero+date_echeance+"\n\n";
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvBills.setText(bills);
                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
}