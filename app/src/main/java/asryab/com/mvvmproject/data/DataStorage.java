package asryab.com.mvvmproject.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

import asryab.com.mvvmproject.models.user.Language;
import asryab.com.mvvmproject.models.user.User;
import asryab.com.mvvmproject.utils.Arguments;

public final class DataStorage {

    private static Context sContext;
    private static SharedPreferences sSharedPreferences;


    public static void initSharedPreference(final Context context) {
        sContext = context;
        if(sSharedPreferences == null) {
            sSharedPreferences = sContext.getSharedPreferences(Arguments.DataStorage.DATA_STORAGE, Context.MODE_PRIVATE);
        }
    }

    private static SharedPreferences getPreferences() {
        if (sSharedPreferences == null) {
            initSharedPreference(sContext);
        }
        return sSharedPreferences;
    }

    public static void saveLanguage(Language language) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(Arguments.DataStorage.LANGUAGE, language.getId());
        editor.apply();
        saveLocale(language);
    }

    private static void saveLocale(Language language) {
        Locale locale = new Locale(language.getLocale());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        sContext.getResources().updateConfiguration(config,
                sContext.getResources().getDisplayMetrics());
    }

    public static Language getAppLanguage() {
        final int langId = getPreferences().getInt(Arguments.DataStorage.LANGUAGE, 1);
        return Language.valueOf(langId);
    }

    public static boolean isLanguageSelected() {
        return getPreferences().getInt(Arguments.DataStorage.LANGUAGE, -1) > 0;
    }

    public static void saveUser(User user) {
        setLogin(true);
        //TODO create save method
        Log.d("TTT", user.toString());
    }

    public static void updateCookie(final String cookie){
        if (sContext != null) {
            SharedPreferences.Editor shPrefEditor = sContext.getSharedPreferences(Arguments.DataStorage.PREFS_COOKIE, Context.MODE_PRIVATE).edit();
            shPrefEditor.putString(Arguments.DataStorage.PREFS_COOKIE_KEY, cookie);
            shPrefEditor.commit();
        }
    }

    public static String getCookies(){
        if (sContext != null) {
            SharedPreferences shPref = sContext.getSharedPreferences(Arguments.DataStorage.PREFS_COOKIE, Context.MODE_PRIVATE);
            return shPref.getString(Arguments.DataStorage.PREFS_COOKIE_KEY, null);
        }
        return null;
    }

    public static void clearCookies(){
        if (sContext != null) {
            SharedPreferences.Editor shPrefEditor = sContext.getSharedPreferences(Arguments.DataStorage.PREFS_COOKIE, Context.MODE_PRIVATE).edit();
            shPrefEditor.remove(Arguments.DataStorage.PREFS_COOKIE_KEY);
            shPrefEditor.commit();
        }
    }

    public static boolean hasCookies(){
        if (sContext != null) {
            SharedPreferences shPref = sContext.getSharedPreferences(Arguments.DataStorage.PREFS_COOKIE, Context.MODE_PRIVATE);
            return shPref.getString(Arguments.DataStorage.PREFS_COOKIE_KEY, null) != null;
        }
        return false;
    }

    public static void setLogin(boolean b){
        if (sContext != null) {
            SharedPreferences.Editor shPrefEditor = getPreferences().edit();
            shPrefEditor.putBoolean(Arguments.DataStorage.IS_LOGIN,b);
            shPrefEditor.commit();
        }
    }

    public static boolean isLogin(){
        if (sContext != null) {
            SharedPreferences shPref = getPreferences();
            return shPref.getBoolean(Arguments.DataStorage.IS_LOGIN, false);
        }
        return false;
    }
}
