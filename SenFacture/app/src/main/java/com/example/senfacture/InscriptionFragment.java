package com.example.senfacture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InscriptionFragment extends Fragment {

    private EditText txtFirstName, txtLastName;
    private CheckBox cbOLevel, cbMaster, cbBachelor;
    private Button btnSave;
    private String firstName, lastName, degrees;
    public static String formation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        cbOLevel = view.findViewById(R.id.cbOLevel);
        cbMaster = view.findViewById(R.id.cbMaster);
        cbBachelor = view.findViewById(R.id.cbBachelor);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = txtFirstName.getText().toString().trim();
                lastName = txtLastName.getText().toString().trim();
                degrees = "";

                if(cbOLevel.isChecked()){
                    degrees += cbOLevel.getText().toString()+" ";
                }

                if(cbBachelor.isChecked()){
                    degrees += cbBachelor.getText().toString()+" ";
                }

                if(cbMaster.isChecked()){
                    degrees += cbMaster.getText().toString();
                }

//                String result = firstName+"\n\n"+lastName+"\n\n"+degrees+"\n\n"+formation;
//                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                isncription();
            }
        });
        return view;
    }

    public void isncription(){
        String url = "http://192.168.1.4/esmt/inscription.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("first_name", firstName)
                .add("last_name", lastName)
                .add("degrees", degrees)
                .add("formation", formation)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

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

                    if(status.equals("KO")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), getString(R.string.error_inscription), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtFirstName.setText("");
                                txtLastName.setText("");
                                cbOLevel.setChecked(false);
                                cbMaster.setChecked(false);
                                cbBachelor.setChecked(false);
                                Toast.makeText(getActivity(), getString(R.string.success_inscription), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}