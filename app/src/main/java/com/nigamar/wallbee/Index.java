package com.nigamar.wallbee;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Index extends AppCompatActivity implements IndexScreen {

    private Toolbar indexToolbar;
    private FloatingActionButton indexFab;
    private FirebaseDatabase database;
    private RecyclerView imagesRv;
    private StaggeredGridLayoutManager manager;
    private DatabaseReference reference;
    private ImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initialize();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("images");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                L.d("there are " + dataSnapshot.getChildrenCount() + " childrens");
                Image singleImage = dataSnapshot.getValue(Image.class);
                L.d("ImageName " + singleImage.getImageName() + " ImageUrl " + singleImage.getImageUrl());
                adapter.addImage(singleImage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("firebase", "the child was changed ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("firebase", "the child was removed ");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initialize() {
        indexToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(indexToolbar);
        imagesRv = (RecyclerView) findViewById(R.id.images_rv);
        adapter = new ImagesAdapter(this, new ArrayList<Image>());
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        imagesRv.setLayoutManager(manager);
        imagesRv.setAdapter(adapter);
    }
}
