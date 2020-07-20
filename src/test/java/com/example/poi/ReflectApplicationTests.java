package com.example.poi;

import com.example.poi.entity.ReflectVo;
import com.example.poi.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class ReflectApplicationTests {
    @Test
    public void test1(){
        User user = new User();
        //判断对象所属的类
        String name = user.getClass().getName();
        System.out.println(name);
    }

    @Test
    public void test2()throws Exception{
        //获取字节码文件对象的三种方法
        Class<?> c = Class.forName("com.example.poi.entity.User");
        System.out.println(c);
        Class<User> c1 = User.class;
        System.out.println(c1);
        User user = new User();
        Class<? extends User> c2 = user.getClass();
        System.out.println(c2);
    }

    @Test
    public void test3() throws Exception{
        //使用反射创建对象
        Class<User> c = User.class;
        User user = c.newInstance();
        System.out.println(user);
        //user.getClass().getSuperclass();
        Constructor<User> constructor = c.getConstructor(Integer.class, String.class);
        User qwx = constructor.newInstance(1, "qwx");
        System.out.println(qwx);
    }

    @Test
    public void test4() throws Exception{
        ReflectVo reflectVo = new ReflectVo(1, "001","qwx");
        //利用反射获取和设置类公用成员变量的值
        Class<ReflectVo> c = (Class<ReflectVo>)Class.forName("com.example.poi.entity.ReflectVo");
        //getfields仅能获取public属性成员包括父类
        Field[] fields = c.getFields();
        for (Field field : fields) {
            String name = field.getName();
            System.out.println(name);
            Object o = field.get(reflectVo);
            System.out.println(name+""+o);
        }
    }

    @Test
    public void test5() throws Exception{
        ReflectVo reflectVo = new ReflectVo(1, "001","qwx");
        //利用反射获取私有成员变量值
        Class<ReflectVo> c = (Class<ReflectVo>)Class.forName("com.example.poi.entity.ReflectVo");
        //getDeclaredFields仅能获取类本身的成员属性包括私有、公共、保护
        // （获取父类的私有属性要通过getSuperclass方法获取父类的字节码文件对象再调用getDeclaredFields方法）
        Field[] declaredFields = c.getDeclaredFields();
        for (Field f : declaredFields) {
            String name = f.getName();
            System.out.println(name);
            int modifiers = f.getModifiers();
            if(modifiers == Modifier.PRIVATE){
                //私有成员 通过暴力反射获取
                f.setAccessible(true);
            }
            Object o = f.get(reflectVo);
            System.out.println(name+":"+o);
        }
    }

    @Test
    public void test6() throws Exception{
        //利用反射获取类的方法
        //getMethods()获取所有公有方法包括父类; getDeclaredMethods()获取类本身所有方法不包括父类
        Class<ReflectVo> c = (Class<ReflectVo>)Class.forName("com.example.poi.entity.ReflectVo");
        Method[] m = c.getDeclaredMethods();
        for (Method method : m) {
            String name = method.getName();
            System.out.println(name);
        }
    }

    @Test
    public void test7() throws Exception{
        ReflectVo reflectVo = new ReflectVo();
        //利用反射调用类的方法
        Class<ReflectVo> c = (Class<ReflectVo>)Class.forName("com.example.poi.entity.ReflectVo");
        Method method = c.getMethod("setValue", String.class);
        method.invoke(reflectVo,"qwx");
        Method m = c.getMethod("getValue");
        Object value = m.invoke(reflectVo);
        System.out.println(value);
        //利用反射调用私有方法
        Method test = c.getDeclaredMethod("test", String.class);
        test.setAccessible(true);
        test.invoke(reflectVo, "this is test");
    }

    @Test
    public void test8() throws Exception {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("name",User.class);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        User user = new User();
        writeMethod.invoke(user,"cbj");
        Method readMethod = propertyDescriptor.getReadMethod();
        Object name = readMethod.invoke(user);
        System.out.println(name);
    }
}
