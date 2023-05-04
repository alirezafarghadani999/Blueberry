package alireza.farghadani.blueberry.list_class_View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alireza.farghadani.blueberry.R;


public class classsAdapter extends RecyclerView.Adapter<classsAdapter.ViewHolder> {
    private final List<classsItem> list_classs_item;
    private final OnClassListener monClassListener;
    private final OnclickClassListener monClickClassListener;

    public classsAdapter(List<classsItem> list_classs_item, OnClassListener onClassListener, OnclickClassListener onclickClassListener) {
        this.list_classs_item = list_classs_item;
        this.monClassListener = onClassListener;
        this.monClickClassListener = onclickClassListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_menu,parent,false);

        return new ViewHolder(v,monClassListener,monClickClassListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        classsItem listitem = list_classs_item.get(position);

        holder.title.setText(listitem.getTitle());
        holder.count_student.setText("درس : "+listitem.getLesson());


    }

    @Override
    public int getItemCount() {
        return list_classs_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener , View.OnClickListener {
        public TextView title;
        public TextView count_student;
        public ConstraintLayout constraintlayout ;
        OnClassListener onClassListener;
        OnclickClassListener onclickClassListener;
        public ViewHolder(@NonNull View itemView,OnClassListener onClassListener, OnclickClassListener onclickClassListener) {
            super(itemView);

            title = itemView.findViewById(R.id.title_class);
            count_student = itemView.findViewById(R.id.discreption);
            constraintlayout = itemView.findViewById(R.id.class_list_item);
            this.onClassListener = onClassListener;
            this.onclickClassListener =onclickClassListener;

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onClassListener.onclasslongclick_list(getAdapterPosition());
            return false;
        }

        @Override
        public void onClick(View view) {
            onclickClassListener.onclicklistener_list_class(getAdapterPosition());
        }
    }

    public interface OnClassListener{
        void onclasslongclick_list(int position);
    }

    public interface OnclickClassListener{
        void onclicklistener_list_class(int position);
    }




}
