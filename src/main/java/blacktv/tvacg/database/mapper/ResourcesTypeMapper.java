package blacktv.tvacg.database.mapper;

import blacktv.tvacg.database.pojo.ResourcesType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * ResourcesType类的mapper接口
 */
public interface ResourcesTypeMapper {
    /**
     * 新增资源分类
     *
     * @param name
     * @return
     */
    @Insert("INSERT INTO `resourcesType`(`name`) VALUES(#{name})")
    public int addResourcesType(@Param("name") String name);

    /**
     * 根据id修改资源分类名称
     * @param resourcesType
     * @return
     */
    @Update("UPDATE `resourcesType` SET `name` = #{resourcesType.name} WHERE `id` = #{resourcesType.id}")
    public int updateNameById(@Param("resourcesType") ResourcesType resourcesType);

    /**
     * 根据id设置资源分类的isDelete属性
     * @param resourcesType
     * @return
     */
    @Update("UPDATE `resourcesType` SET `isDlete` = #{resourcesType.isDlete} WHERE `id` = #{resourcesType.id}")
    public int updateIsDleteById(@Param("resourcesType") ResourcesType resourcesType);

    /**
     * 根据名称查询资源分类
     *
     * @param name
     * @return
     */
    @Select("SELECT * FROM `resourcesType` WHERE `name` = #{name}")
    public ResourcesType getResourcesTypeByName(@Param("name") String name);

    /**
     * 根据id查询资源分类
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM `resourcesType` WHERE `id` = #{id}")
    public ResourcesType getResourcesTypeById(@Param("id") int id);

    /**
     * 查询全部存在的分类
     *
     * @return
     */
    @Select("SELECT * FROM `resourcesType`")
    public List<ResourcesType> getAllResourcesType();

    /**
     * 根据isDelete属性查询资源分类
     * @param isDelete
     * @return
     */
    @Select("SELECT * FROM `resourcesType` WHERE `isDlete` = 1")
    public List<ResourcesType> getResourcesTypeByIsDelete(@Param("isDelete") int isDelete);
}
