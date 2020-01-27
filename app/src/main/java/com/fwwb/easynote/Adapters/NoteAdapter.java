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

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> noteList;

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
        viewHolder.time.setText(note.getTime());
        viewHolder.date.setText(note.getDate());
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
