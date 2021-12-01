package com.example.jubblertechnologies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //se crea un objeto RequestQueue para poder llamar a la request con el URL
    private RequestQueue mQueue;

    //se crea un String de varias dimensiones que contiene los nombres mostrados en el Spinner
    String[] cotizaciones = { "Dolar Oficial", "Dolar Blue", "Dolar Soja", "Dolar Contado con Liqui",
            "Dolar Bolsa", "Bitcoin", "Dolar Turista"};

    // Objetos TextView proximos a ser referenciados con la parte visual de la app
    TextView textNombre;
    TextView textCompra;
    TextView textVenta;
    TextView textVariacion;

    //Se crea un ArrayList global de objetos tipo Casa
    ArrayList<Casa> casasArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //se le dice a Volley que va a ser esta clase quien solicite la request y se crea una instancia
        mQueue = Volley.newRequestQueue(this);
        //se le asigna el id a los TextView
        textNombre = (TextView) findViewById(R.id.textView);
        textCompra = (TextView) findViewById(R.id.textView4);
        textVenta = (TextView) findViewById(R.id.textView5);
        //Se crea un Spinner y se le busca el id
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //se crea un ArrayAdapter para el Spinner
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cotizaciones);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //se setea el ArrayAdapter en el Spinner
        spin.setAdapter(aa);

        //se llama al metodo que trabaja con JSON
        jsonParse();
    }

    private void jsonParse(){

        //declaramos un string que contiene la URL a la cual nos vamos a conectar
        String url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";

        //se crea la request con los parametros de GET y que se obtenga en el Listener un JSONArray
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            //Se obtiene la respuesta y se ejecuta un try catch
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Toast.makeText(getApplicationContext(), "El URL se leyo correctamente", Toast.LENGTH_LONG).show();

                    //se crea un objeto tipo JSONArray con la configuracion obtenida por la URL
                    JSONArray jsonArray = response;
                    //se ejecuta un for que indexa el Array
                    for(int i = 0; i < jsonArray.length(); i++){
                        //se crea un objeto tipo JSONObject y se setea con los objetos almacenados en el Array del URL
                        JSONObject jsonCasa =  jsonArray.getJSONObject(i);

                        //se crean variables String que seran seteadas con la informacion del JSON
                        String nombre = jsonCasa.getJSONObject("casa").getString("nombre");
                        String compra = jsonCasa.getJSONObject("casa").getString("compra");
                        String venta = jsonCasa.getJSONObject("casa").getString("venta");
                        String variacion = jsonCasa.getJSONObject("casa").getString("variacion");

                        //se crea un objeto tipo Casa y se le setea los valores de las variables creadas anteriormente
                        Casa casa = new Casa();
                        casa.setNombre(nombre);
                        casa.setCompra(compra);
                        casa.setVenta(venta);
                        casa.setVariacion(variacion);

                        //se agrega el objeto casa al Array global sentenciado al principio del codigo
                        casasArray.add(casa);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    //Metodo que se ejecuta cuando se selecciona un item del Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //en el for se compara el in position del metodo con el i del for, si es igual devuelve el valor de ese tipo de moneda
       for( int i = 0; i < casasArray.size(); i++) {

           if(i == position ){
               textNombre.setText(casasArray.get(i).nombre);
               textCompra.setText(casasArray.get(i).compra);
               textVenta.setText(casasArray.get(i).venta);


           }
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textNombre.setText(casasArray.get(1).nombre);
        textCompra.setText(casasArray.get(1).compra);
        textVenta.setText(casasArray.get(1).venta);
    }
}