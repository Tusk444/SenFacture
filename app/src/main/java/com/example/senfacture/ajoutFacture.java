package com.example.senfacture;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.senfacture.databinding.ActivityAjoutFactureBinding;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class ajoutFacture extends AppCompatActivity {
    // two buttons
    Button btnCancel, btnSave;

    // four text fields
    EditText txtIntitulé, txtMontant, txtNuméro, txtDate;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register buttons with their proper IDs.
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // register all the EditText fields with their IDs.
        txtIntitulé = findViewById(R.id.txtIntitulé);
        txtMontant = findViewById(R.id.txtMontant);
        txtNuméro = findViewById(R.id.txtNuméro);
        txtDate = findViewById(R.id.txtDate);

        // handle the PROCEED button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Intent i = new Intent(ajoutFacture.this, ajoutFacture.class);
                    startActivity(i);
                }
            }
        });

        // if user presses the cancel button then close the
        // application or the particular activity.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutFacture.this.finish();
                System.exit(0);
            }
        });
    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (txtIntitulé.length() == 0) {
            txtIntitulé.setError("This field is required");
            return false;
        }

        if (txtMontant.length() == 0) {
            txtMontant.setError("This field is required");
            return false;
        }

        if (txtNuméro.length() == 0) {
            txtNuméro.setError("This field is required");
            return false;
        }

        if (txtDate.length() == 0) {
            txtDate.setError("This field is required");
            return false;
        }
        // after all validation return true.
        return true;
    }
}