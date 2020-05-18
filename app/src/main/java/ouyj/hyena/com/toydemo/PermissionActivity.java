package ouyj.hyena.com.toydemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


/**
 * 权限：
 * 活动页权限的检测可放置在onResume中
 */
public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }


    /**
     * 检测活动页是否拥有指定权限
     */
    @Override
    protected void onResume() {
        super.onResume();

        //判断应用是否申请了相应权限
        int flag= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if ( flag != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, "请授予相应的权限！", Toast.LENGTH_SHORT).show();
            return;
        }
    }



}
