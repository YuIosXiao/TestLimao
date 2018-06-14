package com.first.saccelerator.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.model.SigninResponseV2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by XQ on 2017/4/21.
 * 下载更新
 */
public class UpdateAppUtils {
    private Activity mActivity;
    private boolean cancelFlag = false; //取消下载标志位
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progressBar;
    private static String saveFileName = "";

    private List<SigninResponseV2.SettingsBean> mlist;

    private String update_url;

    public UpdateAppUtils(Activity activity, List<SigninResponseV2.SettingsBean> list, String update_url) {
        this.mActivity = activity;
        this.mlist = list;
        this.update_url = update_url;

        String version = list.get(0).getANDROID_VERSION();
        String data[] = version.split("\\|");
        if (data.length > 1) {
            StaticStateUtils.vpn_nickname = "limaojiasu" + data[0] + ".apk";
            saveFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + StaticStateUtils.vpn_nickname; //完整路径名
//            LogUtils.i("saveFileName----->" + saveFileName);
        }
    }


    /**
     * 显示进度条对话框
     */
    private AlertDialog alertDialog; //进度条对话框
    private TextView schedule;
    private ProgressBar mProgress; //下载进度条控件

    public void showDownloadDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle("正在更新中...");
        final LayoutInflater inflater = LayoutInflater.from(mActivity);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        schedule = (TextView) v.findViewById(R.id.schedule);
        dialog.setView(v);
        if ("1".equals(mlist.get(0).getIS_UPDATE())) {
            dialog.setNegativeButton(mActivity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    arg0.dismiss();
                    cancelFlag = true;
                    call.cancel();
                }
            });
        } else if ("2".equals(mlist.get(0).getIS_UPDATE())) {
            dialog.setNegativeButton(mActivity.getResources().getString(R.string.update_exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    arg0.dismiss();
                    cancelFlag = true;
                    call.cancel();

                    mActivity.finish();
                    System.exit(0);
                }
            });
        }
        alertDialog = dialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        downloadAPK();
    }

    /**
     * 下载apk
     */
    private OkHttpClient okHttpClient;
    private Request request;
    private Call call;
    private OkHttpClient.Builder httpClientBuilder;

    public void downloadAPK() {
        //保存下载的APK 开始标识为0
        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
        spUtils.put(StaticStateUtils.vpn_nickname, "0");

        //        okHttpClient = new OkHttpClient();
        httpClientBuilder = new OkHttpClient.Builder();
        okHttpClient = lgnoreHttps();
//        request = new Request.Builder().url("http://192.168.123.178:8080/docs/110.apk").build();
        request = new Request.Builder().url(update_url).build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //删除下载的文件
                File file = new File(saveFileName);
                if (file.exists()) {
                    file.delete();
                }
                mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(saveFileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        progressBar = (int) (sum * 1.0f / total * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOADING);
                    }
                    fos.flush();


                    if (cancelFlag) {
                        //删除下载的文件
                        if (file.exists()) {
                            file.delete();
                        }
                    } else {
                        //保存下载的APK 结束标识为1，完成下载
                        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
                        spUtils.put(StaticStateUtils.vpn_nickname, "1");
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWNLOADED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is.close();
                    fos.close();
                }
            }
        });
    }


    /**
     * 更新UI的handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case DOWNLOADING:
                    mProgress.setProgress(progressBar);
                    schedule.setText(progressBar + "%");
                    break;
                case DOWNLOADED:
                    if (alertDialog != null)
                        alertDialog.dismiss();
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    ToastUtils.showLongToast(mActivity.getResources().getString(R.string.download_failure));
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 下载完成后自动安装apk
     */
    public void installAPK() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//系统大于7.0的时候 需要
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);//添加flags，表明我们要被授予什么样的临时权限
            Uri uri = FileProvider.getUriForFile(mActivity, "com.first.saccelerator.fileprovider", apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            mActivity.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
            mActivity.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        if ("2".equals(mlist.get(0).getIS_UPDATE())) {
            mActivity.finish();
            System.exit(0);
        }
    }

    /**
     * 忽略https验证
     */

    public OkHttpClient lgnoreHttps() {
        okHttpClient = httpClientBuilder.build();
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier hv1 = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        String workerClassName = "okhttp3.OkHttpClient";

        try {
            Class workerClass = Class.forName(workerClassName);
            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(okHttpClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(okHttpClient, sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }


}
