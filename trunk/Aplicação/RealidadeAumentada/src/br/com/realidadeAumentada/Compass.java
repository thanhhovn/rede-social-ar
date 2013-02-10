package br.com.realidadeAumentada;

import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

public class Compass extends MapActivity implements SensorEventListener {

	public static GeoPoint Mypoint,MapPoint; 
    double km,meter;
    public static int kmInDec,meterInDec;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer, mField;
    Location myLoc,mapLoc;
    double myLat,myLong,mapLat,mapLong;


    public static double lat,lng; 
    public static double mylat = 31.567615d;//31.567615d;

    public static double mylon = 74.360962d;
    com.google.android.maps.MapController mapController;
    public static boolean enabled=false;



    @Override
    protected void onCreate(Bundle icicle){
        // TODO Auto-generated method stub
        super.onCreate(icicle);
     //   setContentView(R.layout.map_layout);
        fetchUI();
    }

    public void fetchUI()
    {
        myLoc=new Location("Test");
        mapLoc=new Location("Test");
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        myLat=(31.567615*1e6)/1E6;
        myLong=(74.360962d*1e6)/1E6;
        mapLat=(MapPoint.getLatitudeE6())/1E6;
        mapLong=(MapPoint.getLongitudeE6())/1E6;
        myLoc.setLatitude(31.567615);
        myLoc.setLongitude(74.360962);
        mapLoc.setLatitude(mapLat);
        mapLoc.setLongitude(mapLong);
    }


     protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_UI);
        }
     protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);
        }

     @Override
        protected boolean isRouteDisplayed() {
            // TODO Auto-generated method stub
            return false;
        }

    ///Method to calculate the Distance between 2 Geo Points aroud the Globe 
    public double CalculationByDistance(GeoPoint StartP, GeoPoint EndP) {
        double lat1 = StartP.getLatitudeE6()/1E6;
        double lat2 = EndP.getLatitudeE6()/1E6;
        double lon1 = StartP.getLongitudeE6()/1E6;
        double lon2 = EndP.getLongitudeE6()/1E6;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)+ 
        		   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))*
        		   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= a*c; //Radius
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        kmInDec =  Integer.valueOf(newFormat.format(km));
        meter=valueResult%1000;
        meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        //return Radius * c;
        return kmInDec;
     }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
          if ( myLoc == null ) return;

            float azimuth = event.values[0];
            float baseAzimuth = azimuth;

            GeomagneticField geoField = new GeomagneticField( Double
                .valueOf( myLoc.getLatitude() ).floatValue(), Double
                .valueOf( myLoc.getLongitude() ).floatValue(),
                Double.valueOf( myLoc.getAltitude() ).floatValue(),
                System.currentTimeMillis() );

            azimuth -= geoField.getDeclination(); // converts magnetic north into true north

            // Store the bearingTo in the bearTo variable
            float bearTo = myLoc.bearingTo( mapLoc );

            // If the bearTo is smaller than 0, add 360 to get the rotation clockwise.
            if (bearTo < 0) {
                bearTo = bearTo + 360;
            }

            //This is where we choose to point it
            float direction = bearTo - azimuth;

            // If the direction is smaller than 0, add 360 to get the rotation clockwise.
            if (direction < 0) {
                direction = direction + 360;
            }

           // rotateImageView( compasView, R.drawable.app_icon, direction );

            //Set the field
            String bearingText = "N";

            if ( (360 >= baseAzimuth && baseAzimuth >= 337.5) || (0 <= baseAzimuth && baseAzimuth <= 22.5) ) bearingText = "N";
            else if (baseAzimuth > 22.5 && baseAzimuth < 67.5) bearingText = "NE";
            else if (baseAzimuth >= 67.5 && baseAzimuth <= 112.5) bearingText = "E";
            else if (baseAzimuth > 112.5 && baseAzimuth < 157.5) bearingText = "SE";
            else if (baseAzimuth >= 157.5 && baseAzimuth <= 202.5) bearingText = "S";
            else if (baseAzimuth > 202.5 && baseAzimuth < 247.5) bearingText = "SW";
            else if (baseAzimuth >= 247.5 && baseAzimuth <= 292.5) bearingText = "W";
            else if (baseAzimuth > 292.5 && baseAzimuth < 337.5) bearingText = "NW";
            else bearingText = "?";

          //  Distance.setText(kmInDec+"  Km "+" "+meterInDec+" m");

        }
////This is the method to Show the Image as Compass


     private void rotateImageView( ImageView imageView, int drawable, float rotate ) {

                // Decode the drawable into a bitmap
                Bitmap bitmapOrg = BitmapFactory.decodeResource( getResources(),
                        drawable );

                // Get the width/height of the drawable
                DisplayMetrics dm = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(dm);
                int width = bitmapOrg.getWidth(), height = bitmapOrg.getHeight();

                // Initialize a new Matrix
                Matrix matrix = new Matrix();

                // Decide on how much to rotate
                rotate = rotate % 360;

                // Actually rotate the image
                matrix.postRotate( rotate, width, height );

                // recreate the new Bitmap via a couple conditions
                Bitmap rotatedBitmap = Bitmap.createBitmap( bitmapOrg, 0, 0, width, height, matrix, true );
                //BitmapDrawable bmd = new BitmapDrawable( rotatedBitmap );

                //imageView.setImageBitmap( rotatedBitmap );
                imageView.setImageDrawable(new BitmapDrawable(getResources(), rotatedBitmap));
                imageView.setScaleType( ScaleType.CENTER );
            }

}