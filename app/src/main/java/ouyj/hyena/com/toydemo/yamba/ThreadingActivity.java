package ouyj.hyena.com.toydemo.yamba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ouyj.hyena.com.toydemo.R;

/**
 * (1)按钮（Button）点击处理事件（由活动类实现接口）
 * (2)Java的原生线程
 * 1.定义一个线程内部类（重写run方法，内使用try..catch语句）
 * 2.定义线程类成员字段和"是否已运行"标志字段
 * 4."启动"按钮：（线程未运行时）创建并启动线程
 * 6."停止"按钮：点击中断线程
 * 5.活动销毁时：如果线程对象不为null，则将子线程强行中断（interrupt）并设置为null
 */
public class ThreadingActivity extends AppCompatActivity implements View.OnClickListener{

    //运行时得到类名称
    private static final String TAG = ThreadingActivity.class.getSimpleName();
    private FetchThread fetcher;
    private boolean runFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threading);

        //设置按钮的单击事件
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(this.fetcher!=null) {
            //中断指定线程（目标线程的run方法内会抛出InterruptedException异常）
            //interrupt并不是立即停止线程，而通知线程希望它终止
            this.fetcher.interrupt();
            this.fetcher = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {

            //若未启动（已启动线程则忽略）
            if (!runFlag) {
                this.runFlag = true;

                //创建线程并启动（会调用Thread的run方法）
                //一个线程对象只能调用一次start（若再调用start,会抛出IllegalThreadStateException异常）
                //执行完Run线程状态变为死亡（Dead）此时再调用start,也会抛出以上异常（除非new一个新线程）
                this.fetcher = new FetchThread ();
                this.fetcher.start();

                Log.d(TAG, "启动工作线程！");
            }
        }
        else if (v.getId() == R.id.btn_stop) {
            //终止线程内死循环（修改标志位，使线程执行完run方法，从而自己结束线程）
            this.runFlag = false;
        }
        else{ }
    }


    /**
     * 私有的线程内部类（继承Thread类，而Thread类实现了Runnable接口）
     */
    private class FetchThread extends Thread{

        //Run方法运行结束后线程会自行终止（因此为保持运行，其内可执行一个死循环）
        @Override
        public void run() {
            super.run();

            //判断线程运行标志
            while (runFlag) {
                Log.d(TAG, "工作线程运行中！");
                try {
                    //休眠并让出CPU资源（模拟耗时操作）
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.d(TAG, "工作线程被强制中断！");
                    runFlag = false;
                }
            }

            //线程已结束运行，将标志复位
            Log.d(TAG, "工作线程运行结束！");
            runFlag = false;
        }
    }
}
