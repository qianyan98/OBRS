package com.makiru.utils;

import java.util.Random;

public class VerifyCodeUtil {
    public static String getVerifyCode(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        while (builder.length() < Constant.VERIFY_CODE_LEN){
            int choice = random.nextInt(2);
            if(choice == 0){
                builder.append(random.nextInt(10));
            }else{
                builder.append((char)('A' + random.nextInt(26)));
            }
        }

        return builder.toString();
    }
}
