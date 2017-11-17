package com.ravi.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSingle)
    Button btnSingle;
    @BindView(R.id.btnMultiple)
    Button btnMultiple;

    String[] permissionsRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSingle, R.id.btnMultiple})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSingle:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!PermissionUtil.checkSinglePermission(PermissionUtil.PermissionString.CAMERA, MainActivity.this)) {
                        PermissionUtil.requestSinglePermission(PermissionUtil.PermissionString.CAMERA, PermissionUtil.PermissionCode.CAMERA, "This app needs camera permission.", MainActivity.this);
                    } else {
                        Toast.makeText(getBaseContext(), "We got the Camera Permission", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "This device does not require runtime permission.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnMultiple:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permissionsRequired = new String[]{PermissionUtil.PermissionString.WRITE_EXTERNAL_STORAGE, PermissionUtil.PermissionString.ACCESS_FINE_LOCATION, PermissionUtil.PermissionString.READ_CONTACTS};
                    if (!PermissionUtil.checkMultiplePermission(permissionsRequired, MainActivity.this)) {
                        PermissionUtil.requestMultiplePermission(permissionsRequired, PermissionUtil.PermissionCode.MULTIPLE_PERMISSION, "This app needs Storage, Location and Contact permissions.", MainActivity.this);
                    } else {
                        Toast.makeText(MainActivity.this, "All permission granted.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "This device does not require runtime permission.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.PermissionCode.MULTIPLE_PERMISSION) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                Toast.makeText(MainActivity.this, "All permission granted.", Toast.LENGTH_SHORT).show();
            } else if (PermissionUtil.shouldShowMultipleRequestPermissionRationale(permissionsRequired, MainActivity.this)) {
                PermissionUtil.requestMultiplePermission(permissionsRequired, PermissionUtil.PermissionCode.MULTIPLE_PERMISSION, "This app needs Storage, Location and Contact permissions.", MainActivity.this);
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PermissionUtil.PermissionCode.CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(), "We got the Camera Permission", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
