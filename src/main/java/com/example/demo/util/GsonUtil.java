package com.example.demo.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static class GsonHolder{
        private static final Gson INSTANCE = new Gson();
    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static Gson gson() {
        return GsonHolder.INSTANCE;
    }

}
