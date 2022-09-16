package com.makiru.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class Constant {
    public static final String USER_SESSION = "USER_SESSION";
    public static final String MESSAGE = "msg";
    public static Integer ADMIN = 1;
    public static Integer NORMAL_USER = 2;
    public static Integer EXPIRING = 5;
    public static Integer VERIFY_CODE_LEN = 5;

    public static String USER_BIRTHDAY = "1998-11-02";
    public static Integer USER_GENDER = 1;
    public static String USER_PHONE = "13588888888";
    public static String USER_ADDRESS = "上海";


    static {
        InputStream resource = Constant.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ADMIN = Integer.parseInt(properties.getProperty("admin"));
        NORMAL_USER = Integer.parseInt(properties.getProperty("normalUser"));
    }

}
