package com.mswiczar.deautos;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class resultSearch extends Activity {
	    /** Called when the activity is first created. */
		ArrayList<HashMap<String,String> > listItems = new ArrayList<HashMap<String,String>>() ;
		ProgressDialog dialog;
		private Runnable viewOrders;
		OrderAdapter m_adapter;
		public int cantidad;
		Thread thread;
		
		
		
	    @Override
	    protected void onStop(){
	    	thread.stop();
	    	dialog.dismiss();
	        super.onStop();
	    }

	       
		
 	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        cantidad=0;
        	

	    	deautosApp app = (deautosApp) getApplication();
	        app.tracker.trackPageView("/androidResultadoBusqueda");
	        setContentView(R.layout.resultsearch);
	        app.orden=1;
	        
	        final TextView buttonOrden = (TextView) findViewById(R.id.textLists);
	        buttonOrden.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	    	    	deautosApp app = (deautosApp) getApplication();
	    	    	if (app.orden==6)
	    	    	{
	    	    		app.orden=1;
	    	    	}
	    	    	else
	    	    	{
	    	    		app.orden++;
	    	    		
	    	    	}
	            	switch (app.orden)
	            	{
	            		case 1:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda1));
	            			break;
	            		case 2:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda2));
            				break;
	            		case 3:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda3));
	            			break;
	            		case 4:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda4));
            				break;
	            		case 5:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda5));
            				break;
	            		case 6:
	    	            	buttonOrden.setBackgroundDrawable(getResources().getDrawable(R.drawable.topbusqueda6));
            				break;
	            	}
	            	listItems.clear();
	            	 cantidad=0;
	            	m_adapter.notifyDataSetChanged();
	            	
			        thread =  new Thread(null, viewOrders, "MagentoBackground");
			        thread.start();
			        dialog = ProgressDialog.show(resultSearch.this, "", 
		                    "Buscando avisos. Aguarde por favor...", true);

	            
	            }
	        });

	        
	        final ListView listavisos = (ListView) findViewById(R.id.list);
	        listavisos.setItemsCanFocus(false);
	        listavisos.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					if (arg2 <(listItems.size()-1))
					{
				    	deautosApp app = (deautosApp) getApplication();
				    	app.themapaviso = listItems.get(arg2);
				    	if  (app.nuevos)
				    	{
				    		app.urlimagetoshow = "http://www.deautos.com/images/fotos0KM/"+app.themapaviso.get("id")+"_1g.jpg";
				    	}
				    	else
				    	{
				    		app.urlimagetoshow = "http://www.deautos.com/images/fotosplaya/"+app.themapaviso.get("id")+"_1g.jpg";
				    	}
						Intent intent = new Intent(resultSearch.this, viewauto.class);
		      			startActivity(intent);
					}
					else
					{
				        thread =  new Thread(null, viewOrders, "MagentoBackground");
				        thread.start();
				        dialog = ProgressDialog.show(resultSearch.this, "", 
			                    "Buscando avisos. Aguarde por favor...", true);
						
					}
	      			
	      			
				}
	        	 });
	        
			m_adapter = new OrderAdapter(this, R.layout.row, listItems);
           
            listavisos.setAdapter(m_adapter);
	        
	        viewOrders = new Runnable(){
	            
	            public void run() {
	                getOrders();
	            }
	        };
	        thread =  new Thread(null, viewOrders, "MagentoBackground");
	        thread.start();
	        dialog = ProgressDialog.show(resultSearch.this, "", 
                    "Buscando avisos. Aguarde por favor...", true);
	    } 
	    
	    private Runnable returnRes = new Runnable() 
	    {
	    	
	        public void run() {
	        	try {
					Thread.sleep(100);
					m_adapter.notifyDataSetChanged();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
	        	dialog.dismiss();
	        	
	        	if (listItems.isEmpty())
	        	{
	        		AlertDialog alertDialog;
	        		alertDialog  = new AlertDialog.Builder(resultSearch.this).create();
	        		alertDialog.setTitle("deautos.com");
	        		alertDialog.setMessage("No se han encontrado resultados para esta busqueda!");
	        		alertDialog.setButton("Realizar otra", new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int which) {
	        			resultSearch.this.finish();
	        			return;

	        		} }); 
	        		alertDialog.show();
	        	}
	        	
	        	
	        }
	    };

    
		private void getOrders(){
	          try{
	        	  Thread.sleep(2000);
	        	  
	        	  
	        	  if (cantidad!=0)
	        	  {
		        	  	listItems.remove(cantidad);
	        	  }

	        	  
	        	  
	        	  HashMap<String,String> ahash;
	        	  
	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750301");
	        	  ahash.put("qimages", "1");
	        	  
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 83.000");
	        	  ahash.put("ano", "2011");
	        	  ahash.put("km", "0");
	        	  ahash.put("combustible", "Nafta");

	        	  
	        	  ahash.put("fpago", "Contado");
	        	  ahash.put("color", "Negro Azabache");
	        	  ahash.put("segmento", "Sedan 7 puertas");
	        	  ahash.put("provloc", "Capital Federal");
	        	  ahash.put("accesorios", "Todos");
 
	        	  
	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;
	        	  
	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750302");
	        	  ahash.put("qimages", "2");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 3000");
	        	  ahash.put("ano", "2006");
	        	  ahash.put("km", "10.000");
	        	  ahash.put("combustible", "Diesel");

	        	  ahash.put("fpago", "Cuotas");
	        	  ahash.put("color", "Gris Plata");
	        	  ahash.put("segmento", "Sedan 4 puertas");
	        	  ahash.put("provloc", "Capital Federal");
	        	  ahash.put("accesorios", "");
  
	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;


	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750303");
	        	  ahash.put("qimages", "5");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 25.000");
	        	  ahash.put("ano", "2007");
	        	  ahash.put("km", "135.500");
	        	  ahash.put("combustible", "Nafta");

	        	  ahash.put("fpago", "Contado");
	        	  ahash.put("color", "Azul");
	        	  ahash.put("segmento", "Sedan 4 puertas");
	        	  ahash.put("provloc", "Capital Federal");
	        	  ahash.put("accesorios", "");
	        	  
	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750304");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 9.000");
	        	  ahash.put("ano", "2008");
	        	  ahash.put("km", "1.100");
	        	  ahash.put("combustible", "Gas");
	        	  
	        	  ahash.put("fpago", "Cuotas");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Sedan 4 puertas");
	        	  ahash.put("provloc", "Capital Federal");
	        	  ahash.put("accesorios", "");


	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750305");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 13.600");
	        	  ahash.put("ano", "2009");
	        	  ahash.put("km", "90.000");
	        	  ahash.put("combustible", "Nafta");
	        	  
	        	  ahash.put("fpago", "Financiado");
	        	  ahash.put("color", "Verde Oscuro");
	        	  ahash.put("segmento", "Camioneta");
	        	  ahash.put("provloc", "Capital Federal");
	        	  ahash.put("accesorios", "");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;
	        	  
	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750306");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "$ 13.000");
	        	  ahash.put("ano", "2003");
	        	  ahash.put("km", "245.000");
	        	  ahash.put("combustible", "Gas");
	        	  
	        	  ahash.put("fpago", "Contado");
	        	  ahash.put("color", "Rojo");
	        	  ahash.put("segmento", "3 puertas");
	        	  ahash.put("provloc", "La Plata");
	        	  ahash.put("accesorios", "");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750307");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 31.000");
	        	  ahash.put("ano", "2002");
	        	  ahash.put("km", "1.000");
	        	  ahash.put("combustible", "Diesel");
	        	  ahash.put("fpago", "Financiacion");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Coupe");
	        	  ahash.put("provloc", "");
	        	  ahash.put("accesorios", "Muchos");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  // the more
	        	  
	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750307");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 31.000");
	        	  ahash.put("ano", "2002");
	        	  ahash.put("km", "1.000");
	        	  ahash.put("combustible", "Diesel");
	        	  ahash.put("fpago", "Financiacion");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Coupe");
	        	  ahash.put("provloc", "");
	        	  ahash.put("accesorios", "Muchos");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750307");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 31.000");
	        	  ahash.put("ano", "2002");
	        	  ahash.put("km", "1.000");
	        	  ahash.put("combustible", "Diesel");
	        	  ahash.put("fpago", "Financiacion");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Coupe");
	        	  ahash.put("provloc", "");
	        	  ahash.put("accesorios", "Muchos");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750307");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 31.000");
	        	  ahash.put("ano", "2002");
	        	  ahash.put("km", "1.000");
	        	  ahash.put("combustible", "Diesel");
	        	  ahash.put("fpago", "Financiacion");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Coupe");
	        	  ahash.put("provloc", "");
	        	  ahash.put("accesorios", "Muchos");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;

	        	  ahash = new  HashMap<String,String>();
	        	  ahash.put("id", "750307");
	        	  ahash.put("qimages", "1");
	        	  ahash.put("marca", "Audi");
	        	  ahash.put("modelo", "A3 TFSi");
	        	  ahash.put("precio", "U$S 31.100");
	        	  ahash.put("ano", "2002");
	        	  ahash.put("km", "1.000");
	        	  ahash.put("combustible", "Diesel");
	        	  ahash.put("fpago", "Financiacion");
	        	  ahash.put("color", "Blanco");
	        	  ahash.put("segmento", "Coupe");
	        	  ahash.put("provloc", "");
	        	  ahash.put("accesorios", "Muchos");

	        	  listItems.add(cantidad,ahash);
	        	  cantidad++;
	        	  
	        	  
	        	  ahash = new  HashMap<String,String>();
	        	  listItems.add(cantidad,ahash);

	            } catch (Exception e) { 
	              Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	            runOnUiThread(returnRes);
			}        
	
		
		 private class OrderAdapter extends ArrayAdapter<HashMap<String,String> > {

			 private InputStream OpenHttpConnection(String urlString) 
			    throws IOException
			    {
			        InputStream in = null;
			        int response = -1;
			               
			        URL url = new URL(urlString); 
			        URLConnection conn = url.openConnection();
			         
			        if (!(conn instanceof HttpURLConnection))                     
			            throw new IOException("Not an HTTP connection");
			        
			        try{
			            HttpURLConnection httpConn = (HttpURLConnection) conn;
			            httpConn.setAllowUserInteraction(false);
			            httpConn.setInstanceFollowRedirects(true);
			            httpConn.setRequestMethod("GET");
			            httpConn.connect(); 

			            response = httpConn.getResponseCode();                 
			            if (response == HttpURLConnection.HTTP_OK) {
			                in = httpConn.getInputStream();                                 
			            }                     
			        }
			        catch (Exception ex)
			        {
			            throw new IOException("Error connecting");            
			        }
			        return in;     
			    }
				
				
			    private Bitmap DownloadImage(String URL)
			    {        
			        Bitmap bitmap = null;
			        InputStream in = null;        
			        try {
			            in = OpenHttpConnection(URL);
			            if (in != null)
			            {
			            	bitmap =  BitmapFactory.decodeStream(in);
			            	in.close();
			            }
			        } catch (IOException e1) {
			            
			            e1.printStackTrace();
			        }
			        return bitmap;                
			    }
		  
		        private ArrayList<HashMap<String,String> > items;
		        public OrderAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String,String> > items) {
		                super(context, textViewResourceId, items);
		                this.items = items;
		        }
		      
		        @Override
		        public View getView(int position, View convertView, ViewGroup parent) {
		                View v = convertView;
		                
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    if (position<(items.size()-1))
	                    {
	                    	v = vi.inflate(R.layout.row, null);
	                    }
	                    else
	                    {
	                    	v = vi.inflate(R.layout.rowmore, null);
	                    }

		                if  (position>=(items.size()-1))
		                {
		                	return v;
		                }	
		                
		                
		                HashMap<String,String> o = items.get(position);
		                
		                if (o != null) 
		               {
		                   TextView tt = (TextView) v.findViewById(R.id.labelmodelo);
		                   TextView bt = (TextView) v.findViewById(R.id.labelano);
		                   TextView mt = (TextView) v.findViewById(R.id.labelprecio);
		                   TextView lt = (TextView) v.findViewById(R.id.labelkm);
		                   ImageView imv = (ImageView) v.findViewById(R.id.noimage);
		                   Bitmap abit=null;
		                   
		                   String numero= o.get("id");
		                   String thestr;
	                	   deautosApp app = (deautosApp) getApplication();

	                	   if  (app.nuevos)
		                   {
		                	   thestr =  "http://www.deautos.com/images/fotos0KM/"+numero+"_1c.jpg";
		                   }
		                   else
		                   {
				               thestr =  "http://www.deautos.com/images/fotosplaya/"+numero+"_1c.jpg";
		                   }

	                	   abit =  app.urlToBitmap.get(thestr);
	                	   if(abit==null)
	                	   {
	                		   abit = this.DownloadImage(thestr);
	                		   if (abit==null)
	                		   {
	                			   imv.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
	                			   abit = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
	                			   app.urlToBitmap.put(thestr, abit);
	                		   }
	                		   else
	                		   {
	                			   imv.setImageBitmap(abit);
	                			   app.urlToBitmap.put(thestr, abit);
	                		   }
	                	   }
	                	   else
	                	   {
                			   imv.setImageBitmap(abit);
	                	   }
	                	   
		                   tt.setText(o.get("marca") + " " +o.get("modelo") + " ("+o.get("combustible") +")");
		                   mt.setText("A–o: "+ o.get("ano"));
	                       bt.setText("Precio: "+o.get("precio"));
	                       lt.setText("Km: "+ o.get("km"));   
		               }
		               return v;
		        }
		 }
	}
	