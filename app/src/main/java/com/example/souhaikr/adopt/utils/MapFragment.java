package com.example.souhaikr.adopt.utils;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.souhaikr.adopt.R;
import com.example.souhaikr.adopt.controllers.APIClient;
import com.example.souhaikr.adopt.entities.Pet;
import com.example.souhaikr.adopt.interfaces.APIInterface;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterManagerPlugin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.expressions.Expression.division;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.rgb;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements  PermissionsListener{
    private MapView mapView;
    APIInterface apiInterface;
    private List<Pet> contacts ;
    MapboxMap mMapboxMap ;

    String name ;
    String image ;
    HashMap<Long, Pet> innerMap = new HashMap<Long,Pet>();
    private Marker marker;
    private PermissionsManager permissionsManager;
    private Location originLocation;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        final View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView =  view.findViewById(R.id.mapView);





        mapView.onCreate(savedInstanceState);
//
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mMapboxMap = mapboxMap;
                enableLocationComponent();


                apiInterface = APIClient.getClient().create(APIInterface.class);
                Call<List<Pet>> call = apiInterface.doGetList();
                call.enqueue(new Callback<List<Pet>>() {
                    @Override
                    public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                        contacts = response.body();
                        //contacts.addAll(response.body());





                       for (Pet size : contacts)
                       {



                            IconFactory iconFactory = IconFactory.getInstance(getContext());
                            Icon iconRed = null;
                            Icon iconPurple = null;
                            Icon iconBlue = null;

                            iconRed = iconFactory.fromResource(R.drawable.pin_red);
                            iconPurple = iconFactory.fromResource(R.drawable.pin_purple);
                            iconBlue = iconFactory.fromResource(R.drawable.pin_blue);
                            if (size.getType().equals("Dog")) {

                                marker = mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(size.getLatitude(), size.getLongitude()))
                                        .title("Chicago")
                                        .snippet("Illinois").icon(iconRed)
                                );
                            }
                            else if (size.getType().equals("Cat"))
                            {
                                marker =mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(size.getLatitude(),size.getLongitude()))
                                        .title("Chicago")
                                        .snippet("Illinois").icon(iconPurple)  );



                            }
                            else {

                                marker = mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(size.getLatitude(), size.getLongitude()))
                                        .title("Chicago")
                                        .snippet("Illinois").icon(iconBlue));
                            }


                            Pet p = new Pet() ;

                            p.setImage(size.getImage());
                            p.setId(size.getId());


                            innerMap.put(marker.getId(), p);
                           Log.d("TAG", String.valueOf(innerMap.values()));



                        }



                    }

                    @Override
                    public void onFailure(Call<List<Pet>> call, Throwable t) {
                        call.cancel();
                        Log.d("TAG", "ccccccc");
                    }



                });

//








                mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {



                    @Override
                    public View getInfoWindow(@NonNull Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.info_window, null);

                        for (Long s : innerMap.keySet()) {
                            // print the student id and age
                            if (s.equals(marker.getId()))
                            {
                                ImageView test =  v.findViewById(R.id.badge);
                                Picasso.get().load(innerMap.get(s).getImage()).into(test);

                            }

                        }












                        return v;
                    }
                });

                mapboxMap.setOnInfoWindowClickListener(new MapboxMap.OnInfoWindowClickListener() {

                    @Override
                    public boolean onInfoWindowClick(@NonNull Marker marker) {
                        for (Long s : innerMap.keySet()) {
                            // print the student id and age
                            if (s.equals(marker.getId()))


                            {
                                String id = String.valueOf(innerMap.get(s).getId());
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);

                            }

                        }






                        return false;
                    }
                });




            }
        });




                return view;
    }




    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            LocationComponent locationComponent = mMapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(getActivity());
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            originLocation = locationComponent.getLastKnownLocation();

        } else {
            permissionsManager = new PermissionsManager((PermissionsListener) getActivity());
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getActivity(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }










}
