package com.sinwn.capsule.utils;

public class NumCheckUtil {

    private static final int DEF_COUNT = 10;

    public static int pageNo(String strPage) {
        int pageNo = 1;

        try {
            if (strPage != null) {
                pageNo = Integer.valueOf(strPage);
                if (pageNo <= 0) {
                    pageNo = 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageNo;
    }

    public static int pageCount(String strCount) {
        int pageCount = DEF_COUNT;

        try {
            if (strCount != null) {
                pageCount = Integer.valueOf(strCount);
                if (pageCount <= 0) {
                    pageCount = DEF_COUNT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageCount;
    }
}
