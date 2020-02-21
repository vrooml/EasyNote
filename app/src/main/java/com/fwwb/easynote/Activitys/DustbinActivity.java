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
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.DustbinAdapter;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.MyApplication;
import com.fwwb.easynote.R;
import com.fwwb.easynote.Utils.MySlideRecyclerView;
import com.fwwb.easynote.models.DustbinNote;
import com.fwwb.easynote.models.Note;
import com.yanzhenjie.recyclerview.*;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DustbinActivity extends AppCompatActivity{
    @BindView(R.id.dustbin_empty_image)
    ImageView emptyImage;
    @BindView(R.id.recyclerview_dustbin)
    MySlideRecyclerView dustbinRecyclerView;
    static ImageView backButton;
    DustbinAdapter dustbinAdapter;
    private List<DustbinNote> dustbinArray=new ArrayList<>();
    static TextView allSelectText;
    static ImageView recoveryButton;
    static ImageView deleteButton;
    private Map<Integer,Boolean> selectItem=new HashMap<Integer,Boolean>();
    public static boolean isSelectMode=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin);
        ButterKnife.bind(this);

        backButton=findViewById(R.id.dust_back_button);
        recoveryButton=findViewById(R.id.recov_button);
        deleteButton=findViewById(R.id.delete_button);
        allSelectText=findViewById(R.id.all_select_text);
        recoveryButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        allSelectText.setVisibility(View.GONE);


        //设置toolbar监听器
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        allSelectText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(allSelectText.getText().equals("全选")){
                    allSelectText.setText("全不选");
                    selectItem.clear();
                    for(int i=0;i<dustbinArray.size();i++){
                        selectItem.put(i,true);
                    }
                }else if(allSelectText.getText().equals("全不选")){
                    allSelectText.setText("全选");
                    selectItem.clear();
                }
                dustbinAdapter.setSelectItem(selectItem);
                dustbinAdapter.notifyDataSetChanged();
            }
        });
        recoveryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<Integer> recovList=getSelectedItem();
                for(int i: recovList){
                    DustbinNote dustbinNote=dustbinArray.get(i);
                    dustbinNote.delete();
                    Note note=new Note(dustbinNote);
                    note.save();
                }
                dustbinArray.clear();
                dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
                if(dustbinArray.size()!=0){
                    emptyImage.setVisibility(View.GONE);
                }else{
                    emptyImage.setVisibility(View.VISIBLE);
                }
                dustbinAdapter.notifyDataSetChanged();
                isSelectMode=false;
                initSelectMode(isSelectMode);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<Integer> deleteList=getSelectedItem();
                for(int i: deleteList){
                    DustbinNote dustbinNote=dustbinArray.get(i);
                    dustbinNote.delete();
                }
                dustbinArray.clear();
                dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
                if(dustbinArray.size()!=0){
                    emptyImage.setVisibility(View.GONE);
                }else{
                    emptyImage.setVisibility(View.VISIBLE);
                }
                dustbinAdapter.notifyDataSetChanged();
                isSelectMode=false;
                initSelectMode(isSelectMode);
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        dustbinRecyclerView.setLayoutManager(linearLayoutManager);
        dustbinAdapter=new DustbinAdapter(dustbinArray);
        dustbinAdapter.setOnItemClickListener(new DustbinAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view,int position){
                if(isSelectMode){
                    // 如果当前处于多选状态，则进入多选状态的逻辑
                    // 维护当前已选的position
                    addOrRemove(position);
                    dustbinAdapter.setSelectItem(selectItem);
                    dustbinAdapter.notifyDataSetChanged();
                }else{
                    // 如果不是多选状态，则进入单选事件的业务逻辑
                    Intent intent=new Intent(DustbinActivity.this,NoteDetailActivity.class);
                    intent.putExtra("activity","DustbinActivity");
                    intent.putExtra("position",position);
                    intent.putExtra("note",new Note(dustbinArray.get(position)));
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view,int position){
                if(!isSelectMode){
                    isSelectMode=true;
                    selectItem.clear();
                    dustbinAdapter.notifyDataSetChanged();
                    initSelectMode(isSelectMode);
                }
            }

            @Override
            public void onMenuOneClick(View view,int position){
                DustbinNote dustbinNote=dustbinArray.get(position);
                dustbinNote.delete();
                Note note=new Note(dustbinNote);
                note.save();
                dustbinArray.remove(position);
                if(dustbinArray.size()!=0){
                    emptyImage.setVisibility(View.GONE);
                }else{
                    emptyImage.setVisibility(View.VISIBLE);
                }
                dustbinAdapter.notifyDataSetChanged();
            }

            @Override
            public void onMenuSecondClick(View view,int position){
                //TODO 弹窗
                DustbinNote note=dustbinArray.get(position);
                note.delete();
                dustbinArray.remove(position);
                if(dustbinArray.size()!=0){
                    emptyImage.setVisibility(View.GONE);
                }else{
                    emptyImage.setVisibility(View.VISIBLE);
                }
                dustbinAdapter.notifyDataSetChanged();
            }
        });

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
        if(dustbinArray.size()!=0){
            emptyImage.setVisibility(View.GONE);
        }else{
            emptyImage.setVisibility(View.VISIBLE);
        }
        dustbinAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        dustbinArray.clear();
        dustbinArray.addAll(LitePal.findAll(DustbinNote.class));
        if(dustbinArray.size()!=0){
            emptyImage.setVisibility(View.GONE);
        }else{
            emptyImage.setVisibility(View.VISIBLE);
        }
        dustbinAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(event.getAction()==KeyEvent.ACTION_DOWN){
                if(!isSelectMode){
                    finish();
                }else{
                    isSelectMode=false;
                    initSelectMode(isSelectMode);
                    dustbinAdapter.notifyDataSetChanged();
                }
            }
        }
        return true;
    }

    private void addOrRemove(int position){
        if(isItemSelected(position)){
            setItemSelected(position,false);
        }else{
            setItemSelected(position,true);
        }
    }


    public static void initSelectMode(boolean selectMode){
        if(selectMode){
            allSelectText.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            recoveryButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.GONE);
        }else{
            allSelectText.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            recoveryButton.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean isItemSelected(int position){
        if(selectItem.get(position)==null){
            return false;
        }else{
            return selectItem.get(position);
        }
    }

    public ArrayList<Integer> getSelectedItem(){
        ArrayList<Integer> selectList=new ArrayList<>();
        for(int i=0;i<dustbinArray.size();i++){
            if(isItemSelected(i)){
                selectList.add(i);
            }
        }
        return selectList;
    }

    private void setItemSelected(int position,boolean isChecked){
        selectItem.put(position,isChecked);
    }
}
