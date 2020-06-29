package id.wrbcatering.aplikasi.indihomo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.Context.ACTIVITY_SERVICE;

public class Pras {
    public static final String APP_VERSION = "1";
    public static final String SP_NAME = "UburUburTing";


    public static String generateId() {
        return "android-" + RandomStringUtils.randomAlphanumeric(60);
    }





    public static String bangsatkau(String string) {
        String output = "";
        try {
            byte[] data = string.getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            output = base64;
        } catch (Exception e) {

        }
        return output;
    }
}
