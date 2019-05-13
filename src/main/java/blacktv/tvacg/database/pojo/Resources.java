package blacktv.tvacg.database.pojo;

import lombok.Data;

import java.util.Date;

/**
 * resources表的pojo类
 */
@Data
public class Resources {
    private Long id;
    private Long typeId;//资源分类id
    private Long userId;//资源作者id
    private Long clicks;//点击量
    private String title;//资源标题
    private String describe;//资源描述，小作文
    private String coverFilename;//资源封面的文件名
    private String from;//资源来源
    private String remarks;//资源备注，一般用来写解压密码和提取码
    private String download;//资源下载链接
    private Byte isDlete;//1为存在，0为删除
    private Byte toExamine;//1为审核通过，0为待审核，-1为未通过
    private Date gmt_create;
    private Date gmt_modified;

    private String typeName;//资源分类名称，这个属性不在数据库中存在，只是为了返回给数据表格好看，总不能返回typeId吧

    public Resources() {

    }

    /**
     * 新增资源用的构造
     *
     * @param typeId
     * @param userId
     * @param title
     * @param describe
     * @param coverFilename
     * @param from
     * @param remarks
     * @param download
     */
    public Resources(Long typeId, Long userId, String title, String describe, String coverFilename, String from, String remarks, String download) {
        this.typeId = typeId;
        this.userId = userId;
        this.title = title;
        this.describe = describe;
        this.coverFilename = coverFilename;
        this.from = from;
        this.remarks = remarks;
        this.download = download;
    }
}
