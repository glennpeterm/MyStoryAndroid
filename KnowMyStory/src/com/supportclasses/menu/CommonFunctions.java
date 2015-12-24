/*
 * Copyright (C) 2014 The Android Open Source Project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.supportclasses.menu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.database.menu.Database;
import com.database.menu.LoginTable;
import com.facebook.Session;
import com.model.UserDetail;

/**
 * Class that contains all the common functions
 */
public class CommonFunctions 
{
	// Declaring variables for the class
	private Context mContext;
	static Context sContext;
	private static PowerManager powermanager;

	static int MAX_IMAGE_DIMENSION=300;	
	private final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
					"\\@" +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
					"(" +
					"\\." +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
					")+"
			);

	// Parameterized constructor for the class
	public CommonFunctions(Context context)
	{
		mContext=context;
		sContext=context;
	}

	/**
	 * Function for checking the Internet connectivity
	 */
	public boolean isConnectingToInternet()
	{
		ConnectivityManager connectivity = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
		}
		return false;
	}



	/**
	 * Function for validating email address 
	 */
	public boolean CheckEmail(String email) 
	{
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	

	/**
	 * Function to close the keyboard
	 */
	public void CloseKeyBoard(EditText et)
	{
		InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}


	/**
	 * Function for decoding the bitmap images
	 */
	public static Bitmap decodeSampledBitmapFromResource(String imageFileName,int reqWidth, int reqHeight) 
	{
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Config.RGB_565;
		options.inDither = true;
		BitmapFactory.decodeFile(imageFileName,options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imageFileName, options);
	}

	/**
	 * Function for decoding the bitmap images
	 */
	public static Bitmap decodeSampledBitmapFromResource1(String imageFileName,int reqWidth, int reqHeight,Context context, Uri uri)
	{
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imageFileName,options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imageFileName, options);
	}

	/**
	 * Function to manage the orientation of the image
	 * @param imagePath 
	 */
	public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri, String imagePath) throws IOException 
	{
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
	//	int orientation = getOrientation(context, photoUri);
		int orientation = getExifOrientation(imagePath);

		if (orientation == 90 || orientation == 270)
		{
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} 
		else 
		{
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) 
		{
			float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		}
		else 
		{
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
		if (orientation > 0) 
		{
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),srcBitmap.getHeight(), matrix, true);
		}
		return srcBitmap;
	}
	public static int getExifOrientation(String filepath) {// YOUR MEDIA PATH AS STRING
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                }

            }
        }
        return degree;
    }
	/**
	 * Function to get the orientation of an image
	 */
	public static int getOrientation(Context context, Uri photoUri)
	{
		System.out.println("photouri: "+photoUri);
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor.getCount() != 1) 
		{
			return -1;
		}
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	/**
	 * Function to calculate the size of am image
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) 
		{
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) 
			{
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * Function to convert an image to base64 format
	 */
	public static String Base64Converter(Bitmap bm)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 

		bm.compress(Bitmap.CompressFormat.JPEG,50, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();
		String result=Base64.encodeToString(byteArray, Base64.DEFAULT);
		bm.recycle();
		bm=null;
		return result;    
	}


	/**
	 * Function to convert a profile image to base64 format
	 */
	public static String ProfileBase64Converter(Bitmap bm)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
		
		bm.compress(Bitmap.CompressFormat.JPEG,80, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();
		String result=Base64.encodeToString(byteArray, Base64.DEFAULT);
		
		bm.recycle();
		bm=null;
		
		return result;    
	}


	/**
	 * For generating the MD5 hash value of bitmap
	 */
	public static String MD5Converter(Bitmap bm)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] bitmapBytes = baos.toByteArray();
		String s = new String(bitmapBytes);
		bm.recycle();
		bm=null;
		MessageDigest mdObj = null;
		try 
		{
			mdObj = MessageDigest.getInstance("MD5");
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}

		mdObj.update(s.getBytes(),0,s.length());
		String hash = new BigInteger(1, mdObj.digest()).toString(32);
		return hash.toUpperCase(Locale.getDefault());
	}

	/**
	 * Funtion to curve the edges of the images being uploaded
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) 
	{
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * Function to crop the image being uploaded
	 */
	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius)
	{
		Bitmap sbmp;
		if(bmp.getWidth() != radius || bmp.getHeight() != radius)
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		else
			sbmp = bmp;
		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
				sbmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		//canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
		//		sbmp.getWidth() / 2+0.1f, paint);
		canvas.drawRect(rect, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sbmp, rect, rect, paint);

		return output;
	}

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	/**
	 * Generate a value suitable for use in {@link #setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	public static int generateViewId()
	{
		for (;;)
		{
			final int result = sNextGeneratedId.get();
			// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
			if (sNextGeneratedId.compareAndSet(result, newValue)) 
			{
				return result;
			}
		}
	}

	public static StringBuilder setFirstCaptialLetter(String originalValue)
	{
		final StringBuilder result = new StringBuilder(originalValue.length());
		String[] words = originalValue.split("\\s");
		for(int i=0,l=words.length;i<l;++i)
		{
			if(i>0) result.append(" ");      
			result.append(Character.toUpperCase(words[i].charAt(0)))
			.append(words[i].substring(1));

		}
		return result;
	}


	/**
	 * Function to check if logged in with facebook or not
	 */
	public boolean isLoggedInWithFacebook()
	{
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
		int rotate = 0;
		try {
			context.getContentResolver().notifyChange(imageUri, null);
			File imageFile = new File(imagePath);
			ExifInterface exif = new ExifInterface(
					imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
	}




	/**
	 * Function for creating the folder and file for holding the images in SD card
	 */
	public File getOutputMediaFile(int type,String folderName)
	{
		File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				folderName);
		if (!mediaStorageDir.exists())
		{
			if (!mediaStorageDir.mkdirs())
			{
				Log.d("County apps", "failed to create directory");
				return null;
			}
		}
		File mediaFile;
		if (type == Constants.CAMERA_REQUEST)
		{

			SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss",Locale.getDefault());
			String format = s.format(new Date());
			mediaFile = new File(mediaStorageDir.getPath() + File.separator+format+".jpg");

		}  
		else 
		{
			return null;
		}
		return mediaFile;
	}

	/**
	 * Function for creating the folder and file for holding the profile images in SD card
	 */
	public File getOutputProfileMediaFile(int type,String folderName)
	{
		File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				folderName);
		if (!mediaStorageDir.exists())
		{
			if (!mediaStorageDir.mkdirs())
			{
				Log.d("County apps", "failed to create directory");
				return null;
			}
		}
		File mediaFile;
		if (type == Constants.CAMERA_REQUEST)
		{

			//	            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss",Locale.getDefault());
			//	            String format = s.format(new Date());
			mediaFile = new File(mediaStorageDir.getPath() + File.separator+"profile_pic"+".jpg");

		}  
		else 
		{
			return null;
		}
		return mediaFile;
	}

	public static boolean isScreenOn()
	{
		if(powermanager==null)
		{
			powermanager = (PowerManager) sContext.getSystemService(Context.POWER_SERVICE); 
		}
		return powermanager.isScreenOn();
	}

	/**
	 * Function to get the double value from the editText
	 * @param v edittext
	 * @return double value if editText not null 
	 */
	public double getDoubleValue(EditText v)
	{
		if(v!=null && v.getText().toString().length()>0)
		{
			try {
				return Double.parseDouble(v.getText().toString());
			} catch (Exception e) {
				return 0;
			}
		}
		else
			return 0;

	}

	
	/**
	 * Function to round the decimal places
	 * 
	 * @param value the value to be rounded
	 * @param places number of plces after the decimal
	 * @return the value after rounding
	 */
	public double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public BigDecimal round(String value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd/*.doubleValue()*/;
	}

	public int getCurrentYear(){

		Calendar c = Calendar.getInstance(); 
		return c.get(Calendar.YEAR);
	}


	public String getFrommattedPrice(String price)
	{

		DecimalFormat formatter = new DecimalFormat("#,###,###");		
		String number_string="";
		if(price.indexOf(".")!=-1)
		{
			if(price.trim().length()>15)
			{
				number_string = formatter.format(Double.parseDouble(price));
			}
			else
				number_string= formatter.format(round(price, 2));
		}
		else
		{
			if(price.trim().length()>15)
			{
				number_string = formatter.format(Long.parseLong(price));
			}
			else
				number_string= formatter.format(round(price, 2));

		}
		return " "+number_string;

	}

	public String getFormattedBathCount(double bath) {

		if ((int)bath != 0)
		{

			double y = bath * 10.0;			
			int i = (int) y;	
			int mod = i % 10;
			if(mod == 0)			
				return String.valueOf((i/10));			
			else
				return String.valueOf((i/10) + "." + mod);
			//			return String.valueOf(String.format("%.0f", bath));

			//If the value of `result` is not equal to zero, then, you have a decimal portion which is not equal to 0.
		}
		else
		{
			if(bath==0.0)
			{
				return String.valueOf(0);
			}
			String str=String.valueOf(bath);
			int n=str.indexOf(".");
			if(n!=0)
			{
				return str.substring(n, str.length());

			}


		}
		return null;
	}

	public void SaveUserDeatils(UserDetail data)
	{
		Database dbClass=new Database(sContext);
		ContentValues cv=new ContentValues();
		cv.put(LoginTable.FIRST_NAME, data.getFirstName());
		cv.put(LoginTable.LAST_NAME, data.getLastName());
		cv.put(LoginTable.EMAIL, data.getEmail());
		cv.put(LoginTable.PROFILE_PIC, data.getProfilepic());
		System.out.println("profile pic SAVE: "+data.getProfilepic());
		cv.put(LoginTable.PROVIDER, data.getType());
		cv.put(LoginTable.TOKEN, data.getToken());
		cv.put(LoginTable.ADDRESS, data.getAddress());
		cv.put(LoginTable.CITY, data.getCity());
		cv.put(LoginTable.STATE, data.getState());
		cv.put(LoginTable.COUNTRY, data.getCountry());
		cv.put(LoginTable.DOB, data.getDob());
		cv.put(LoginTable.GENDER, data.getGender());
		cv.put(LoginTable.PHONE, data.getPhone());
		cv.put(LoginTable.ZIPCODE, data.getZipCode());
		cv.put(LoginTable.ACTIVE, data.getActive());
		cv.put(LoginTable.AUTH_TOKEN, data.getAuthtoken());
		cv.put(LoginTable.TOKEN, data.getToken());cv.put(LoginTable.TOKEN, data.getToken());
		
		dbClass.insert(LoginTable.TABLE_NAME, null, cv);


	}
	
	public void UpdateUserDeatils(UserDetail data)
	{
		Database dbClass=new Database(sContext);
		ContentValues cv=new ContentValues();
		cv.put(LoginTable.FIRST_NAME, data.getFirstName());
		System.out.println("update firstname: "+data.getFirstName());
		cv.put(LoginTable.LAST_NAME, data.getLastName());
		cv.put(LoginTable.EMAIL, data.getEmail());
		System.out.println("update email: "+data.getEmail());
		
		cv.put(LoginTable.PROFILE_PIC, data.getProfilepic());
		System.out.println("profile pic UPDATE: "+data.getProfilepic());
		cv.put(LoginTable.PROVIDER, data.getType());
		cv.put(LoginTable.TOKEN, data.getToken());
		cv.put(LoginTable.ADDRESS, data.getAddress());
		cv.put(LoginTable.CITY, data.getCity());
		cv.put(LoginTable.STATE, data.getState());
		cv.put(LoginTable.COUNTRY, data.getCountry());
		cv.put(LoginTable.DOB, data.getDob());
		cv.put(LoginTable.GENDER, data.getGender());
		cv.put(LoginTable.PHONE, data.getPhone());
		cv.put(LoginTable.ZIPCODE, data.getZipCode());
		cv.put(LoginTable.ACTIVE, data.getActive());
		cv.put(LoginTable.AUTH_TOKEN, data.getAuthtoken());
		cv.put(LoginTable.TOKEN, data.getToken());cv.put(LoginTable.TOKEN, data.getToken());
		
		//dbClass.update(LoginTable.TABLE_NAME, cv, LoginTable.EMAIL+"=?", new String[]{data.getEmail()});
		dbClass.update(LoginTable.TABLE_NAME, cv, null, null);


	}

	public void UpdateUserEmail(String email) {
		Database dbClass=new Database(sContext);
		ContentValues cv=new ContentValues();
		cv.put(LoginTable.EMAIL, "");
		dbClass.update(LoginTable.TABLE_NAME, cv, LoginTable.EMAIL+"=?", new String[]{email});
	}

	public void UpdateLogoutStatus(String logoutStatus, String user_Email) {
		Database dbClass=new Database(sContext);
		ContentValues cv=new ContentValues();
		cv.put(LoginTable.LOGOUT, logoutStatus);
		dbClass.update(LoginTable.TABLE_NAME, cv, LoginTable.EMAIL+"=?", new String[]{user_Email});
		
	}

	public void insertEmail(String email) {
	
		Database dbClass=new Database(sContext);
		ContentValues cv=new ContentValues();
		cv.put(LoginTable.EMAIL, email);
		dbClass.insert(LoginTable.TABLE_NAME, null, cv);
	}


}
