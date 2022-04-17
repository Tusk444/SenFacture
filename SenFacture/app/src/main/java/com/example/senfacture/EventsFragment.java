package com.example.senfacture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

public class EventsFragment extends Fragment {

    private VideoView videoView;
    private MediaController mediaController;
    private String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        videoView = view.findViewById(R.id.videoView);
        path = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.ucad;
        mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(path);
        videoView.start();
        return view;
    }
}