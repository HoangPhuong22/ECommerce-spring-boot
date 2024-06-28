package vn.zerocoder.Mart.utils;

public class Normalizer {
    public static String nameNormalize(String name) {
        if(name.isEmpty()) return name;
        String[] a = name.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String s : a) {
            result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }
    public static String titleNormalize(String title) {
        if(title.isEmpty())  return title;
        String[] a = title.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0 ; i < a.length; i++) {
            if(i == 0) {
                result.append(a[i].substring(0, 1).toUpperCase()).append(a[i].substring(1).toLowerCase()).append(" ");
            } else {
                result.append(a[i].toLowerCase()).append(" ");
            }
        }
        return result.toString().trim();
    }

}
