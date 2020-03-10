package com.mswiczar.deautos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.mswiczar.deautos/databases/";
    private static String DB_NAME = "deautos.sql";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;

    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
    	(new File(DB_PATH)).mkdir();

    	
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
        try
        {
    
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}catch(SQLiteException e){
		 
		//database does't exist yet.

	}
    	
    }
 
    /*
		sqlite> .schema
		CREATE TABLE combustible (id integer, desc varchar(64));
		CREATE TABLE localidades (id integer , id_prov integer , nomloc varchar(128), nomzona varchar(128));
		CREATE TABLE marcas (id integer, desc varchar(128));
		CREATE TABLE modelos (id integer, desc varchar(128) , id_marca integer);
		CREATE TABLE provincias (id integer, pais_id integer, desc varchar(128),orden integer);
		CREATE INDEX idx_loc  on localidades (id_prov);
		CREATE INDEX idx_marcas on marcas (desc);
		CREATE INDEX idx_modelos on modelos (id_marca , desc);
		CREATE INDEX idx_provincias  on provincias  (pais_id,desc);
    */
    
    public Cursor createCursorMarcas () 
    {
    	return 	myDataBase.rawQuery("select id , desc as nombremarca from marcas order by desc", null);
    }
    
    
    public Cursor createCursorProvincias () 
    {
    	 
    	return myDataBase.rawQuery("select id , desc from provincias  where pais_id =1 order by orden ", null);
    }

    public Cursor createCursorModelos (int idmarca) 
    {
    	 
    	return myDataBase.rawQuery("select id , desc from modelos  where id_marca = " + idmarca + " order by desc ", null);
    }

    public Cursor createCursorLoc (int idprov) 
    {
    	 
    	return myDataBase.rawQuery("select id ,    nomloc  ||', '|| nomzona  from localidades  where id_prov =" + idprov + " order by nomzona ", null);
    }

    public Cursor createCursorCombustible () 
    {
    	 
    	return myDataBase.rawQuery("select id ,  desc  from combustible", null);
    }

    
    
    
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
 
}