package nju.zhizaolian.help;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lk on 15/4/20.
 */
public class MyUtils {
    public static String getCurrentDate(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        String s=format.format(c.getTime());
        return s;
    }

}
