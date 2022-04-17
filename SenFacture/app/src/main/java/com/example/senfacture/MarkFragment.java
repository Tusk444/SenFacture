package com.example.senfacture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarkFragment extends Fragment {
    private Button btnMarks;
    private TextView tvMarks;
    private String marks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mark, container, false);
        btnMarks = view.findViewById(R.id.btnMarks);
        tvMarks = view.findViewById(R.id.tvMarks);

        btnMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMarks();
            }
        });
        return view;
    }

    public void getMarks(){
        String url = "http://192.168.1.4/esmt/mark.php?login="+ com.example.myschool.MainActivity.login;
        marks = "";

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
                    JSONArray ja = jo.getJSONArray("marks");

                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String course = element.getString("course");
                        String grade = element.getString("grade");
                        marks+= course+": "+grade+"\n\n";
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMarks.setText(marks);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}