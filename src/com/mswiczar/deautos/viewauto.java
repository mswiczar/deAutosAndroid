package com.mswiczar.deautos;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class viewauto extends Activity {
    /** Called when the activity is first created. */
	
    private Gallery gallery;
    public int qimages;
    public Boolean hasaccesorios=false;
    public int numero;
	private Runnable viewOrders;
	ProgressDialog dialog;

    public ArrayList<Bitmap>  listImages = new ArrayList<Bitmap>() ;
    Thread thread;
    AddImgAdp m_adapter;

    
    
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
    
    
    
    public class AddImgAdp extends BaseAdapter {
        int GalItemBg;
        private Context cont;
        // 	Adding images.
        
        public AddImgAdp(Context c) {
            cont = c;
            TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
            GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
            typArray.recycle();
        }
        public int getCount() {
            return listImages.size();
        }
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imgView = new ImageView(cont);
            imgView.setImageBitmap(listImages.get(position));

            imgView.setLayoutParams(new Gallery.LayoutParams(250, 125));
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgView.setBackgroundResource(GalItemBg);
            return imgView;
        }
    }
    
    @Override
    protected void onStop(){
    	thread.stop();
    	dialog.dismiss();
        super.onStop();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	deautosApp app = (deautosApp) getApplication();
        app.tracker.trackPageView("/androidDetalleAuto");
        setContentView(R.layout.viewauto);
        
        listImages.clear();
        qimages =Integer.parseInt(app.themapaviso.get("qimages"));
        numero  =Integer.parseInt(app.themapaviso.get("id"));
        
        viewOrders = new Runnable(){
            
            public void run() {
            	getImagesFromWeb();
            }
        };

        
        
         thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        dialog = ProgressDialog.show(viewauto.this, "", 
                "Obteniendo imagenes. Aguarde por favor...", true);

        m_adapter = new AddImgAdp(this);
        
        gallery = (Gallery) findViewById(R.id.examplegallery);
                 gallery.setAdapter(m_adapter);
                 gallery.setOnItemClickListener(new OnItemClickListener() {

     				public void onItemClick(AdapterView<?> arg0, View arg1,
     						int arg2, long arg3) {
     			    	deautosApp app = (deautosApp) getApplication();

     	                 if  (app.nuevos)
     	                 {
     	                	app.urlimagetoshow =  "http://www.deautos.com/images/fotos0KM/"+numero+"_"+(arg2+1)+"g.jpg";
     	                 }
     	                 else
     	                 {
     	                	app.urlimagetoshow =  "http://www.deautos.com/images/fotosplaya/"+numero+"_"+(arg2+1)+"g.jpg";
     	                 }

                        final ImageView buttonimagen = (ImageView) findViewById(R.id.noimage);
                        buttonimagen.setImageBitmap(listImages.get(arg2));
     	      			
     				}
     	        	 }); 

                 
                 if (qimages==0)
                 {
                	 gallery.setVisibility(View.INVISIBLE); 
                 }
                 else
                 {
                	 gallery.setVisibility(View.VISIBLE); 
                 }
                 
                 
                 
         
                 final TextView buttonContacto = (TextView) findViewById(R.id.buttonContacto);
                 buttonContacto.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                    	 
                     	Intent intent = new Intent(viewauto.this, contacto.class);
                     	startActivity(intent);
                     }
                 });
                 

                 final TextView buttonImagenes = (TextView) findViewById(R.id.buttonFotosvertodas);
                 buttonImagenes.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                     	 deautosApp app = (deautosApp) getApplication();
                    	 app.showall=true;
                    	 
                     	Intent intent = new Intent(viewauto.this, imagenes.class);
                     	startActivity(intent);
                     }
                 });
                 
                 
                 final ImageView buttonimagen = (ImageView) findViewById(R.id.noimage);
                 buttonimagen.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                    	 if (qimages!=0)
                         {
                         	 deautosApp app = (deautosApp) getApplication();
                        	 app.showall=false;

                    		 Intent intent = new Intent(viewauto.this, imagenes.class);
                    		 startActivity(intent);
                         }
                     }
                 });


       
                 
                 String thestr;
                 
                 if  (app.nuevos)
                 {
              	   thestr =  "http://www.deautos.com/images/fotos0KM/"+numero+"_1c.jpg";
                 }
                 else
                 {
              	   thestr =  "http://www.deautos.com/images/fotosplaya/"+numero+"_1c.jpg";
                 }
 
                 
                 

  	    		Bitmap abit =  app.urlToBitmap.get(thestr);
  	    		if(abit==null)
  	    		{
  	    			abit = this.DownloadImage(thestr);
  	    			if (abit==null)
  	    			{
  	    				buttonimagen.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
  	    				abit = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
  	    				app.urlToBitmap.put(thestr, abit);
  	    			}
  	    			else
  	    			{
  	    				buttonimagen.setImageBitmap(abit);
  	    				app.urlToBitmap.put(thestr, abit);
  	    			}
  	    		}
  	    		else
  	    		{
  	    			buttonimagen.setImageBitmap(abit);
  	    		}
                 
                 final TextView buttoncaract = (TextView) findViewById(R.id.buttonCaracteristica);
                 buttoncaract.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                     	Intent intent = new Intent(viewauto.this, equipamiento.class);
                     	startActivity(intent);
                     }
                 });
                 
                  final TextView labelmodelo      = (TextView) findViewById(R.id.labelmodelo);
                  final TextView labelprov        = (TextView) findViewById(R.id.labelprov);
                  final TextView labelfromapago   = (TextView) findViewById(R.id.labelfromapago);
                  final TextView labelcolor       = (TextView) findViewById(R.id.labelcolor);
                  final TextView labelsegmento    = (TextView) findViewById(R.id.labelsegmento);
                  final TextView labelkm          = (TextView) findViewById(R.id.labelkm);
                  final TextView labelprecio      = (TextView) findViewById(R.id.labelprecio);
                  final TextView labelcombustible = (TextView) findViewById(R.id.labelcombustible);
                  final TextView labelano         = (TextView) findViewById(R.id.labelano);

                  // id
                  // modelo
                  // precio
                  // ano
                  // km
                  // combustible
                  // fpago
                  // color
                  // segmento
                  // provloc
                  // accesorios
                 // get("qimages");
                  
                  String modelo =app.themapaviso.get("marca") + "  " + app.themapaviso.get("modelo");
                  String prov ="Prov/Loc: " + app.themapaviso.get("provloc");
                  String pago ="Forma de pago: "+app.themapaviso.get("fpago");
                  String color ="Color: "+app.themapaviso.get("color");
                  String segmento ="Segmento: "+app.themapaviso.get("segmento");
                  String km ="km: "+app.themapaviso.get("km");
                  String precio ="Precio: "+app.themapaviso.get("precio");
                  String combustible ="Combustible: "+app.themapaviso.get("combustible");
                  String ano ="A–o: "+app.themapaviso.get("ano");

                  if (app.themapaviso.get("accesorios")=="")
                  {
                	  hasaccesorios=false;
                  }
                  else
                  {
                	  hasaccesorios=true;
                  }
                  
                  if (hasaccesorios)
                  {
                 	 buttoncaract.setVisibility(View.VISIBLE); 
                  }
                  else
                  {
                 	 buttoncaract.setVisibility(View.INVISIBLE); 
                  } 
                  
                  labelmodelo.setText(modelo);
                  labelprov.setText(prov);  
                  labelfromapago.setText(pago);
                  labelcolor.setText(color);
                  labelsegmento.setText(segmento);
                  labelkm.setText(km);
                  labelprecio.setText(precio);
                  labelcombustible.setText(combustible);
                  labelano.setText(ano);
                 
            }
    
		private void getImagesFromWeb()
		{
	          try{
	          		deautosApp app = (deautosApp) getApplication();
	          		int zzz;
          			String temp;
	          		for (zzz=1; zzz<=qimages;zzz++)
	          		{
	          			if  (app.nuevos)
	          			{
	          				temp = "http://www.deautos.com/images/fotos0KM/"+app.themapaviso.get("id")+"_"+zzz+"c.jpg";
	          			}
	          			else
	          			{
	          				temp = "http://www.deautos.com/images/fotosplaya/"+app.themapaviso.get("id")+"_"+zzz+"c.jpg";
	          			}
		          		Bitmap bm = viewauto.this.DownloadImage(temp);
		          		if (bm!=null)
		          		{
		          			listImages.add(bm);
		          		}
	          		}
	            } 
				catch (Exception e) { 
	              //  Log.e("BACKGROUND_PROC", e.getMessage());
	            }
	            	runOnUiThread(returnRes);
		}        

	    private Runnable returnRes = new Runnable() 
	    {
	        public void run() {
	        	try {
					Thread.sleep(10);
					m_adapter.notifyDataSetChanged();
	                 final TextView buttonImagenes = (TextView) findViewById(R.id.buttonFotosvertodas);

			          if (qimages==0)
		                 {
		                	 buttonImagenes.setVisibility(View.INVISIBLE); 
		                 }
		                 else
		                 {
		                	 buttonImagenes.setVisibility(View.VISIBLE); 
		                 }
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	dialog.dismiss();
	        }
	    };
}   
   

        
       
        
        
 
       
