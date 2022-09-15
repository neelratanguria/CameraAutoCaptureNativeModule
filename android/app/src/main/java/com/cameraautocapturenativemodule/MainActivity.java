package com.cameraautocapturenativemodule;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cameraautocapturenativemodule.listeners.PictureCapturingListener;
import com.cameraautocapturenativemodule.services.APictureCapturingService;
import com.cameraautocapturenativemodule.services.PictureCapturingServiceImpl;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends ReactActivity implements PictureCapturingListener, ActivityCompat.OnRequestPermissionsResultCallback{


  @Override
  protected String getMainComponentName() {
    return "CameraAutoCaptureNativeModule";
  }

  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new MainActivityDelegate(this, getMainComponentName());
  }

  private static final String[] requiredPermissions = {
          Manifest.permission.WRITE_EXTERNAL_STORAGE,
          Manifest.permission.CAMERA,
  };
  private static final int MY_PERMISSIONS_REQUEST_ACCESS_CODE = 1;

  @Override
  public void onDoneCapturingAllPhotos(TreeMap<String, byte[]> picturesTaken) {
//    if (picturesTaken != null && !picturesTaken.isEmpty()) {
//      //showToast("Done capturing all photos!");
//      return;
//    }
//    Log.v("Picture",picturesTaken.toString());
//    //showToast("No camera detected!");
  }

  @Override
  public void onCaptureDone(String pictureUrl, byte[] pictureData) {
//    if (pictureData != null && pictureUrl != null) {
//      runOnUiThread(() -> {
//        final Bitmap bitmap = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
//        final int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//        final Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//        if (pictureUrl.contains("0_pic.jpg")) {
////          uploadBackPhoto.setImageBitmap(scaled);
//        } else if (pictureUrl.contains("1_pic.jpg")) {
////          uploadFrontPhoto.setImageBitmap(scaled);
//        }
//      });
//      //showToast("Picture saved to " + pictureUrl);
//      Log.d("Picture saved to " , pictureUrl);
//    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String permissions[], @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_ACCESS_CODE: {
        if (!(grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
          checkPermissions();
        }
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.M)
  public void checkPermissions() {
    Log.d("Aditya", "Inside CheckPermission()");
    final List<String> neededPermissions = new ArrayList<>();
    for (final String permission : requiredPermissions) {
      if (ContextCompat.checkSelfPermission(getApplicationContext(),
              permission) != PackageManager.PERMISSION_GRANTED) {
        neededPermissions.add(permission);
      }
    }
    if (!neededPermissions.isEmpty()) {
      requestPermissions(neededPermissions.toArray(new String[]{}),
              MY_PERMISSIONS_REQUEST_ACCESS_CODE);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("Aditya", "Inside OnCreate");
    checkPermissions();
  }
  public static class MainActivityDelegate extends ReactActivityDelegate {
    public MainActivityDelegate(ReactActivity activity, String mainComponentName) {
      super(activity, mainComponentName);
    }

    @Override
    protected ReactRootView createRootView() {
      ReactRootView reactRootView = new ReactRootView(getContext());
      // If you opted-in for the New Architecture, we enable the Fabric Renderer.
      reactRootView.setIsFabric(BuildConfig.IS_NEW_ARCHITECTURE_ENABLED);
      return reactRootView;
    }

    @Override
    protected boolean isConcurrentRootEnabled() {
      // If you opted-in for the New Architecture, we enable Concurrent Root (i.e. React 18).
      // More on this on https://reactjs.org/blog/2022/03/29/react-v18.html
      return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    }
  }
}
