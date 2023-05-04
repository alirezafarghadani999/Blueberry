package alireza.farghadani.blueberry.public_custom_list_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alireza.farghadani.blueberry.R;

public class PublicViewAdapter extends RecyclerView.Adapter<PublicViewAdapter.ViewHolder> {
    private final List<PublicViewItem> public_view_list;
    private final Onlistener onListener;
    private final OnLongListener monLongListener;

    public PublicViewAdapter(List<PublicViewItem> public_view_list,Onlistener onListener,OnLongListener onLongListener1) {
        this.public_view_list = public_view_list;
        this.onListener = onListener;
        this.monLongListener = onLongListener1;
    }

    @NonNull
    @Override
    public PublicViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_menu,parent,false);

        return new ViewHolder(v,onListener,monLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicViewAdapter.ViewHolder holder, int position) {

        PublicViewItem listitem = public_view_list.get(position);

        holder.label.setText(listitem.getLabel());
        holder.Des.setText(listitem.getDescription());

    }

    @Override
    public int getItemCount() {
        return public_view_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{

        public TextView label;
        public TextView Des;
        Onlistener onlistener_holder;
        OnLongListener onLongClickListener_holder;
        public ViewHolder(@NonNull View itemView,Onlistener onlistener,OnLongListener onLongListener) {

            super(itemView);
            label = itemView.findViewById(R.id.title_class);
            Des = itemView.findViewById(R.id.discreption);
            this.onlistener_holder = onlistener;
            this.onLongClickListener_holder = onLongListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListener.onlistener_listview(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            monLongListener.onlonglistener_listview(getAdapterPosition());
            return false;
        }
    }

    public interface Onlistener{
        void onlistener_listview(int pos);
    }

    public interface OnLongListener{
        void onlonglistener_listview(int pos);
    }

}
