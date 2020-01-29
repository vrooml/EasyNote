package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.recyclerview_note)
    RecyclerView noteRecyclerView;
    @BindView(R.id.add_button)
    Button addButton;
    @BindView(R.id.menu_button)
    Button menuButton;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    NoteAdapter noteAdapter;

    private List<Note> noteArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(linearLayoutManager);

        noteAdapter=new NoteAdapter(noteArray);
        noteRecyclerView.setAdapter(noteAdapter);

        //设置toolbar监听器
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                drawer.openDrawer(Gravity.START);
            }
        });

        //设置菜单栏按键
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        //装载数据
        noteArray.addAll(LitePal.findAll(Note.class));
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        noteArray.clear();
        noteArray.addAll(LitePal.findAll(Note.class));
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.search_item:

                break;
            case R.id.dustbin_item:

                break;
            case R.id.out_item:

                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(navigationView.getVisibility()==View.VISIBLE){
                //当左边的侧滑栏是可见的，则关闭
                drawer.closeDrawer(navigationView);

            }else if(event.getAction()==KeyEvent.ACTION_DOWN){
                System.exit(0);
            }
        }
        return true;
    }

}