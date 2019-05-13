package blacktv.tvacg.tool;

import java.io.*;

/**
 * ���������л����ַ��������ַ��������л�Ϊ����
 */
public class SerializeUtils {
    /**
     * ���������л�Ϊ�ַ���������,����Ķ������Ҫ����Serializable�ӿ�
     * @param obj
     * @return
     * @throws IOException
     */
    public static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);//���������л��������byteArrayOutputStream����
        String string = byteArrayOutputStream.toString("ISO-8859-1");//ȡ��byteArrayOutputStream���е��ַ������ַ������ܸģ��ľͳ���
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return string;
    }

    /**
     * ���ַ��������л�ΪObject���͵Ķ���
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object serializeToObject(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));//�ַ������ܸģ��ľͳ���
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }
}