package com.fwwb.easynote.Adapters;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fwwb.easynote.R;
import com.fwwb.easynote.models.DustbinNote;

import java.util.*;

import static com.fwwb.easynote.Activitys.DustbinActivity.isSelectMode;

public class DustbinAdapter extends RecyclerView.Adapter<DustbinAdapter.ViewHolder>{
    private List<DustbinNote> noteList;
    Calendar nowCalendar=Calendar.getInstance();
    String date;
    private Map<Integer,Boolean> selectItem=new HashMap<Integer,Boolean>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
        void onMenuOneClick(View view,int position);
        void onMenuSecondClick(View view,int position);
    }

    public void setSelectItem(Map<Integer,Boolean> selectItem){
        this.selectItem=selectItem;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView note;
        TextView date;
        TextView time;
        TextView location;
        ImageView locationImage;
        CheckBox checkBox;
        ConstraintLayout noteLayout;
        ImageView deleteButton;
        ImageView recoveryButton;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.title);
            note=itemView.findViewById(R.id.detail_note);
            date=itemView.findViewById(R.id.detail_date);
            time=itemView.findViewById(R.id.detail_time);
            location=itemView.findViewById(R.id.detail_location);
            locationImage=itemView.findViewById(R.id.image_location);
            checkBox=itemView.findViewById(R.id.checkbox_note);
            noteLayout=itemView.findViewById(R.id.root_layout_note);
            recoveryButton=itemView.findViewById(R.id.first_menubutton);
            deleteButton=itemView.findViewById(R.id.second_menubutton);
        }
    }

    public DustbinAdapter(List<DustbinNote> noteList){
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int i){
        DustbinNote note=noteList.get(i);
        viewHolder.recoveryButton.setImageResource(R.drawable.ic_recovery);

        //设置数据
        if(note.getTitle()==null){
            viewHolder.title.setVisibility(View.GONE);
        }else{
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.title.setText(note.getTitle());
        }
        viewHolder.note.setText(note.getNote());
        viewHolder.note.setMaxLines(2);
        //按照距今时间设置对应日期显示方式
        if(nowCalendar.get(Calendar.YEAR)!=note.getYear()){
            date=note.getYear()+"年"+note.getMonth()+"月"+note.getDay()+"日";
        }else{
            if(nowCalendar.get(Calendar.MONTH)+1==note.getMonth()&&nowCalendar.get(Calendar.DATE)-1==note.getDay()){
                date="昨天";
            }else if(nowCalendar.get(Calendar.MONTH)+1==note.getMonth()&&nowCalendar.get(Calendar.DATE)==note.getDay()){
                date="今天";
            }else{
                date=note.getMonth()+"月"+note.getDay()+"日";
            }
        }
        viewHolder.time.setText(note.getTime());
        viewHolder.date.setText(date);
        if(note.getLocation()==null){
            viewHolder.location.setVisibility(View.GONE);
            viewHolder.locationImage.setVisibility(View.GONE);
        }else{
            viewHolder.location.setVisibility(View.VISIBLE);
            viewHolder.locationImage.setVisibility(View.VISIBLE);
            viewHolder.location.setText(note.getLocation());
        }

        if(selectItem.get(i)!=null){
            viewHolder.checkBox.setChecked(selectItem.get(i));
        }else{
            viewHolder.checkBox.setChecked(false);
        }

        viewHolder.noteLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onItemClickListener.onItemClick(v,i);
            }
        });

        viewHolder.noteLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                onItemClickListener.onItemLongClick(v,i);
                return true;
            }
        });

        viewHolder.recoveryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(onItemClickListener!=null){
                    onItemClickListener.onMenuOneClick(v,i);
                }
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(onItemClickListener!=null){
                    onItemClickListener.onMenuSecondClick(v,i);
                }
            }
        });

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onItemClickListener.onItemClick(v,i);
            }
        });

        if(!isSelectMode){
            viewHolder.checkBox.setVisibility(View.GONE);
        }else{
            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount(){
        return noteList.size();
    }


}
