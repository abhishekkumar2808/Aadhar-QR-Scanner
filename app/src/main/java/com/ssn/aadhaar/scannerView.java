package com.ssn.aadhaar;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.sax.Element;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerView extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
   ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                     permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult)
    {

       String data = rawResult.getText();
       System.out.println(data);

        try {
            toJSON(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        onBackPressed();
    }

    private void toJSON(String data) throws JSONException {

        String[] listOfString = data.split("[?]>",3);

        String metaData = listOfString[1];
        metaData = metaData.substring(24,metaData.length()-2);
        String[] actualList = metaData.split("\" ");

        JSONObject json = new JSONObject();
        for(String i:actualList)
        {
            i = i.replaceAll("\"","");
            //System.out.println("\n"+i);
            int equalTO = i.indexOf("=");
            String beforeEqual = i.substring(0,equalTO);
            String afterEqual = i.substring(equalTO+1);
            json.put(beforeEqual,afterEqual);

        }
        System.out.println("\n\nJSON data = "+json.toString());
//        MainActivity.scantext.setText(json.toString());
            Intent dataIntent = new Intent(scannerView.this, FormActivity.class);
            dataIntent.putExtra("data", json.toString());
            startActivity(dataIntent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}