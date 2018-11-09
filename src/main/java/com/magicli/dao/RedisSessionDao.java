package com.magicli.dao;

import com.magicli.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * Created by gaonl on 2018/9/18.
 */
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    public static final String ip = "127.0.0.1";
    public static final int port = 6379;

    protected Serializable doCreate(Session session) {

        Serializable sessionId = super.doCreate(session);
        System.out.println("------------------>保存session,id: " + sessionId);
        Jedis jedis = null;
        try {
            jedis = new Jedis(ip, port);
            jedis.set(SerializableUtils.serialize(sessionId), SerializableUtils.serialize(session));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        System.out.println("------------------>读取session,id: " + sessionId);

        Jedis jedis = null;
        try {
            jedis = new Jedis(ip, port);
            byte[] data = jedis.get(SerializableUtils.serialize(sessionId));
            if (data == null) {
                return null;
            }
            Session session = (Session) SerializableUtils.deserialize(data);

            //将获取到的session缓存起来，不知道为啥CachingSessionDao为啥没有实现
            cache(session, sessionId);

            return session;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    protected void doUpdate(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("------------------>更新session,id: " + sessionId);
        Jedis jedis = null;
        try {
            jedis = new Jedis(ip, port);

            byte[] serializedSessionId = SerializableUtils.serialize(sessionId);

            boolean exists = jedis.exists(serializedSessionId);
            if (exists) {
                jedis.set(serializedSessionId, SerializableUtils.serialize(session));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    protected void doDelete(Session session) {
        Serializable sessionId = session.getId();
        System.out.println("------------------>删除session,id: " + sessionId);
        Jedis jedis = null;
        try {
            jedis = new Jedis(ip, port);
            jedis.del(SerializableUtils.serialize(sessionId));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}
