package ru.basejava.webapp.util;

import ru.basejava.webapp.model.Institution;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Institution.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }
}
