package com.ravi.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by botree on 11/17/17.
 */

public class PermissionUtil {
    public static class PermissionString {
        public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
        public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
        public static final String CAMERA = Manifest.permission.CAMERA;
        public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
        public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
        public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
        public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
        public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
        public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
        public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
        public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
        public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
        public static final String USE_SIP = Manifest.permission.USE_SIP;
        public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
        public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
        public static final String SEND_SMS = Manifest.permission.SEND_SMS;
        public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
        public static final String READ_SMS = Manifest.permission.READ_SMS;
        public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
        public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
        public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    public static class PermissionCode {
        public static final Integer MULTIPLE_PERMISSION = 1000;
        public static final Integer READ_CALENDAR = 1001;
        public static final Integer WRITE_CALENDAR = 1002;
        public static final Integer CAMERA = 1003;
        public static final Integer READ_CONTACTS = 1004;
        public static final Integer WRITE_CONTACTS = 1005;
        public static final Integer GET_ACCOUNTS = 1006;
        public static final Integer ACCESS_FINE_LOCATION = 1007;
        public static final Integer ACCESS_COARSE_LOCATION = 1008;
        public static final Integer RECORD_AUDIO = 1009;
        public static final Integer READ_PHONE_STATE = 1010;
        public static final Integer CALL_PHONE = 1011;
        public static final Integer READ_CALL_LOG = 1012;
        public static final Integer WRITE_CALL_LOG = 1013;
        public static final Integer ADD_VOICEMAIL = 1014;
        public static final Integer USE_SIP = 1015;
        public static final Integer PROCESS_OUTGOING_CALLS = 1016;
        public static final Integer BODY_SENSORS = 1017;
        public static final Integer SEND_SMS = 1018;
        public static final Integer RECEIVE_SMS = 1019;
        public static final Integer READ_SMS = 1020;
        public static final Integer RECEIVE_WAP_PUSH = 1021;
        public static final Integer RECEIVE_MMS = 1022;
        public static final Integer READ_EXTERNAL_STORAGE = 1023;
        public static final Integer WRITE_EXTERNAL_STORAGE = 1024;
    }

    public static boolean checkSinglePermission(String permission, Context context) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkMultiplePermission(String[] permission, Context context) {
        for (String aPermission : permission) {
            if (ContextCompat.checkSelfPermission(context, aPermission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

    public static boolean shouldShowMultipleRequestPermissionRationale(String[] permission, Activity activity) {
        for (String aPermission : permission) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, aPermission)) {
                return false;
            }
        }
        return true;
    }


    public static void requestSinglePermission(String permission, Integer code, String message, final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            //Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

            new AlertDialog.Builder(activity)
                    .setTitle(R.string.permission_required)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openSettingForPermission(activity);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();


        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, code);
        }
    }

    public static void requestMultiplePermission(String[] permission, Integer code, String message, final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            //Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

            new AlertDialog.Builder(activity)
                    .setTitle(R.string.need_multiple_permissions)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.grant, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openSettingForPermission(activity);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();


        } else {
            ActivityCompat.requestPermissions(activity, permission, code);
        }
    }

    //Open setting screen for app permission
    private static void openSettingForPermission(Activity activity) {

        /*Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
        intent.setData(uri);
        mActivity.startActivity(intent);*/

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + activity.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(i);
    }
}
