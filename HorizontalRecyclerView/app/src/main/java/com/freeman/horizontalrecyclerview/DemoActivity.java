package com.freeman.horizontalrecyclerview;

import android.app.Activity;
import android.os.Bundle;

import com.freeman.support.v7.widget.LinearLayoutManager;
import com.freeman.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends Activity {

    RecyclerView mRecyclerView;
    List<Integer> mList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        initView();
    }
    void initView(){
        for(int i = 0; i < 3; i++){
            mList.add(R.drawable.pic4);
            mList.add(R.drawable.pic5);
            mList.add(R.drawable.pic6);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        LbsPackRecyclerAdapter adapter = new LbsPackRecyclerAdapter(mRecyclerView, mList);
        mRecyclerView.setAdapter(adapter);
    }

}
