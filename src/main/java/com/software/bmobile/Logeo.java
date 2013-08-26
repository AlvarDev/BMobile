package com.software.bmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;



public class Logeo extends Activity {
    public final static String EXTRA_MESSAGE = "com.software.bmobile.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logeo);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logeo, menu);
        return true;
    }

    public void ingresar(View view){
        new Handler().execute();

    }

    public class Handler extends AsyncTask<Void, Void, Integer> {

        protected Integer doInBackground(Void... urls){

            EditText usuario= (EditText) findViewById(R.id.usuario);
            EditText clave= (EditText) findViewById(R.id.clave);

            //String url="http://69.73.171.10/~fiis/PROYECTOPHPSISTEMAMANTENIMIENTO/CONTROLADOR/ValidarAcceso.php?txtusuario=ivan&txtclave=123";
            //String url="http://192.168.1.34:8083/BMovil/Login.php?usuario="+usuario.getText()+"&clave="+clave.getText();
            String url="/Login.php?usuario="+usuario.getText()+"&clave="+clave.getText();
            try{
                JSONObject jObj = new Conexion().invocarHttpClient(url);
                return Integer.parseInt(jObj.getString("estado"));

            }catch(Exception e){
                return 2;
            }



        }

        protected void onPostExecute(Integer result) {
            /*Intent intent = new Intent(getApplicationContext(), Home.class);
            //Intent intent = new Intent(this, Home.class);
            EditText usuario= (EditText) findViewById(R.id.usuario);
            String message = usuario.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
            finish();*/

            if(result==1){
                Intent intent = new Intent(getApplicationContext(), Home.class);
                //Intent intent = new Intent(this, Home.class);
                EditText usuario= (EditText) findViewById(R.id.usuario);
                String message = usuario.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                finish();
            }else if(result==0){
                Toast.makeText(getApplicationContext(), "Usuario o Clave incorrecto",Toast.LENGTH_SHORT).show();
                EditText usuario= (EditText) findViewById(R.id.usuario);
                EditText clave= (EditText) findViewById(R.id.clave);
                usuario.setText("");
                clave.setText("");
            }else {
                Toast.makeText(getApplicationContext(), "No se conecto con server",Toast.LENGTH_SHORT).show();
            }
        }


    }





}
