package nju.zhizaolian.help;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by lk on 15/4/23.
 */
public class PictureUtil {

    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
    }
}
