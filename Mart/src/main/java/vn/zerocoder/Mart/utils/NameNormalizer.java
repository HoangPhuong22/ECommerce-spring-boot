package vn.zerocoder.Mart.utils;

public class NameNormalizer {
    public static String normalize(String name) {
        if(name.isEmpty()) {
            return name;
        }
        String[] a = name.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String s : a) {
            result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }
}
