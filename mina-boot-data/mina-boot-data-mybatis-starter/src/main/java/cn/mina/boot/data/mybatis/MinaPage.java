package cn.mina.boot.data.mybatis;

import java.util.Collection;

/**
 * @author Created by haoteng on 2022/8/4.
 */
public class MinaPage {
    /**
     * 将集合数据转为分页数据，每次分页查询只可以调用一次
     * @param collection
     * @return
     * @param <E>
     */
    public static <E> Page<E> cover(Collection<E> collection) {
        Page<E> page = new Page<>();
        page.addAll(collection);
        page.setTotalCount(PageInterceptor.countStorage.get());
        // 获取完后及时清空
        PageInterceptor.countStorage.remove();
        return page;
    }
}
