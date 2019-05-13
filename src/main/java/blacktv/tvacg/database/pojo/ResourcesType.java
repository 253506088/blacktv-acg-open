package blacktv.tvacg.database.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * resourcesType的pojo类，由于需要丢到缓存中，所以引入Serializable
 */
@Data
public class ResourcesType implements Serializable {
    private Long id;
    private String name;
    private Integer isDlete;

    public ResourcesType() {
    }

    public ResourcesType(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
