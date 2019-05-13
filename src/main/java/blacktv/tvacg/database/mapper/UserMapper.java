package blacktv.tvacg.database.mapper;

import blacktv.tvacg.database.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * User类的mapper接口
 */
public interface UserMapper {
    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user`(`username`,`password`,`email`,`gmt_create`,`gmt_modified`) VALUE (#{user.username},#{user.password},#{user.email},NOW(),NOW())")
    public int addUser(@Param("user") User user);

    /**
     * 设置指定用户的删除状态,以及上次修改时间
     *
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `isDlete` = #{user.isDlete},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setIsDelete(@Param("user") User user);

    /**
     * 设置指定用户的激活状态,以及上次修改时间
     *
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `isActivation` = #{user.isActivation},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setIsActivation(@Param("user") User user);

    /**
     * 修改指定用户的密码，以及上次修改时间
     *
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `password` = #{user.password},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setPassword(@Param("user") User user);

    /**
     * 修改指定用户的邮箱
     *
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `email` = #{user.email},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setEmail(@Param("user") User user);

    /**
     * 设置指定用户的管理员状态,以及上次修改时间
     *
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `isAdmin` = #{user.isAdmin},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setIsAdmin(@Param("user") User user);

    /**
     * 设置指定用户的昵称，以及上次修改时间
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET `username` = #{user.username},`gmt_modified` = NOW() WHERE id = #{user.id}")
    public int setUsername(@Param("user") User user);

    /**
     * 查询全部用户
     *
     * @return
     */
    @Select("SELECT * FROM `user`")
    public List<User> getAllUser();

    /**
     * 获取全部用户总数
     * @return
     */
    @Select("SELECT COUNT(1) FROM `user`")
    public Long getAllNumber();

    /**
     * 根据管理员等级获取用户
     * @param isAdmin
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `isAdmin` = #{isAdmin}")
    public List<User> getUserByIsAdmin(@Param("isAdmin") Byte isAdmin);

    /**
     * 获取指定的管理员等级的用户总数
     * @param isAdmin
     * @return
     */
    @Select("SELECT COUNT(1) FROM `user` WHERE `isAdmin` = #{isAdmin}")
    public Long getNumberByIsAdmin(@Param("isAdmin") Byte isAdmin);

    /**
     * 根据邮箱登录
     *
     * @param user
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `email` = #{user.email} AND `password` = #{user.password} AND `isDlete` = 1 AND `isActivation` = 1")
    public User loginByEmail(@Param("user") User user);

    /**
     * 根据用户名登录
     *
     * @param user
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `username` = #{user.username} AND `password` = #{user.password} AND `isDlete` = 1 AND `isActivation` = 1")
    public User loginByUsername(@Param("user") User user);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `id` = #{id}")
    public User getUserById(@Param("id") Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `username` = #{username}")
    public User getUserByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    @Select("SELECT * FROM `user` WHERE `email` = #{email}")
    public User getUserByEmail(@Param("email") String email);
}
