package com.hanium.smartdispenser.ai;

public abstract class AiUtils {

    public static int roundOrZero(Double d) {
        return d == null ? 0 : (int) Math.round(d);
    }
}
