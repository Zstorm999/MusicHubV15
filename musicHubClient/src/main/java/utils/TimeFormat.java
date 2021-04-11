package utils;

/**
 *
 * @author CLEMENT Aimeric
 */
public class TimeFormat{
    
    /**
     * convert seconds into a time in hours, minutes and seconds
     * @param fullSec
     * @return a human readable time
     */
    public static String secToString(int fullSec){
        int hr= fullSec/3600;
        int min= (fullSec % 3600) / 60;
        int sec = fullSec % 60;

        return String.valueOf(hr)+"h "+format2Digits(min)+":"+format2Digits(sec);
    }
    
    private static String format2Digits(int num){
        String ret= String.valueOf(num);
        if(num<10)
            ret= "0"+ret;
        return ret;
    }
}

