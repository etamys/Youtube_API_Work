package com.example.song_player;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public  class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private Handler handler;;
    private List<VideoList> searchResults;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //searchResults = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerView);
        //recyclerAdapter = new RecyclerAdapter(getApplicationContext(),searchResults);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(recyclerAdapter);
        handler = new Handler();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchOnYoutube(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchOnYoutube(final String keywords){

        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(MainActivity.this);
                searchResults = yc.search(keywords);

                handler.post(new Runnable(){
                    public void run(){
                        fillYoutubeVideos();

                    }
                });
            }
            //starting the thread
        }.start();
    }

    //method for creating adapter and setting it to recycler view
    private void fillYoutubeVideos(){

        //object of YoutubeAdapter which will fill the RecyclerView
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),searchResults);

        //setAdapter to RecyclerView
        recyclerView.setAdapter(recyclerAdapter);

        //notify the Adapter that the data has been downloaded so that list can be updapted
        recyclerAdapter.notifyDataSetChanged();
    }
}