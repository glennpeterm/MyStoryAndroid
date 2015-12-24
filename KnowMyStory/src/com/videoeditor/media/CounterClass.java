/*
 * Know My Story 
 */
package com.videoeditor.media;

import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;

public class CounterClass extends CountDownTimer {  
	long milli;
    public CounterClass(long millisInFuture, long countDownInterval) {  
        super(millisInFuture, countDownInterval);  
        System.out.println("millisInFuture: "+millisInFuture);
        milli = millisInFuture;
   }  
   @Override  
  public void onFinish() {  
 
    CustomCameraActivity.CallFinish();
  }  
  
   @Override  
   public void onTick(long millisUntilFinished) {  
         long millis = millisUntilFinished;  
          /*String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),  
              TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),  
              TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))); */
          
          
          String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),  
                 // TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),  
                  TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))); 
          
          
          System.out.println(hms);  
         // textViewTime.setText(hms);  
          CustomCameraActivity.SetTimerText(hms);
          CustomCameraActivity.TimeInMillis(String.valueOf(milli - millis));
          System.out.println("milli: "+milli);
   }  
}  


