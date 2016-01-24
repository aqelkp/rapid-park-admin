package in.aqel.quickparksdk.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahammad on 24/01/16.
 */
public class PrefUtils {

    private static String sp = "sPrefs";
    private static String spEmail = "email";
    private static String spParking = "parkingObject";
    private static String spParkingId = "parkingId";

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



}
