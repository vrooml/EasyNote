package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.DustbinAdapter;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.DustbinNote;
import com.fwwb.easynote.models.Note;
import com.yanzhenjie.recyclerview.*;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class DustbinActivity extends AppCompatActivity{
    @BindView(R.id.recyclerview_dustbin)
    SwipeRecyclerView dustbinRecyclerView;
    @BindView(R.id.dust_back_button)
    Button backButton;
    DustbinAdapter dustbinAdapter;
    private List<DustbinNote> dustbinArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin);
        ButterKnife.bind(this);

        //设置toolbar监听器
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //swipeRecyclerview设置
        dustbinRecyclerView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(View view,int adapterPosition){
                startActivity(new Intent(DustbinActivity.this,NoteDetailActivity.class));
            }
        });
        SwipeMenuCreator swipeMenuCreator=new SwipeMenuCreator(){
            @Override
            public void onCreateMenu(SwipeMenu leftMenu,SwipeMenu rightMenu,int position){
                Resources res = DustbinActivity.this.getResources();
                Bitmap oldBmp = BitmapFactory.decodeResource(res,R.drawable.ic_delete);
                Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp,55,55, true);
                Drawable drawable = new BitmapDrawable(res, newBmp);
                SwipeMenuItem deleteItem=new SwipeMenuItem(DustbinActivity.this)
                        .setBackground(null)
                        .setImage(drawable)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                        .setWidth(150);
                rightMenu.addMenuItem(deleteItem);
            }
        };
        dustbinRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        dustbinRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener(){
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge,int adapterPosition){
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==0){
                    DustbinNote dustbinNote=dustbinArray.get(adapterPosition);
                    dustbinNote.delete();
                    Note note=new Note(dustbinNote);
                    note.save();
                    dustbinArray.remove(adapterPosition);
                    dustbinAdapter.notifyDataSetChanged();
                }
                if(menuBridge.getDirection()==SwipeRecyclerView.RIGHT_DIRECTION&&menuBridge.getPosition()==1){
                    //TODO 弹窗
                    DustbinNote note=dustbinArray.get(adapterPosition);
                    note.delete();
                    dustbinArray.remove(adapterPosition);
                    dustbinAdapter.notifyDataSetChanged();
                }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        dustbinRecyclerView.setLayoutManager(linearLayoutManager);
        dustbinAdapter=new DustbinAdapter(dustbinArray);
        dustbinRecyclerView.setAdapter(dustbinAdapter);


        //装载数据
        dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
        dustbinAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        dustbinArray.clear();
        dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
        dustbinAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        dustbinArray.clear();
        dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
        dustbinAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(event.getAction()==KeyEvent.ACTION_DOWN){
                finish();
            }
        }
        return true;
    }
}
