package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.DustbinNote;
import com.fwwb.easynote.models.Note;
import com.yanzhenjie.recyclerview.*;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{
    @BindView(R.id.search_empty_image)
    ImageView emptyImage;
    @BindView(R.id.search_back_button)
    Button backButton;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.search_edittext)
    EditText searchEditText;
    @BindView(R.id.recyclerview_search)
    SwipeRecyclerView searchRecyclerView;
    NoteAdapter searchAdapter;
    private List<Note> searchArray=new ArrayList<>();
    private static String calanderEventURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        //设置toolbar监听器
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String searchText=searchEditText.getText().toString().trim();
                searchArray.clear();
                searchArray.addAll(LitePal
                        .where("title like ? or note like ? or location like ?","%"+searchText+"%","%"+searchText+"%","%"+searchText+"%")
                        .find(Note.class));
                Toast.makeText(SearchActivity.this,"搜索到"+searchArray.size()+"条数据",Toast.LENGTH_SHORT).show();
                if(searchArray.size()!=0){
                    emptyImage.setVisibility(View.GONE);
                }else{
                    emptyImage.setVisibility(View.VISIBLE);
                }
                searchAdapter.notifyDataSetChanged();
            }
        });

        //swipeRecyclerview设置
        searchRecyclerView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(View view,int adapterPosition){
                Intent intent=new Intent(SearchActivity.this,NoteDetailActivity.class);
                intent.putExtra("note",searchArray.get(adapterPosition));
                intent.putExtra("position",adapterPosition);
                intent.putExtra("activity","SearchActivity");
                startActivity(intent);
            }
        });
        SwipeMenuCreator swipeMenuCreator=new SwipeMenuCreator(){
            @Override
            public void onCreateMenu(SwipeMenu leftMenu,SwipeMenu rightMenu,int position){
                Resources res=SearchActivity.this.getResources();
                Bitmap oldBmp=BitmapFactory.decodeResource(res,R.drawable.ic_calendar);
                Bitmap newBmp=Bitmap.createScaledBitmap(oldBmp,55,55,true);
                Drawable drawable=new BitmapDrawable(res,newBmp);
                SwipeMenuItem calendarItem=new SwipeMenuItem(SearchActivity.this)
                        .setBackground(null)
                        .setImage(drawable)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(150);
                oldBmp=BitmapFactory.decodeResource(res,R.drawable.ic_delete);
                newBmp=Bitmap.createScaledBitmap(oldBmp,55,55,true);
                drawable=new BitmapDrawable(res,newBmp);
                SwipeMenuItem deleteItem=new SwipeMenuItem(SearchActivity.this)
                        .setBackground(null)
                        .setImage(drawable)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(150);
                rightMenu.addMenuItem(calendarItem);
                rightMenu.addMenuItem(deleteItem);
            }
        };
        searchRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        searchRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener(){
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge,int adapterPosition){
                menuBridge.closeMenu();
                int direction=menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int menuPosition=menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==0){
                    Intent intent=new Intent(Intent.ACTION_INSERT)
                            .setData(Uri.parse(calanderEventURL))
                            .putExtra("title",searchArray.get(adapterPosition).getTitle())
                            .putExtra("description",searchArray.get(adapterPosition).getNote());
                    startActivity(intent);
                }else if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==1){
                    Note note=searchArray.get(adapterPosition);
                    note.delete();
                    DustbinNote dustbinNote=new DustbinNote(note);
                    dustbinNote.save();
                    searchArray.remove(adapterPosition);
                    if(searchArray.size()!=0){
                        emptyImage.setVisibility(View.GONE);
                    }else{
                        emptyImage.setVisibility(View.VISIBLE);
                    }
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchAdapter=new NoteAdapter(searchArray);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    static {
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            calanderEventURL = "content://com.android.calendar/events";
        } else {
            calanderEventURL = "content://calendar/events";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }
}
