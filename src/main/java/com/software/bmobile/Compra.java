package com.software.bmobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.math.BigInteger;


public class Compra extends Activity {
    String[] anwMessage;
    String op;
    public Compra(){
        anwMessage=new String[3];
        op="";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        // Show the Up button in the action bar.
        setupActionBar();

        Intent intent = getIntent();
        anwMessage = intent.getStringArrayExtra(Home.EXTRA_MESSAGE);
        alertaSaldo();
    }

    public void alertaSaldo(){
        if(anwMessage[1].equals("1")){
            AlertDialog.Builder alert = new AlertDialog.Builder(Compra.this);
            alert.setTitle("Saldo");
            alert.setMessage("Su saldo se encuentra en el 20%");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compra, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void volverHomeCompra(View view){
        finish();
    }

    public void setRadio(View view){

        RadioButton rAlimento = (RadioButton)findViewById(R.id.radioButton);
        RadioButton rVestido= (RadioButton)findViewById(R.id.radioButton2);
        RadioButton rEstudio = (RadioButton)findViewById(R.id.radioButton3);
        RadioButton rOcio = (RadioButton)findViewById(R.id.radioButton4);

       // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    op="01";
                    // Pirates are the best

                    break;
            case R.id.radioButton2:
                if (checked)
                    op="02";
                    // Ninjas rule
                    break;
            case R.id.radioButton3:
                if (checked)
                    op="03";
                    // Ninjas rule
                    break;
            case R.id.radioButton4:
                if (checked)
                    op="04";
                    // Ninjas rule
                    break;

        }


    }

    public void realizarCompra(View view){
        Double saldo=Double.parseDouble(anwMessage[2]);
        if(saldo>captarMontoCompra() && !captarMontoCompra().equals("-")){
            new HandlerCompra().execute();
        }else{
            Toast.makeText(getApplicationContext(), "Ingrese Datos Validos",Toast.LENGTH_SHORT).show();
        }
    }

    //A PARTIR DE AQUI AGREGUE


    public Double captarMontoCompra(){
        Double montoF=0.0;
        try{EditText monto=(EditText)findViewById(R.id.monto);
        montoF= Double.parseDouble(monto.getText().toString());
        }catch (Exception e){

        }
        return montoF;
    }

    public String captarNumCuentadeCompras(){
        EditText numCta=(EditText)findViewById(R.id.numCuenta);
        String nroCuenta="-";
        //Double numCtaF= Double.parseDouble(numCta.getText().toString());
        nroCuenta=numCta.getText().toString();
        return nroCuenta;
    }

    public class HandlerCompra extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... urls){

            BigInteger biA = new BigInteger(""+Integer.parseInt(anwMessage[0],16));
            String nroCuentaA = biA.toString(2);
            BigInteger biB = new BigInteger(""+Integer.parseInt(captarNumCuentadeCompras(),16));
            String nroCuentaB = biB.toString(2);

            String url="/Movimiento.php?monto="+captarMontoCompra()+"&cuentaA="+nroCuentaA+"&cuentaB="+nroCuentaB+"&tipo="+op;
            String answer="";
            try{
                JSONObject jObj = new Conexion().invocarHttpClient(url);
                answer="1";

            }catch(Exception e){
                answer="2";
            }

            return answer;

        }

        protected void onPostExecute(String getResult) {
            String realizado="";

            if(getResult.equals("1")){
                realizado="La Compra se realizo con éxito";
            }else {
                realizado="No se realizo la compra";
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(Compra.this);
            alert.setTitle("Saldo");
            alert.setMessage(realizado);
            alert.setPositiveButton("OK", null);
            alert.show();

        }


    }




}
