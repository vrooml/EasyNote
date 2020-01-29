package com.fwwb.easynote.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.MyApplication;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;
import org.w3c.dom.Text;

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

    String address=null;

    private final static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);

        // 为目标控件设置TypeFace
        titleEditText.setTypeface(MyApplication.boldSongTypeface);
        noteEditText.setTypeface(MyApplication.songTypeface);
        finishNoteButton.setEnabled(false);

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
                Note note=new Note();
                if(!titleEditText.getText().toString().equals("")){
                    note.setTitle(titleEditText.getText().toString());
                }
                note.setNote(noteEditText.getText().toString());
                if(!addLocationTextView.getText().equals("添加地点")){
                    note.setLocation(address);
                }
                Calendar calendar = Calendar.getInstance();
                note.setCalendar(calendar);

                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("note",note);
                //设置返回数据
                setResult(RESULT_OK, intent);
                //关闭Activity
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!=Activity.RESULT_CANCELED){
            if(requestCode==REQUEST_CODE){
                address=data.getExtras().getString("address");//得到返回的地址
                addLocationTextView.setText(address);
            }
        }
        super.onActivityResult(requestCode,resultCode,data);

    }
}
