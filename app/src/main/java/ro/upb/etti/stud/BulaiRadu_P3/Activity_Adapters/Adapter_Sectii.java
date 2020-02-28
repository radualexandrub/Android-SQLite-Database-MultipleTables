package ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Sectii;
import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Adapter_Sectii extends RecyclerView.Adapter<Adapter_Sectii.MyViewHolder> {
    private Context context;
    private List<Sectii> sectiiList;

    public Adapter_Sectii(Context context, List<Sectii> sectiiList) {
        this.context = context;
        this.sectiiList = sectiiList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrow_dot;
        public TextView listrow_idpacient;
        public TextView listrow_idmedic;
        public TextView listrow_nume;
        public TextView listrow_buget;

        public TextView listrow_afisare_pacient;
        public TextView listrow_afisare_medic;


        public MyViewHolder(View view) {
            super(view);
            listrow_dot = view.findViewById(R.id.idrow_sectie_id_dot);
            listrow_idpacient = view.findViewById(R.id.idrow_sectie_id_pacient);
            listrow_idmedic = view.findViewById(R.id.idrow_sectie_id_medic);
            listrow_nume = view.findViewById(R.id.idrow_sectie_nume);
            listrow_buget = view.findViewById(R.id.idrow_sectie_buget);

            listrow_afisare_pacient = view.findViewById(R.id.idrow_sectie_afisare_pacient);
            listrow_afisare_medic = view.findViewById(R.id.idrow_sectie_afisare_medic);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_sectii, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sectii sectie = sectiiList.get(position);

        // Afisare date(id, nume, buget) introduse pentru fiecare sectie in Tabela Sectii (sectiiList)
        holder.listrow_dot.setText(Integer.toString(sectie.getIdSectie()));
        holder.listrow_idpacient.setText(Integer.toString(sectie.getIdPacient()));
        holder.listrow_idmedic.setText(Integer.toString(sectie.getIdMedic()));
        holder.listrow_nume.setText("NumeSectie: " + sectie.getNume());
        holder.listrow_buget.setText("Buget: " + Float.toString(sectie.getBuget()));

        holder.listrow_afisare_medic.setText("Medic alocat: Dr. " + sectie.getNumeMedic() + " " + sectie.getPrenumeMedic());
        holder.listrow_afisare_pacient.setText("Pacient alocat: " + sectie.getNumePacient() + " " + sectie.getPrenumePacient());
        /* Cred ca am stat cam 4 ore sa ma gandesc cum puteam sa afisez Numele Prenumele din
        * tabelele Medici si Pacienti, in acest RecyclerView dedicat doar clasei Sectii ...
        * Pe net nu era nicio solutie..
        * Intr-un final am in clasa sectii 4 atribute in plus (Nume Prenume medic/pacient) pe care
        * le-am preluat la INNER JOIN. */
    }

    @Override
    public int getItemCount() {
        return sectiiList.size();
    }
}
