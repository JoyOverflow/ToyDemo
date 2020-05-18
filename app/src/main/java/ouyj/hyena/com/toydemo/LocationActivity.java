package ouyj.hyena.com.toydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 位置：
 * 活动继续时：为位置管理器的添加监听器
   活动销毁时：为位置管理器移除监听器
 */
public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }
}
