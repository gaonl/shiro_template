package com.magicli.security.captcha;


import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("captchaDao")
public class CaptchaDaoMemoryImpl extends CaptchaDao {
    private Map<String, String> captchaMap = new ConcurrentHashMap<>();

    @Override
    public void doStoreByKeyValue(String key, String value, Long expire) {
        captchaMap.put(key, value);
    }

    @Override
    public String doGetByKey(String key) {
        return captchaMap.get(key);
    }

    @Override
    public boolean fake() {
        return super.fake();
    }

    @Override
    public void doRemoveByKey(String key) {
        captchaMap.remove(key);
    }
}
