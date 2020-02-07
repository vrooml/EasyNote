package com.fwwb.easynote.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.MyApplication;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;
import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity{
    @BindView(R.id.edittext_title)
    EditText titleEditText;
    @BindView(R.id.edittext_note)
    EditText noteEditText;
    @BindView(R.id.textview_add_location)
    TextView addLocationTextView;
    @BindView(R.id.button_finish_note)
    Button finishNoteButton;
    @BindView(R.id.add_back_button)
    ImageView backButton;
    private String address=null;
    private Note originNote=null;

    private final static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        //若为修改入口
        if(getIntent().getSerializableExtra("note")!=null){
            originNote=(Note)getIntent().getSerializableExtra("note");
            if(originNote.getTitle()!=null){
                titleEditText.setText(originNote.getTitle());
            }
            noteEditText.setText(originNote.getNote());
            if(originNote.getLocation()!=null){
                addLocationTextView.setText(originNote.getLocation());
            }
        }


        // 为目标控件设置TypeFace
        titleEditText.setTypeface(MyApplication.boldSongTypeface);
        noteEditText.setTypeface(MyApplication.songTypeface);
        finishNoteButton.setEnabled(false);

        //设置toolbar监听器
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        //设置编辑栏监听
        noteEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s,int start,int count,int after){

            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count){

            }

            @Override
            public void afterTextChanged(Editable s){
                if(noteEditText.getText().toString().equals("")){
                    finishNoteButton.setEnabled(false);
                }else{
                    finishNoteButton.setEnabled(true);
                }
            }
        });


        //设置按键监听
        addLocationTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(AddNoteActivity.this,MapActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        finishNoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getIntent().getSerializableExtra("note")!=null){
                    originNote=LitePal.find(Note.class,originNote.getId());
                    int isDelete=originNote.delete();
                    Toast.makeText(getApplicationContext(),"删除状态"+isDelete,Toast.LENGTH_SHORT).show();
                }
                Note note=new Note();
                if(!titleEditText.getText().toString().equals("")){
                    note.setTitle(titleEditText.getText().toString());
                }
                note.setNote(noteEditText.getText().toString());
                if(!addLocationTextView.getText().equals("添加地点")){
                    note.setLocation(address);
                }
                Calendar calendar=Calendar.getInstance();
                note.setYear(calendar.get(Calendar.YEAR));
                note.setMonth(calendar.get(Calendar.MONTH+1));
                note.setDay(calendar.get(Calendar.DATE));
                SimpleDateFormat df=new SimpleDateFormat("HH:mm");
                note.setTime(df.format(calendar.getTime()));

                boolean isSave=note.save();
//                if (isSave) {
//                    Toast.makeText(getApplicationContext(), "成功保存", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "失败啦", Toast.LENGTH_SHORT).show();
//                }
                //关闭Activity
                Intent intent=new Intent(AddNoteActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!=Activity.RESULT_CANCELED){
            if(requestCode==REQUEST_CODE){
                address=data.getStringExtra("address");//得到返回的地址
                addLocationTextView.setText(address);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);

    }
}
