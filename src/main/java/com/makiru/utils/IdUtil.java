package com.makiru.utils;

import java.util.UUID;

public class IdUtil {
    public static String getNextId(){
        return UUID.randomUUID().toString();
    }
}
