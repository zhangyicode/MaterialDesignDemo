package com.example.materialdesigntest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;

    private DrawerLayout mDrawerLayout;

    private Fruit[] fruits=new Fruit[]{
                new Fruit("Apple",R.drawable.apple),new Fruit("Banana",
                    R.drawable.banana),
                new Fruit("Orange",R.drawable.orange),new Fruit("Watermelon",
                    R.drawable.watermelon),
                new Fruit("Pear",R.drawable.pear),new Fruit("Grape",
                    R.drawable.grape),
                new Fruit("Pineapple",R.drawable.pineapple),new Fruit("Strawberry",
                    R.drawable.strawberry),
                new Fruit("Cherry",R.drawable.cherry),new Fruit("Mango",
                    R.drawable.mango),};

    private List<Fruit> fruitList=new ArrayList<>();

    private FruitAdapter adapter;

    private void initFruits(){ //初始化界面上显示的水果
        fruitList.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        /*drawerLayout是谷歌提供的一个实现滑动菜单的布局控件,在布局中可以直接放入两个子空间，
          第一个控件显示在主屏幕中，第二个控件显示在滑动菜单中*/

        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        //NavigationView是Design Support库中的一个控件，是滑动菜单的布局，需要准备menu和headerView，是drawerLayout的第二个控件

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);//Design Support库中的悬浮控件，用法和普通的button一样
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Data restored!",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                //Toast.makeText(MainActivity.this, "FAB clicked!",Toast.LENGTH_SHORT).show();
            }
        });

        initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //navigationView的选项监听事件
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               // mDrawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"Call!",Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this,"Friends!",Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this,"Location!",Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this,"Mail!",Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(MainActivity.this,"Task!",Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                }
                return true;
            }
        });
    }

    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.backup:
                Toast.makeText(MainActivity.this,"Backup!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete:
                Toast.makeText(MainActivity.this,"Delete!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                Toast.makeText(MainActivity.this,"Settings!",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
