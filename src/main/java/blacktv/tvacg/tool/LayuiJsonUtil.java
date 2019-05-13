package blacktv.tvacg.tool;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * 给layui的数据表格返回json数据用的工具
 */

public class LayuiJsonUtil {
    /**
     * 给layui的数据表格返回json数据用的工具
     *
     * @param code  一般传入0
     * @param count 总页数
     * @param msg   一般传入空字符串就行
     * @param data  主体数据
     * @return
     */
    public static String getLayuiJson(int code, long count, String msg, List data) {
        if (msg == null || msg.length() < 1)
            msg = "\"\"";
        return "{\"code\": " + code + ",\"msg\": " + msg + ",\"count\": " + count + ",\"data\": " + JSONArray.toJSONString(data) + "}";
    }
}
