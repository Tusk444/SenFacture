package com.example.senfacture;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senfacture.models.Facture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PayeesFragment extends Fragment {
    private int id;
    private String bills,email;
    private Button btnBills;
    RecyclerView recyclerView;
    ArrayList<String> intitules = new ArrayList<>();
    ArrayList<String> entreprises = new ArrayList<>();
    ArrayList<String> numeros = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payees, container, false);
        btnBills = view.findViewById(R.id.btnBills);
        // get the reference of RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        btnBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBills();
                CustomAdapter customAdapter = new CustomAdapter(getContext(), intitules, entreprises, numeros,dates);
                recyclerView.setAdapter(customAdapter);
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
                        if(Integer.parseInt(element.getString("is_paid"))==1)
                        {
                            intitules.add(element.getString("intitule"));
                            entreprises.add(element.getString("entreprise"));
                            numeros.add(element.getString("numero"));
                            dates.add(element.getString("date_echeance"));
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
}