package com.mswiczar.deautos;

import java.util.HashMap;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Application;
//import android.util.Log;
import android.graphics.Bitmap;


public class deautosApp extends Application {
	public String idmarcaChoosen="0";
	public String idmodeloChoosen="0";
	public String idCombustibleChoosen="0";
	public String idProvinciaChoosen="0";
	public String idLocalidadChoosen="0";
	public String idAnodesdeChoosen="0";
	public String idAnohastaChoosen="0";
	
	public int orden=1;
	

	public String strMarca="";
	public String strModelo="";
	public String strCombustible="";
	public String strProvincia="";
	public String strLocalidad="";
	public String strAnodesde="";
	public String strAnohasta="";
	
	
	public String strMarcaDefault="Marca";
	public String strModeloDefault="Modelo";
	public String strCombustibleDefault="Combustible";
	public String strProvinciaDefault="Provincia";
	public String strLocalidadDefault="Localidad";
	public String strAnodesdeDefault="A–o desde";
	public String strAnohastaDefault="A–o hasta";

	public Boolean nuevos=true;
	
	
	public HashMap<String,String> themapaviso;
	
	public Boolean needreload;
	public HashMap<String,Bitmap> urlToBitmap;
	public String urlimagetoshow;
	public Boolean  showall=false;
	
	
	public GoogleAnalyticsTracker tracker;

	public deautosApp ()
	{
		super();
        tracker = GoogleAnalyticsTracker.getInstance();
        urlToBitmap = new HashMap<String,Bitmap>();
	}
}