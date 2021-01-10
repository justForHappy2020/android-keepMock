package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.entity.AddImage;
import com.example.myapplication.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.utils.ClientUploadUtils.upload;

public class activity_community_postNewShare extends Activity implements View.OnClickListener {


    private ImageButton btn_cancel;//取消按钮
    private Button btn_release;//发布按钮
    private EditText et;//输入框
    private ImageView ibUpdatePhoto;//url下载下来的图片
    private ImageButton ibPhoto;//显示的照片item
    private String et_str;//输入的内容
//    private RecyclerView recyclerView;
//    private MultipleItemQuickAdapter multipleItemQuickAdapter;
//    private RecyclerView.LayoutManager mLayoutmanager;
    private GridView gridView;
    private MyAdapter madapt;
    private Context mcontext;
    private int Position;

    private TextView locate;
    private String URL_LOCATION;

    private SharedPreferences saveSP;
    private int httpCode;
    private String url;//一张图片的URL
    private List<String> urlList = new ArrayList();//动态图片URL数组
    private ArrayList<AddImage> mData = new ArrayList();//用来展示图片
    private Dialog dialog;//放大图片
    private ImageView image;

    private String imgUrlList;//http请求的参数，一组图片url，用空格分隔
    private String token;//相当于用户的唯一ID


    private String locationStr = "";//定位的经纬度
    private String address;
    private String message = "";


    private static final int REQUEST_CODE = 10;//定位的请求码

    private static final int MY_ADD_CASE_CALL_PHONE2 = 7; //打开相册的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community4);
        mcontext = activity_community_postNewShare.this;
        getLocation();
        getaddress();

        initView();
    }
