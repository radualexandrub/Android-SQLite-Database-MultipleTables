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

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medici;
import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Adapter_Medici extends RecyclerView.Adapter<Adapter_Medici.MyViewHolder> {

    private Context context;
    private List<Medici> mediciList;

    public Adapter_Medici(Context context, List<Medici> mediciList) {
        this.context = context;
        this.mediciList = mediciList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrow_dot;
        public TextView listrow_specializare;
        public TextView listrow_nume;
        public TextView listrow_prenume;

        public MyViewHolder(View view) {
            super(view);
            listrow_dot = view.findViewById(R.id.idrow_medic_id_dot);
            listrow_specializare = view.findViewById(R.id.idrow_medic_specializare);
            listrow_nume = view.findViewById(R.id.idrow_medic_nume);
            listrow_prenume = view.findViewById(R.id.idrow_medic_prenume);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_medici, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Medici medic = mediciList.get(position);

        // Displaying literally a dot from HTML character code
        //holder.listrow_dot.setText(Html.fromHtml("&#8226;"));
        holder.listrow_dot.setText(Integer.toString(medic.getId()));
        holder.listrow_nume.setText(medic.getNume());
        holder.listrow_prenume.setText(medic.getPrenume());
        holder.listrow_specializare.setText(medic.getSpecializare());
    }

    @Override
    public int getItemCount() {
        return mediciList.size();
    }
}
