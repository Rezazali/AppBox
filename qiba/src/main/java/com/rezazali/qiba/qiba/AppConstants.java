package com.rezazali.qiba.qiba;


import com.google.android.gms.maps.model.LatLng;

public class AppConstants {

    /**
     * App variables for applications like folders,reciters and database
     * Variable for KEY_BASE64_PUBLICK_KEY In APP Purchase
     */
    public static final String URL="https://s3.eu-west-2.amazonaws.com/sigmastorage/Qfiles/";

    public static final String KEY_NO_MIDEA =".nomedia";
    public static final String KEY_APP_NAME ="IslamicTools";
    public static final String KEY_RECITER="Qari";
    public static final String KEY_DATABASES="databases";
    public static final String KEY_PRAY="doa";
    public static final String KEY_BASE64_PUBLICK_KEY  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAopFmNmhwomoXfQ6Y6O/LJMSXYK8FnttokA8oAoYYNHfyqRBvbmdCsNbbveSqs0eb0KP+YeyV+n623X3Fmu72mVL3VZbnGIyXsL6N6VZtD1ESbFpXJ0WdCojXblEHGAf22vxsK6j8MxXxj0IANTykfJCx45mtincGIyKPZUWfqw47EQdp7QEOVmVHx/AVM+0R17TZpdUGvPBX0QUmt/hHLIUCIYrOSnfqNG/6q6M8JyicE7BlyapSZJg1zaZzohHbyEyHL0imTUhBMMljpn5yXijMF1xhZ3t9Mwg1+GSh3c/NZ+k1taAslBgP8ADRbHiZAbCEX/7BWmlpcJP3ahj7IwIDAQAB";

    public static final String KEY_TRANSLATION_URL="https://sigmastorage.s3.amazonaws.com/Qfiles/TranslateDBSqlite/";

    //public static String KEY_TRANSLATION_URL_OLD="https://s3.eu-west-2.amazonaws.com/sigmastorage/Qfiles/TranslateDBSqlite/";//just for comment


    public static final String TWITTER_APP_PACKAGE_NAME = "com.twitter.android";
    public static final String INSTAGRAM_APP_PACKAGE_NAME = "com.instagram.android";

    public static final String FACEBOOK_PAGE_ID = "192051227982980";
    public static final String TWITTER_LINK = "https://twitter.com/qurankuran";
    public static final String INSTAGRAM_LINK = "http://instagram.com/_u/qurankuran";
    public static final String FACEBOOK_LINK = "https://www.facebook.com/qurankuranapp";
    public static final String WEBSITE_LINK = "http://www.qurankuran.com";
    public static final String EMAIL_ADDRESS = "support@qurankuran.com";

    public static final String GOOGLE_MOBILEADS_ID = "ca-app-pub-7913251915181103~5036926277";
    // Google Console APIs developer key
    public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyDl5764At4b2f-ei7Ztp-Ks5ODd_uUnkeo";
    public static final String SKU_PRO_VERSION = "com.cellsigma.kalemkuran.pro_version"; //for test "android.test.purchased" "android.test.refunded" "com.cellsigma.kalemkuran.pro_version"


    public static final int APP_FREE = 0;
    public static final int APP_PRO = 1;


    public static final String KEY_PRAY_URL="https://sigmastorage.s3.amazonaws.com/Qfiles/SmartDua/";



    public static LatLng MEKKEH_LOCATION = new LatLng(21.42664, 39.82563);
    public static LatLng MEDINA_LOCATION = new LatLng(24.468614, 39.610861);
    public static LatLng KARBALA_LOCATION = new LatLng(32.649881, 44.003102);
    public static LatLng NAJAF_LOCATION = new LatLng(32.055013, 44.328101);
    public static LatLng MASHHAD_LOCATION =new LatLng(36.287881, 59.615217);




}

