package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.DustbinNote;
import com.fwwb.easynote.models.Note;
import com.yanzhenjie.recyclerview.*;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;
import org.litepal.LitePal;

import java.util.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.main_empty_image)
    ImageView emptyImage;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview_note)
    SwipeRecyclerView noteRecyclerView;
    @BindView(R.id.add_button)
    ImageView addButton;
    @BindView(R.id.menu_button)
    ImageView menuButton;
    @BindView(R.id.sort_button)
    ImageView sortButton;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    NoteAdapter noteAdapter;
    private static String calanderEventURL=null;
    private List<Note> noteArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        sortButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showSortMenu();
            }
        });


        //设置菜单栏按键
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        //swipeRecyclerview设置
        noteRecyclerView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(View view,int adapterPosition){
                Intent intent=new Intent(MainActivity.this,NoteDetailActivity.class);
                intent.putExtra("note",noteArray.get(adapterPosition));
                intent.putExtra("position",adapterPosition);
                intent.putExtra("activity","MainActivity");
                startActivity(intent);
            }
        });
        SwipeMenuCreator swipeMenuCreator=new SwipeMenuCreator(){
            @Override
            public void onCreateMenu(SwipeMenu leftMenu,SwipeMenu rightMenu,int position){
                Resources res=MainActivity.this.getResources();
                Bitmap oldBmp=BitmapFactory.decodeResource(res,R.drawable.ic_calendar);
                Bitmap newBmp=Bitmap.createScaledBitmap(oldBmp,55,55,true);
                Drawable drawable=new BitmapDrawable(res,newBmp);
                SwipeMenuItem calendarItem=new SwipeMenuItem(MainActivity.this)
                        .setBackground(null)
                        .setImage(drawable)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(150);
                oldBmp=BitmapFactory.decodeResource(res,R.drawable.ic_delete);
                newBmp=Bitmap.createScaledBitmap(oldBmp,55,55,true);
                drawable=new BitmapDrawable(res,newBmp);
                SwipeMenuItem deleteItem=new SwipeMenuItem(MainActivity.this)
                        .setBackground(null)
                        .setImage(drawable)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(150);
                rightMenu.addMenuItem(calendarItem);
                rightMenu.addMenuItem(deleteItem);
            }
        };
        noteRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        noteRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener(){
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge,int adapterPosition){
                menuBridge.closeMenu();
                int direction=menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int menuPosition=menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==0){
                    Intent intent=new Intent(Intent.ACTION_INSERT)
                            .setData(Uri.parse(calanderEventURL))
                            .putExtra("title",noteArray.get(adapterPosition).getTitle())
                            .putExtra("description",noteArray.get(adapterPosition).getNote());
                    startActivity(intent);
                }else if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==1){
                    Note note=noteArray.get(adapterPosition);
                    note.delete();
                    DustbinNote dustbinNote=new DustbinNote(note);
                    dustbinNote.save();
                    noteArray.remove(adapterPosition);
                    if(noteArray.size()!=0){
                        emptyImage.setVisibility(View.GONE);
                    }else{
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    noteAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(linearLayoutManager);
        noteAdapter=new NoteAdapter(noteArray);
        noteRecyclerView.setAdapter(noteAdapter);


        //装载数据
        noteArray.addAll(LitePal.findAll(Note.class));
        if(noteArray.size()!=0){
            emptyImage.setVisibility(View.GONE);
        }else{
            emptyImage.setVisibility(View.VISIBLE);
        }
        noteAdapter.notifyDataSetChanged();
    }

    private void showSortMenu(){
        final PopupMenu popupMenu;
        popupMenu=new PopupMenu(this,sortButton);
        getMenuInflater().inflate(R.menu.sort_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem){
                switch(menuItem.getItemId()){
                    case R.id.one:
                        noteArray.clear();
                        noteArray.addAll(LitePal.findAll(Note.class));
                        noteAdapter.notifyDataSetChanged();
                        popupMenu.dismiss();
                        break;
                    case R.id.two:
                        Collections.sort(noteArray,new Comparator<Note>(){
                            @Override
                            public int compare(Note o1,Note o2){
                                if(o1.getYear()>o2.getYear()){
                                    return 1;
                                }else if(o1.getYear()==o2.getYear()){
                                    if(o1.getMonth()>o2.getMonth()){
                                        return 1;
                                    }else if(o1.getMonth()==o2.getMonth()){
                                        if(o1.getDay() >= o2.getDay()){
                                            return 1;
                                        }else{
                                            return -1;
                                        }
                                    }else{
                                        return -1;
                                    }
                                }else{
                                    return -1;
                                }
                            }
                        });
                        noteAdapter.notifyDataSetChanged();
                        popupMenu.dismiss();
                        break;
                    case R.id.three:
                        Collections.sort(noteArray,new Comparator<Note>(){
                            @Override
                            public int compare(Note o1,Note o2){
                                if(o1.getYear()<o2.getYear()){
                                    return 1;
                                }else if(o1.getYear()==o2.getYear()){
                                    if(o1.getMonth()<o2.getMonth()){
                                        return 1;
                                    }else if(o1.getMonth()==o2.getMonth()){
                                        if(o1.getDay()<o2.getDay()){
                                            return 1;
                                        }else{
                                            return -1;
                                        }
                                    }else{
                                        return -1;
                                    }
                                }else{
                                    return -1;
                                }
                            }
                        });
                        if(noteArray.size()!=0){
                            emptyImage.setVisibility(View.GONE);
                        }else{
                            emptyImage.setVisibility(View.VISIBLE);
                        }
                        noteAdapter.notifyDataSetChanged();
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        noteArray.clear();
        noteArray.addAll(LitePal.findAll(Note.class));
        if(noteArray.size()!=0){
            emptyImage.setVisibility(View.GONE);
        }else{
            emptyImage.setVisibility(View.VISIBLE);
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        Intent intent;
        switch(menuItem.getItemId()){
            case R.id.search_item:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
            case R.id.dustbin_item:
                startActivity(new Intent(MainActivity.this,DustbinActivity.class));
                break;
            case R.id.out_item:
                System.exit(0);
                break;
        }
        return false;
    }

    static{
        if(Integer.parseInt(Build.VERSION.SDK) >= 8){
            calanderEventURL="content://com.android.calendar/events";
        }else{
            calanderEventURL="content://calendar/events";
        }
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
