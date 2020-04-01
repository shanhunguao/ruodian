package com.ncu.springboot.core.jdkextends;


import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.ncu.springboot.core.exception.NcuErrInfoException;

/**
 * JDK功能扩展 - Optional
 *      如果在多线程环境中保证线程安全，请使用Optional，NcuOptional线程不安全
 * */
public class NcuOptional<T> implements Serializable {
    private T value;

    private NcuOptional() { }

    private NcuOptional(T value) {
        this.value = value;
    }

    /**
     * 修改NcuOptional的值
     * */
    public NcuOptional<T> ofInInstance(T obj) {
        this.value = obj;
        return this;
    }

    /**
     * 根据NcuOptional产生一个新的NcuOptional
     *
     * @param optional 如果optional为空 ， 则初始化一个空的NcuOptional
     *                  如果optional不为空，则取原optional的值创建NcuOptional
     * */
    public static <T> NcuOptional<T> of(NcuOptional<T> optional) {
    	NcuOptional<T> NcuOptional;
        if( null == optional ) {
        	NcuOptional = new NcuOptional<>();
        } else {
            NcuOptional = new NcuOptional<>(optional.getNoCheck());
        }
        return NcuOptional;
    }

    /**
     * 创建一个空的NcuOptional
     * */
    public static <T>  NcuOptional<T> empty() {
        return new NcuOptional<>();
    }

    /**
     * 根据指定的value创建一个NcuOptional
     *
     * @param obj 指定value
     *            如果obj == null ， 则会抛出NullPointException
     * */
    public static <T> NcuOptional<T> ofWithCheck(T obj) {
        return of( Objects.requireNonNull(obj) );
    }

    /**
     * 根据指定的value创建一个NcuOptional
     *
     * @param obj 指定value
     * */
    public static <T> NcuOptional<T> of(T obj) {
        return new NcuOptional<>( obj );
    }

    /**
     * 将JDK-Optional转化为NcuOptional
     * */
    public static <T> NcuOptional<T> of(Optional<T> op) {
    	NcuOptional<T> optional = NcuOptional.empty();
        if( null != op ) {
            op.ifPresent(value -> optional.ofInInstance(value) );
        }
        return optional;
    }

    /**
     * 如果NcuOptional的value不为null ， 则执行customer
     * */
    public NcuOptional<T> ifPresent(Consumer<? super T> customer) {
        if( null != value ) {
            customer.accept(value);
        }
        return this;
    }

    /**
     * 如果NcuOptional的value为null ， 则执行runner
     * */
    public NcuOptional<T> notPresent(Runnable runnable) {
        if( null == value ) {
            runnable.run();
        }
        return this;
    }

    /**
     * 如果value为null，使用执行supplier进行赋值
     * */
    public NcuOptional<T> orElseSupplier(Supplier<? extends T> supplier) {
        if( null == value ) {
            ofInInstance( supplier.get() );
        }
        return this;
    }

    /**
     * 如果value为null，则抛出一个指定errorNo的NcuErrInfoException
     * */
    public NcuOptional<T> orThrow(String errorNo) {
        if( null == value ) {
            throw new NcuErrInfoException(errorNo);
        }
        return this;
    }

    /**
     * 如果value为null，则抛出一个指定的运行时异常
     * */
    public NcuOptional<T> orThrow(RuntimeException e) {
        if( null == value && null != e ) {
            throw e;
        }
        return this;
    }

    /**
     * 判断value是否为null
     *
     * @return 如果value不为null，则返回true
     * */
    public boolean isPresent() {
        return null != value;
    }

    /**
     * 如果value为null，则将指定的值复制给value ， 并将value返回
     * */
    public T orElse(T obj) {
        if( null == value ) {
            this.value = obj;
        }
        return this.value;
    }

    /**
     * 如果value为null，则抛出NoSuchElementException异常，否则返回value
     * */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * 无论value是否为null ，返回value
     * */
    public T getNoCheck() {
        return value;
    }

    /**
     * 如果value不为null ， 则执行function对象方法返回
     * 如果value为null ， 则执行supplier对象方法返回
     * */
    public <R> R get(Function<T , ? extends R> function , Supplier<? extends R> supplier) {
        T value = getNoCheck();
        return null != value ? function.apply(value) : supplier.get();
    }

    /**
     * 如果value不为null ， 则执行function对象方法返回 ， 否则返回null
     * */
    public <R> R get(Function<T , ? extends R> function) {
        return get(function , (R)null);
    }

    /**
     * 如果value不为null ， 则执行function对象方法返回 ， 否则返回defaultValue
     * */
    public <R> R get(Function<T , ? extends R> function , R defaultValue) {
        T value = getNoCheck();
        return null != value ? function.apply(value) : defaultValue;
    }

    /**
     * 类似于 {@link #get(Function)}，将结果使用NcuOptional进行封装
     * */
    public <R> NcuOptional<R> getNcuOptional(Function<T , ? extends R> function) {
        return NcuOptional.of( get(function) );
    }

}
