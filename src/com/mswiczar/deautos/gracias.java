package com.mswiczar.deautos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class gracias extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	deautosApp app = (deautosApp) getApplication();
        app.tracker.trackPageView("/androidMensajeEnviado");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.gracias);
    
    
        final TextView textInicio = (TextView) findViewById(R.id.textInicio);
        textInicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	gracias.this.finish();

            
            }
        });
   

        final TextView textvolver = (TextView) findViewById(R.id.textvolver);
        textvolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//this.
//            	viewauto.this.finish();
            	gracias.this.finish();
            	

            
            }
        });
 
        
        
        
    }
    
    
}
