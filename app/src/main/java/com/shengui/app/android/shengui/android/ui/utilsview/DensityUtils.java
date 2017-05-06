package com.shengui.app.android.shengui.android.ui.utilsview;
import android.content.Context;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类 
 * @author yybo
 *
 */
public class DensityUtils  
{  
    private DensityUtils()  
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");
    }  
  
    /** 
     * dp转px 
     *  
     * @param context 
     * @param val 
     * @return 
     */  
    public static int dp2px(Context context, float dpVal)
    {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());  
    }


    public static int dp2px(Context context, int dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    /** 
     * sp转px 
     *  
     * @param context 
     * @param val 
     * @return 
     */  
    public static int sp2px(Context context, float spVal)
    {  
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());  
    }  
  
    /** 
     * px转dp 
     *  
     * @param context 
     * @param pxVal 
     * @return 
     */  
    public static float px2dp(Context context, float pxVal)
    {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (pxVal / scale);  
    }  
  
    /** 
     * px转sp 
     *  
     * @param fontScale 
     * @param pxVal 
     * @return 
     */  
    public static float px2sp(Context context, float pxVal)
    {  
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);  
    }

    public static int dp(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()) + 0.5F);
    }

}
