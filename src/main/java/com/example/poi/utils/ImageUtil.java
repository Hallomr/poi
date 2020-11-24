package com.example.poi.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtil {
    /**
     * 设置源图片为背景透明，并设置透明度
     * @param srcFile 源图片
     * @param desFile 目标文件
     * @param alpha 透明度
     * @param formatName 文件格式
     * @throws IOException
     */
    public static void transparentImage(String srcFile,String desFile,int alpha,String formatName) throws IOException {
        BufferedImage temp =  ImageIO.read(new File(srcFile));//取得图片
        transparentImage(temp, desFile, alpha, formatName);
    }
    /**
     * 设置源图片为背景透明，并设置透明度
     * @param srcImage 源图片
     * @param desFile 目标文件
     * @param alpha 透明度
     * @param formatName 文件格式
     * @throws IOException
     */
    public static void transparentImage(BufferedImage srcImage,
                                        String desFile, int alpha, String formatName) throws IOException {
        int imgHeight = srcImage.getHeight();//取得图片的长和宽
        int imgWidth = srcImage.getWidth();
        int c = srcImage.getRGB(3, 3);
        //防止越位
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 10) {
            alpha = 10;
        }
        BufferedImage bi = new BufferedImage(imgWidth, imgHeight,
                BufferedImage.TYPE_4BYTE_ABGR);//新建一个类型支持透明的BufferedImage
        for(int i = 0; i < imgWidth; ++i)//把原图片的内容复制到新的图片，同时把背景设为透明
        {
            for(int j = 0; j < imgHeight; ++j)
            {
                //把背景设为透明
                if(srcImage.getRGB(i, j) == c){
                    bi.setRGB(i, j, c & 0x00ffffff);
                }
                //设置透明度
                else{
                    int rgb = bi.getRGB(i, j);
                    rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
                    bi.setRGB(i, j, rgb);
                }
            }
        }
        ImageIO.write(bi, formatName, new File(desFile));
    }

    private static void setAlpha(String srcImageFile, String descImageDir,int alpha) {

        try {
            //读取图片
            FileInputStream stream = new FileInputStream(new File(srcImageFile));// 指定要读取的图片

            // 定义一个字节数组输出流，用于转换数组
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] data =new byte[1024];// 定义一个1K大小的数组
            while (stream.read(data) != -1) {
                os.write(data);
            }

            ImageIcon imageIcon = new ImageIcon(os.toByteArray());
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

            //判读透明度是否越界
            if (alpha < 0) {
                alpha = 0;
            } else if (alpha > 10) {
                alpha = 10;
            }

            // 循环每一个像素点，改变像素点的Alpha值
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            // 生成图片为PNG

            ImageIO.write(bufferedImage, "jpg", new File(descImageDir));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        setAlpha("C:\\Users\\zeng\\Desktop\\新建文件夹\\mm.jpg",
                "C:\\Users\\zeng\\Desktop\\新建文件夹\\mm1.jpg", 10);
    }
}
