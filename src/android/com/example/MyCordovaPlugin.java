/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Date;

public class MyCordovaPlugin extends CordovaPlugin {
  private static final String TAG = "MyCordovaPlugin";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing MyCordovaPlugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("echo")) {
      String phrase = args.getString(0);
      // Echo back the first argument
      //Context context = View.getContext();
	CharSequence text = "Hello toast!";
	int duration = Toast.LENGTH_LONG;

	Toast toast = Toast.makeText(this.cordova.getActivity().getBaseContext(), text, duration);
	toast.show();
      final PluginResult result = new PluginResult(PluginResult.Status.OK, phrase);
      callbackContext.sendPluginResult(result);
      Log.d(TAG, phrase);


      final CordovaPlugin that = this;
      Intent intentScan = new Intent(that.cordova.getActivity().getBaseContext(), MainActivity.class);
      intentScan.addCategory(Intent.CATEGORY_DEFAULT);
      intentScan.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());

      that.cordova.startActivityForResult(that, intentScan, 1234);
    } else if(action.equals("getDate")) {
      // An example of returning data back to the web layer
      final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
      callbackContext.sendPluginResult(result);
    }
      else if(action.equals("getWifiDetails")){
        WifiManager mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo connInfo = mWifiManager.getConnectionInfo();

        //Get IP Address
        int ipAddress = connInfo.getIpAddress();


        //Converting IP address from hex to decimal
         ipAddressValue = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));

         JSONObject wifiObject = new JSONObject();
         wifiObject.put("IP Address", ipAddressValue);
         wifiObject.put("SSID", connInfo.getMacAddress());
         wifiObject.put("MAC Address", connInfo.getMacAddress());
         wifiObject.put("LinkSpeed", connInfo.getLinkSpeed() + WifiInfo.LINK_SPEED_UNITS);

         final PluginResult result = new PluginResult(PluginResult.Status.OK, wifiObject);
         callbackContext.sendPluginResult(result);
    }
    return true;
  }

}
