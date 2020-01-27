package com.fwwb.easynote.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.MyApplication;
import com.fwwb.easynote.R;

public class AddNoteActivity extends AppCompatActivity{
    @BindView(R.id.edittext_title)
    EditText titleEditText;
    @BindView(R.id.edittext_note)
    EditText noteEditText;
    @BindView(R.id.textview_add_location)
    TextView addLocationTextView;

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

        addLocationTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(AddNoteActivity.this,MapActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!=Activity.RESULT_CANCELED){
            if(requestCode==REQUEST_CODE){
                address=data.getExtras().getString("address");//得到返回的地址
            }
        }
        super.onActivityResult(requestCode,resultCode,data);

    }
}
