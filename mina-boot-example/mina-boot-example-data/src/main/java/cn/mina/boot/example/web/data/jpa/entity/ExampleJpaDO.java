//package cn.mina.boot.example.web.data.jpa.entity;
//
//import lombok.Data;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * @author Created by haoteng on 2022/8/4.
// */
//@Data
//@Entity // 表示该类是实体类
//@Table(name = "example_info")
//@DynamicInsert
//@DynamicUpdate
//public class ExampleJpaDO implements Serializable {
//
//    private static final long serialVersionUID = -1289488132046787900L;
//
//    @Id // 表示该字段是主键
//    @GeneratedValue(strategy = GenerationType.AUTO) // 主键的生成策略
//    private Integer id;
//
//    private String name;
//
//    private String address;
//
//    private Boolean isDeleted;
//}
