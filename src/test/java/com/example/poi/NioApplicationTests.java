package com.example.poi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@SpringBootTest
@RunWith(value = SpringRunner.class)
class NioApplicationTests {

    /**
     * 本地我文件写数据
     * */
    @Test
    void write() throws Exception{
        //1. 创建输出流
        FileOutputStream fos=new FileOutputStream("C:\\Users\\zeng\\Pictures\\Saved Pictures\\test.txt");
        //2. 从流中得到一个通道
        FileChannel fc=fos.getChannel();
        //3. 提供一个缓冲区
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        //4. 往缓冲区中存入数据
        String str="hello,nio";
        buffer.put(str.getBytes());
        //5. 翻转缓冲区
        buffer.flip();
        //6. 把缓冲区写到通道中
        fc.write(buffer);
        //7. 关闭
        fos.close();
    }

    /**
     * 从本地文件读取数据
     * */
    @Test
    public void read() throws Exception{
        File file=new File("C:\\Users\\zeng\\Pictures\\Saved Pictures\\test.txt");
        //1. 创建输入流
        FileInputStream fis=new FileInputStream(file);
        //2. 得到一个通道
        FileChannel fc=fis.getChannel();
        //3. 准备一个缓冲区
        ByteBuffer buffer=ByteBuffer.allocate((int)file.length());
        //4. 从通道里读取数据并存到缓冲区中
        fc.read(buffer);
        System.out.println(new String(buffer.array()));
        //5. 关闭
        fis.close();
    }

    @Test
    public void copy() throws Exception{
        //1. 创建两个流
        FileInputStream fis=new FileInputStream("C:\\Users\\zeng\\Pictures\\Saved Pictures\\test.txt");
        FileOutputStream fos=new FileOutputStream("C:\\Users\\zeng\\Pictures\\Saved Pictures\\23_iso100_14mm.txt");

        //2. 得到两个通道
        FileChannel sourceFC=fis.getChannel();
        FileChannel destFC=fos.getChannel();

        //3. 复制
        destFC.transferFrom(sourceFC,0,sourceFC.size());

        //4. 关闭
        fis.close();
        fos.close();
    }

    @Test
    public void File1(){
        Path path = Paths.get("C:\\Users\\zeng\\Pictures\\Saved Pictures\\text.jpg");
        try {
            if(Files.exists(path)){
                Files.delete(path);
            }else{
                Files.createFile(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*遍历目录下文件*/
    @Test
    public void File2(){
        Path path = Paths.get("C:\\Users\\zeng\\Pictures\\Saved Pictures");
        try {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            for (Path path1 : paths) {
                System.out.println(path1.getFileName());
                System.out.println("------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*遍历目录文件包含子目录*/
    @Test
    public void File3() throws Exception{
        Path dir = Paths.get("C:\\Users\\zeng\\Pictures");
        Files.walkFileTree(dir,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if(file.toString().endsWith(".txt")){
                    System.out.println(file.getFileName());
                }
                return super.visitFile(file, attrs);
            }
        });
    }

    /*创建多级目录 文件复制*/
    @Test
    public void File4(){
        //Path path = Paths.get("C:\\Users\\zeng\\Pictures\\Camera Roll\\text1\\cbj.jpg");
        try {
            //Files.createDirectories(path);
            Path source = Paths.get("C:\\Users\\zeng\\Pictures\\Camera Roll\\23_iso100_14mm.txt");
            Path target = Paths.get("C:\\Users\\zeng\\Pictures\\Camera Roll\\test.txt");

            Files.copy(source,target,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void File5(){

    }
}
