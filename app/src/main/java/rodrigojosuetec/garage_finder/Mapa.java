package rodrigojosuetec.garage_finder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng myPosition; //guarda la ultima posicion del usuario
    Marker marca; //marca del usuario
    Polyline linea;
    Marker inicio;
    Marker fin;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        marca = null;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3).setApiKey(getString(R.string.google_maps_key)).setConnectTimeout(1, TimeUnit.SECONDS).setReadTimeout(1, TimeUnit.SECONDS).setWriteTimeout(1, TimeUnit.SECONDS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                return;
            }
        }
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[0].legs[0].duration.humanReadable + " Distance :" + results.routes[0].legs[0].distance.humanReadable;
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        inicio = mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].startLocation.lat, results.routes[0].legs[0].startLocation.lng)).title(results.routes[0].legs[0].startAddress));
        fin = mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[0].legs[0].endLocation.lat, results.routes[0].legs[0].endLocation.lng)).title(results.routes[0].legs[0].startAddress).snippet(getEndLocationTitle(results)));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        linea = mMap.addPolyline(new PolylineOptions().addAll(decodedPath).width(5).color(Color.RED).geodesic(true));
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
    public void onMapReady(GoogleMap map) {
        try {
            mMap = map;
            linea = null;
            inicio = null;
            fin = null;
            setupGoogleMapScreenSettings(mMap);
            //Solicita permisos al usuario para tener acceso a su ubicacíon
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //Si el usuario permitió el acceso, entonces continue
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos de ubicación otorgados",
                        Toast.LENGTH_SHORT).show();
                //Activa el boton para ubicar al usuario, debe tener el GPS encendido
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else{
                Toast.makeText(this, "No se otorgaron permisos",
                        Toast.LENGTH_SHORT).show();
            }

            //Localiza la posicion marcada por el usuaruio
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {

                        if(getIntent().getStringExtra("OPCION")!=null){
                            int  opcion = Integer.parseInt(getIntent().getStringExtra("OPCION"));
                            switch (opcion) {
                                case 1:
                                    Intent intent = new Intent();
                                    intent.putExtra("keyName", point.latitude + "," + point.longitude);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    break;
                            }
                        }
                        if (myPosition != null && linea != null && inicio != null && fin != null) {
                            DateTime now = new DateTime();
                            try {
                                DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                                        .mode(TravelMode.DRIVING)
                                        .origin(new com.google.maps.model.LatLng(myPosition.latitude, myPosition.longitude))
                                        .destination(new com.google.maps.model.LatLng(point.latitude, point.longitude))
                                        .departureTime(now)
                                        .await();
                                inicio.remove();
                                fin.remove();
                                addMarkersToMap(result, mMap);
                                linea.remove();
                                addPolyline(result, mMap);
                            } catch (com.google.maps.errors.ApiException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            DateTime now = new DateTime();
                            try {
                                DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                                        .mode(TravelMode.DRIVING)
                                        .origin(new com.google.maps.model.LatLng(myPosition.latitude, myPosition.longitude))
                                        .destination(new com.google.maps.model.LatLng(point.latitude, point.longitude))
                                        .departureTime(now)
                                        .await();
                                addMarkersToMap(result, mMap);
                                addPolyline(result, mMap);
                            } catch (com.google.maps.errors.ApiException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                // Getting LocationManager object from System Service LOCATION_SERVICE
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // Creating a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Getting the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Getting Current Location
                Location location = locationManager.getLastKnownLocation(provider);

                if (location != null) {
                    // Getting latitude of the current location
                    double latitude = location.getLatitude();

                    // Getting longitude of the current location
                    double longitude = location.getLongitude();

                    // Creating a LatLng object for the current location
                    LatLng latLng = new LatLng(latitude, longitude);

                    myPosition = new LatLng(latitude, longitude);
                }

            else{
                Toast.makeText(this, "La aplicación necesita permisos de usuario para continuar",
                        Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception e) {
            Toast.makeText(this, "No se pudo",
                    Toast.LENGTH_LONG).show();
        }

    }
}