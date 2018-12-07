package com.example.ggrr1.bookyou;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button register;
    ListView booklist;
    int user_id;
    int book_list_status;
    String book_id, name, author, price, sale_price, created;
    BookListAdapter bookListAdapter;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        booklist = (ListView) findViewById(R.id.listview);
        register = (Button) findViewById(R.id.register);

        bookListAdapter = new BookListAdapter();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dataSetting();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookRegisterActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        booklist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }
    private void dataSetting(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    book_list_status = jsonResponse.getInt("book_list_status");

                    if(book_list_status == 2){
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookListActivity.this);
                        builder.setMessage("서버와 통신이 원할하지 않습니다.")
                                .setNegativeButton("AGAIN", null)
                                .create()
                                .show();
                    }

                    JSONArray book_list = jsonResponse.getJSONArray("book_list");
                    for(int i=0; i<book_list.length(); i++) {
                        book_id = book_list.getJSONObject(i).getString("id");
                        name = book_list.getJSONObject(i).getString("name");
                        author = book_list.getJSONObject(i).getString("author");
                        price = book_list.getJSONObject(i).getString("price");
                        sale_price = book_list.getJSONObject(i).getString("sale_price");
                        created = book_list.getJSONObject(i).getString("created");
                        bookListAdapter.addItem(book_id, name, author, price, sale_price, created);
                    }
                    booklist.setAdapter(bookListAdapter);
                    booklist.setOnItemClickListener(bookClick);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ListRequest listRequest = new ListRequest(String.valueOf(user_id), responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookListActivity.this);
        queue.add(listRequest);
    }

    private OnItemClickListener bookClick = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String book_id = ((BookListItem) bookListAdapter.getItem(position)).getBook_id();

            // new Intent(현재 Activity의 Context, 시작할 Activity 클래스)
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            // putExtra(key, value)
            intent.putExtra("user_id", user_id);
            intent.putExtra("book_id", book_id);
            startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.nav_book_list:

                break;
            case R.id.nav_message:

                break;
            case R.id.nav_qr_code:
                Toast.makeText(this, "nav_qr_code clicked..", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }


}
