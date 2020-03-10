package com.mswiczar.deautos;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.Settings.Secure;

public class contacto extends Activity {
    /** Called when the activity is first created. */
	
	 public static final String PREFS_NAME = "deautosPrefsContacto";
	 
		private Runnable viewOrders;
		ProgressDialog dialog;
		Thread thread;
		Bitmap abitCaptcha;
	 
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
		
		
	    public Bitmap DownloadImage(String URL)
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
	    
	    
	    public void postData() {
	        // Create a new HttpClient and Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        Boolean nuevos = true;
	        String theurltocall;
            String android_id = Secure.getString(getBaseContext().getContentResolver(),
                    Secure.ANDROID_ID);

	    	if(nuevos)
	    	{
	    		theurltocall="http://iphonedata.deautos.com/nuevos/Contactar/?UID="+android_id; 
	    	}
	    	else
	    	{
	    		theurltocall="http://iphonedata.deautos.com/Usados/Contactar/?UID="+android_id; 
	    		
	    	}

	        
	        
	        HttpPost httppost = new HttpPost(theurltocall);

	        try {
	            // Add your data
	        	
	            final EditText editapellido = (EditText) findViewById(R.id.editnombre);
	            final EditText edittelefono = (EditText) findViewById(R.id.edittel);
	            final EditText editemail = (EditText) findViewById(R.id.editemail);
	            final EditText editcomments = (EditText) findViewById(R.id.editcomments);
	            final EditText editcaptcha = (EditText) findViewById(R.id.editcaptcha);

	            deautosApp app = (deautosApp) getApplication();
	            
	        	
	            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("Id", app.themapaviso.get("id")));
	            nameValuePairs.add(new BasicNameValuePair("nombre", editapellido.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("telefono", edittelefono.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("email", editemail.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("comentario", editcomments.getText().toString()));
	            nameValuePairs.add(new BasicNameValuePair("captcha", editcaptcha.getText().toString()));

	            
	           /*
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"Id=%@&",[diccionario objectForKey:@"aviso_id"]]];
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"nombre=%@&",[diccionario objectForKey:@"TextApellido"]]];
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"telefono=%@&",[diccionario objectForKey:@"TextTel"]]];
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"email=%@&",[diccionario objectForKey:@"TextEmail"]]];
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"comentario=%@&",[diccionario objectForKey:@"TextComentarios"]]];
	        	string = [string stringByAppendingString:[NSString stringWithFormat:@"captcha=%@",[diccionario objectForKey:@"captcha"]]];
	            */
	            
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	            String responseBody = EntityUtils.toString(response.getEntity());
	            Log.d("deautos", "response: " + responseBody);
	            
	            
	        } catch (ClientProtocolException e) {
	        } catch (IOException e) {
	        }
	    } 

	    
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	deautosApp app = (deautosApp) getApplication();
        app.tracker.trackPageView("/androidContactar");

        setContentView(R.layout.contacto);
   
    
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String  apellido = settings.getString("apellido", "");
        String  telefono = settings.getString("telefono", "");
        String  email = settings.getString("email", "");
        
        
        final EditText editapellido = (EditText) findViewById(R.id.editnombre);
        final EditText edittelefono = (EditText) findViewById(R.id.edittel);
        final EditText editemail = (EditText) findViewById(R.id.editemail);
        


        
        
        
        editapellido.setText(apellido);
        edittelefono.setText(telefono);
        editemail.setText(email);
        
        
        
      
        
        final Button buttonRefreshCaptcha = (Button) findViewById(R.id.botonrefresh);
        buttonRefreshCaptcha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	thread =  new Thread(null, viewOrders, "MagentoBackground");
                thread.start();
                dialog = ProgressDialog.show(contacto.this, "", 
                        "Obteniendo Captcha. Aguarde por favor...", true);
            
            }
        });

     viewOrders = new Runnable(){
            
            public void run() {
            	getImagesFromWeb();
            }
        };
        
        thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        dialog = ProgressDialog.show(contacto.this, "", 
                "Obteniendo Captcha. Aguarde por favor...", true);
        
     
    final TextView button = (TextView) findViewById(R.id.buttonContactar);
    button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	
            final EditText editapellido = (EditText) findViewById(R.id.editnombre);
            final EditText edittelefono = (EditText) findViewById(R.id.edittel);
            final EditText editemail = (EditText) findViewById(R.id.editemail);

        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        	SharedPreferences.Editor editor = settings.edit();
        	
        	editor.putString("apellido",editapellido.getText().toString());
        	editor.putString("telefono",edittelefono.getText().toString());
        	editor.putString("email",editemail.getText().toString());
        	
        	editor.commit();        	
        	
        	Intent intent = new Intent(contacto.this, gracias.class);
        	startActivity(intent);
        	contacto.this.finish();
        }
    });
    
    }
    
    @Override
    protected void onStop(){
   		thread.stop();
    	dialog.dismiss();
       	final EditText editapellido = (EditText) findViewById(R.id.editnombre);
       	final EditText edittelefono = (EditText) findViewById(R.id.edittel);
       	final EditText editemail = (EditText) findViewById(R.id.editemail);

       	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
   		SharedPreferences.Editor editor = settings.edit();
   	
   		editor.putString("apellido",editapellido.getText().toString());
   		editor.putString("telefono",edittelefono.getText().toString());
   		editor.putString("email",editemail.getText().toString());
   		editor.commit();        	
        super.onStop();

       
    }   
    
    

    
    
    private void getImagesFromWeb()
	{
          try{
                String android_id = Secure.getString(getBaseContext().getContentResolver(),
                                                                        Secure.ANDROID_ID);
                abitCaptcha =  contacto.this.DownloadImage("http://iphonedata.deautos.com/captcha.aspx?UID="+android_id);

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
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
        	dialog.dismiss();
	        final ImageView buttonRefreshCaptcha = (ImageView) findViewById(R.id.imgcaptcha);
	        if(abitCaptcha!=null)
            {
            	buttonRefreshCaptcha.setImageBitmap(abitCaptcha);
            }
        	
        }
    };
    
    
    
}
