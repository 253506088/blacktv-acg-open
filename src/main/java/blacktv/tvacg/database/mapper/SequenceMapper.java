package blacktv.tvacg.database.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * sequence表的mapper接口，用于查询序列,由于只返回Long类型所以不需要pojo
 */
public interface SequenceMapper {
    /**
     * 查询指定序列的下一个值
     * @param name
     * @return
     */
    @Select("SELECT NEXTVAL(#{name})")
    public Long getNextVal(@Param("name") String name);

    /**
     * 查询指定序列的当前值
     * @param name
     * @return
     */
    @Select("SELECT CURRVAL(#{name}")
    public Long getCurrVal(@Param("name") String name);
}