//通过经纬度获取地址
    private void getaddress(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL_LOCATION = "http://api.map.baidu.com/geocoder?output=json&location="+locationStr+"&ak=esNPFDwwsXWtsQfw4NMNmur1";
//                    URL url = new URL("http://api.map.baidu.com/geocoder?output=json&location=23.213542,116.41&ak=esNPFDwwsXWtsQfw4NMNmur1");
                    URL url = new URL(URL_LOCATION);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setRequestMethod("GET");

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200){
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        String str = null;
                        StringBuffer buffer = new StringBuffer();

                        while((str = reader.readLine()) != null){
                            buffer.append(str);
                        }
                        String result = buffer.toString();

                        JSONObject result_json = new JSONObject(result);
                        JSONObject re = result_json.getJSONObject("result");//经纬度
                        address = re.getString("formatted_address");//地址
//                        JSONArray location = result_json.getJSONArray("location");//经纬度
//                        address[0] = result_json.getString("formatted_address");//地址

//                        JSONObject object1 = location.getJSONObject(0);
//                        Double longitude = object1.getDouble("lng");//经度
//                        Double latitude = object1.getDouble("lat");//纬度
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

//获取经纬度
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
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);//功耗
        //获取LocationManager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 获取最好的定位方式
        String provider = locationManager.getBestProvider(criteria, true); // true 代表从打开的设备中查找

        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        // 测试一般都在室内，这里颠倒了书上的判断顺序
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
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


    private void initView(){
        btn_cancel = findViewById(R.id.community4_Leftarrow_btn);
        btn_release = findViewById(R.id.community4_Release_btn);
        et = findViewById(R.id.community4_Sharetext_edit);
        ibUpdatePhoto = findViewById(R.id.community4_AddImage_btn4);
        //ibPhoto = findViewById(R.id.community4_item_image);
        locate = findViewById(R.id.locate);
        saveSP = getSharedPreferences("url",MODE_PRIVATE);

        locate.setText(address);

        btn_cancel.setOnClickListener(this);
        btn_release.setOnClickListener(this);
        ibUpdatePhoto.setOnClickListener(this);
        //ibPhoto.setOnClickListener(this);

        dialog = new Dialog(this,R.style.FullActivity);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(attributes);

        gridView = findViewById(R.id.community4_gridview);

//        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.community4_recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));//ing
//        recyclerView.setAdapter(multipleItemQuickAdapter);
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


//        if (requestCode == REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted准许
//                getLocation();
//            } else {
//                // Permission Denied拒绝
//            }
//        }

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

    //   通过图片url获取drawable
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

    //显示图片线程
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
            //url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606458247444&di=16bd6df8ac282275048bb95bfaef3cda&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20190522%2Fe63c244cf52b4675b36f773a70ac0582.jpeg";
            //urlList.add(url);
                        try {
                Uri photoUri = data.getData();//获取路径
                //final String filename = photoUri.getPath();
                final String filepath = getRealPathFromUriAboveApi19(this,photoUri);//获取绝对路径
                final String httpurl = "http://159.75.2.94:8080/api/user/uploadImageAndroid";

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
                                    SharedPreferences.Editor editor = saveSP.edit();
                                    editor.putString("url",url);
                                    if (!editor.commit()) {
                                        System.out.println("ERROR commit");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            Looper.prepare();
                            Toast.makeText(activity_community_postNewShare.this,"图片过大，请重新上传",Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                            e.printStackTrace();
                        }
                    }

                });
                thread.start();
                thread.join(5000);
                if(url != null){
                    for (int i = 0; i <urlList.size(); i++) {
                        mData.add(new AddImage(i, urlList.get(i)));
                        urlList.clear();
                    }
                    madapt = new MyAdapter(mData, activity_community_postNewShare.this){
                    };

                    gridView.setAdapter(madapt);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Position = position;
                            if (Position < mData.size()) {
                                image = findViewById(R.id.community4_item_image);
                                dia();
                                dialog.show();
                            }
                            else {
                                //  6.0之后动态申请权限 SD卡写入权限
                                ibUpdatePhoto.setVisibility(View.GONE);
                                if (ContextCompat.checkSelfPermission(activity_community_postNewShare.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(activity_community_postNewShare.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MY_ADD_CASE_CALL_PHONE2);

                                } else {
                                    //打开相册
                                    choosePhoto();
                                }
                            }
                        }
                    });
                    Deleteclick();


                }
            } catch (Exception e) {
                //"上传失败");
            }
            if(httpCode!=200){
                ibUpdatePhoto.setVisibility(View.VISIBLE);
                Toast.makeText(activity_community_postNewShare.this,"上传图片失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Deleteclick() {
        madapt.delete(new MyAdapter.Det() {
            @Override
            public void del(int position) {
//              删除list中的数据，重新绑定数据
                mData.remove(position);
                madapt.notifyDataSetChanged();
            }
        });
    }

    //点击图片放大
    private void dia(){
        image = getImageView();
        dialog.setContentView(image);

        //大图的点击事件（点击让他消失）
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ImageView getImageView(){
        ImageView imageView = new ImageView(this);

        //宽高
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //imageView设置图片
        //@SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.sisi);

        //Drawable drawable = BitmapDrawable.createFromStream(is, null);
        //new DownImageTask(image).execute(mData.get(Position).getImgUrl());
        Glide.with(this).load(mData.get(Position).getImgUrl()).into(imageView);
        return imageView;
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

                for (int i = 0; i <urlList.size(); i++)imgUrlList = imgUrlList + urlList.get(i) + " ";

                if(urlList.size() > 0) {
                    for (int i = 0; i <urlList.size(); i++)imgUrlList = imgUrlList + urlList.get(i) + " ";
                    imgUrlList = imgUrlList.substring(0,imgUrlList.length()-1);
                }
                if(et_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"请输入文字",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"动态发布成功",Toast.LENGTH_SHORT).show();
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
                                String responseData = HttpUtils.connectHttp("http://159.75.2.94:8080/api/community/sharing",json);
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
                ibUpdatePhoto.setVisibility(View.GONE);
                if (ContextCompat.checkSelfPermission(activity_community_postNewShare.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity_community_postNewShare.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE2);

                } else {
                    //打开相册
                    choosePhoto();
                }
                break;
                //点击删除图片
            case R.id.community4_item_cancel:
//                final int position = (int) view.getTag();
//                mData.remove(position);
//                madapt.notifyDataSetChanged();
                break;
//            case R.id.community4_item_image:
//
//                ibPhoto.setImageResource(R.drawable.sucai);//测试。实际需做：删除此图片item
//                urlList.remove(0);//从urlList中删除对应的图片url ，括号内为要删除的第几张图片（从0开始算）
        }

    }


}

/*
     //小图点击事件
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });
//}
//
//    private void init() {
//        imageView = (ImageView) findViewById(R.id.image);
//
//        //展示在dialog上面的大图
//        dialog = new Dialog(nextAcitivy.this, R.style.FullActivity);
//
//        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
//        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().setAttributes(attributes);
//
//        image = getImageView();
//        dialog.setContentView(image);
//
//        //大图的点击事件（点击让他消失）
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dis-miss();
//            }
//        });
//    }
//        private ImageView getImageView(){
//            ImageView imageView = new ImageView(this);
//
//            //宽高
//            imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            //imageView设置图片
//            @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.drawable.camera);
//
//            Drawable drawable = BitmapDrawable.createFromStream(is, null);
//            imageView.setImageDrawable(drawable);
//
//            return imageView;
//        }//图片显示
 */


