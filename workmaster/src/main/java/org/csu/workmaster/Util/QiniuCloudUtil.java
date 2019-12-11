package org.csu.workmaster.Util;


import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import java.io.File;
import java.io.FileInputStream;
import com.qiniu.util.*;
import okhttp3.*;
public class QiniuCloudUtil {
    private static String ak = "lzahKsdfTl_zi1TqVkpiEyK2dJsUEJ_3MSwSsgNM";
    private static String sk = "fDVrYpi_4lSaor6Mr0d8WzRdvWmlXyGfY6sSRZ49"; // 密钥配置
    private static Auth auth = Auth.create(ak, sk);    // TODO Auto-generated constructor stub
    private static String bucketname = "donowfile";    //空间名

    public static String getUpToken() {
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }

    public static void put64image(File file,String key) throws Exception {
        //String file = "C:\\Users\\shuxin\\Desktop\\mitacs\\Snipaste_2018-08-28_00-49-27.png";//图片路径
        int l = (int) file.length();
        byte[] src = new byte[l];
        FileInputStream fis = new FileInputStream(file);
        fis.read(src);
        String file64 = Base64.encodeToString(src, 0);
        String url = "http://upload-z2.qiniu.com/putb64/" + l+"/key/"+ UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}