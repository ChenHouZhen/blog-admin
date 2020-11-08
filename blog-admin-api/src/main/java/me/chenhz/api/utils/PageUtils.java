package me.chenhz.api.utils;


import me.chenhz.api.form.PageForm;

public class PageUtils {

    private static final int DETAULT_PAGE = 1;

    private static final int DETAULT_LIMIT = 1000;

    public static int getPage(PageForm form){
        Integer page = form.getPage();
        if (page == null || page < 0){
            return DETAULT_PAGE;
        }
        return page;
    }


    public static int getLimit(PageForm form){
        Integer limit  = form.getLimit();
        if (limit == null || limit < 0){
            return DETAULT_LIMIT;
        }
        return limit;
    }
}
