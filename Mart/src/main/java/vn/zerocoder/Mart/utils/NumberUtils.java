package vn.zerocoder.Mart.utils;

public class NumberUtils {
    public static boolean isNumber(Object str) {
        try {
            Long.parseLong(str.toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
