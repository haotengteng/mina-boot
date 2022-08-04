package cn.mina.boot.example.web.data.dao;

import cn.mina.boot.example.web.data.entity.ExampleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by haoteng on 2022/8/4.
 */
@Mapper
public interface ExampleMapper {

    ExampleDO findById(Integer id);


    List<ExampleDO> findListByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

}
