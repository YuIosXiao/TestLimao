package com.first.saccelerator.myclass;

import android.content.Context;

import com.first.saccelerator.App;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.utils.StaticStateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Xq on 2018/3/9.
 * 公共实用方法类（一些需要公共的方法）
 */
public class PublicPracticalMethod {


    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder {
        public static PublicPracticalMethod instance = new PublicPracticalMethod();
    }

    private PublicPracticalMethod() {
    }

    public static PublicPracticalMethod newInstance() {
        return SingletonHolder.instance;
    }


    /**
     * 00001
     * 下载vpn翻墙配置文件
     */
    public void downloadVpnConfig(String url, final Context context) {
        final String fileNametemporary = url.substring(url.lastIndexOf("/") + 1);
        final String[] fileNameArray = fileNametemporary.split("\\?");
        final String fileName = fileNameArray[0];

        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
//        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(StaticStateUtils.usebath + File.separator + url);
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    write2Data(response.body(), fileName, context);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });


    }

    /**
     * 00001
     * 把文件写入到data目录下
     */
    public boolean write2Data(ResponseBody body, String fileName, Context context) {
        try {
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();

                if (App.isDomestic()) {
                    StaticStateUtils.mergeFiles(StaticStateUtils.originalFilePath_d2o, StaticStateUtils.usesFilePath_d2o, StaticStateUtils.targetFilePath_d2o);
                } else {
                    StaticStateUtils.mergeFiles(StaticStateUtils.originalFilePath_o2d, StaticStateUtils.usesFilePath_o2d, StaticStateUtils.targetFilePath_o2d);
                }
                return true;
            } catch (IOException e) {

                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
