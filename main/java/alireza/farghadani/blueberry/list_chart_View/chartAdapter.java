package alireza.farghadani.blueberry.list_chart_View;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import alireza.farghadani.blueberry.R;

public class chartAdapter extends RecyclerView.Adapter<chartAdapter.ViewHolder> {
    private final List<chartItem> list_chart_item;

    public chartAdapter(List<chartItem> list_chart_item) {
        this.list_chart_item = list_chart_item;
    }

    @Override
    public chartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview_chart,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chartAdapter.ViewHolder holder, int position) {
        chartItem chartItem = list_chart_item.get(position);
        switch (chartItem.getVisible_view()){
            case 1:
                holder.view1.setVisibility(View.VISIBLE);
                holder.view2.setVisibility(View.GONE);
                holder.view3.setVisibility(View.GONE);
                holder.label_view1.setText(chartItem.getLabel());
                holder.Description_view1.setText(chartItem.getDes());
                holder.Point_view1.setText(String.valueOf(position+1));
                break;

            case 2:
                holder.view1.setVisibility(View.GONE);
                holder.view2.setVisibility(View.GONE);
                holder.view3.setVisibility(View.VISIBLE);
                holder.label_view2.setText(chartItem.getLabel());
                LineDataSet linedt = new LineDataSet(chartItem.getPoint_data(),chartItem.getLabel());
                linedt.setColor(Color.parseColor("#2C3440"));
                linedt.setCubicIntensity(1f);
                ArrayList<ILineDataSet> ds = new ArrayList<>();
                ds.add(linedt);
                LineData data = new LineData(ds);
                holder.mplinechart.setData(data);
                holder.mplinechart.invalidate();

                break;

            case 3:
                holder.view1.setVisibility(View.GONE);
                holder.view2.setVisibility(View.VISIBLE);
                holder.view3.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list_chart_item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout view1;
        public ConstraintLayout view2;
        public ConstraintLayout view3;

        public TextView label_view1;
        public TextView Description_view1;
        public TextView Point_view1;

        public TextView label_view2;
        public LineChart mplinechart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view1 = itemView.findViewById(R.id.top_st);
            view2 = itemView.findViewById(R.id.ai_point);
            view3 = itemView.findViewById(R.id.chart_view);

            label_view1 = itemView.findViewById(R.id.title_class);
            Description_view1 = itemView.findViewById(R.id.discreption);
            Point_view1 = itemView.findViewById(R.id.top_num);

            mplinechart =(LineChart) itemView.findViewById(R.id.chartst);
            label_view2 = itemView.findViewById(R.id.title_chart);

        }
    }
}
