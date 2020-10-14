package com.syp.pattern.prototype;

import java.io.*;
import java.util.List;

/**
 * 2020-03-31
 * 原型模式
 * 是指原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象
 * 使用场景：
 * 类初始化消耗资源多
 * new 产生的一个对象需要非常繁琐的过程（数据准备，访问权限等）
 * 构造函数比较复杂
 * 循环体中生产大量对象时
 *
 * clone 和new 的区别 ：clone不再调用构造方法
 * 深克隆和浅克隆的区别：
 * jdk提供的clone方法时浅拷贝：证明方法如下：
 *
 *
 * 1.单例不能实现cloneable接口，单例不能成为原型,或者重写clone方法返回返回instance
 * 2.单例可以被序列化破坏，因为序列化clone不走构造方法
 *
 * 实现深克隆的方法？
 * 1.序列化
 * 2.转json
 *
 * 源码：
 * ArrayList HashMap
 *
 * 总结：
 * 优点：
 * 1.基于内存二进制流的拷贝，比直接new对象性能提升了很多
 * 2.可以使用深克隆方式保存对象的状态，使用原型模式将对象复制一份并将其状态保存起来，简化了创建过程
 *
 * 缺点：
 * 1.必须配备克隆方法
 * 2.违反开闭原则：
 */

public class ProtoTypePattern implements Cloneable {
    private int age;
    private String name;
    private List<String> hobbies;

    public ProtoTypePattern shadowClone(){
        try {
            return (ProtoTypePattern) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProtoTypePattern deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        bos.close();
        oos.close();
        bis.close();
        ois.close();

        return (ProtoTypePattern) ois.readObject();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
