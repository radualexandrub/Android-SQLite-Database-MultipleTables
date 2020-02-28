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

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Pacienti;
import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Adapter_Pacienti extends RecyclerView.Adapter<Adapter_Pacienti.MyViewHolder> {
    private Context context;
    private List<Pacienti> pacientiList;

    public Adapter_Pacienti(Context context, List<Pacienti> pacientiList) {
        this.context = context;
        this.pacientiList = pacientiList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrow_dot;
        public TextView listrow_cnp;
        public TextView listrow_nume;
        public TextView listrow_prenume;
        public TextView listrow_adresa;
        public TextView listrow_asigurare;

        public MyViewHolder(View view) {
            super(view);
            listrow_dot = view.findViewById(R.id.idrow_pacient_id_dot);
            listrow_cnp = view.findViewById(R.id.idrow_pacient_cnp);
            listrow_nume = view.findViewById(R.id.idrow_pacient_nume);
            listrow_prenume = view.findViewById(R.id.idrow_pacient_prenume);
            listrow_adresa = view.findViewById(R.id.idrow_pacient_adresa);
            listrow_asigurare = view.findViewById(R.id.idrow_pacient_asigurare);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_pacienti, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pacienti pacient = pacientiList.get(position);

        holder.listrow_dot.setText(Integer.toString(pacient.getId()));
        holder.listrow_cnp.setText("CNP: " + pacient.getCnp());
        holder.listrow_nume.setText(pacient.getNume());
        holder.listrow_prenume.setText(pacient.getPrenume());
        holder.listrow_adresa.setText("Adresa: " + pacient.getAdresa());
        holder.listrow_asigurare.setText(pacient.getAsigurare());
    }

    @Override
    public int getItemCount() {
        return pacientiList.size();
    }
}
