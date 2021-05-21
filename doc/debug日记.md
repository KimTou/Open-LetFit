## Mybatis查询返回List

**bug描述**：mysql查询确实返回多条数据，List集合中却只收到一条记录



* 实体类

```java
public class UserComment implements Serializable {
    @ApiModelProperty("评论者id")
    private Integer userId;
    
    @ApiModelProperty("评论者名")
    private String userName;
    
    @ApiModelProperty("评论者头像")
    private String avatar;
    
    @ApiModelProperty("评论信息")
    private Comment comment;
}
```

* Mapper

```xml
<resultMap id="userCommentMap" type="com.letfit.pojo.vo.UserComment">
    <id property="userId" column="user_id"/>
    <result property="userName" column="user_name"/>
    <result property="avatar" column="user_avatar"/>
    <association property="comment" javaType="com.letfit.pojo.entity.Comment">
        <id property="commentId" column="comment_id"/>
        <result property="pid" column="pid"/>
        <result property="recordId" column="record_id"/>
        <result property="userId" column="user_id"/>
        <result property="content" column="content"/>
        <result property="gmtCreate" column="gmt_create"/>
    </association>
</resultMap>

<select id="getCommentList" resultMap="userCommentMap">
        select
            c.comment_id,
            u.user_name,
            u.user_avatar,
            c.root_pid,
            c.pid,
            c.record_id,
            c.user_id,
            c.content,
            c.gmt_create
        from
            user as u
        left join
            comment as c
        on
            c.user_id = u.user_id
        <where>
            <if test="recordId != null">
                record_id = #{recordId}
            </if>
            <if test="pid != null">
                and root_pid = #{pid}
            </if>
        </where>
        order by
            gmt_create desc
</select>
```



**原因分析：**

由于评论同一个用户可以评论多次，如果使用用户id作为主键

<id property="userId" column="user_id"/>

**resultMap中如果不定义类似主键之类的能够区分每一条结果集的字段的话，会引起后面一条数据覆盖前面一条数据的现象。**

user_id相同，则返回数据会被覆盖。

故应选择不会重复的属性作为主键，如comment_id



1、修改实体类

```java
public class UserComment implements Serializable {
    @ApiModelProperty("评论id")
    private Integer commentId;
    
    @ApiModelProperty("评论者名")
    private String userName;
    
    @ApiModelProperty("评论者头像")
    private String avatar;
    
    @ApiModelProperty("评论信息")
    private Comment comment;
}
```

2、修改Mapper

```xml
<resultMap id="userCommentMap" type="com.letfit.pojo.vo.UserComment">
    <id property="commentId" column="comment_id"/>
    <result property="userName" column="user_name"/>
    <result property="avatar" column="user_avatar"/>
    <association property="comment" javaType="com.letfit.pojo.entity.Comment">
        <result property="pid" column="pid"/>
        <result property="recordId" column="record_id"/>
        <result property="userId" column="user_id"/>
        <result property="content" column="content"/>
        <result property="gmtCreate" column="gmt_create"/>
    </association>
</resultMap>
```





## 首页最近打卡模块

**功能需求**：根据用户最近的打卡记录的发布时间，对用户的模块进行排序

**bug描述**：对模块排序失败



* 初始Mapper

```xml
<select id="getUserModule" resultType="com.letfit.pojo.vo.UserRecordModule">
    select
        m.module_id,
        m.module_name,
        m.module_cover,
        r.gmt_create,
        count(record_id) as record_number
    from
        module as m
    left join
        record as r
    on
        m.module_id = r.module_id
    where
        r.user_id = #{userId}
    group by
        m.module_id
    order by
        r.gmt_create desc
</select>
```



**原因分析：首先要使用order by子句，排序字段就必须被select出来。其次，由于每个record的gmt_create不同，应把最大的时间戳筛选出来，再根据这个最大的时间戳来排序。**



* 修复后Mapper

```xml
<select id="getUserModule" resultType="com.letfit.pojo.vo.UserRecordModule">
    select
        m.module_id,
        m.module_name,
        m.module_cover,
        MAX(r.gmt_create) as gmt,
        count(record_id) as record_number
    from
        module as m
    left join
        record as r
    on
        m.module_id = r.module_id
    where
        r.user_id = #{userId}
    group by
        m.module_id
    order by
        gmt desc
</select>
```

