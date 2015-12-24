package com.supportclasses;

import java.io.File;

import com.tabview.TabViewActivity;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class CheckAvailableStorage {

	
	public CheckAvailableStorage() {
		// TODO Auto-generated constructor stub
	}
	
	

	


     public long checkProjectpathstorage(){
    	 String  projpath = TabViewActivity.getProjectPath();
    	 File directory = new File(projpath);
    	 Log.d("master",projpath);
    	 long length = 0;
 	    for (File file : directory.listFiles()) {
 	        if (file.isFile()){
 	            length += file.length();
 	        }
 	        }
 	    
 	    length = length/1024;
 	    length = length/1024;
 	    
 	    return length;
     }
     
     

	   
    public  boolean hasEnoughInternalMemory() {
    	long buffer = Constants.BUFFER_DATA_STOR;
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getAvailableBlocks();
       
        long size = (totalBlocks * blockSize);
        Log.d("size","--"+size);
        size = size/1024;
        Log.d("size","--"+size);
        size = size/1024;
        
        Log.d("size","--"+size);
        Log.d("project","---"+checkProjectpathstorage());
        Log.d("buffer","--"+buffer);
        
         if(size>(buffer-checkProjectpathstorage())){
        Log.d("greater","greater");
        	 return true;
         }else{
        Log.d("lesser","lesser");
        	 return false;
         }
    }


   
    
    
    
	
}
