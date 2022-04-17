package com.example.senfacture;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myschool.databinding.FragmentUniversityBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UniversityFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentUniversityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_university, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //Coordonees esmt lat : 14.7000409; long : -17.45104995
        LatLng esmt = new LatLng(14.700178532184664, -17.451023274236622);
        mMap.addMarker(new MarkerOptions().position(esmt).title("ESMT").snippet("Contact: 338690300 Site: esmt.sn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(esmt, 14));

        LatLng ucad = new LatLng(14.692847361814557, -17.461958760742913);
        mMap.addMarker(new MarkerOptions().position(ucad).title("UCAD").snippet("Contact: 338690301 Site: ucad.sn"));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CircleOptions co = new CircleOptions()
                .center(esmt)
                .radius(600)
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK)
                .strokeWidth(6);
        mMap.addCircle(co);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if(marker.getTitle().equals("ESMT")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:784378656"));
                    startActivity(intent);
                }

                if(marker.getTitle().equals("UCAD")){
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:784378656"));
                    intent.putExtra("sms_body", "Bonjour les experts mobiles DAR ! C'est Cephas pour le servir.");
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}