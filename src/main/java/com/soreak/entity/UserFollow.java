package com.soreak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: welog
 * @author: soreak
 * @description: 用户关注表
 * @create: 2020-12-17 16:34
 **/
@Data
@TableName(value = "sk_user_follow")
public class UserFollow implements Serializable {

    /**
     * 标识符Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 关注的用户Id（被上面的用户关注）
     */
    private Long userFollowId;

    /**
     * 关注状态（0 关注 1 取消）
     */
    private boolean status;

    /**
     * 关注创建时间
     */
    private Date createTime;

}
