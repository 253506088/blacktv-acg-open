package blacktv.tvacg.tool;

import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.Properties;


/**
 * 用于读取Property文件的工具类
 *
 * @author hp
 */
@Log4j
public class GetProperty {
    /**
     * 通过类加载器进行获取properties文件流,适用于web项目
     *
     * @param fileName
     * @return
     */
    public Properties getPropertyByFileName(String fileName) {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/" + fileName);
            props.load(in);
        } catch (FileNotFoundException e) {
            log.error(fileName + "未找到");
        } catch (IOException e) {
            log.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(fileName + "文件流关闭出现异常");
            }
        }
        return props;
    }

    /**
     * 适用于非web项目
     * 比如读取config/email.properties,则path=config,fileName=email.properties
     *
     * @param path     路径
     * @param fileName 文件名
     * @return
     */
    public Properties getPropertyByFileName(String path, String fileName) {
        Properties props = new Properties();
        InputStream in = null;
        try {
            File file = new File(path + "/" + fileName);
            in = new FileInputStream(file);
            props.load(in);
        } catch (FileNotFoundException e) {
            log.error(fileName + "未找到");
        } catch (IOException e) {
            log.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(fileName + "文件流关闭出现异常");
            }
        }
        return props;
    }
}
