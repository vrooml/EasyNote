package com.fwwb.easynote.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;

import java.util.Calendar;

public class NoteDetailActivity extends AppCompatActivity{
    @BindView(R.id.detail_title)
    TextView title;
    @BindView(R.id.detail_note)
    TextView note;
    @BindView(R.id.detail_date)
    TextView date;
    @BindView(R.id.detail_time)
    TextView time;
    @BindView(R.id.detail_location)
    TextView location;
    @BindView(R.id.detail_image_location)
    ImageView locationImage;
    @BindView(R.id.rewrite_button)
    ImageView rewriteButton;
    @BindView(R.id.detail_back_button)
    ImageView backButton;
    private Note noteBean;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);

        //设置数据
        noteBean=(Note)getIntent().getSerializableExtra("note");
        if(getIntent().getSerializableExtra("activity").equals("DustbinActivity")){
            rewriteButton.setVisibility(View.GONE);
        }
        if(noteBean.getTitle()==null){
            title.setVisibility(View.GONE);
        }else{
            title.setText(noteBean.getTitle());
        }
        note.setText(noteBean.getNote());
        Calendar nowCalendar=Calendar.getInstance();
        if(nowCalendar.get(Calendar.YEAR)!=noteBean.getYear()){
            date.setText(noteBean.getYear()+"年"+noteBean.getMonth()+"月"+noteBean.getDay()+"日");
        }else{
            if(nowCalendar.get(Calendar.MONTH)+1==noteBean.getMonth()&&nowCalendar.get(Calendar.DATE)-1==noteBean.getDay()){
                date.setText("昨天");
            }else if(nowCalendar.get(Calendar.MONTH)+1==noteBean.getMonth()&&nowCalendar.get(Calendar.DATE)==noteBean.getDay()){
                date.setText("今天");
            }else{
                date.setText(noteBean.getMonth()+"月"+noteBean.getDay()+"日");
            }
        }
        time.setText(noteBean.getTime());
        if(noteBean.getLocation()==null){
            location.setVisibility(View.GONE);
            locationImage.setVisibility(View.GONE);
        }else{
            location.setText(noteBean.getLocation());
        }

        //设置监听
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        rewriteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(NoteDetailActivity.this,AddNoteActivity.class);
                intent.putExtra("note",noteBean);
                startActivity(intent);
            }
        });
    }
}
