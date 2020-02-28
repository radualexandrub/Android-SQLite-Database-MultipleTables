package ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Consultatii;
import ro.upb.etti.stud.BulaiRadu_P3.R;

public class Adapter_Consultatie extends RecyclerView.Adapter<Adapter_Consultatie.MyViewHolder> {
    private Context context;
    private List<Consultatii> consultatiiList;

    public Adapter_Consultatie(Context context, List<Consultatii> consultatiiList) {
        this.context = context;
        this.consultatiiList = consultatiiList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView listrow_dot;
        public TextView listrow_idpacient;
        public TextView listrow_idmedicament;
        public TextView listrow_dataconsult;
        public TextView listrow_diagnostic;
        public TextView listrow_doza;

        // TextView pentru a afisa atributele celorlalte clase
        public TextView listrow_afisare_medicament;
        public TextView listrow_afisare_pacient;
        public TextView listrow_afisare_medic;


        public MyViewHolder(View view) {
            super(view);
            listrow_dot = view.findViewById(R.id.idrow_consultatie_id_dot);
            listrow_idpacient = view.findViewById(R.id.idrow_consultatie_id_pacient);
            listrow_idmedicament = view.findViewById(R.id.idrow_consultatie_id_medicament);
            listrow_dataconsult = view.findViewById(R.id.idrow_consultatie_dataconsultatie);
            listrow_diagnostic = view.findViewById(R.id.idrow_consultatie_diagnostic);
            listrow_doza = view.findViewById(R.id.idrow_consultatie_doza);

            //

            listrow_afisare_medicament = view.findViewById(R.id.idrow_consultatie_afisare_medicament);
            listrow_afisare_pacient = view.findViewById(R.id.idrow_consultatie_afisare_pacient);
            listrow_afisare_medic = view.findViewById(R.id.idrow_consultatie_afisare_medic);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_consultatii, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Consultatii consultatii = consultatiiList.get(position);

        // Afisare date(id, dataconsultatie, diagnostic, dozamedicament)
        // introduse pentru fiecare consultatie in Tabela Consultatii (consultatiiList)
        holder.listrow_dot.setText(Integer.toString(consultatii.getIdConsultatie()));
        holder.listrow_idpacient.setText(Integer.toString(consultatii.getIdPacient()));
        holder.listrow_idmedicament.setText(Integer.toString(consultatii.getIdMedicament()));
        holder.listrow_dataconsult.setText(consultatii.getDataconsultatie());
        holder.listrow_diagnostic.setText("Diagnostic " + consultatii.getDiagnostic());
        holder.listrow_doza.setText("Doza: " + Float.toString(consultatii.getDozamedicament()) + " mg");

        holder.listrow_afisare_medicament.setText("Medicament recomandat: " + consultatii.getDenumireMedicament());
        holder.listrow_afisare_pacient.setText("Pacient: " + consultatii.getNumePacient() + " " + consultatii.getPrenumePacient());
        holder.listrow_afisare_medic.setText("Medic: Dr. " + consultatii.getNumeMedic() + " " + consultatii.getPrenumeMedic());
    }

    @Override
    public int getItemCount() {
        return consultatiiList.size();
    }
}
