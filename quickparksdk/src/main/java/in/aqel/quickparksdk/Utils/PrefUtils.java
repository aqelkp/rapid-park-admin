package in.aqel.quickparksdk.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahammad on 24/01/16.
 */
public class PrefUtils {

    private static String sp = "sPrefs";
    private static String spEmail = "email";
    private static String spName = "name";
    private static String spProfilePic = "profilepic";
    private static String spParking = "parkingObject";
    private static String spParkingId = "parkingId";
    private static String spLogedin ="logedin";

    public static void setEmail(Context context, String email){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spEmail, email);
        editor.commit();
    }

    public static String getEmail(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spEmail, null);
    }

    public static void setParking(Context context, String parking){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spParking, parking);
        editor.commit();
    }

    public static String getParking(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spParking, null);
    }

    public static void setParkingId(Context context, String parking){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spParkingId, parking);
        editor.commit();
    }

    public static String getParkingId(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spParkingId, null);
    }

    public static String getProfilePic(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spProfilePic, null);
    }

    public static String getName(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spName, null);
    }

    public static void setName(Context context, String name) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spName, name);
        editor.commit();
    }

    public static void setProfilePic(Context context, String profilepic) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spProfilePic, profilepic);
        editor.commit();
    }
    public static void setLogedin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(spLogedin, true);
        editor.commit();

    }
    public static Boolean isLogedin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        return pref.getBoolean(spLogedin, false);

    }


    public static void clearpref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
