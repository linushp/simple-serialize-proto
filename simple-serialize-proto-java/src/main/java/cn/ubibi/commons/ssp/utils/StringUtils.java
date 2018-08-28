package cn.ubibi.commons.ssp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return isEmpty(str) || str.trim().isEmpty();
    }

    public static String camel2Underline(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
