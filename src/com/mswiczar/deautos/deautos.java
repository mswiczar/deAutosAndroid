package com.mswiczar.deautos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


/*
    	deautosApp app = (deautosApp) getApplication();
        app.tracker.trackPageView("/androidBannerClick");
 */

public class deautos extends Activity {
	/** Called when the activity is first created. */

	
	
	DataBaseHelper myDbHelper;
	
	ArrayList<String> marcasID = new ArrayList<String>();
	ArrayList<String> marcas = new ArrayList<String>();
	ArrayAdapter<String> thearrMarcas;
    

	ArrayList<String> modelosID = new ArrayList<String>();
	ArrayList<String> modelos = new ArrayList<String>();
	ArrayAdapter<String> thearrModelos;

	
	
	ArrayList<String> provinciasID = new ArrayList<String>();
	ArrayList<String> provincias = new ArrayList<String>();
	ArrayAdapter<String> thearrProvincias;
	
	ArrayList<String> localidadesID = new ArrayList<String>();
	ArrayList<String> localidades = new ArrayList<String>();
	ArrayAdapter<String> thearrLocalidades;

	
	ArrayList<String> combustiblesID = new ArrayList<String>();
	ArrayList<String> combustibles = new ArrayList<String>();
	ArrayAdapter<String> thearrCombustibles;

	
	ArrayList<String> anos = new ArrayList<String>();
	ArrayAdapter<String> thearrAnos;

	
	
	
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	deautosApp app = (deautosApp) getApplication();
        
    	app.tracker.start("UA-11714679-1", 10, this);
        app.tracker.trackPageView("/androidStart");
        setContentView(R.layout.main);
       
        myDbHelper = new DataBaseHelper(this);
        try 
        {
        	myDbHelper.createDataBase();
        } 
        catch (IOException ioe) 
        {
        	throw new Error("Unable to create database");
        }
        
        
 	
 	try 
 	
 	{
     		myDbHelper.openDataBase();
    		

 		
 		
 		Cursor curmarcas;

 		curmarcas = myDbHelper.createCursorMarcas();
 		curmarcas.moveToFirst();
 		while (!curmarcas.isAfterLast())
 		{
 			marcasID.add(""+curmarcas.getInt(0));
 		 	marcas.add(curmarcas.getString(1)) ;
 		 	curmarcas.moveToNext();
 		}
 		curmarcas.close();
    	thearrMarcas = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, marcas);

