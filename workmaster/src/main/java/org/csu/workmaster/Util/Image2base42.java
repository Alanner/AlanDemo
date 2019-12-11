package org.csu.workmaster.Util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Image2base42 {
    public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * @Descriptionmap 对字节数组字符串进行Base64解码并生成图片
     * @author
     * @Date 2016-12-06
     * @param base64 图片Base64数据
     * @param path 图片路径
     * @return
     */
    public static boolean base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null){ // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //测试
    public static void main(String[] str) throws Exception {

        String path = "C:\\Users\\shuxin\\Desktop\\mitacs\\Snipaste_2018-08-28_00-46-57.png";
        String base64 = Image2base42.imageToBase64(path);
        File file = new File("C:\\Users\\shuxin\\Desktop\\test.txt");
        file.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(base64);
        out.flush();
        out.close();


        Image2base42.base64ToImage(base64, "C:\\Users\\shuxin\\Desktop\\2.png");

        System.out.println(base64 + "+++++++++++");


    }
}
