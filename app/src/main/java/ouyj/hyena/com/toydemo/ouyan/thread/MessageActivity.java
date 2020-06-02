package ouyj.hyena.com.toydemo.ouyan.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import ouyj.hyena.com.toydemo.R;
import ouyj.hyena.com.toydemo.utils.DateUtil;

/**
 * (1)主线程与子线程间的消息传递
 * 1.主线程中创建一个Handler（处理器）对象，重写handleMessage方法
 *   一旦接收到Message，此方法就会被执行（它运行于主线程中）
 * 2.按钮的单击事件处理（启动、停止线程）
 * 3.定义内部的子线程类（派生自Thread）
 *   子线程中创建一个Message对象，通过主线程的Handler将Message对象发送出去
 *   sendEmptyMessage：发送开始播放的空消息(直接指定What）
 *   sendMessage：发送消息对象（what消息标识）
 *
 * 4.从数组中随机选择一个元素
 * 5.文本视图（多行文本的显示）
 * 6.获取当前系统时间
 */
public class MessageActivity extends AppCompatActivity
        implements View.OnClickListener {

    //线程是否在运行（播放新闻）
    private boolean isPlaying = false;

    //文本视图
    private TextView txtMessage;
    //1=滚动，2=结束
    private int BEGIN = 1, END = 2;
    //新闻列表
    private String[] newsList = {
            "北斗三号卫星发射成功！",
            "拉斯维加斯发生枪击事件！",
            "越南跨海大桥未建完已下沉。",
            "南水北调，数亿人喝上长江水。",
            "马克龙呼吁重建强大欧洲。"
    };

    /**
     * 主线程中创建一个处理器对象
     */
    private Handler handler = new Handler() {
        //收到消息时进行处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //取得文本视图的现有内容
            String title = txtMessage.getText().toString();
            if (msg.what == BEGIN) {
                //开始播放
                title = String.format(
                        "%s\n%s %s",
                        title,
                        DateUtil.getNowTime(),
                        msg.obj
                );
            } else if (msg.what == END) {
                //结束播放
                title = String.format(
                        "%s\n%s %s",
                        title,
                        DateUtil.getNowTime(),
                        "新闻联播结束"
                );
            }
            txtMessage.setText(title);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //获取文本视图
        txtMessage = findViewById(R.id.txt_message);
        //设置文本视图的文字对齐方式（靠左且靠下）
        txtMessage.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        //设置文本视图的高度并且最多显示8行文字
        txtMessage.setLines(8);
        txtMessage.setMaxLines(8);
        //设置文本视图的内部文本滚动方式
        txtMessage.setMovementMethod(new ScrollingMovementMethod());


        //设置按钮的事件
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    /**
     * 按钮的单击事件处理（启动、停止线程）
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            //启动线程（调用Thread的start方法）
            if (!isPlaying) {
                isPlaying = true;
                new PlayThread().start();
            }
        }
        else if (v.getId() == R.id.btn_stop) {
            //停止线程
            isPlaying = false;
        }
    }

    /**
     * 内部的子线程类（派生自Thread）
     */
    private class PlayThread extends Thread {
        @Override
        public void run() {
            super.run();

            //死循环（判断标志位）
            while (isPlaying) {
                //休眠指定时间（需捕获异常）
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //创建消息对象实例
                Message message = Message.obtain();
                //消息标识
                message.what = BEGIN;
                //消息内的数据（从数组中随机选择一个元素）
                message.obj = newsList[(int) (Math.random() * 30 % 5)];
                //立即发送消息
                handler.sendMessage(message);
            }
            //发送结束运行的空消息(直接指定What）
            handler.sendEmptyMessage(END);
            //子线程已运行完成
            isPlaying = false;
        }
    }
}
