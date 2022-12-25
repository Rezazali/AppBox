package com.rezazali.qiba.qiba;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rezazali.qiba.R;
import com.rezazali.qiba.qiba.compass.ConcurrencyUtil;
import com.rezazali.qiba.qiba.compass.ConstantUtilInterface;
import com.rezazali.qiba.qiba.compass.LocationEnum;
import com.rezazali.qiba.qiba.compass.QiblaCompassManager;
import com.rezazali.qiba.qiba.utily.AppSetting;
import com.rezazali.qiba.qiba.utily.DateUtils;
import com.rezazali.qiba.qiba.utily.GPSTracker;
import com.rezazali.qiba.qiba.utily.SnackBarUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class CompassActivity extends AppCompatActivity implements Animation.AnimationListener,
        SharedPreferences.OnSharedPreferenceChangeListener, ConstantUtilInterface {
    private boolean faceUp = true;
    private boolean gpsLocationFound = true;
    private String location_line2 = "";
    // Current location that is set by QiblaManager
    public Location currentLocation = null;

    // These tow variable is usefull to compute the difference between new
    // angles and last angles.(To compute the rotation degree and also some
    // performance and smoothing behaviours that prevents the arrow to rotate
    // for very smal angles)

    boolean isSuccess = false;
    private double lastQiblaAngle = 0;
    private double lastNorthAngle = 0;
    private double lastQiblaAngleFromN = 0;

    // This animation is used to rotate north and qibla images
    private RotateAnimation animation;

    private ImageView compassImageView;
    private ImageView qiblaImageView;
    // This class informs us about changes in qibla and north direction
    private final QiblaCompassManager qiblaManager = new QiblaCompassManager(
            this);

    // QiblaManager is talking to us about changes in angles through accessors
    // of this variable and a TimerTask repeatedly checks this
    // variable.(QiblaManager will not sent messages directly because of
    // syncronization of animations). Though the TimerTask will check if any
    // animation is in run mode, if there wasn't any animation, timerTask will
    // use new angles. There might be some angles that are lost but it will not
    // affect the results.
    private boolean angleSignaled = false;
    private Timer timer = null;

    // These tow variables are redundant now. but they can be usefull when
    // registering and unregistering services.
    public boolean isRegistered = false;
    public boolean isGPSRegistered = false;
    TextView angleText;
    TextView locationText;

    Location Qiblalocation; // location
    GPSTracker gpsTracker;
    Location mlocation; // location


    DateUtils dateUtils;
    LinearLayout main;

    SnackBarUtil snackBarUtil;


    ImageView img_back;


    RadioGroup toggle;
    RadioButton rdo_medina;
    RadioButton rdo_mecca;
    RadioButton rdo_najaf;
    RadioButton rdo_karbala;
    RadioButton rdo_mashad;
    TextView txt_title;
    AppSetting appSetting;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        appSetting=new AppSetting(getApplicationContext());
        gpsTracker = new GPSTracker(this);

        
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        Toast.makeText(CompassActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onPermissionsChecked: ");
                        if(gpsTracker.canGetLocation) {
                            appSetting.setLat(gpsTracker.getLatitude());
                            appSetting.setLang(gpsTracker.getLongitude());
                            appSetting.hasLocation(true);
                            isSuccess = true;
                            Log.d(TAG, "onPermissionsChecked: ");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        isSuccess = false;
                        Log.d(TAG, "onPermissionRationaleShouldBeShown: ");
                    }
                }) .withErrorListener(dexterError -> Toast.makeText(CompassActivity.this, ""+dexterError.name(), Toast.LENGTH_SHORT).show()).check();
        





        main = (LinearLayout) findViewById(R.id.mainLayout);
        locationText = (TextView) findViewById(R.id.compassLocation);
        angleText = (TextView) findViewById(R.id.compassangle);
        img_back=(ImageView)findViewById(R.id.img_back);
        toggle=findViewById(R.id.toggle);
        rdo_medina=findViewById(R.id.rdo_medina);
        rdo_mecca=findViewById(R.id.rdo_mecca);
        rdo_najaf=findViewById(R.id.rdo_najaf);
        rdo_karbala=findViewById(R.id.rdo_karbala);
        rdo_mashad=findViewById(R.id.rdo_mashad);
        txt_title=findViewById(R.id.txt_title);



        txt_title.setText("qible");

        locationText.setVisibility(View.VISIBLE);
        angleText.setVisibility(View.VISIBLE);


        Qiblalocation = new Location("mecca");
        Qiblalocation.setLongitude(21.42664);
        Qiblalocation.setLatitude(39.82563);



        snackBarUtil = new SnackBarUtil();

        dateUtils = new DateUtils(getApplicationContext());








    mlocation = new Location("MyLocation");
        Bundle bundle = getIntent().getExtras();


        double Latitude = Double.parseDouble(appSetting.getLat());
        double Longitude = Double.parseDouble(appSetting.getLan());

        if (Latitude > 0 && Longitude > 0) {
            mlocation.setLatitude(Latitude);
            mlocation.setLongitude(Longitude);
            // registering for listeners
            registerListeners();
        } else {
            mlocation = gpsTracker.getLocation();
            registerListeners();

        }
        if(!isRegistered){
            showSensorNotSupportAlert();

        }


        this.qiblaImageView = (ImageView) findViewById(R.id.arrowImage);
        this.compassImageView = (ImageView) findViewById(R.id.compassImage);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.rdo_medina) {
                    Qiblalocation.setLatitude(AppConstants.MEDINA_LOCATION.latitude);
                    Qiblalocation.setLongitude(AppConstants.MEDINA_LOCATION.longitude);
                } else if (i == R.id.rdo_mecca) {
                    Qiblalocation.setLatitude(AppConstants.MEKKEH_LOCATION.latitude);
                    Qiblalocation.setLongitude(AppConstants.MEKKEH_LOCATION.longitude);
                } else if (i == R.id.rdo_najaf) {
                    Qiblalocation.setLatitude(AppConstants.NAJAF_LOCATION.latitude);
                    Qiblalocation.setLongitude(AppConstants.NAJAF_LOCATION.longitude);
                } else if (i == R.id.rdo_karbala) {
                    Qiblalocation.setLatitude(AppConstants.KARBALA_LOCATION.latitude);
                    Qiblalocation.setLongitude(AppConstants.KARBALA_LOCATION.longitude);
                } else if (i == R.id.rdo_mashad) {
                    Qiblalocation.setLatitude(AppConstants.MASHHAD_LOCATION.latitude);
                    Qiblalocation.setLongitude(AppConstants.MASHHAD_LOCATION.longitude);
                }
            }
        });


    }


    // TimerTask talks to us by sending messages about changes in direction
    // of north and Qibla
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == ROTATE_IMAGES_MESSAGE) {
                Bundle bundle = message.getData();
                // These are for us to know that if qibla direction is changed
                // or north direction is changed.
                boolean isQiblaChanged = bundle.getBoolean(IS_QIBLA_CHANGED);
                boolean isCompassChanged = bundle
                        .getBoolean(IS_COMPASS_CHANGED);
                // These are the delta angles from north and qibla (first set to
                // zero and if they are changed in this message, we will update
                // them)
                double qiblaNewAngle = 0;
                double compassNewAngle = 0;
                if (isQiblaChanged)
                    qiblaNewAngle = (Double) bundle.get(QIBLA_BUNDLE_DELTA_KEY);
                if (isCompassChanged) {
                    compassNewAngle = (Double) bundle
                            .get(COMPASS_BUNDLE_DELTA_KEY);
                }
                // This
                syncQiblaAndNorthArrow(compassNewAngle, qiblaNewAngle,
                        isCompassChanged, isQiblaChanged);
                angleSignaled = false;
            }
        }

    };

    public void setLocationText(String textToShow) {
        this.location_line2 = textToShow;
    }

    /*
     * This is actually a loop task that check for new angles when no animation
     * is in run and then provide a Message for QiblaActivity. Please note that
     * this class is running in another thread.
     */
    private TimerTask getTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                if (angleSignaled && !ConcurrencyUtil.isAnyAnimationOnRun()) {

                    // numAnimationOnRun += 2;
                    Map<String, Double> newAnglesMap = qiblaManager
                            .fetchDeltaAngles();
                    Double newNorthAngle = newAnglesMap
                            .get(QiblaCompassManager.NORTH_CHANGED_MAP_KEY);
                    Double newQiblaAngle = newAnglesMap
                            .get(QiblaCompassManager.QIBLA_CHANGED_MAP_KEY);

                    Message message = mHandler.obtainMessage();
                    message.what = ROTATE_IMAGES_MESSAGE;
                    Bundle b = new Bundle();
                    if (newNorthAngle == null) {
                        b.putBoolean(IS_COMPASS_CHANGED, false);
                    } else {
                        ConcurrencyUtil.incrementAnimation();
                        b.putBoolean(IS_COMPASS_CHANGED, true);

                        b.putDouble(COMPASS_BUNDLE_DELTA_KEY, newNorthAngle);
                    }
                    if (newQiblaAngle == null) {
                        b.putBoolean(IS_QIBLA_CHANGED, false);

                    } else {
                        ConcurrencyUtil.incrementAnimation();
                        b.putBoolean(IS_QIBLA_CHANGED, true);
                        b.putDouble(QIBLA_BUNDLE_DELTA_KEY, newQiblaAngle);
                    }

                    message.setData(b);
                    mHandler.sendMessage(message);
                } else if (ConcurrencyUtil.getNumAimationsOnRun() < 0) {
                    Log.d(NAMAZ_LOG_TAG,
                            " Number of animations are negetive numOfAnimation: "
                                    + ConcurrencyUtil.getNumAimationsOnRun());
                }
            }
        };
        return timerTask;
    }

    /*
     * Running the TimerTask. (for example when application is started or became
     * back from pause mode.)
     */
    private void schedule() {

        if (timer == null) {
            timer = new Timer();
            this.timer.schedule(getTimerTask(), 0, 200);
        } else {
            timer.cancel();
            timer = new Timer();
            timer.schedule(getTimerTask(), 0, 200);
        }
    }

    /*
     * Stopping the timerTask (For example when activity is paused or stopped)
     */
    private void cancelSchedule() {

        if (timer == null)
            return;
        // timer.cancel();
    }

    /*
     * When user changes the gps status to on mode. The QiblaImages must became
     * unvisible and some screen texts must be changed. These changes will
     * became permanent until the GPS device recieves location, or user set GPS
     * to off.
     */
    /*private void onInvalidateQible(String message) {
        // TextView textView = (TextView)
        // findViewById(R.id.location_text_line1);
        TextView textView = (TextView) findViewById(R.id.location_text_line2);
        // TextView textView3 = (TextView)
        // findViewById(R.id.location_text_line3);

        textView.setText("");
        textView.setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.arrowImage))
                .setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.compassImage))
                .setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.frameImage))
                .setVisibility(View.INVISIBLE);
        ((FrameLayout) findViewById(R.id.qiblaLayout))
                .setVisibility(View.INVISIBLE);
        TextView textView3 = (TextView) findViewById(R.id.noLocationText);
        textView3.setText(message);
        ((LinearLayout) findViewById(R.id.noLocationLayout))
                .setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.textLayout))
                .setVisibility(View.INVISIBLE);

    }*/

    private void requestForValidationOfQibla() {
        // TextView textView = (TextView)
        // findViewById(R.id.location_text_line1);
        TextView textView2 = (TextView) findViewById(R.id.location_text_line2);
        ImageView arrow = ((ImageView) findViewById(R.id.arrowImage));
        ImageView compass = ((ImageView) findViewById(R.id.compassImage));
        ImageView frame = ((ImageView) findViewById(R.id.frameImage));
        FrameLayout qiblaFrame = ((FrameLayout) findViewById(R.id.qiblaLayout));
        LinearLayout noLocationLayout = ((LinearLayout) findViewById(R.id.noLocationLayout));

        if (faceUp && (gpsLocationFound || currentLocation != null)) {
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(location_line2);
            ((LinearLayout) findViewById(R.id.textLayout))
                    .setVisibility(View.VISIBLE);
            noLocationLayout.setVisibility(View.INVISIBLE);
            qiblaFrame.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
            compass.setVisibility(View.VISIBLE);
            frame.setVisibility(View.VISIBLE);
        } else {
            if (!faceUp) {
                onScreenDown();
            } else if (!(gpsLocationFound || currentLocation != null)) {
                onGPSOn();
            }
        }
    }

    private void onGPSOn() {
        //   gpsLocationFound = false;
        // onInvalidateQible(getString(R.string.no_location_yet));
    }

    // When new Locations are set in the class the information about the
    // location will be printed
    // private void setLocationText() {
    // TextView textView = (TextView) findViewById(R.id.location_text_line1);
    // TextView textView2 = (TextView) findViewById(R.id.location_text_line2);
    //
    // // textView.setText(getString(R.string.location_set));
    // textView2.setText(getLocationForPrint(currentLocation.getLatitude(),
    // currentLocation.getLongitude()));
    //
    // }

    /*
     * Qible direction is set with the assumption of horizontal and up to ceil
     * screen orientation. If the user changes these aligns, we wil notify
     * him/her with messages.
     */
    public void onScreenDown() {
        faceUp = true;
        // onInvalidateQible(getString(R.string.screen_down_text));
    }

    /*
     * when user changes align of screen to horizontal and up to sky. The
     * previously set messages will changes
     */
    public void onScreenUp() {
        faceUp = true;
        requestForValidationOfQibla();
    }

    /*
     * QiblaManager will set new location of the device with this method. We
     * will set appropriate me.ssages
     */
    public void onNewLocationFromGPS(Location location) {
        gpsLocationFound = true;
        currentLocation = location;
       /* this.setLocationText(getLocationForPrint(location.getLatitude(),
                location.getLongitude()));*/
        requestForValidationOfQibla();
    }

    /*
     * when user changes the GPS status off, any changes we must show the images
     * and use last location for direction
     */
    private void onGPSOff(Location defaultLocation) {
        currentLocation = defaultLocation;
        gpsLocationFound = false;
        requestForValidationOfQibla();
    }

    /*
     * This method get us appropraite message string about latitude and
     * longitude points
     */
    /*private String getLocationForPrint(double latitude, double longitude) {
        int latDegree = (new Double(Math.floor(latitude))).intValue();
        int longDegree = (new Double(Math.floor(longitude))).intValue();
        String latEnd = getString(R.string.latitude_south);
        String longEnd = getString(R.string.longitude_west);
        if (latDegree > 0) {
            latEnd = getString(R.string.latitude_north);

        }
        if (longDegree > 0) {
            longEnd = getString(R.string.longitude_east);
        }
        double latSecond = (latitude - latDegree) * 100;
        double latMinDouble = (latSecond * 3d / 5d);
        int latMinute = new Double(Math.floor(latMinDouble)).intValue();

        double longSecond = (longitude - longDegree) * 100;
        double longMinDouble = (longSecond * 3d / 5d);
        int longMinute = new Double(Math.floor(longMinDouble)).intValue();
        return String.format(getString(R.string.geo_location_info), latDegree,
                latMinute, latEnd, longDegree, longMinute, longEnd);
        // return getString(R.string.geo_location_info);

    }*/


    /*
     * Unregistering every listeners
     */
    private void unregisterListeners() {
        ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .removeUpdates(qiblaManager);

        ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .removeUpdates(qiblaManager);
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gsensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.unregisterListener(qiblaManager, gsensor);
        mSensorManager.unregisterListener(qiblaManager, msensor);
        cancelSchedule();


        PackageManager manager = getPackageManager();
        boolean hasAccelerometer = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

        if (hasAccelerometer == false) {

            snackBarUtil.alert(main, getApplicationContext(), getResources().getString(R.string.compass_not_support) + "", 0);
        }

    }

    /*
     * Registering for locationListener (When GPS is set on)
     */
    private void registerForGPS() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        LocationManager locationManager = ((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        String provider = locationManager.getBestProvider(criteria, true);

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, MIN_LOCATION_TIME,
                    MIN_LOCATION_DISTANCE, qiblaManager);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                MIN_LOCATION_TIME, MIN_LOCATION_DISTANCE, qiblaManager);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, MIN_LOCATION_TIME,
                MIN_LOCATION_DISTANCE, qiblaManager);
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            qiblaManager.onLocationChanged(location);
        }


    }

    /*
     * Unregistering from Location Listener (When GPS is set off)
     */
    private void unregisterForGPS() {
        ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .removeUpdates(qiblaManager);

    }

    /*
     * Registering for all Listeners. LocationListener will be registered if and
     * only if GPS status is on.
     */
    private void registerListeners() {
        SharedPreferences perfs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        if (perfs.getBoolean(getString(R.string.gps_pref_key), false)) {
            registerForGPS();
        } else {
            useDefaultLocation(perfs,
                    getString(R.string.state_location_pref_key));
        }
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gsensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(qiblaManager, gsensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(qiblaManager, msensor,
                SensorManager.SENSOR_DELAY_GAME);
        schedule();
        if(msensor != null && gsensor!= null)
            isRegistered = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerListeners();


        //    AnalyticsChecker();
    }




    @Override
    protected void onPause() {
        super.onPause();
        ConcurrencyUtil.setToZero();
        ConcurrencyUtil.directionChangedLock.readLock();
        unregisterListeners();
    }


    /*
     * This method synchronizes the Qibla and North arrow rotation.
     */
    public void syncQiblaAndNorthArrow(double northNewAngle,
                                       double qiblaNewAngle, boolean northChanged, boolean qiblaChanged) {
        if (northChanged) {
            lastNorthAngle = rotateImageView(northNewAngle, lastNorthAngle,
                    compassImageView);
            // if North is changed and our location are not changed(Though qibla
            // direction is not changed). Still we need to rotated Qibla arrow
            // to have the same difference between north and Qibla.
            if (qiblaChanged == false && qiblaNewAngle != 0) {
                lastQiblaAngleFromN = qiblaNewAngle;
                lastQiblaAngle = rotateImageView(qiblaNewAngle + northNewAngle,
                        lastQiblaAngle, qiblaImageView);
            } else if (qiblaChanged == false && qiblaNewAngle == 0)

                lastQiblaAngle = rotateImageView(lastQiblaAngleFromN
                        + northNewAngle, lastQiblaAngle, qiblaImageView);

        }
        if (qiblaChanged) {
            lastQiblaAngleFromN = qiblaNewAngle;
            lastQiblaAngle = rotateImageView(qiblaNewAngle + lastNorthAngle,
                    lastQiblaAngle, qiblaImageView);

        }
    }

    private double rotateImageView(double newAngle, double fromDegree,
                                   ImageView imageView) {

        newAngle = newAngle % 360;
        double rotationDegree = fromDegree - newAngle;
        rotationDegree = rotationDegree % 360;
        long duration = new Double(Math.abs(rotationDegree) * 2000 / 360)
                .longValue();
        if (rotationDegree > 180)
            rotationDegree -= 360;
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.qiblaLayout);
        float toDegree = new Double(newAngle % 360).floatValue();
        final int width = Math.abs(frameLayout.getRight()
                - frameLayout.getLeft());
        final int height = Math.abs(frameLayout.getBottom()
                - frameLayout.getTop());


        float pivotX = width / 2f;
        float pivotY = height / 2f;
        animation = new RotateAnimation(new Double(fromDegree).floatValue(),
                toDegree, pivotX, pivotY);
        animation.setRepeatCount(0);
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillEnabled(true);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        Log.d(NAMAZ_LOG_TAG, "rotating image from degree:" + fromDegree
                + " degree to rotate: " + rotationDegree + " ImageView: "
                + imageView.getId());

        imageView.setVisibility(View.INVISIBLE);

        imageView.startAnimation(animation);
        return toDegree;

    }

    public void signalForAngleChange() {
        this.angleSignaled = true;
    }

    public void onAnimationEnd(Animation animation) {
        if (ConcurrencyUtil.getNumAimationsOnRun() <= 0) {
            Log.d(NAMAZ_LOG_TAG,
                    "An animation ended but no animation was on run!!!!!!!!!");
        } else {
            ConcurrencyUtil.decrementAnimation();
        }
        schedule();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
        cancelSchedule();

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        String gpsPerfKey = getString(R.string.gps_pref_key);
        String defaultLocationPerfKey = getString(R.string.state_location_pref_key);
        if (gpsPerfKey.equals(key)) {
            boolean isGPS = false;
            try {
                isGPS = Boolean.parseBoolean(sharedPreferences.getString(key,
                        "false"));
            } catch (ClassCastException e) {
                isGPS = sharedPreferences.getBoolean(key, false);
            } catch (Exception e) {

            }
            if (isGPS) {
                registerForGPS();
                currentLocation = null;
                onGPSOn();
            } else {
                useDefaultLocation(sharedPreferences, defaultLocationPerfKey);
                unregisterForGPS();

            }
        } else if (defaultLocationPerfKey.equals(key)) {
            sharedPreferences.edit().putBoolean(gpsPerfKey, false);
            sharedPreferences.edit().commit();
            unregisterForGPS();
            useDefaultLocation(sharedPreferences, key);
        } else {
            Log.d(NAMAZ_LOG_TAG, "preference with key:" + key
                    + " is changed and it is not handled properly");
        }

    }


    private void useDefaultLocation(SharedPreferences perfs, String key) {
        int defLocationID = Integer.parseInt(perfs.getString(key, ""
                + LocationEnum.MENU_TEHRAN.getId()));
        LocationEnum locationEnum = LocationEnum.values()[defLocationID - 1];
        Location location = mlocation; // location;
        qiblaManager.onLocationChanged(location);
       /* this.setLocationText(String.format(
                getString(R.string.default_location_text),
                locationEnum.getName(this)));*/
        onGPSOff(location);


        try {


            if (location.getLatitude() > 0 || location.getLongitude() >= 0) {
                bearing = bearing(location.getLatitude(), location.getLongitude(), Qiblalocation.getLatitude(), Qiblalocation.getLongitude());


                String bearingString = Double.toString(bearing);
                String fString;
                String[] numberParts = bearingString.split("\\.");
                if (numberParts.length > 0) {
                    String part2 = numberParts[1].substring(0, 2);
                    fString = numberParts[0] + "." + part2;
                } else {
                    fString = bearingString;

                }

// dblVariable is a number variable and not a String in this case
                angleText.setText(fString+ " N ");
                //angleText.setText(String.format("%.2d", Double.toString(compass.bearing)));
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                Geocoder geoCoder = new Geocoder(CompassActivity.this, Locale.getDefault());
                StringBuilder builder = new StringBuilder();
                try {
                    List<Address> address = null;
                    try {
                        address = geoCoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int maxLines = address.get(0).getMaxAddressLineIndex();
                    for (int i = 0; i < maxLines; i++) {
                        String addressStr = address.get(0).getAddressLine(i);
                        builder.append(addressStr);
                        builder.append(" ");
                    }

                    String finalAddress = builder.toString().trim(); //This is the complete address.
                    String[] addressParts = finalAddress.split(" ");

                    // if(addressParts.length > 0)

                    //    locationText.setText(addressParts[1]); //This will display the final address.

                    // else
                    locationText.setText(addressParts[0] ); //This will display the final address.

                } catch (Exception e) {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //-----------************************************************

    public void showSensorNotSupportAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompassActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.sensor_not_support));

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.no_sensor_your_device));

        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    //---------------
    double bearing;

    protected double bearing(double startLat, double startLng, double endLat, double endLng) {

        double lonDelta = (endLng - startLng);
        double y = Math.sin(lonDelta) * Math.cos(endLat);
        double x = Math.cos(startLat) * Math.sin(endLat) - Math.sin(startLat) * Math.cos(endLat) * Math.cos(lonDelta);
        double brng = Math.atan2(y, x);
        double fbearing = (Math.toDegrees(brng));

        return fbearing;


    }






}