package com.sarriaroman.PhotoViewer;

import android.content.Intent;
import android.os.Build;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Class to Open PhotoViewer with the Required Parameters from Cordova
 * - URL
 * - Title
 */
public class PhotoViewer extends CordovaPlugin {

    // we keep these just in case you later want to re-enable the old behaviour,
    // but we DON'T use them now.
    // public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    // public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    // public static final String READ_IMAGES = Manifest.permission.READ_MEDIA_IMAGES;
    // public static final int REQ_CODE = 0;
    public static final int PERMISSION_DENIED_ERROR = 20;

    protected JSONArray args;
    protected CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("show".equals(action)) {
            this.args = args;
            this.callbackContext = callbackContext;

            // ✅ NEW: we just launch, no permission checks
            this.launchActivity();
            return true;
        }
        return false;
    }

    // no-op now; kept for compatibility
    protected void getPermission() {
        // intentionally empty: we don't need runtime permissions
    }

    protected void launchActivity() throws JSONException {
        Intent i = new Intent(this.cordova.getActivity(), com.sarriaroman.PhotoViewer.PhotoActivity.class);
        i.putExtra("ARGS", this.args.toString());
        this.cordova.getActivity().startActivity(i);
        this.callbackContext.success("");
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException {
        // since we don't request permissions anymore, we can leave this empty
        // or just always succeed
        this.launchActivity();
    }
}
