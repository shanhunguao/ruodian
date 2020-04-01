package com.ncu.springboot.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ncu.springboot.core.jdkextends.NcuOptional;
import com.ncu.springboot.core.model.Traversable;

public class CollectionUtil {
    private static final JSONObject DEFAULT_JSON = JSONObject.parseObject(StringUtil.EMPTY_STRING_JSON);

    public static <T> Map<String, T> merge(Map<String, T>... maps) {
        return !CollectionUtil.isEmpty(maps) ? Arrays.stream(maps).reduce( new HashMap<>() , (destMap , sourceMap) -> {
        	NcuOptional.of( sourceMap ).ifPresent(map -> destMap.putAll(map) );
            return destMap;
        } ) : new HashMap<>();
    }

    public static <T> Map<String, T> merge(Collection<Map<String, T>> maps) {
        return !CollectionUtil.isEmpty(maps) ? maps.stream().reduce( new HashMap<>() , (destMap , sourceMap) -> {
        	NcuOptional.of( sourceMap ).ifPresent( map -> destMap.putAll(map) );
            return destMap;
        } ) : new HashMap<>();
    }

    public static <T> Map<String, T> subMap(Map<String, T> sourceMap, String... keys) {
        Map<String, T> subMap = new HashMap<>();
        if ( CollectionUtil.isEmpty(sourceMap) ) {
            return subMap;
        }
        if ( CollectionUtil.isEmpty(keys) ) {
            subMap.putAll(sourceMap);
        } else {
            Arrays.stream(keys).forEach( key -> NcuOptional.of( sourceMap.get(key) ).ifPresent( value -> subMap.put(key , value) ) );
        }
        return subMap;
    }

    public static <K, V> Map<K, V> subMapByValue(Map<K, V> sourceMap, V value, boolean deleteIfExist) {
        Map<K, V> resultMap = new HashMap<>();
        if ( !CollectionUtil.isEmpty(sourceMap) && null != value) {
            Iterator<Entry<K , V>> iterator = sourceMap.entrySet().iterator();
            while( iterator.hasNext() ) {
                Entry<K , V> entry = iterator.next();
                V tmpValue = entry.getValue();
                if (value.equals(tmpValue)) {
                    resultMap.put(entry.getKey(), tmpValue);
                    if( deleteIfExist ) {
                        iterator.remove();
                    }
                }
            }
        }
        return resultMap;
    }

    public static <K, V> void remove(Map<K, V> map, Collection<K> keys) {
        if (!isEmpty(map) && !isEmpty(keys)) {
            keys.forEach( key -> map.remove(key) );
        }
    }

    public static <K, V> void remove(Map<K, V> map, K... keys) {
        if (!isEmpty(map) && !isEmpty(keys)) {
            Arrays.stream(keys).forEach( key -> map.remove(key) );
        }
    }

    /**
     * 判断item是否存在与collection中
     */
    public static <T> boolean inCollection(T item, T... collection) {
        return -1 != ArrayUtils.indexOf(collection , item);
    }

    public static <K , V> Map<K , V> transKey(Map<K , V> map, K preKey, K afterKey) {
        if ( !CollectionUtil.isEmpty(map) && map.containsKey(preKey) && !map.containsKey(afterKey) ) {
            map.put(afterKey, map.remove(preKey));
        }
        return map;
    }

    public static <T> boolean isEmpty(T[] array) {
        return null == array || 0 == array.length;
    }

    public static <T> boolean isEmpty(T[][] array) {
        return null == array || 0 == array.length || 0 == array[0].length;
    }

