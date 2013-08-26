package com.software.bmobile;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Conexion {

    public Conexion(){

    }

    public JSONObject invocarHttpClient(String url) throws IOException, JSONException {
        JSONObject  jObj = null;
        InputStream iStream = null;
        try{
            HttpGet hGet = new HttpGet("http://10.0.2.2:8083/BMovil"+url);
            HttpClient hClient = new DefaultHttpClient();
            HttpResponse response = (HttpResponse)hClient.execute(hGet);
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity buffer = new BufferedHttpEntity(entity);
            iStream = buffer.getContent();
            String aux= "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
            String line;
            while((line = reader.readLine()) != null){
                aux +=line;
            }
            jObj = new JSONObject(aux);
        }
        catch(Exception ex){
            jObj = new JSONObject();
        }
        finally{
            if(iStream != null){
                iStream.close();
            }
        }
        return jObj;
    }

}
