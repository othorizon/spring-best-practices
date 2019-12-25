package top.rizon.springbestpractice.web.model.vo;

import lombok.Data;

import java.util.UUID;

/**
 * @author Rizon
 * @date 2019/12/25
 */
@Data
public class CacheExampleVo {
    private String uuid = UUID.randomUUID().toString();
    private long timestamp = System.currentTimeMillis();
}
