package com.example.poi;

import com.example.poi.entity.LinkList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class LinkListTest {
    @Test
    public void addLink(){
        LinkList<String> list = new LinkList<>();
        list.add("zxk");
        list.add("hp");
        System.out.println(list);
    }

}
