package vn.zerocoder.utils;

public class NameNormalizer {
    public static String normalize(String name) {
        String[] a = name.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String s : a) {
            result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }
}
