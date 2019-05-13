package blacktv.tvacg.database.mapper;

import blacktv.tvacg.database.pojo.Resources;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * Resources的mapper接口
 */
public interface ResourcesMapper {
    /**
     * 新增资源
     *
     * @param resources
     * @return
     */
    @Insert("INSERT INTO `resources` (`id`,`typeId`,`title`,`describe`,`coverFilename`,`from`,`remarks`,`download`,`userId`,`gmt_create`,`gmt_modified`) " +
            "VALUE (#{resources.id},#{resources.typeId},#{resources.title},#{resources.describe},#{resources.coverFilename},#{resources.from},#{resources.remarks},#{resources.download},#{resources.userId},NOW(),NOW())")
    public int addResources(@Param("resources") Resources resources);

    /**
     * 设置指定资源的删除状态,以及上次修改时间
     *
     * @param resources
     * @return
     */
    @Update("UPDATE `resources` SET `isDlete` = #{resources.isDlete},`gmt_modified` = NOW() WHERE id = #{resources.id}")
    public int setIsDelete(@Param("resources") Resources resources);

    /**
     * 设置指定资源的审核状态,以及上次修改时间
     *
     * @param resources
     * @return
     */
    @Update("UPDATE `resources` SET `toExamine` = #{resources.toExamine},`gmt_modified` = NOW() WHERE id = #{resources.id}")
    public int setToExamine(@Param("resources") Resources resources);

    /**
     * 设置指定资源的点击量，以及上次修改时间
     *
     * @param resources
     * @return
     */
    @Update("UPDATE `resources` SET `clicks` = #{resources.clicks},`gmt_modified` = NOW() WHERE id = #{resources.id}")
    public int setClicks(@Param("resources") Resources resources);

    /**
     * 修改指定资源的：分类、标题、描述、封面文件名、来源、备注、下载链接
     *
     * @param resources
     * @return
     */
    @Update("UPDATE `resources` SET `typeId` = #{resources.typeId},`title` = #{resources.title},`describe` = #{resources.describe},`from` = #{resources.from},`remarks` = #{resources.remarks},`download` = #{resources.download},`coverFilename` = #{resources.coverFilename},`gmt_modified` = NOW() WHERE id = #{resources.id}")
    public int modifyResources(@Param("resources") Resources resources);

    /**
     * 根据isDelete和toExamine获取资源
     * @param isDlete
     * @param toExamine
     * @return
     */
    @Select("SELECT * FROM `resources` WHERE `isDlete` = #{isDlete} AND `toExamine` = #{toExamine}  order by `id` desc")
    public List<Resources> getResourcesByToExamineAndIsDelete(@Param("isDlete") Byte isDlete,@Param("toExamine") Byte toExamine);

    /**
     * 根据isDelete和toExamine获取总数
     * @param isDlete
     * @param toExamine
     * @return
     */
    @Select("SELECT COUNT(1) FROM resources WHERE `isDlete` = #{isDlete} AND `toExamine` = #{toExamine}")
    public Long getNumberByToExamineAndIsDelete(@Param("isDlete") Byte isDlete,@Param("toExamine") Byte toExamine);

    /**
     * 根据资源类型获取未被删除、通过审核的资源
     *
     * @param
     * @return
     */
    @Select("SELECT * FROM `resources` WHERE `isDlete` = 1 AND `toExamine` = 1 AND `typeId` = #{typeId}  order by `id` desc")
    public List<Resources> getResourcesByType(@Param("typeId") Long typeId);

    /**
     * 根据资源类型获取未被删除、通过审核的资源总数
     * @param typeId
     * @return
     */
    @Select("SELECT COUNT(1) FROM `resources` WHERE `isDlete` = 1 AND `toExamine` = toExamine AND `typeId` = #{typeId}")
    public Long getNumberByType(@Param("typeId") Long typeId);

    /**
     * 根据用户id获取资源
     * @return
     */
    @Select("SELECT * FROM `resources` where `userId` = #{userId}")
    public List<Resources> getResourcesByUserid(@Param("userId") Long userId);

    /**
     * 根据用户id获取资源总数
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(1) FROM `resources` where `userId` = #{userId}")
    public Long getNumberByUserid(@Param("userId") Long userId);

    /**
     * 获取全部资源的总数
     * @return
     */
    @Select("SELECT COUNT(1) FROM `resources`")
    public Long getAllNumber();

    /**
     * 获取全部资源,,根据id倒序查询
     *
     * @return
     */
    @Select("SELECT * FROM `resources` order by `id` desc")
    public List<Resources> getAllResources();

    /**
     * 根据id获取资源
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `resources` WHERE `id` = #{id}")
    public Resources getResourcesById(@Param("id") Long id);
}
