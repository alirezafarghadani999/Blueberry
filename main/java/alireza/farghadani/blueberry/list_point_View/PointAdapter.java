package alireza.farghadani.blueberry.list_point_View;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import alireza.farghadani.blueberry.R;
import alireza.farghadani.blueberry.database.databasehelp_class;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> {
    private final List<PointItem> list_point_item;
    databasehelp_class my_class_db;
    Context context;
    public PointAdapter(List<PointItem> list_point_item,Context context) {
        this.list_point_item = list_point_item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_set_point,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PointItem listpoint = list_point_item.get(position);

        holder.name.setText(listpoint.getName() +" "+ listpoint.getFamily());
        float point = get_point(listpoint.getPoint_data(),listpoint.getTopic_id());
        holder.point.setText(String.valueOf(point));
        holder.point.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                holder.point.selectAll(); }});
        


        holder.point.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (!holder.point.getText().toString().isEmpty() && !holder.point.getText().toString().equals("-1.0") && !holder.point.getText().toString().equals("-1.")){
                    set_point(listpoint.getPoint_data(),listpoint.getTopic_id(),holder.point.getText().toString(),listpoint.getSt_ID());
                }else {
                    remove_point(listpoint.getPoint_data(),listpoint.getTopic_id(),listpoint.getSt_ID());
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_point_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name ;
        public EditText point;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title_class);
            point = itemView.findViewById(R.id.point);

        }

    }


    public float get_point(String point_data,int topic_id){
        try {
            JSONObject jsonObject = new JSONObject(point_data);
            if (!jsonObject.isNull(String.valueOf(topic_id))){
                return Float.parseFloat(jsonObject.getString(String.valueOf(topic_id)));
            }else {
                return -1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void set_point(String point_data,int topic_id,String point,int st_id){
        try {
            JSONObject jsonObject = new JSONObject(point_data);
            jsonObject.put(String.valueOf(topic_id),point);
            my_class_db = new databasehelp_class(context);
            my_class_db.update_student_db(st_id,"","",jsonObject.toString(),"",null);
            my_class_db.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public void remove_point(String point_data,int topic_id,int st_id){
        try {
            JSONObject jsonObject = new JSONObject(point_data);
            jsonObject.remove(String.valueOf(topic_id));
            my_class_db = new databasehelp_class(context);
            my_class_db.update_student_db(st_id,"","",jsonObject.toString(),"",null);
            my_class_db.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
