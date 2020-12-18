package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class community4 extends Activity implements View.OnClickListener {

    private ImageButton btn_cancel;
    private Button btn_release;
    private EditText et;
    private ImageButton ibUpdatePhoto;
    private ImageButton ibPhoto;//显示的照片item

    private String et_str;

    private SharedPreferences saveSP;
    private int httpCode;
    private String url;//一张图片的URL
    private List<String> urlList = new ArrayList();//动态图片URL数组
    private String imgUrlList;//http请求的参数，一组图片url，用空格分隔
    private String token;//相当于用户的唯一ID

    private String locationStr = "";//定位的经纬度
    private String message = "";

    private static final int REQUEST_CODE = 10;//定位的请求码
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7; //打开相册的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community4);
        initView();
        getLocation();
    }

    private void getLocation() {
        if (Build.VERSION.SDK_INT >= 23) {// android6 执行运行时权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            }
        }

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，如果设置为高精度，依然获取不了location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗
        //获取LocationManager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 获取最好的定位方式
        String provider = locationManager.getBestProvider(criteria, true); // true 代表从打开的设备中查找

        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        // 测试一般都在室内，这里颠倒了书上的判断顺序
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else {
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "Please Open Your GPS or Location Service", Toast.LENGTH_SHORT).show();
            return;
        }

        LocationListener locationListener = new LocationListener() {
            //当位置改变的时候调用
            @Override
            public void onLocationChanged(Location location) {
                //经度
                double longitude = location.getLongitude();
                //纬度
                double latitude = location.getLatitude();

                //海拔
                double altitude = location.getAltitude();

                locationStr = longitude + "_" + latitude;
                //launcher.callExternalInterface("getLocationSuccess", locationStr);
            }

            //当GPS状态发生改变的时候调用
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                switch (status) {

                    case LocationProvider.AVAILABLE:
                        message = "当前GPS为可用状态!";
                        break;

                    case LocationProvider.OUT_OF_SERVICE:
                        message = "当前GPS不在服务内!";
                        break;

                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        message = "当前GPS为暂停服务状态!";
                        break;

                }
                //launcher.callExternalInterface("GPSStatusChanged", message);

            }

            //GPS开启的时候调用
            @Override
            public void onProviderEnabled(String provider) {
                message = "GPS开启了!";
                //launcher.callExternalInterface("GPSOpenSuccess", message);

            }

            //GPS关闭的时候调用
            @Override
            public void onProviderDisabled(String provider) {
                message = "GPS关闭了!";
                //launcher.callExternalInterface("GPSClosed", message);

            }
        };

        //获取上次的location
        Location location = locationManager.getLastKnownLocation(provider);

        /**
         * 参1:选择定位的方式
         * 参2:定位的间隔时间
         * 参3:当位置改变多少时进行重新定位
         * 参4:位置的回调监听
         */
        locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
        while(location == null){ location = locationManager.getLastKnownLocation(provider); } //移除更新监听
        locationManager.removeUpdates(locationListener);
        if (location != null) { //不为空,显示地理位置经纬度
            double longitude = location.getLongitude();// 经度
            double latitude = location.getLatitude();// 纬度
            locationStr = latitude+ ","+longitude;
        }
    }
    /**
     * 获取权限结果
     */

    private void initView(){
        btn_cancel = findViewById(R.id.community4_Leftarrow_btn);
        btn_release = findViewById(R.id.community4_Release_btn);
        et = findViewById(R.id.community4_Sharetext_edit);
        ibUpdatePhoto = findViewById(R.id.community4_AddImage_btn4);
        ibPhoto = findViewById(R.id.community4_Image_btn1);

        btn_cancel.setOnClickListener(this);
        btn_release.setOnClickListener(this);
        ibUpdatePhoto.setOnClickListener(this);
        ibPhoto.setOnClickListener(this);

        token = getSharedPreferences("saved_token",MODE_PRIVATE).getString("token", "");//从本地获取用户的token

    }





    //json封装comntent和url图片

    //点击 + 后跳转到添加图片的layout


    //打开相册
    private void choosePhoto() {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }


    /**
     * 申请权限回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted准许
                getLocation();
            } else {
                // Permission Denied拒绝
            }
        }
        if (requestCode == MY_ADD_CASE_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的 uri, 则通过document id来进行处理
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    //通过图片url获取drawable
    private Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), imageUrl + ".jpg");
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }

        return drawable ;
    }

    //显示头像线程
    private void startThread() {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                final Drawable drawable = loadImageFromNetwork(url);
                // post() 特别关键，就是到UI主线程去更新图片
                ibPhoto.post(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        ibPhoto.setImageDrawable(drawable) ;
                    }}) ;
            }

        });
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * startActivityForResult执行后的回调方法，接收返回的图片
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//data为返回到的图片
        if (requestCode == 1 && resultCode != Activity.RESULT_CANCELED) {

            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) return;
            // 把原图显示到界面上
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            //测试数据，后期去掉，然后用下面注释的代码
            url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606458247444&di=16bd6df8ac282275048bb95bfaef3cda&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190522%2Fe63c244cf52b4675b36f773a70ac0582.jpeg";
            urlList.add(url);
            startThread();
            /*            try {
                Uri photoUri = data.getData();//获取路径
                //final String filename = photoUri.getPath();
                final String filepath = getRealPathFromUriAboveApi19(this,photoUri);//获取绝对路径
                final String httpurl = "http://192.168.16.1:8080/api/user/uploadImageAndroid";
                //http请求获取上传到云后的图片URL
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String responseData = upload(httpurl,filepath).string();//http请求
                            try {
                                JSONObject jsonObject1 = new JSONObject(responseData);
                                //相应的内容
                                httpCode = jsonObject1.getInt("code");
                                if(httpCode == 200){
                                    url= jsonObject1.getString("data");//云上的图片URL
                                    urlList.add(url);
*//*                                    SharedPreferences.Editor editor = saveSP.edit();
                                    editor.putString("url",url);
                                    if (!editor.commit()) {
                                        System.out.println("ERROR commit");
                                    }*//*
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                thread.join(5000);
                if(url != null)startThread();//显示图片线程
            } catch (Exception e) {
                //"上传失败");
            }
            if(httpCode!=200)Toast.makeText(community4.this,"上传图片失败",Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.community4_Leftarrow_btn:
                finish();
                break;
            case R.id.community4_Release_btn:
                et_str = et.getText().toString().trim();
                imgUrlList = "";
                if(urlList.size() > 0) for (int i = 0; i <urlList.size(); i++)imgUrlList = imgUrlList + urlList.get(i) + " ";
                imgUrlList = imgUrlList.substring(0,imgUrlList.length()-1);
                if(et_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"输入为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"不为空",Toast.LENGTH_SHORT).show();
                    //将输入内容发送到数据库--http请求
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //设置JSON数据
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("token", token);
                                    json.put("content", et_str);
                                    json.put("imgUrlList", imgUrlList);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String responseData = HttpUtils.connectHttp("https://www.fastmock.site/mock/774dcf01fef0c91321522e08613b412e/api/api/community/sharing",json);
                                try {
                                    JSONObject jsonObject1 = new JSONObject(responseData);
                                    //相应的内容
                                    httpCode = jsonObject1.getInt("code");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    try {
                        thread.join(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(httpCode==200)Toast.makeText(this,  "动态发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.community4_AddImage_btn4:
                //  6.0之后动态申请权限 SD卡写入权限
                if (ContextCompat.checkSelfPermission(community4.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(community4.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE2);

                } else {
                    //打开相册
                    choosePhoto();
                }
                break;
            //点击删除图片
            case R.id.community4_Image_btn1:
                ibPhoto.setImageResource(R.drawable.sucai);//测试。实际需做：删除此图片item
                urlList.remove(0);//从urlList中删除对应的图片url ，括号内为要删除的第几张图片（从0开始算）
        }

    }

}