    	Cursor curprovincias = myDbHelper.createCursorProvincias();
    	curprovincias.moveToFirst();
 		while (!curprovincias.isAfterLast())
 		{
 			provinciasID.add(""+curprovincias.getInt(0));
 		 	provincias.add(curprovincias.getString(1)) ;
 		 	curprovincias.moveToNext();
 		}
 		curprovincias.close();
    	thearrProvincias = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, provincias);
    	
    	
    	Cursor curcombustible = myDbHelper.createCursorCombustible();
    	curcombustible.moveToFirst();
 		while (!curcombustible.isAfterLast())
 		{
 			combustiblesID.add(""+curcombustible.getInt(0));
 		 	combustibles.add(curcombustible.getString(1)) ;
 		 	curcombustible.moveToNext();
 		}
 		curcombustible.close();
 		thearrCombustibles = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, combustibles);

 		Calendar toDay = Calendar.getInstance();
 		int year = toDay.get(Calendar.YEAR);
 		
 		for (int i=(year+1) ; i>=1980 ; i --)
 		{
 			anos.add(""+i) ;
 		}
 		thearrAnos = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, anos);

    	
    	
 		}catch(SQLException sqle){
 
 			throw sqle;
 
 		}

        final TextView buttonMarca = (TextView) findViewById(R.id.textMarca);
        if (app.idmarcaChoosen=="0")
        {
            buttonMarca.setText  (app.strMarcaDefault);
        }
        else
        {
            buttonMarca.setText  (app.strMarca);
        }
        buttonMarca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("Marca");
            	builder.setAdapter(thearrMarcas, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strMarca = marcas.get(item);
            	    	app.idmarcaChoosen= marcasID.get(item);
            	    	app.idmodeloChoosen="0";
            	    	app.strModelo="";
            	    	buttonMarca.setText(app.strMarca);
            	        final TextView buttonModelo = (TextView) findViewById(R.id.textModelo);
            	        buttonModelo.setText(app.strModeloDefault);
            	    }});
            	builder.show();
            }
        });

        
        final TextView buttonProvincias = (TextView) findViewById(R.id.textProvincia);
        if (app.idProvinciaChoosen=="0")
        {
        	buttonProvincias.setText  (app.strProvinciaDefault);
        }
        else
        {
        	buttonProvincias.setText  (app.strProvincia);
        }
        buttonProvincias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("Provincia");
            	builder.setAdapter(thearrProvincias, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strProvincia =provincias.get(item);
            	    	app.idProvinciaChoosen=provinciasID.get(item);
            	    	app.idLocalidadChoosen="0";
            	    	app.strLocalidad="";
            	    	buttonProvincias.setText(app.strProvincia);
            	        final TextView buttonLocalidad = (TextView) findViewById(R.id.textLocalidad);
            	        buttonLocalidad.setText(app.strLocalidadDefault);
            	    }});
            	builder.show();
            }
        });

        
        final TextView buttonCombustible = (TextView) findViewById(R.id.textCombustible);
        if (app.idCombustibleChoosen=="0")
        {
        	buttonCombustible.setText  (app.strCombustibleDefault);
        }
        else
        {
        	buttonCombustible.setText  (app.strCombustible);
        }
        buttonCombustible.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("Combustible");
            	builder.setAdapter(thearrCombustibles, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strCombustible = combustibles.get(item);
            	    	buttonCombustible.setText(app.strCombustible);
            	    	app.idCombustibleChoosen=combustiblesID.get(item);
            	    }});
            	builder.show();
            }
        });
        
      
        
        
        
        final TextView buttonADesde = (TextView) findViewById(R.id.textADesde);
        if (app.idAnodesdeChoosen=="0")
        {
        	buttonADesde.setText  (app.strAnodesdeDefault);
        }
        else
        {
        	buttonADesde.setText  (app.strAnodesde);
        }
        buttonADesde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("A–o desde");
            	builder.setAdapter(thearrAnos, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strAnodesde=anos.get(item);
            	    	buttonADesde.setText(app.strAnodesde);
            	    	app.idAnodesdeChoosen=app.strAnodesde;
            	    }});
            	builder.show();
            }
        });
        
        
        final TextView buttonAHasta = (TextView) findViewById(R.id.textAHasta);
        if (app.idAnohastaChoosen=="0")
        {
        	buttonAHasta.setText  (app.strAnohastaDefault);
        }
        else
        {
        	buttonAHasta.setText  (app.strAnohasta);
        }
        buttonAHasta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("A–o hasta");
            	builder.setAdapter(thearrAnos, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strAnohasta=anos.get(item);
            	    	buttonAHasta.setText(app.strAnohasta);
            	    	app.idAnohastaChoosen=app.strAnohasta;
            	    }});
            	builder.show();
            }
        });
        
        
        
    	
        final TextView buttonModelo = (TextView) findViewById(R.id.textModelo);
        if (app.idmodeloChoosen=="0")
        {
        	buttonModelo.setText  (app.strModeloDefault);
        }
        else
        {
        	buttonModelo.setText  (app.strModelo);
        }
        buttonModelo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	modelos = new ArrayList<String>();
    	    	deautosApp app = (deautosApp) getApplication();
            	int marcaid = Integer.parseInt(app.idmarcaChoosen);
            	if(marcaid==0)
            	{
            		return;
            	}
            	Cursor curmodelos = myDbHelper.createCursorModelos(marcaid);
            	curmodelos.moveToFirst();
            	modelosID.clear();

         		while (!curmodelos.isAfterLast())
         		{
         			modelosID.add(""+curmodelos.getInt(0)) ;
         		 	modelos.add(curmodelos.getString(1)) ;
         		 	curmodelos.moveToNext();
         		}
         		curmodelos.close();
         		thearrModelos = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, modelos);
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("Modelos");
            	
            	builder.setAdapter(thearrModelos, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strModelo = modelos.get(item);
            	    	buttonModelo.setText(app.strModelo);
            	    	app.idmodeloChoosen = modelosID.get(item);
            	    	
            	    }});
            	builder.show();
            
            }
        });
        
          
        
        final TextView buttonLocalidad = (TextView) findViewById(R.id.textLocalidad);
        if (app.idLocalidadChoosen=="0")
        {
        	buttonLocalidad.setText  (app.strLocalidadDefault);
        }
        else
        {
        	buttonLocalidad.setText  (app.strLocalidad);
        }
        buttonLocalidad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	localidades = new ArrayList<String>();
    	    	deautosApp app = (deautosApp) getApplication();

            	int provid = Integer.parseInt(app.idProvinciaChoosen);

            	if(provid==0)
            	{
            		return;
            	}
            	localidadesID.clear();
            	Cursor curLocalidades = myDbHelper.createCursorLoc(provid);
            	curLocalidades.moveToFirst();
         		while (!curLocalidades.isAfterLast())
         		{
         			localidadesID.add(""+curLocalidades.getInt(0)) ;

         			localidades.add(curLocalidades.getString(1)) ;
         		 	curLocalidades.moveToNext();
         		}
         		curLocalidades.close();
         		thearrLocalidades = new ArrayAdapter<String>(deautos.this, android.R.layout.select_dialog_item, localidades);
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(deautos.this);
            	builder.setTitle("Localidades");
            	
            	builder.setAdapter(thearrLocalidades, new DialogInterface.OnClickListener() {
            	    public void onClick(DialogInterface dialog, int item) {
            	    	deautosApp app = (deautosApp) getApplication();
            	    	app.strLocalidad = localidades.get(item);
            	    	buttonLocalidad.setText(app.strLocalidad);
            	    	app.idLocalidadChoosen=localidadesID.get(item);
            	    }});
            	   
            	builder.show();
            
            }
        });
        
        final TextView buttonNuevosUssados = (TextView) findViewById(R.id.textnuevos);

        if (app.nuevos)
        {
        	buttonNuevosUssados.setBackgroundDrawable(getResources().getDrawable(R.drawable.nuevos));
        	
        }
        else
        {
        	buttonNuevosUssados.setBackgroundDrawable(getResources().getDrawable(R.drawable.usados));
        }
        
        buttonNuevosUssados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
    	    	deautosApp app = (deautosApp) getApplication();
                if (app.nuevos)
                {
                	app.nuevos=false;
                	buttonNuevosUssados.setBackgroundDrawable(getResources().getDrawable(R.drawable.usados));

                	
                }
                else
                {
                	app.nuevos=true;
                	buttonNuevosUssados.setBackgroundDrawable(getResources().getDrawable(R.drawable.nuevos));
                }
            
            
            }
        });

        
        final Button button = (Button) findViewById(R.id.buttonBuscar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    	    	deautosApp app = (deautosApp) getApplication();
    	    	app.needreload=true;
    	    	app.urlToBitmap.clear();
    	    	
    	    	
            	Intent intent = new Intent(deautos.this, resultSearch.class);
            	startActivity(intent);
            }
        });
    
       
        
    }  
    

}


