package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.myapplication.Runnable.DownloadRunnable;
import com.example.myapplication.Beans.TaskInfo;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * 工具类名：VideoCacheUtil
 * 方法：
 * 1）getVideoUri(String VideoURL)，传入网络视频URL，若存在本地缓存则返回本地缓存(Uri)
 * 2）getVideoLocPath(String VideoURL)，传入网络视频URL，若存在本地缓存则返回本地缓存地址(String)
 * 3）isExistInLocal(String Url)，传入网络视频URL，判断本地是否有缓存
 */
public class VideoCacheUtil {
    final int DOWNLOAD_FAINED = -1;


    private List<TaskInfo> infoList;
    private HashMap<String,String> urlMap = new HashMap<String,String>();
    private String cacheRootDir;
    private Context context;
    private SharedPreferences shp ;
    private Set<String> videoPathSet;
    private DownloadRunnable dlRunnable;
    private Handler superHandler;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Message smsg = new Message();
            switch (msg.what){
                case DOWNLOAD_FAINED:
                    smsg.what=DOWNLOAD_FAINED;
                    break;
                default:
                    videoPathSet=new HashSet<String>(videoPathSet);
                    videoPathSet.add(infoList.get(msg.what).getUrl()+"&%VCU%&"+infoList.get(msg.what).getPath()+infoList.get(msg.what).getName());

                    SharedPreferences.Editor editor = shp.edit();
                    editor.putStringSet("videoPathList",videoPathSet);
                    editor.commit();

                    smsg.what=msg.what;
                    break;
            }
            Log.d("VCU handler msg= ",String.valueOf(msg.what));
            superHandler.sendMessage(smsg);

            return true;
        }
    });

    public VideoCacheUtil(List<TaskInfo> infoList,String cacheRootDir,Context context,Handler superHandler){
        this.infoList=infoList;
        this.cacheRootDir=cacheRootDir;//context.getCacheDir() + "/videoCache/";
        this.context=context;
        shp=context.getSharedPreferences("videoCache",MODE_PRIVATE);
        this.videoPathSet=shp.getStringSet("videoPathList",new HashSet<String>());
        this.superHandler=superHandler;
    }

    public VideoCacheUtil(String cacheRootDir,Context context){
        this.cacheRootDir=cacheRootDir;//context.getCacheDir() + "/videoCache/";
        this.context=context;
        shp=context.getSharedPreferences("videoCache",MODE_PRIVATE);
        this.videoPathSet=shp.getStringSet("videoPathList",new HashSet<String>());

    }

    public Uri getVideoUri(String VideoURL){
        if(isExistInLocal(VideoURL)){
            return  Uri.parse(urlMap.get(VideoURL));
        }else{
            return null;
        }

    }

    public String getVideoLocPath(String VideoURL){
        if(isExistInLocal(VideoURL)){
            return  urlMap.get(VideoURL);
        }else{
            download();
            return null;
        }

    }

    public Boolean isExistInLocal(String Url){
        Iterator<String> it= videoPathSet.iterator();
        while(it.hasNext()){
            //Log.d("isExistInLocal it,next",it.next());
            String str = it.next();
            urlMap.put(str.split("&%VCU%&")[0],str.split("&%VCU%&")[1]);
        }

        if(urlMap.containsKey(Url)&&new File(urlMap.get(Url)).exists()){
            return urlMap.containsKey(Url);
        }else{
            return false;
        }
    }

    public void download(){
        for(int i=0;i<infoList.size();i++){
            dlRunnable = new DownloadRunnable(infoList.get(i),i,handler);
            new Thread(dlRunnable).start();
        }

    }

    public void stop(){
        dlRunnable.stop();
        dlRunnable = null;
    }

    public List<TaskInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<TaskInfo> infoList) {
        this.infoList = infoList;
    }

    public String getCacheRootDir() {
        return cacheRootDir;
    }

    public void setCacheRootDir(String cacheRootDir) {
        this.cacheRootDir = cacheRootDir;
    }

    public Handler getSuperHandler() {
        return superHandler;
    }

    public void setSuperHandler(Handler superHandler) {
        this.superHandler = superHandler;
    }
}
