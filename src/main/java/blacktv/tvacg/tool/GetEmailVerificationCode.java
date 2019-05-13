package blacktv.tvacg.tool;

import java.util.Random;

/**
 * 随机生成验证码，可指定长度
 */
public class GetEmailVerificationCode {
    private static String[] strings = {"-","1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "g", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成验证码，可指定长度
     * @param length
     * @return
     */
    public static String getVerificationCode(int length) {
        String code = "";
        int max = strings.length;
        for (int i = 0; i < length; i++) {
            code += strings[new Random().nextInt(max-1)+1];
        }
        return code;
    }
}
