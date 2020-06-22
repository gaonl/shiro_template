package com.magicli.security.captcha;

import com.magicli.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

public abstract class CaptchaDao {
    /**
     * 发送短信的类型  手机短信消息验证码
     */
    public static final String MESSAGE_TYPE = "mobile_captcha";
    /**
     * 验证码过期时间
     */
    public static final Long EXPIRE_TIME = 60L;

    public abstract void doStoreByKeyValue(String key, String captchaCode, Long expire);

    public abstract String doGetByKey(String key);

    public abstract void doRemoveByKey(String key);

    /**
     * 标志真假的，用于测试
     *
     * @return boolean
     */
    public boolean fake() {
        return Boolean.parseBoolean(PropertiesUtil.getProperty("sms.fake"));
    }

    public synchronized String nextCaptcha(String phone) {
        String key = getKey(phone);

        String captcha = genCaptcha();
        doStoreByKeyValue(key, captcha, EXPIRE_TIME);
        return captcha;

    }

    public synchronized boolean captchaVerify(String phone, String captchaFromCustomer) {
        String key = getKey(phone);
        String captchaFromServer = doGetByKey(key);

        if (StringUtils.equals(captchaFromServer, captchaFromCustomer)) {
            //验证成功，就要删掉验证码了
            doRemoveByKey(key);
            return true;
        }

        /**
         * 判断是否开启了体验功能，并且手机号和验证码都是配置写死的
         */
        boolean taste = Boolean.parseBoolean(PropertiesUtil.getProperty("taste"));
        String tastePhone = PropertiesUtil.getProperty("taste.phone");
        String tasteCaptcha = PropertiesUtil.getProperty("taste.captcha");
        if (taste && StringUtils.equals(tastePhone, phone) && StringUtils.equals(tasteCaptcha, captchaFromCustomer)) {
            return true;
        }

        return false;
    }

    private String getKey(String phone) {
        return CaptchaDao.MESSAGE_TYPE + "_" + phone;
    }

    private String genCaptcha() {
        String captcha = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        return captcha;
    }

}
