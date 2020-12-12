package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myapplication.entity.MultipleItem;
import com.example.myapplication.entity.Share;
import com.example.myapplication.entity.User;

import java.util.ArrayList;
import java.util.List;


public class community_main extends Fragment {
    private View.OnClickListener onClickListener;
    List<Share> postList= new ArrayList<>();

    private List<Share> datas01= new ArrayList<>();
    private List<MultipleItem> datas02= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_main, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view){
        ImageView img1 = view.findViewById(R.id.community_main_follow);
        ImageView img2= view.findViewById(R.id.community_main_search);


        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommunityClick(view);
            }
        };
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);
        final RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.community_main_recyclerView);
        //设置recyclerView的样式
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        MultipleItemQuickAdapter myAdapter = new MultipleItemQuickAdapter(datas02);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int i) {
                Intent intent = new Intent(getContext(),activity_sharedetail.class);
                intent.putExtra("shareId","id_test");
                startActivity(intent);
            }
        });
        myAdapter.addChildClickViewIds(R.id.follow,R.id.users_head,R.id.users_id,R.id.like,R.id.praises,R.id.comment_img,R.id.comments);
        myAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int i) {
                switch (view.getId()){
                    case R.id.follow:
                        Toast.makeText(getContext(), "点击了关注", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.users_head:
                        Toast.makeText(getContext(), "点击了头像", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.users_id:
                        Toast.makeText(getContext(), "点击了用户名", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.comments:
                    case R.id.comment_img:
                        Toast.makeText(getContext(), "点击了评论", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.praises:
                    case R.id.like:
                        Boolean isClicked = false;//从接口获取，List.get(i).getLiked();
                        if(!isClicked){
                            isClicked=true;
                            ImageView item_like=recyclerView.getLayoutManager().findViewByPosition(i).findViewById(R.id.like);
                            TextView item_praises=recyclerView.getLayoutManager().findViewByPosition(i).findViewById(R.id.praises);
                            item_praises.setText(String.valueOf(Integer.parseInt(item_praises.getText().toString())+1));
                        }

                        break;
                }
            }
        });
        recyclerView.setAdapter(myAdapter);

        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }
    public void onCommunityClick(View view){
        Intent intent = null;
        switch (view.getId()) {
            case R.id.community_main_follow:
                intent = new Intent(getActivity(),search_user.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
            case R.id.community_main_search:
                intent = new Intent(getActivity(),search.class);//点击搜索按钮跳转到搜索结果
                startActivity(intent);
                break;
        }}
    private void initData(){

        User user = new User(6666,"迪奥·布兰度",R.drawable.sucai);
        Share share1 = new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.scenery,"623","857",user);
        Share share2 = new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.scenery,"5547","3350",user);
        Share share3= new Share(user.getUserName(),"波纹呼吸法",R.drawable.sucai,R.drawable.scenery,"328","255",user);

        datas01.add(share1);
        datas01.add(share2);
        datas01.add(share3);

        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(2)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(0)));
        datas02.add(new MultipleItem(MultipleItem.SHARE, datas01.get(1)));
    }
}
