package com.whomade.kycarrots.framework.common.util;

import com.whomade.kycarrots.framework.common.object.DataMap;

public final class PagingUtil {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    private PagingUtil() {}

    /** pageNo는 1-base 기준, 유효범위 보정 포함 */
    public static void applyPaging(DataMap param, Integer pageNo, Integer pageSize) {
        int ps = normalizePageSize(pageSize);
        int pn = normalizePageNo(pageNo);
        int offset = (pn - 1) * ps;

        param.put("offset", offset);
        param.put("limit", ps);
    }

    public static DataMap withPaging(DataMap base, Integer pageNo, Integer pageSize) {
        applyPaging(base, pageNo, pageSize);
        return base;
    }

    private static int normalizePageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) return DEFAULT_PAGE_SIZE;
        return Math.min(pageSize, MAX_PAGE_SIZE);
    }

    private static int normalizePageNo(Integer pageNo) {
        if (pageNo == null || pageNo <= 0) return 1;
        return pageNo;
    }
}
