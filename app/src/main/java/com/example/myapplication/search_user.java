package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class search_user extends AppCompatActivity implements View.OnClickListener {

    final int SEARCH_ALL = 0;
    final int SEARCH_USER = 3;

    private ImageButton ibSearch;
    private ImageButton ibback;
    private ImageButton btReset;
    private EditText etInput;
    private String searchContent;
    LinearLayout search_linerLayout;
    TextView tv_searchContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_search_user);
        initView();
    }
    private void initView() {
        ibback = findViewById(R.id.community5_LeftArrow_btn);
        ibSearch = findViewById(R.id.community5_Search_btn);
        btReset = findViewById(R.id.community5_Cancel_btn);
        etInput = findViewById(R.id.community5_Search_edit);
        search_linerLayout = findViewById(R.id.search_linerLayout);
        tv_searchContent = findViewById(R.id.tv_searchContent);

        ibback.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        btReset.setOnClickListener(this);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(!text.toString().trim().isEmpty()){
                    tv_searchContent.setText(text.toString().trim());
                    search_linerLayout.setVisibility(View.VISIBLE);

                }else{
                    tv_searchContent.setText("");
                    search_linerLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager manager = ((InputMethodManager)search_user.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }

    private void initData(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searching_button:
                search();
                break;
            case R.id.search_back:
                finish();
                break;
            case R.id.search_reset:
                etInput.setText("");
                search_linerLayout.setVisibility(View.VISIBLE);
                etInput.setText(null);
                break;
        }
    }

    public void search_linerLayout_onClick(View view){
        search();
    }

    private void search(){
        Intent intent = new Intent(this, search_result.class);
        if(etInput.getText().toString().trim().isEmpty()){
            Toast.makeText(search_user.this,"请输入搜索内容",Toast.LENGTH_SHORT).show();
        }else{
            searchContent = etInput.getText().toString().trim();
            intent.putExtra("from",SEARCH_USER);
            intent.putExtra("searchContent",searchContent);
            startActivity(intent);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

