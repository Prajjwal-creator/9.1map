package com.example.a91map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.*;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;

public class AddItemActivity extends AppCompatActivity {

    EditText editTitle, editDescription, editContact, editLocation;
    Spinner spinnerStatus;
    Button btnSubmit, btnGetLocation;
    FusedLocationProviderClient fusedLocationClient;
    String locationText = "";

    static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Runtime permission request
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editContact = findViewById(R.id.editContact);
        editLocation = findViewById(R.id.editLocation);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnGetLocation = findViewById(R.id.btnGetLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // ✅ Initialize Places API with correct key
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDwhipoUQgPoI9UM3qb0MRkMU18rGFct_s");
        }

        editLocation.setOnClickListener(v -> {
            try {
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,
                        Arrays.asList(Place.Field.ADDRESS))
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            } catch (Exception e) {
                Toast.makeText(this, "Autocomplete error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnGetLocation.setOnClickListener(v -> getCurrentLocation());

        btnSubmit.setOnClickListener(v -> {
            DBHelper db = new DBHelper(this);
            db.insertItem(new Item(0,
                    editTitle.getText().toString(),
                    editDescription.getText().toString(),
                    spinnerStatus.getSelectedItem().toString(),
                    editContact.getText().toString(),
                    locationText));
            Toast.makeText(this, "Item Added!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void getCurrentLocation() {
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {
                locationText = location.getLatitude() + ", " + location.getLongitude();
                editLocation.setText(locationText);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                locationText = place.getAddress();
                editLocation.setText(locationText);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "Autocomplete error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
