package com.mswiczar.deautos;

import android.app.Activity; 
import android.os.Bundle; 
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class imagenes extends Activity {
    /** Called when the activity is first created. */
	WebView browser;
	
    private  static final FrameLayout.LayoutParams ZOOM_PARAMS =

    	new FrameLayout.LayoutParams(

    	ViewGroup.LayoutParams.FILL_PARENT,

    	ViewGroup.LayoutParams.WRAP_CONTENT,

    	Gravity.BOTTOM); 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	deautosApp app = (deautosApp) getApplication();
        app.tracker.trackPageView("/androidImagenesAuto");

        
        setContentView(R.layout.imagenes);
    	browser=(WebView)findViewById(R.id.webkit);
    	browser.getSettings().setSupportZoom(true);
    	browser.setInitialScale(10);
    	FrameLayout mContentView = (FrameLayout) getWindow().

        getDecorView().findViewById(android.R.id.content);
    	
    	 final View zoom = this.browser.getZoomControls();
    	 mContentView.addView(zoom, ZOOM_PARAMS);

    	 zoom.setVisibility(View.GONE);
    	 
       
    	 if (app.showall)
    	 {
    		 if(app.nuevos)
    		 {
    			 browser.loadUrl("http://www.deautos.com/nuevos/gallery.asp?id=" + app.themapaviso.get("id"));
    		 }
    		 else
    		 {
    			 browser.loadUrl("http://www.deautos.com/usados/gallery.asp?id=" + app.themapaviso.get("id"));
    		 }
    	 }
    	 else
    	 {
    		 browser.loadUrl(app.urlimagetoshow);
    	 }
    	 
    	
    	
    }
}

