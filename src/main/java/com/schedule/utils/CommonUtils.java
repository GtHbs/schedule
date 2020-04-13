package com.schedule.utils;

import java.util.Objects;
import java.util.Random;

/**
 * @author: 李昭
 * @Date: 2020/4/13 13:43
 */
public class CommonUtils {

    public static boolean isEmpty(Object ... args) {
        for (int i = 0; i < args.length; ++i) {
            if (Objects.isNull(args[i])) {
                return false;
            }
        }
        return true;
    }

    public static Integer createId() {
        Random random = new Random();
        int id = random.nextInt(1000000000);
        return id;
    }
}
