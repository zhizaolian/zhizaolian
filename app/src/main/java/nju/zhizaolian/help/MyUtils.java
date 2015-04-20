package nju.zhizaolian.help;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lk on 15/4/20.
 */
public class MyUtils {
    public static String getCurrentDate(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());

        String s=format.format(date);
        return s;
    }

}
