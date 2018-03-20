package com.cypoem.retrofit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.View;

import com.cypoem.retrofit.R;
import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.response.MeiZi;
import com.cypoem.retrofit.net.DefaultObserver;
import com.cypoem.retrofit.net.DownloadObserver;
import com.cypoem.retrofit.net.IdeaApi;
import com.cypoem.retrofit.net.RequestHelper;
import com.cypoem.retrofit.utils.ToastUtils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity {

    private File mFile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    //  登录 成功后保存token
    public void login(View view) {
        new RequestHelper(this).login();
    }

    public void getData(View view) {
        IdeaApi.getApiService()
                .getMezi()
                .compose(this.<BasicResponse<List<MeiZi>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<MeiZi>>>(this, "正在加载...") {
                    @Override
                    public void onSuccess(BasicResponse<List<MeiZi>> response) {
                        List<MeiZi> results = response.getResults();
                        showToast("请求成功，妹子个数为" + results.size());
                    }

                    /**
                     * token刷新成功后重新请求数据
                     */
                    @Override
                    public void onTokenUpdateSuccess() {
                        super.onTokenUpdateSuccess();
                        getData(null);
                    }
                });
    }


    public void download(View view){
        IdeaApi.getApiService()
                .download("http://www.oitsme.com/download/oitsme.apk")
                .compose(this.<ResponseBody>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        saveFile(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadObserver<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        ToastUtils.show("文件下载成功");
                    }
                });

    }

    private void saveFile(ResponseBody body) {
        String fileName = "app.apk";
        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            InputStream is = body.byteStream();
            mFile = new File(fileStoreDir +"/"+ fileName);
            FileOutputStream fos = new FileOutputStream(mFile);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            //ToastUtils.show("文件已保存");
            fos.close();
            bis.close();
            is.close();
            installApk();

        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.show("文件保存失败");
        }
    }

    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(mFile),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
