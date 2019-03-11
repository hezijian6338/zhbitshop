package com.bootdo.utils;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author Mickey</a>
 */

public class ChangePageUtils {
    public static ModelAndView ChangePage(String obj) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(obj);
        return mav;
    }
}

