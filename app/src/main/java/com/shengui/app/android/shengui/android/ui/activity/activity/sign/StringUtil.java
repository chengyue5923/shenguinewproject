package com.shengui.app.android.shengui.android.ui.activity.activity.sign;


public final class StringUtil {
    /**
     * Check whether the input string is empty
     *
     * @param input String
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static int getStringLength(int number) {
        return String.valueOf(number).length();
    }
}
