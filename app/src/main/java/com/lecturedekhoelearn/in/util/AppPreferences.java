package com.lecturedekhoelearn.in.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author itgc
 */
public class AppPreferences {
    static String MyPREFERENCES = "loginprefence";
    private static SharedPreferences mPrefs;
    private static SharedPreferences.Editor mPrefsEditor;

    private static SharedPreferences mPrefsnonclear;
    private static SharedPreferences.Editor mPrefsEditornonclear;

    public static void Logout(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.clear().commit();
    }


    public static String getID(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("regid", "");
        Log.d("", "user id " + id);
        return id;
    }

    public static void setID(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("regid", value);
        mPrefsEditor.commit();
    }







    public static String getP_Name(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("Evn_Name", "");
        Log.d("", "user id " + id);
        return id;
    }

    public static void setP_Name(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("Evn_Name", value);
        mPrefsEditor.commit();
    }


    public static String getC_name(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("update_p", "");

        return id;
    }

    public static void setC_name(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("update_p", value);
        mPrefsEditor.commit();
    }

    public static String getP_phone(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("evn_venue", "");
        Log.d("", "user id " + id);
        return id;
    }

    public static void setP_phone(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("evn_venue", value);
        mPrefsEditor.commit();
    }

    public static String getP_email(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("date", "");
        Log.d("", "user id " + id);
        return id;
    }

    public static void setP_email(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("date", value);
        mPrefsEditor.commit();
    }

    public static String getC_phone(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("latlong", "");
        Log.d("", "user id " + id);
        return id;
    }

    public static void setC_phone(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("latlong", value);
        mPrefsEditor.commit();
    }


    public static String getC_email(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("gender", "");
        Log.d("", "gender " + id);
        return id;
    }

    public static void setC_email(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("gender", value);
        mPrefsEditor.commit();
    }


    public static String getDesignation(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("designation", "");
        Log.d("", "gender " + id);
        return id;
    }

    public static void setDesignation(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("designation", value);
        mPrefsEditor.commit();
    }


    public static String getInstitute(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("institute", "");
        Log.d("", "gender " + id);
        return id;
    }

    public static void setUserid(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("userid", value);
        mPrefsEditor.commit();
    }

    public static String getUserid(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("userid", "");
        Log.d("", "gender " + id);
        return id;
    }

    public static void setInstitute(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("institute", value);
        mPrefsEditor.commit();
    }


    public static String getQualification(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String id = mPrefs.getString("qualification", "");
        Log.d("", "gender " + id);
        return id;
    }

    public static void setQualification(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("qualification", value);
        mPrefsEditor.commit();
    }

    public static String getEvn_img(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("evn_image", "");
    }

    public static void setEvn_img(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("evn_image", value);
        mPrefsEditor.commit();
    }



    public static String getImage(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("image", "");
    }

    public static void setImage(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("image", value);
        mPrefsEditor.commit();
    }


    public static boolean isUserLoggedIn(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getBoolean("Login", false);
    }


    public static void setUserLoggedIn(Context ctx, Boolean value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putBoolean("Login", value);
        mPrefsEditor.commit();
    }


    public static String getUserName(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("username", "");
    }

    public static void setUserName(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("username", value);
        mPrefsEditor.commit();
    }

    public static String getMobile(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("mobile", "");
    }

    public static void setMobile(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("mobile", value);
        mPrefsEditor.commit();
    }


    public static String getSEmail(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("SEmail", "");
    }

    public static void setSEmail(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("SEmail", value);
        mPrefsEditor.commit();
    }


    public static String getEmail(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("Email", "");
    }

    public static void setEmail(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("Email", value);
        mPrefsEditor.commit();
    }


    public static void setName(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("lname", value);
        mPrefsEditor.commit();
    }

    public static String getName(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("lname", "");
    }


    public static void setLName(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("lastname", value);
        mPrefsEditor.commit();
    }

    public static String getLName(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("lastname", "");
    }


    public static String getPath(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("path", "");
    }

    public static void setPath(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("path", value);
        mPrefsEditor.commit();
    }


    public static String getPass(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("pass", "");
    }

    public static void setPass(Context ctx, String value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("pass", value);
        mPrefsEditor.commit();
    }

    public static String getUniqueCode(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getString("uniqCode", "");
    }


    public static String getToken(Context ctx) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefsnonclear.getString("tokenId", "");
    }

    public static void setToken(Context ctx, String value) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditornonclear = mPrefsnonclear.edit();
        mPrefsEditornonclear.putString("tokenId", value);
        mPrefsEditornonclear.commit();
    }


    public static String getDetail(Context ctx) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefsnonclear.getString("details", "");
    }

    public static void setDetail(Context ctx, String value) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditornonclear = mPrefsnonclear.edit();
        mPrefsEditornonclear.putString("details", value);
        mPrefsEditornonclear.commit();
    }

    public static String getEvn_about(Context ctx) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefsnonclear.getString("evn_about", "");
    }

    public static void setEvn_about(Context ctx, String value) {
        mPrefsnonclear = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditornonclear = mPrefsnonclear.edit();
        mPrefsEditornonclear.putString("evn_about", value);
        mPrefsEditornonclear.commit();
    }


    public static boolean getDele(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return mPrefs.getBoolean("area", false);
    }

    public static void setDele(Context ctx, boolean value) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putBoolean("area", value);
        mPrefsEditor.commit();
    }

    public static void seveDocumentList(Context ctx, List<String> docList) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();


        Set<String> docSet = new HashSet<>();
        for (String docPath :
                docList) {
            docSet.add(docPath);
        }

        mPrefsEditor.putStringSet("doc_list", docSet);

        mPrefsEditor.commit();
    }

    public static List<String> getDocumentList(Context ctx) {
        mPrefs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        List<String> docList = null;
        Set<String> docSet = mPrefs.getStringSet("doc_list", null);

        if (docSet != null) {
            docList = new ArrayList<>();
            for (String path :
                    docSet) {
                docList.add(path);
            }

        }
        return docList;
    }


}
