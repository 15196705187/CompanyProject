package yisan.com.guidetest.reflection;

import android.util.Log;

/**
 * Created by asus on 2018/5/1.
 */

public class Person {
    String name;
    private int age;
    public Person(){
        Log.i("Person","Person的无参构造函数");
    }
    public Person(String name,int age){
        Log.i("Person","Person的有参构造函数");
        this.age=age;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
