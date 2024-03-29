package blacktv.tvacg.database.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author hp
 *
 */
@Service
public class JwtUtil {
    @Value("${tvacg.jjwt.key}")
    private String key;

    /**
     * 创建jwt
     *
     * @param id        JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
     * @param subject   json字符串
     * @param ttlMillis JWT的有效时长(单位:毫秒)
     * @param claims    创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）,一般用于存放自定义信息
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis, Map<String, Object> claims) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        long nowMillis = System.currentTimeMillis();// 生成JWT的时间
        Date now = new Date(nowMillis);

        // 如果claims是空则创建一个
        if (claims == null)
            claims = new HashMap<String, Object>();

        /**
         * 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret,
         * 那就意味着客户端是可以自我签发jwt了。
         */
        SecretKey key = this.generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()    //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims) //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id) //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now) //iat: jwt的签发时间
                .setSubject(subject) //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); //设置签名使用的签名算法和签名使用的秘钥

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp); //设置过期时间
        }
        return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = this.generalKey(); //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser() //得到DefaultJwtParser
                .setSigningKey(key) //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }

    /**
     * 由字符串生成加密key,使用jdk8的Base64类进行解码与编码
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public SecretKey generalKey() throws UnsupportedEncodingException {
        String stringKey = Base64.getEncoder().encodeToString(key.getBytes("utf-8"));// 本地配置文件中加密的密文,这里图省事直接获取编码
        byte[] encodedKey = Base64.getDecoder().decode(stringKey);// 本地的密码解码
        /**
         * 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng
         * 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
         */
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
