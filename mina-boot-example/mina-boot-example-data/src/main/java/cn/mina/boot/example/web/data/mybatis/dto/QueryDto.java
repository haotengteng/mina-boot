package cn.mina.boot.example.web.data.mybatis.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by haoteng on 2022/8/8.
 */
@Data
public class QueryDto implements Serializable {

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
