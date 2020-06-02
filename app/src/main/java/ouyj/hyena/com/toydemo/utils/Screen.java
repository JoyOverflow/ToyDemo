package ouyj.hyena.com.toydemo.utils;

import android.content.Context;

public class Screen {

    /**
     * 将dp（设备无关像素）转换为px（像素）
     * @param context
     * @param dp
     * @return
     */
    public static int dip2px(Context context, float dp) {
        //获取当前手机的屏幕密度
        final float scale = context.getResources().getDisplayMetrics().density;
        //得到像素值（四舍五入取整）
        return (int) (dp * scale + 0.5f);
    }




}
