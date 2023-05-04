package alireza.farghadani.blueberry.list_presentation_View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import alireza.farghadani.blueberry.R;
import alireza.farghadani.blueberry.database.databasehelp_class;

public class PresentationAdapter extends RecyclerView.Adapter<PresentationAdapter.ViewHolder> {
    List<PresentationItem> p_item;
    databasehelp_class my_class_db;
    Context context;
    select_date selectDate;

    public PresentationAdapter(List<PresentationItem> p_item, Context context, select_date select_date) {
        this.p_item = p_item;
        this.context = context;
        this.selectDate = select_date;
    }


    @Override
    public PresentationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_presentation, parent, false);
        return new ViewHolder(v,selectDate);
    }

    @Override
    public void onBindViewHolder(@NonNull PresentationAdapter.ViewHolder holder, int position)  {

        PresentationItem listitem = p_item.get(position);
        switch (listitem.getView()) {
            case 1:
                holder.v2.setVisibility(View.GONE);
                holder.year.setText(String.valueOf(listitem.getYear()));
                holder.month.setText(String.valueOf(listitem.getMonth()));
                holder.day.setText(String.valueOf(listitem.getDay()));
                break;

            case 2:
                holder.v1.setVisibility(View.GONE);
                holder.student_name.setText(listitem.getStudent_name());
                holder.present_check.setChecked(listitem.getPresent());
                Boolean checked = get_student_presentation(listitem.getPeresent_data(), listitem.getYear(), listitem.getMonth(), listitem.getDay());
                holder.present_check.setChecked(checked);
                holder.present_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Log.d("check", String.valueOf(b));
                        String json = set_student_presentation(listitem.getPeresent_data(), listitem.getYear(), listitem.getMonth(), listitem.getDay(), b);
                        my_class_db = new databasehelp_class(context);
                        my_class_db.update_student_db(listitem.getID(), "", "", "", json, null);
                        my_class_db.close();
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return p_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ConstraintLayout v1;
        public ConstraintLayout v2;

        public TextView year;
        public TextView month;
        public TextView day;

        public TextView student_name;
        public CheckBox present_check;

        select_date selectDate;
        public ViewHolder(@NonNull View itemView ,select_date select_date) {
            super(itemView);

            this.selectDate = select_date;
            v1 = itemView.findViewById(R.id.date_view);
            v2 = itemView.findViewById(R.id.student_view);

            year = itemView.findViewById(R.id.year);
            month = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);

            student_name = itemView.findViewById(R.id.student_name);
            present_check = itemView.findViewById(R.id.student_checkbox);

            v1.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            selectDate.date_selecor_click(getAdapterPosition());
        }
    }

    public interface select_date{
        void date_selecor_click(int pos);
    }

    public Boolean get_student_presentation(String present_data, int year, int month, int day) {


        try {
            // create a json object from the string
            JSONObject jsonObject = new JSONObject(present_data);
            if (!jsonObject.isNull(String.valueOf(year) + month + day)) {
                return jsonObject.getBoolean(String.valueOf(year) + month + day);
            }
            // print the properties

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;


    }

    public String set_student_presentation(String present_data, int year, int month, int day, Boolean check) {


        try {
            // create a json object from the string
            JSONObject jsonObject = new JSONObject(present_data);
            if (check){
                jsonObject.put(String.valueOf(year) + month + day, String.valueOf(check));
            }else {
                jsonObject.remove(String.valueOf(year) + month + day);
            }
            return jsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return present_data;
    }
}
