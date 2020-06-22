package com.magicli.security;


import com.magicli.security.captcha.CaptchaDao;
import com.magicli.security.exceptions.CaptchaCodeAuditFailException;
import com.magicli.security.exceptions.SysUserAuditFailException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 登录、登出Controller
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CaptchaDao captchaDao;

    /**
     * 后台登录页面
     *
     * @return 后台登录地址
     */
    @RequestMapping(value = "sysLogin")
    public String sysLogin() {
        return "manage/sysLogin";
    }

    /**
     * 登录
     *
     * @param loginParam 登录的参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public String login(@RequestBody LoginParam loginParam) {
        try {
            LOGGER.info("login-->" + loginParam.toString());
            AuthenticationToken token = createToken(loginParam);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            Session session = subject.getSession(true);
            //登录后，设置session过期时间
            session.setTimeout(30 * 24 * 3600000L);
            session.setAttribute(SessionUtil.SESSION_KEY, subject.getPrincipal());


            Map<String, Object> data = new HashMap<>();
            data.put(AllInOnePasswordToken.TOKEN_KEY, String.valueOf(session.getId()));
            data.put("info", subject.getPrincipal());
            return "登录成功后的一些数据信息";
        } catch (CaptchaCodeAuditFailException e) {
            LOGGER.error("e: ", e);
            return "验证码错误";
        } catch (SysUserAuditFailException e) {
            LOGGER.error("e: ", e);
            return "用户审核不通过";
        } catch (AuthenticationException e) {
            LOGGER.error("e: ", e);
            return "用户认证错误";
        } catch (Exception e) {
            LOGGER.error("e: ", e);
            return "其他的一些错误";
        }
    }

    /**
     * 登出
     */
    @ResponseBody
    @RequestMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "退出登录的提示信息";
    }

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     * @return 验证码结果
     */
    @ResponseBody
    @RequestMapping(value = "/sendCaptcha")
    public String sendCaptcha(String phone) {
        String captcha = captchaDao.nextCaptcha(phone);

        LOGGER.info("验证码：" + captcha);

        if (!captchaDao.fake()) {
            try {
                //发送验证码
                return "发送验证码成功!";
            } catch (Exception e) {
                LOGGER.error("e: ", e);
                return "发送验证码失败，请联系稍后重试!";
            }

        } else {
            return "验证码: " + captcha + "";
        }
    }

    private AuthenticationToken createToken(LoginParam loginParam) {

        AllInOnePasswordToken allInOnePasswordToken = new AllInOnePasswordToken();


        BeanUtils.copyProperties(loginParam, allInOnePasswordToken);
        allInOnePasswordToken.setPassword(loginParam.getPassword() == null ? "".toCharArray() : loginParam.getPassword().toCharArray());

        return allInOnePasswordToken;
    }

    public static class LoginParam {
        private String moduleId;
        private String openid;
        private String xcxOpenid;
        private String unionid;
        private String phone;
        private String captchaCode;
        private String username;
        private String password;

        public String getModuleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getXcxOpenid() {
            return xcxOpenid;
        }

        public void setXcxOpenid(String xcxOpenid) {
            this.xcxOpenid = xcxOpenid;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCaptchaCode() {
            return captchaCode;
        }

        public void setCaptchaCode(String captchaCode) {
            this.captchaCode = captchaCode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginParam [moduleId=" + moduleId + ", openid=" + (openid == null ? "real null" : openid)
                    + ",xcxOpenid=" + xcxOpenid + ", unionid=" + unionid + ", phone="
                    + phone + ", captchaCode=" + captchaCode + ", username=" + username + ", password=" + password
                    + "]";
        }
    }
}


