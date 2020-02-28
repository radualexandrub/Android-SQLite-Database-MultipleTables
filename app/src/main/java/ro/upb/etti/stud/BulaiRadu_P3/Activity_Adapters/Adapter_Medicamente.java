package ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medicamente;
import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Adapter_Medicamente extends RecyclerView.Adapter<Adapter_Medicamente.MyViewHolder> {
    private Context context;
    private List<Medicamente> medicamenteList;

    public Adapter_Medicamente(Context context, List<Medicamente> medicamenteList) {
        this.context = context;
        this.medicamenteList = medicamenteList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrow_dot;
        public TextView listrow_denumire;

        public MyViewHolder(View view) {
            super(view);
            listrow_dot = view.findViewById(R.id.idrow_medicament_id_dot);
            listrow_denumire = view.findViewById(R.id.idrow_medicament_denumire);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_medicamente, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Medicamente medicament = medicamenteList.get(position);

        // Displaying literally a dot from HTML character code
        //holder.listrow_dot.setText(Html.fromHtml("&#8226;"));
        holder.listrow_dot.setText(Integer.toString(medicament.getId()));
        holder.listrow_denumire.setText(medicament.getDenumire());
    }

    @Override
    public int getItemCount() {
        return medicamenteList.size();
    }
}
