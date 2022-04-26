package com.example.senfacture;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.senfacture.databinding.FragmentStructuresBinding;

public class StructureFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentStructuresBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_structures, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng orange = new LatLng(14.666937049443456, -17.437621123668485);
        mMap.addMarker(new MarkerOptions().position(orange).title("Orange Sonatel").snippet("Contact: 338392100 | Site Web: http://www.orange.sn/"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orange, 14));

        LatLng senelec = new LatLng(14.71281993627529, -17.455441339010857);
        mMap.addMarker(new MarkerOptions().position(senelec).title("Senelec").snippet("Contact: 338399476 | Site Web: http://www.senelec.sn/"));

        LatLng seneau = new LatLng(14.720336475649072, -17.433933501944242);
        mMap.addMarker(new MarkerOptions().position(seneau).title("SDE").snippet("Contact: 338393737 | Site Web: http://www.sde.sn/"));

        LatLng canalplus = new LatLng(14.66975425530867, -17.429331174839028);
        mMap.addMarker(new MarkerOptions().position(canalplus).title("Canal +").snippet("Contact: 338895040 | Site Web: https://www.canalplus-afrique.com/sn/"));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if(marker.getTitle().equals("Orange Sonatel")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338392100"));
                    startActivity(intent);
                }
                if(marker.getTitle().equals("Senelec")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338399476"));
                    startActivity(intent);
                }
                if(marker.getTitle().equals("SDE")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338393737"));
                    startActivity(intent);
                }
                if(marker.getTitle().equals("Canal +")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338895040"));
                    startActivity(intent);
                }

                return false;
            }
        });
    }
}