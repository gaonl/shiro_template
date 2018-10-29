package com.magicli.util;

import java.io.*;

/**
 * Created by gaonl on 2018/9/19.
 */
public class SerializableUtils {
    public static byte[] serialize(Object serializable) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(serializable);

        oos.flush();
        oos.close();

        return baos.toByteArray();

    }

    public static Object deserialize(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);

        Object serializable = ois.readObject();
        ois.close();
        return serializable;

    }
}