    public static JSONObject strToJsonObject(String source) {
        source = ValueUtil.getString(source, StringUtil.EMPTY_STRING_JSON);
        try {
            return JSONObject.parseObject(source);
        } catch (Exception e) {
            return DEFAULT_JSON;
        }
    }

    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }

    public static <T extends CharSequence> String join(Collection<T> collection, String split) {
        split = ValueUtil.getString(split);
        return !isEmpty(collection) ? collection.stream().collect( Collectors.joining(split) ) : StringUtil.EMPTY_STRING;
    }

    public static Map<String, Integer> toStringIntegerMap(Map<String, String> sourceMap) {
        Map<String, Integer> targetMap = new HashMap<>();
        sourceMap.entrySet().forEach(entry -> targetMap.put(entry.getKey(), ValueUtil.getInt(entry.getValue())) );
        return targetMap;
    }

    public static <T> List<T> subList(List<T> source, int size) {
        return subList(source, size, ArrayList.class);
    }

    public static <T> List<T> subList(List<T> source, int size, Class<? extends List> targetClazzz) {
        if (targetClazzz == source.getClass() && size == source.size()) {
            return source;
        }
        List target = ReflectionUtil.newInstance(targetClazzz);
        copy(source, target, size);
        return target;
    }

    public static <T> Set<T> subSet(Set<T> source, int size) {
        return subSet(source, size, HashSet.class);
    }

    public static <T> Set<T> subSet(Set<T> source, int size, Class<? extends Set> targetClazzz) {
        if (targetClazzz == source.getClass() && size == source.size()) {
            return source;
        }
        Set target = ReflectionUtil.newInstance(targetClazzz);
        copy(source, target, size);
        return target;
    }

    private static <T> void copy(Collection<T> source, Collection<T> target, int size) {
        target.addAll( source.stream().limit(size).collect(Collectors.toList()) );
    }

    public static <T> List<T> removeDuplicateElement(List<T> sourceList) {
        return sourceList.stream().distinct().collect(Collectors.toList());
    }

    public static List<String> split(String source, String separatorChars) {
        return new ArrayList<>(Arrays.asList(StringUtils.split(ValueUtil.getString(source), separatorChars)));
    }

    public static <K, V> void addItemToMapList(Map<K, List<V>> map, K key, V value) {
        addItemToMapList(map, key, value, ArrayList.class);
    }

    public static <FK, SK, TK> void addItemToMapList(Map<FK, Map<SK, List<TK>>> map, FK fk, SK sk, TK tk) {
        Map<SK, List<TK>> secondMap = map.get(fk);
        if (secondMap == null) {
            map.put(fk, secondMap = new HashMap<>());
        }
        List<TK> list = secondMap.get(sk);
        if (list == null) {
            secondMap.put(sk, list = new ArrayList<>());
        }
        list.add(tk);
    }

    public static <FK , SK , V> void addItemToInnerMap(Map<FK, Map<SK, V>> map, FK firstKey, SK secondKey, V value) {
        if( map != null ) {
            Map<SK , V> innerMap = map.getOrDefault(firstKey , new HashMap<>());
            innerMap.put( secondKey , value );
            map.put(firstKey , innerMap);
        }
    }

    public static <K, V> void addItemToMapList(Map<K, List<V>> map, K key, V value, Class<? extends List> listClass) {
        if (map != null) {
            List<V> destCollection = map.getOrDefault(key , ReflectionUtil.newInstance(listClass));
            destCollection.add(value);
            map.put(key, destCollection);
        }
    }

    public static <FK, SK> void incr(Map<FK, Map<SK, Integer>> map, FK firstKey, SK secondKey) {
        incrBy(map, firstKey, secondKey, 1);
    }

    public static <FK, SK> void decr(Map<FK, Map<SK, Integer>> map, FK firstKey, SK secondKey) {
        incrBy(map, firstKey, secondKey, -1);
    }

    public static <FK, SK> void incrBy(Map<FK, Map<SK, Integer>> map, FK firstKey, SK secondKey, int incrValue) {
        if (map.containsKey(firstKey)) {
            incrBy(map.get(firstKey), secondKey, incrValue);
        } else {
            Map<SK, Integer> childMap = new HashMap<>();
            incrBy(childMap, secondKey, incrValue);
            map.put(firstKey, childMap);
        }
    }


    public static <K> void incr(Map<K, Integer> map, K key) {
        incrBy(map, key, 1, 0);
    }

    public static <K> void incrBy(Map<K, Integer> map, K key, int incrValue) {
        incrBy(map, key, incrValue, 0);
    }

    public static <K> void incrBy(Map<K, Integer> map, Entry<K, Integer> entry) {
        incrBy(map, entry.getKey(), entry.getValue(), 0);
    }

    public static <K> void incrBy(Map<K, Integer> map, K key, int incrValue, int defaultValue) {
        Integer value = ValueUtil.getInt(map.get(key), defaultValue) + incrValue;
        map.put(key, value);
    }

    public static void removeLast(List<?> list) {
        if (!isEmpty(list)) {
            list.remove(list.size() - 1);
        }
    }

    public static <T> List<T> removeFirst(List<T> list) {
        if (!isEmpty(list)) {
            list.remove(0);
        }
        return list;
    }

    public static <T> void addIfNotNull(Collection<T> collection, T obj) {
        if (null != collection && null != obj) {
            collection.add(obj);
        }
    }

    public static <T> void addIfNotNull(Collection<T> collection, NcuOptional<T> optional) {
        if (null != collection && optional.isPresent() ) {
            collection.add(optional.get());
        }
    }

    public static <T> T[] toArray(Collection<T> collection, Class<T> clazz) {
        int size = isEmpty(collection) ? 0 : collection.size();
        return collection.toArray(ReflectionUtil.newArray(clazz, size));
    }

    public static <K, V, AK extends K, AV extends V> AV mapGet(Map<K, V> map, AK key, AV defaultValue) {
        if (CollectionUtil.isEmpty(map)) {
            return defaultValue;
        }
        return (AV) map.getOrDefault( key , defaultValue );
    }

    public static <K, V> NcuOptional<V> mapGet(Map<K, V> map, K key) {
        if( CollectionUtil.isEmpty(map) ) {
            return NcuOptional.empty();
        }
        return NcuOptional.of( map.get(key) );
    }

    public static <T> T[][] changeArrayLength(T[][] oldArray, int newFirstLen, int newSecondLen, Class<T> clazz) {
        T[][] newArray = ReflectionUtil.newArray(clazz, newFirstLen, newSecondLen);
        if (isEmpty(oldArray)) {
            return newArray;
        }
        int minFirstLen = Math.min(newFirstLen, oldArray.length), minSecondLen = Math.min(newSecondLen, oldArray[0].length);
        for (int i = 0; i < minFirstLen; i++) {
            System.arraycopy(oldArray[i], 0, newArray[i], 0, minSecondLen);
        }
        return newArray;
    }

    public static <T> T addItemToList(List<T> list, T item) {
        return addItemToList(list , item , 1);
    }

    public static <T> T addItemToList(List<T> list, T item , int times) {
        if( times > 0 ) {
            list.add(item);
        }
        return item;
    }

    public static <K, V> List<V> removeAll(Map<K, V> map, Collection<K> collection) {
        return removeAll(map, collection, false);
    }

    public static <K, V> List<V> removeAll(Map<K, V> map, Collection<K> collection, boolean delFromCollection) {
        List<V> resultList = new ArrayList<>();
        if (!isEmpty(map) && !isEmpty(collection)) {
            Iterator<K> iterator = collection.iterator();
            while (iterator.hasNext()) {
            	NcuOptional.of(map.remove(iterator.next())).ifPresent(v -> {
                    resultList.add(v);
                    if( delFromCollection ) {
                        iterator.remove();
                    }
                } );
            }
        }
        return resultList;
    }

    public static <K, V> void mappedValueByKey(Map<K, V> sourceMap, Map<K, V> destMap) {
        mappedValueByKey(sourceMap, destMap, null);
    }

    public static <K, V> void mappedValueByKey(Map<K, V> sourceMap, Map<K, V> destMap, V defaultValue) {
        destMap.keySet().forEach( key -> {
            V value = sourceMap.containsKey(key) ? sourceMap.get(key) : defaultValue;
            destMap.put(key, value);
        });
    }

    public static <K, V> List<K> mappedKeyByValue(Map<K, V> map, V value) {
        return mappedKeyByValue(map, value, Integer.MAX_VALUE);
    }

    public static <K, V> List<K> mappedKeyByValue(Map<K, V> map, V value, int limit) {
        return isEmpty(map) || null == value || limit <= 0 ? new ArrayList<>() :
                map.entrySet().stream().limit(limit).map(Entry::getKey).collect(Collectors.toList());
    }

    public static <K, V> void removeListInMap(Map<K, List<V>> map, V value) {
        if (!isEmpty(map)) {
            map.values().forEach( coll -> coll.remove(value) );
        }
    }

    public static <T extends Traversable> List<T> traversalByDepth(List<T> sourceList) {
        List<T> resultList = new ArrayList<>();
        sourceList.forEach( item -> traversalByDepthInRecursion(resultList, item) );
        return resultList;
    }

    private static <T extends Traversable> void traversalByDepthInRecursion(List<T> resultList, T parentItem) {
        resultList.add(parentItem);
        if (!isEmpty(parentItem.getSubList())) {
            List<T> subList = parentItem.getSubList();
            subList.forEach( (item) -> traversalByDepthInRecursion(resultList, item) );
        }
    }

    public static <T extends Traversable> List clearTraversalInfo(List<T> list) {
        list.forEach(item -> item.setSubList(null));
        return list;
    }

    public static <T> T[][] clone(T[][] ary) {
        return null == ary ? null : ary.clone();
    }

    public static <T> T[] clone(T[] ary) {
        return null == ary ? null : ary.clone();
    }

    public static boolean existStartWith(Set<String> sourceSet, String prefix, boolean removeIsExist) {
        Set<String> removeSourceSet = null;
        if (removeIsExist) {
            removeSourceSet = new HashSet<>();
        }
        for (String source : sourceSet) {
            if (source.equals(prefix) || prefix.startsWith(source)) {              //如果source与prefix相同,或者如果source是prefix的上级,那么返回false
                return false;
            } else if (removeIsExist && source.startsWith(prefix)) {               //如果source是prefix的下级,那么考虑将source置入removeSourceSet
                removeSourceSet.add(source);
            }
        }
        if (!isEmpty(removeSourceSet)) {
            sourceSet.removeAll(removeSourceSet);
        }
        return true;
    }

    public static <T> NcuOptional<T> getLast(List<T> list) {
        if (isEmpty(list)) {
            return NcuOptional.empty();
        }
        return NcuOptional.of(list.get(list.size() - 1));
    }

    public static <T> NcuOptional<T> getLast(T[] ary) {
        if (isEmpty(ary)) {
            return NcuOptional.empty();
        }
        return NcuOptional.of(ary[ary.length - 1]);
    }

    public static <K, V> boolean addIfNotExist(Map<K, V> map, K key, V value) {
        if (map != null && !map.containsKey(key)) {
            map.put(key, value);
            return true;
        }
        return false;
    }

    public static <K, V> Map<K, V> removeIfEqualValue(Map<K, V> map, V value) {
        for (Iterator<Entry<K, V>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<K, V> entry = iterator.next();
            if (value == null && entry.getValue() == null || value.equals(entry.getValue())) {
                iterator.remove();
            }
        }
        return map;
    }

    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        list.sort(comparator);
        return list;
    }

    public static <K> K findKeyWithContains(Map<K, String> map, String value) {
        return value == null ? null :
                map.entrySet().stream().filter( entry -> entry.getValue().contains(value) ).map(Entry::getKey).findFirst().get();
    }

    public static <T> T[] toArray(Map<T, T> map, Class<T> clazz) {
        if (!isEmpty(map)) {
            T[] array = ReflectionUtil.newArray(clazz, map.size() * 2);
            AtomicInteger integer = new AtomicInteger(0);
            map.entrySet().forEach( entry -> {
                array[integer.getAndIncrement()] = entry.getKey();
                array[integer.getAndIncrement()] = entry.getValue();
            });
            return array;
        }
        return ReflectionUtil.newArray(clazz, 0);
    }

    public static <SUB extends SUP, SUP> List<SUB> tranList(List<SUP> supList) {
        List<SUB> subList = new ArrayList();
        supList.forEach( sup -> subList.add((SUB)sup) );
        return subList;
    }

    public static <T> List<T> filter(List<T> list , Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> NcuOptional<T> getFirst(Collection<T> collection) {
        if( isEmpty( collection ) ) {
            return NcuOptional.empty();
        }
        return NcuOptional.of( collection.iterator().next() );
    }

    public static <T> NcuOptional<T> getFirst(T[] array) {
        if( isEmpty( array ) ) {
            return NcuOptional.empty();
        }
        return NcuOptional.of( array[0] );
    }

    public static NcuOptional getFirst(Object obj) {
        if( CollectionUtil.isArrayOrCollection(obj) ) {
            if( obj instanceof Collection ) {
                return getFirst((Collection)obj);
            } else {
                return getFirst( Array.get(obj , 0) );
            }
        }
        return NcuOptional.of(obj);
    }

    public static <T> String[] toStringArray(T... sourceAry) {
        if( isEmpty(sourceAry) ) {
            return new String[0];
        }
        AtomicInteger ai = new AtomicInteger();
        String[] destAry = new String[sourceAry.length];
        Stream.of( sourceAry ).forEach( source -> destAry[ai.getAndIncrement()] = ValueUtil.getString(source)  );
        return destAry;
    }

    public static <K, V> Map<String,String> toStringStringMap(Map<K, V> sourceMap) {
        Map<String,String> resMap = new HashMap<>();
        sourceMap.forEach( (key , value) -> resMap.put( ValueUtil.getString(key) , ValueUtil.getString(value) ) );
        return resMap;
    }

    public static boolean isArrayOrCollection(Object source) {
        if( null == source ) {
            return false;
        }
        return source.getClass().isArray() || source instanceof Collection;
    }
}
