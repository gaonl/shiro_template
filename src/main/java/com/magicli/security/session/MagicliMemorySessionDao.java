package com.magicli.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MagicliMemorySessionDao extends CachingSessionDAO {

    private Map<Serializable, Session> sessionMap = new ConcurrentHashMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(MagicliMemorySessionDao.class);

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        LOGGER.info("创建session, id: " + sessionId);

        sessionMap.put(sessionId, session);

        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        LOGGER.info("读取session, id: " + sessionId);
        return sessionMap.get(sessionId);
    }

    protected void doUpdate(Session session) {
        LOGGER.info("更新session, id: " + session.getId());
        sessionMap.put(session.getId(), session);
    }

    protected void doDelete(Session session) {
        LOGGER.info("删除session, id: " + session.getId());
        sessionMap.remove(session.getId());
    }
}
