package com.example.senfacture;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.senfacture.databinding.FragmentStructuresBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

        Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName("Sonatel, Senegal", 5);
            for (int i=0 ; i<addressList.size() ; i++) {
                Address address = addressList.get(i);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .position(latLng)
                        .title("Orange Sonatel")
                        .snippet("Contact: 338392100 | Site Web: http://www.orange.sn/"));
                if(i==0)
                {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                }
            }
            addressList = geocoder.getFromLocationName("Senelec, Senegal", 2);
            for (int i=0 ; i<addressList.size() ; i++) {
                Address address = addressList.get(i);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        .position(latLng)
                        .title("Senelec")
                        .snippet("Contact: 338399476 | Site Web: http://www.senelec.sn/"));
            }
            addressList = geocoder.getFromLocationName("Sénégalaise des eaux, Senegal", 2);
            for (int i=0 ; i<addressList.size() ; i++) {
                Address address = addressList.get(i);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .position(latLng)
                        .title("Senelec")
                        .snippet("Contact: 338393737 | Site Web: http://www.sde.sn/"));
            }

            addressList = geocoder.getFromLocationName("Canal-Plus, Senegal", 2);
            for (int i=0 ; i<addressList.size() ; i++) {
                Address address = addressList.get(i);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .position(latLng)
                        .title("Canal +")
                        .snippet("Contact: 338895040 | Site Web: https://www.canalplus-afrique.com/sn/"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if(marker.getTitle().equals("Orange Sonatel")){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:338392100"));
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    startActivity(intent);                                    }
                            },
                            3000);
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