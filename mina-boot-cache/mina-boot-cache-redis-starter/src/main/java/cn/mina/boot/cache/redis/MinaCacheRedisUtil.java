package cn.mina.boot.cache.redis;

import cn.mina.boot.common.util.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Created by haoteng on 2022/7/22.
 */
public class MinaCacheRedisUtil {


    protected static RedisTemplate<String, String> redisTemplate;

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(@NonNull final String key, @NonNull final long timeout) {

        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(@NonNull final String key, @NonNull final long timeout, @NonNull final TimeUnit unit) {

        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @return true=存在；false=不存在
     */
    public static boolean hasKey(@NonNull final String key) {

        Boolean ret = redisTemplate.opsForValue().getOperations().hasKey(key);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public static boolean del(@NonNull final String key) {

        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long del(@NonNull final Collection<String> keys) {

        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    /**
     * 存入普通对象
     *
     * @param key   Redis键
     * @param value 值
     */
    public static void put(@NonNull final String key, @NonNull final Object value) {

        redisTemplate.opsForValue().set(key, JsonUtils.toJSONString(value));
    }

    // 存储普通对象操作

    /**
     * 存入普通对象
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效期，单位秒
     */
    public static void put(@NonNull final String key, @NonNull final Object value, @NonNull final long timeout) {

        redisTemplate.opsForValue().set(key, JsonUtils.toJSONString(value), timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @return 对象
     */
    public static String get(@NonNull final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取pojo对象
     *
     * @param key 键
     * @return 对象
     */
    public static <T> T getBean(@NonNull final String key, Class<T> clazz) {
        return JsonUtils.toBean(get(key), clazz);
    }

    // 存储Hash操作

    /**
     * 确定哈希hashKey是否存在
     *
     * @param key  键
     * @param hkey hash键
     * @return true=存在；false=不存在
     */
    public static boolean hasHashKey(@NonNull final String key, @NonNull String hkey) {

        Boolean ret = redisTemplate.opsForHash().hasKey(key, hkey);
        return ret != null && ret;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public static void putHash(@NonNull final String key, @NonNull final String hKey, @NonNull final Object value) {

        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key    Redis键
     * @param values Hash键值对
     */
    public static void putAllHash(@NonNull final String key, @NonNull final Map<String, Object> values) {

        redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static <T> T getHash(@NonNull final String key, @NonNull final String hKey, Class<T> clazz) {

        return clazz.cast(redisTemplate.opsForHash().get(key, hKey));
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @return Hash对象
     */
    public static Map<Object, Object> getAllHash(@NonNull final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static List<Object> getMultiHash(@NonNull final String key, @NonNull final Collection<Object> hKeys) {

        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static <T> long delHashKeys(@NonNull final String key, @NonNull final Collection<T> hKeys) {
        return redisTemplate.opsForHash().delete(key, hKeys);
    }

    // 存储Set相关操作

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static <T> long putSet(@NonNull final String key, @NonNull final T... values) {
        String[] array = Arrays.stream(values).map(JsonUtils::toJSONString).toArray(String[]::new);
        Long count = redisTemplate.opsForSet().add(key, array);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public static <T> long delSet(@NonNull final String key, @NonNull final T... values) {
        String[] array = Arrays.stream(values).map(JsonUtils::toJSONString).toArray(String[]::new);
        Long count = redisTemplate.opsForSet().remove(key, array);
        return count == null ? 0 : count;
    }

    /**
     * 获取set中的所有对象
     *
     * @param key Redis键
     * @return set集合
     */
    public static <T> Set<T> getSetAll(@NonNull final String key, Class<T> clazz) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        if (members == null) {
            return null;
        }
        return members.stream().map(value -> JsonUtils.toBean(value, clazz)).collect(Collectors.toSet());
    }


    // 存储List相关操作

    /**
     * 往List中存入数据
     *
     * @param key   Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public static <T> long pushList(@NonNull final String key, @NonNull final T value) {
        Long count = redisTemplate.opsForList().rightPush(key, JsonUtils.toJSONString(value));
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static <T> long pushAllList(@NonNull final String key, @NonNull final Collection<T> values) {
        String[] array = values.stream().map(JsonUtils::toJSONString).toArray(String[]::new);
        Long count = redisTemplate.opsForList().rightPushAll(key, array);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static <T> long pushAllList(@NonNull final String key, @NonNull final T... values) {
        String[] array = Arrays.stream(values).map(JsonUtils::toJSONString).toArray(String[]::new);
        Long count = redisTemplate.opsForList().rightPushAll(key, array);
        return count == null ? 0 : count;
    }

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key   Redis键
     * @param start 开始位置
     * @param end   结束位置（start=0，end=-1表示获取全部元素）
     * @return List对象
     */
    public static <T> List<T> getList(@NonNull final String key, @NonNull final int start, @NonNull final int end, Class<T> clazz) {
        List<String> objects = redisTemplate.opsForList().range(key, start, end);
        if (objects == null) {
            return null;
        }
        return objects.stream().map(v -> JsonUtils.toBean(v, clazz)).collect(Collectors.toList());
    }
}
