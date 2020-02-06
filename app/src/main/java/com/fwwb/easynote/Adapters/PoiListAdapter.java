package com.fwwb.easynote.Adapters;


import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.fwwb.easynote.Activitys.MapActivity;
import com.fwwb.easynote.R;

import java.util.List;

public class PoiListAdapter extends BaseAdapter{
    private List<String> poiInfoList;
    private Context context;

    public PoiListAdapter(List<String> poiInfoList,Context context){
        this.poiInfoList=poiInfoList;
        this.context=context;
    }

    @Override
    public int getCount(){
        return poiInfoList.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(final int position,View convertView,ViewGroup parent){
        Holder holder=null;
        if(convertView==null){
            holder=new Holder();
            convertView=LayoutInflater.from(context).inflate(R.layout.view_poi,null);
            holder.poiName=convertView.findViewById(R.id.poi_name);
            holder.poiLayout=convertView.findViewById(R.id.root_layout_poi);
            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        holder.poiName.setText(poiInfoList.get(position));

        holder.poiLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MapActivity.locationEditText.setText(poiInfoList.get(position));
            }
        });
        return convertView;
    }

    class Holder {
        private TextView poiName;
        private ConstraintLayout poiLayout;
    }

}
