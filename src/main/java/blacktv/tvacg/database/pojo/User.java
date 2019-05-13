package blacktv.tvacg.database.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类,继承Serializable接口用于序列化
 */
@Data
public class User implements Serializable {
    private Long id;
    private Byte isDlete;
    private Byte isAdmin;
    private Byte isActivation;
    private String username;
    private String email;
    private String password;
    private Date gmt_create;
    private Date gmt_modified;

    /**
     * 创建新用户时使用
     *
     * @param username
     * @param email
     * @param password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }
}
