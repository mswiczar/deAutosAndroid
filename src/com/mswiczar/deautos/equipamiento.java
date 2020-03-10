package com.mswiczar.deautos;

	import android.app.Activity;
import android.os.Bundle;
	//import android.view.View;
	//import android.widget.ArrayAdapter;
	//import android.widget.Button;
	//import android.widget.Spinner;
	import android.webkit.WebView;

	
	
	public class equipamiento extends Activity {
		WebView browser;
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    	deautosApp app = (deautosApp) getApplication();
	        app.tracker.trackPageView("/androidDetalleEquipamiento");

	        setContentView(R.layout.equipamiento);
	        String accesorios = "<br><br><br>El presente aviso no posee informacion.<br><br><br><br><br><br><br><br><br><br><br><br><br>";
	        
			browser=(WebView)findViewById(R.id.webkit);
			String data = "<html><head></head><body><br><br><center>"+accesorios+"</center></body></html>";
			browser.loadData(data
					,"text/html", "UTF-8"
				);
	    }
	}

	
	
	