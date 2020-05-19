package ouyj.hyena.com.toydemo.Handler;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import ouyj.hyena.com.toydemo.R;

/**
 * 延时执行任务（20200520）
 */
public class DelayActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay);

        //显示圆圈进度对话框
        dialog = ProgressDialog.show(
                DelayActivity.this,
                "请稍候",
                "正在努力加载页面"
        );

        //延迟指定毫秒后自动关闭
        handler.postDelayed(dialogClosed, 1500);
    }

    /**
     * 关闭对话框的任务
     */
    private Runnable dialogClosed = new Runnable() {
        @Override
        public void run() {
            //对话框是否在显示
            if (dialog.isShowing()) {
                //关闭对话框
                dialog.dismiss();
            }
        }
    };
}
