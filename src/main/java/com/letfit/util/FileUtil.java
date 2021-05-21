package com.letfit.util;

import com.google.gson.Gson;
import com.letfit.common.Const;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author cjt
 * @date 2021/4/10 11:04
 */
@Slf4j
public class FileUtil {

    /**
     * 上传视频
     * @param file
     * @param suffixName
     * @return
     */
    public static String uploadVideo(BufferedInputStream file, String suffixName){
        String sourceName = getFileName() + suffixName;
        //设置转码操作参数
        String fops = "avthumb/mp4/vcodec/libx264";
        //设置转码的队列
        String pipeline = "default.sys";
        //可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        String urlbase64 = UrlSafeBase64.encodeToString(Const.QNY_BUCKET + ":" + sourceName);
        String pfops = fops + "|saveas/" + urlbase64;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证
        Auth auth = Auth.create(Const.QNY_ACCESS_KEY, Const.QNY_SECRET_KEY);
        String uploadToken = auth.uploadToken(Const.QNY_BUCKET, null, 3600,
                new StringMap().putNotEmpty("persistentOps", pfops).putNotEmpty("persistentPipeline", pipeline));
        //上传
        try {
            Response response = uploadManager.put(file,sourceName,uploadToken,null,null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            //返回七牛云存储路径
            return Const.QNY_PATH + '/' + putRet.key;
        } catch (QiniuException e) {
            log.error(e.response.toString());
            e.printStackTrace();
        } finally {
            try {
                if(file!=null){
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 上传图片至七牛云
     * @param file 文件io流
     * @param suffixName 文件后缀名
     * @return String
     */
    public static String uploadImgQny(BufferedInputStream file, String suffixName){
        String imgName = getFileName() + suffixName;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证
        Auth auth = Auth.create(Const.QNY_ACCESS_KEY, Const.QNY_SECRET_KEY);
        String uploadToken = auth.uploadToken(Const.QNY_BUCKET);
        //上传
        try {
            Response response = uploadManager.put(file,imgName,uploadToken,null,null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            //返回七牛云存储路径
            return Const.QNY_PATH + '/' + putRet.key;
        } catch (QiniuException e) {
            log.error(e.response.toString());
            e.printStackTrace();
        } finally {
            try {
                if(file!=null){
                    file.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取新文件名
     * @return String
     */
    public static String getFileName(){
        String date = DateUtil.getFileCurrentDate();
        int ran = (int) (Math.random() * 10000);
        String ranStr = String.valueOf(ran);
        return date + ranStr;
    }


}
