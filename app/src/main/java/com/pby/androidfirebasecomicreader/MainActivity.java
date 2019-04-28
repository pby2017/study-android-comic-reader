package com.pby.androidfirebasecomicreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pby.androidfirebasecomicreader.interfaces.IBannerLoadDone;
import com.pby.androidfirebasecomicreader.interfaces.IComicLoadDone;
import com.pby.androidfirebasecomicreader.adapter.MyComicAdapter;
import com.pby.androidfirebasecomicreader.adapter.MySliderAdapter;
import com.pby.androidfirebasecomicreader.common.Common;
import com.pby.androidfirebasecomicreader.model.Comic;
import com.pby.androidfirebasecomicreader.service.PicassoLoadingService;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IComicLoadDone {

    private Slider slider;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerComic;
    private TextView textComic;

    // Database
    private DatabaseReference banners;
    private DatabaseReference comics;

    // Listener
    private IBannerLoadDone bannerListener;
    private IComicLoadDone comicListener;

    // Dialog
    private android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics = FirebaseDatabase.getInstance().getReference("Comic");

        // Init Listener
        bannerListener = this;
        comicListener = this;


        slider = findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });

        recyclerComic = findViewById(R.id.recycler_comic);
        recyclerComic.setHasFixedSize(true);
        recyclerComic.setLayoutManager(new GridLayoutManager(this, 2));

        textComic = findViewById(R.id.text_comic);
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
    }

    @Override
    public void onComicLoadDoneListener(List<Comic> comicList) {
        Common.comicList = comicList;

        recyclerComic.setAdapter(new MyComicAdapter(getBaseContext(), comicList));

        textComic.setText(String.format("NEW COMIC (%d)", comicList.size()));

        if (!swipeRefreshLayout.isRefreshing()) {
            alertDialog.dismiss();
        }
    }

    private void loadComic() {
        // show dialog
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Please wait...")
                .build();

        if (swipeRefreshLayout.isRefreshing()) {
            alertDialog.show();
        }

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Comic> comicList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comicSnapshot : dataSnapshot.getChildren()) {
                    Comic comic = comicSnapshot.getValue(Comic.class);
                    comicList.add(comic);
                }

                comicListener.onComicLoadDoneListener(comicList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBanner() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSnapshot : dataSnapshot.getChildren()) {
                    String image = bannerSnapshot.getValue(String.class);
                    bannerList.add(image);
                }

                // Call listener
                bannerListener.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
