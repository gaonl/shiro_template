package com.magicli.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;


public class MagicliRedisSessionDao extends CachingSessionDAO {
    private static final String SESSION_HASH_KEY = "INTRODUCE_SESSION_HASH_KEY";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MagicliRedisSessionDao.class);

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        LOGGER.info("创建session, id: " + sessionId);

        try {
            redisTemplate.opsForHash().put(SESSION_HASH_KEY, sessionId, session);
        } catch (Exception e) {
            LOGGER.error("e: ", e);
        }

        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        LOGGER.info("读取session, id: " + sessionId);
        try {
            Session session = (Session) redisTemplate.opsForHash().get(SESSION_HASH_KEY, sessionId);

            if (session == null) {
                LOGGER.info("根据session id :" + String.valueOf(sessionId) + " 获取不到session.");
            }

            return session;
        } catch (Exception e) {
            LOGGER.error("e: ", e);
            return null;
        }
    }

    protected void doUpdate(Session session) {
        LOGGER.info("更新session, id: " + session.getId());
        try {
            redisTemplate.opsForHash().put(SESSION_HASH_KEY, session.getId(), session);
        } catch (Exception e) {
            LOGGER.error("e: ", e);
        }
    }

    protected void doDelete(Session session) {
        LOGGER.info("删除session, id: " + session.getId());
        try {
            redisTemplate.opsForHash().delete(SESSION_HASH_KEY, session.getId());
        } catch (Exception e) {
            LOGGER.error("e: ", e);
        }
    }
}
