package cn.mina.boot.data.mybatis;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页model对象
 *
 * @author Created by haoteng on 2022/8/4.
 */
public class Page<E> extends ArrayList<E> {

    private Long totalCount;


    protected Page() {
    }

    public Long getTotalCount() {
        return totalCount;
    }

    protected void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
