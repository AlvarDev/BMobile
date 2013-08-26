package com.software.bmobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.math.BigInteger;

public class Home extends Activity {
    public final static String EXTRA_MESSAGE = "com.software.bmobile.MESSAGE";
    String message;
    String eAlerta;
    String eSaldo;
    public Home(){
        message="";
        eAlerta="";
        eSaldo="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        message = intent.getStringExtra(Logeo.EXTRA_MESSAGE);
        TextView cuenta=(TextView) findViewById(R.id.infocuenta);
        cuenta.append(": "+message);

        new HandlerSaldo().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void irCompra(View view){
        TextView saldo=(TextView) findViewById(R.id.infocuenta);
        String[] msn=new String[3];
        msn[0]=message;//nroCuenta
        msn[1]=eAlerta;//20%
        msn[2]=eSaldo;//Saldo
        Intent intent = new Intent(this, Compra.class);
        intent.putExtra(EXTRA_MESSAGE, msn);
        startActivity(intent);
    }

    public void irRecarga(View view){
        Intent intent = new Intent(this, Recarga.class);
        startActivity(intent);
    }

    public void irReporte(View view){
        Intent intent = new Intent(this, Reporte.class);
        startActivity(intent);
    }

    public  void salirHome(View view){
        finish();
    }






    public class HandlerSaldo extends AsyncTask<Void, Void, String[]> {

        protected String[] doInBackground(Void... urls){
            Intent intent = getIntent();
            String message = intent.getStringExtra(Logeo.EXTRA_MESSAGE);
            BigInteger bi = new BigInteger(""+Integer.parseInt(message,16));
            String nroCuenta = bi.toString(2);
            String[] result=new String[2];
            String urlSaldo="/Saldo.php?cuenta="+nroCuenta+"&option=saldo";
            String urlAlerta="/Saldo.php?cuenta="+nroCuenta+"&option=alerta";

            try{
                JSONObject jObjSaldo = new Conexion().invocarHttpClient(urlSaldo);
                JSONObject jObjAlerta = new Conexion().invocarHttpClient(urlAlerta);

                result[0] = jObjSaldo.getString("saldo");
                result[1] = jObjAlerta.getString("alerta");

            }catch(Exception e){

                result[0]="--";
                result[1]="--";
            }

            return result;

        }

        protected void onPostExecute(String[] getResult) {
            TextView saldo=(TextView) findViewById(R.id.infocuenta);
            saldo.append(" Saldo: "+getResult[0]);

            if(getResult[1].equals("1")){
            AlertDialog.Builder alert = new AlertDialog.Builder(Home.this);
            alert.setTitle("Saldo");
            alert.setMessage("Su saldo se encuentra en el 20%");
            alert.setPositiveButton("OK", null);
            alert.show();
            }
            eSaldo=getResult[0];
            eAlerta=getResult[1];
        }


    }



}
