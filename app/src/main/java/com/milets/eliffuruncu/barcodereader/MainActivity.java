package com.milets.eliffuruncu.barcodereader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button scan_btn;
    private TextView scan_txt;

    String ad,soyad,bolum,sehir,telefon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan_btn=(Button)findViewById(R.id.scan_button);
        scan_txt=(TextView)findViewById(R.id.scan_Txt);
        final Activity activity=this;

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator =new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        try {
            JSONObject jsonObject = new JSONObject(result.getContents());

            ad = jsonObject.getString("Ad");
            soyad = jsonObject.getString("Soyad");
            bolum = jsonObject.getString("Bolum");
            sehir = jsonObject.getString("Sehir");
            telefon = jsonObject.getString("Telefon");

            scan_txt.setText(" Ad Soyad : "+ad+" "+soyad+" \n Bölüm:"+bolum+"\n Şehir: "+sehir+"\n Telefon : "+telefon);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Json parsing error: " + e.getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }

        if(result !=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(MainActivity.this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
