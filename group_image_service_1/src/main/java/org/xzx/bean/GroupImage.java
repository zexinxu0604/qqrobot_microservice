package org.xzx.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@TableName("images")
public class GroupImage {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("url")
    private String url;

    @TableField("poster")
    private long poster;

    @TableField("isDel")
    private int isDel;

    @TableField("groupid")
    private long groupid;

    @TableField("localurl")
    private String localurl;

}
