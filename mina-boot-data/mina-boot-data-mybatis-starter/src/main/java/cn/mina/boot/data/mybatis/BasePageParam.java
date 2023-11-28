package cn.mina.boot.data.mybatis;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询 基础参数
 *
 * @author Created by haoteng on 2022/8/8.
 */
@Data
public class BasePageParam implements Serializable {

    private static final long serialVersionUID = -1820157852481624517L;

    private Integer page;

    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
