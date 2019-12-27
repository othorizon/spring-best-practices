package top.rizon.springbestpractice.dao.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Rizon
 * @date 2019/12/27
 */
@Data
@Accessors(chain = true)
@TableName("HistoryFakeTableName")
public class HistoryPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private String data;
    private LocalDateTime createTime;
}
