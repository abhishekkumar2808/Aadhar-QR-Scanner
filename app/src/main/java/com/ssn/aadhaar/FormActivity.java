package com.ssn.aadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.kazimasum.qrdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FormActivity extends AppCompatActivity {

    EditText UIDET;
    EditText nameET;
    EditText genderET;
    EditText yobET;
    EditText coET;
    EditText houseET;
    EditText streetET;
    EditText lmET;
    EditText vtcET;
    EditText poET;
    EditText distET;
    EditText subdistET;
    EditText stateET;
    EditText pcET;
    EditText dobET;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        UIDET = findViewById(R.id.UIDET);
        nameET = findViewById(R.id.nameET);
        genderET = findViewById(R.id.genderET);
        yobET = findViewById(R.id.yobET);
        coET = findViewById(R.id.coET);
        houseET = findViewById(R.id.houseET);
        streetET = findViewById(R.id.streetET);
        lmET = findViewById(R.id.lmET);
        vtcET = findViewById(R.id.vtcET);
        poET = findViewById(R.id.poET);
        distET = findViewById(R.id.distET);
        subdistET = findViewById(R.id.subdistET);
        stateET = findViewById(R.id.stateET);
        pcET = findViewById(R.id.pcET);
        dobET = findViewById(R.id.dobET);

        Intent dataIntent = getIntent();
        json=null;
        try {
            json = new JSONObject(dataIntent.getStringExtra("data"));
            HasData("subdist",subdistET);
            HasData(" uid",UIDET);
            HasData("name",nameET);
            HasData("yob",yobET);
            HasData("co",coET);
            HasData("house",houseET);
            HasData("street",streetET);
            HasData("gender",genderET);
            HasData("lm",lmET);
            HasData("vtc",vtcET);
            HasData("dist",distET);
            HasData("state",stateET);
            HasData("po",poET);
            HasData("pc",pcET);
            HasData("dob",dobET);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FormActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }


    }

    public void HasData(String data, EditText et) throws JSONException {
        if(json.has(data)) {
            et.setText(json.get(data).toString());
        }
    }
}