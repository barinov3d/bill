package com.barinov3dgmail.bill;

import java.text.SimpleDateFormat;

/**
 * Created by Vitaly on 20.08.2015.
 */
public class Utils {

    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

}
