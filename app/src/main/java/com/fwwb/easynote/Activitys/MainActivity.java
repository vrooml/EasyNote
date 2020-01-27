package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.Adapters.NoteAdapter;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.recyclerview_note)
    RecyclerView noteRecyclerView;
    @BindView(R.id.add_button)
    Button addButton;
    @BindView(R.id.menu_button)
    Button menuButton;
    private List<Note> noteArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        noteRecyclerView.setLayoutManager(linearLayoutManager);

        Note note=new Note(null,"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","今天","15：57",null);
        noteArray.add(note);
        noteArray.add(new Note("标题","测试","今天","15：57","自贡"));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        noteArray.add(new Note(null,"测试","今天","15：57",null));
        NoteAdapter noteAdapter=new NoteAdapter(noteArray);
        noteRecyclerView.setAdapter(noteAdapter);

        //设置toolbar监听器
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

}
