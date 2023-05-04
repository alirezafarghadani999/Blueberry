package alireza.farghadani.blueberry.list_profile_View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alireza.farghadani.blueberry.R;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.ViewHolder> {

    private final List<profileItem> list_profile_item;
    private final Context contex;

    public profileAdapter(List<profileItem> list_profile_item, Context contex) {
        this.list_profile_item = list_profile_item;
        this.contex = contex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_menu,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        profileItem listitem = list_profile_item.get(position);

        holder.title.setText(listitem.getTitle());
        holder.dis.setText(listitem.getDiscription());

    }

    @Override
    public int getItemCount() {
        return list_profile_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView dis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_class);
            dis = itemView.findViewById(R.id.discreption);
        }
    }
}
