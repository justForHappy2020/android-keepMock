package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class search_course extends Activity implements View.OnClickListener{


    final int SEARCH_ALL = 0;
    final int SEARCH_COURSE = 1;

    private ImageButton ibSearch;
    private ImageButton ibback;
    private ImageButton btReset;
    private EditText etInput;
    private String searchContent;
    LinearLayout search_linerlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        initData();
        initView();
    }

    private void initView() {
        ibback = findViewById(R.id.search_back);
        ibSearch = findViewById(R.id.searching_button);
        btReset = findViewById(R.id.search_reset);
        etInput = findViewById(R.id.text_inout_search);
        search_linerlayout = findViewById(R.id.search_linerlayout);

        ibback.setOnClickListener(this);
        ibSearch.setOnClickListener(this);
        btReset.setOnClickListener(this);

        etInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(text.toString().trim().isEmpty()){
                    search_linerlayout.setVisibility(View.VISIBLE);
                }else{
                    search_linerlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initData(){

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.searching_button:
                intent = new Intent(this, search_result.class);
                searchContent = etInput.getText().toString().trim();
                intent.putExtra("from",SEARCH_COURSE);
                intent.putExtra("searchContent",searchContent);

                startActivity(intent);
                break;
            case R.id.go_back_button:
                finish();
                break;
            case R.id.search_reset:
                search_linerlayout.setVisibility(View.VISIBLE);
                etInput.setText(null);
                break;
        }
    }
}
