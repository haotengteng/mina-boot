package cn.mina.boot.data.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 分页model对象
 *
 * @author Created by haoteng on 2022/8/4.
 */
public class Page<E> implements Serializable {

    private static final long serialVersionUID = -5727691743037813207L;

    private Long count;

    private Collection<E> list;


    protected Page() {
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Collection<E> getList() {
        return list;
    }

    public void setList(Collection<E> list) {
        this.list = list;
    }
}
