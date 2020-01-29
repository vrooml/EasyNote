package com.fwwb.easynote.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.Note;

import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> noteList;
    Calendar nowCalendar=Calendar.getInstance();
    String time;
    String date;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView note;
        TextView date;
        TextView time;
        TextView location;
        ImageView locationImage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            note=(TextView)itemView.findViewById(R.id.note);
            date=(TextView)itemView.findViewById(R.id.date);
            time=(TextView)itemView.findViewById(R.id.time);
            location=(TextView)itemView.findViewById(R.id.location);
            locationImage=(ImageView)itemView.findViewById(R.id.image_location);
        }
    }

    public NoteAdapter(List<Note> noteList){
        this.noteList=noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_note,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i){
        Note note=noteList.get(i);
        if(note.getTitle()==null){
            viewHolder.title.setVisibility(View.GONE);
        }else{
            viewHolder.title.setText(note.getTitle());
        }
        viewHolder.note.setText(note.getNote());
        time=String.valueOf(note.getCalendar().get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(note.getCalendar().get(Calendar.MINUTE));
        //按照距今时间设置对应日期显示方式
        if(nowCalendar.get(Calendar.YEAR)!=note.getCalendar().get(Calendar.YEAR)){
            date=String.valueOf(note.getCalendar().get(Calendar.YEAR))+"年"
                    +String.valueOf(note.getCalendar().get(Calendar.MONTH)+1)+"月"
                    +String.valueOf(note.getCalendar().get(Calendar.DATE))+"日";
        }else{
            if(nowCalendar.get(Calendar.DAY_OF_YEAR)==note.getCalendar().get(Calendar.YEAR)+1){
                date="昨天";
            }else if(nowCalendar.get(Calendar.DAY_OF_YEAR)==note.getCalendar().get(Calendar.YEAR)){
                date="今天";
            }else{
                date=String.valueOf(note.getCalendar().get(Calendar.MONTH)+1)+"月"
                        +String.valueOf(note.getCalendar().get(Calendar.DATE))+"日";
            }
        }

        viewHolder.time.setText(time);
        viewHolder.date.setText(date);
        if(note.getLocation()==null){
            viewHolder.location.setVisibility(View.GONE);
            viewHolder.locationImage.setVisibility(View.GONE);
        }else{
            viewHolder.location.setText(note.getLocation());
        }
    }
    @Override
    public int getItemCount(){
        return noteList.size();
    }



}
