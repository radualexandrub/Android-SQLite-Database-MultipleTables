package ro.upb.etti.stud.BulaiRadu_P3.Activities;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters.Adapter_Consultatie;
import ro.upb.etti.stud.BulaiRadu_P3.DatabaseHelper;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Consultatii;
import ro.upb.etti.stud.BulaiRadu_P3.R;
import ro.upb.etti.stud.BulaiRadu_P3.utils.MyDividerItemDecoration;
import ro.upb.etti.stud.BulaiRadu_P3.utils.RecyclerTouchListener;

public class Activity_Consultatii extends AppCompatActivity {
    private Adapter_Consultatie mAdapter;
    private List<Consultatii> consultatiiArrayList  = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noConsultatiiView;

    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //buton de intoarcere <- din toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultatii);

        coordinatorLayout = findViewById(R.id.consultatii_coordinator_layout);
        recyclerView = findViewById(R.id.consultatii_recycler_view);
        noConsultatiiView = findViewById(R.id.empty_consultatii_view);
        db = new DatabaseHelper(this);

        // Actiune pentru butonul FAB ("+")
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.consultatii_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConsultatiiEditDialog(false, null, -1);
            }
        });

        consultatiiArrayList.addAll(db.getAllConsultatii());
        mAdapter = new Adapter_Consultatie(this, consultatiiArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyConsultatii();

        // On long press on RecyclerView item, open alert dialog with options to choose: Edit / Delete
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionDialog(position);
            }
        }));

    }

    /********************************/
    /* Methods for onCreate buttons */
    /********************************/
    /* Insert new consultatie in db and refresh consultatiiArrayList */
    private void createConsultatie(Integer id_pacient,
                              Integer id_medicament,
                              String dataconsult,
                              String diagnostic,
                              Float dozamedicament) {
        long id = db.insertConsultatie(id_pacient, id_medicament, dataconsult, diagnostic, dozamedicament);

        Consultatii obj_consultatie = db.getConsultatie(id);

        if (obj_consultatie != null) {
            consultatiiArrayList.add(0, obj_consultatie);
            mAdapter.notifyDataSetChanged();

            toggleEmptyConsultatii();
        }
    }

    /* Update consultatii in db and update item in the consultatiiArrayList by its position */
    private void updateConsultatie(Integer id_pacient,
                                   Integer id_medicament,
                                   String dataconsult,
                                   String diagnostic,
                                   Float dozamedicament,
                                    int position) {
        // update consultatie as object
        Consultatii obj_consultatie = consultatiiArrayList.get(position);
        obj_consultatie.setIdPacient(id_pacient);
        obj_consultatie.setIdMedicament(id_medicament);
        obj_consultatie.setDataconsultatie(dataconsult);
        obj_consultatie.setDiagnostic(diagnostic);
        obj_consultatie.setDozamedicament(dozamedicament);

        // update consultatie object in db
        db.updateConsultatii(obj_consultatie);

        // refresh the list/adapter
        consultatiiArrayList.set(position, obj_consultatie);
        mAdapter.notifyItemChanged(position);

        toggleEmptyConsultatii();
    }

    /* Delete consultatii from SQLite db and remove the item from the consultatiiArrayList by its position */
    private void deleteConsultatie(int position) {
        db.deleteConsultatii(consultatiiArrayList.get(position)); // delete consultatie from db

        consultatiiArrayList.remove(position); // delete consultatie from consultatiiArrayList adapter
        mAdapter.notifyItemRemoved(position);

        toggleEmptyConsultatii();
    }

    /****************************************/
    /* Open dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1 */
    private void showActionDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Editeaza", "Sterge"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected_option) {
                if (selected_option == 0) {
                    showConsultatiiEditDialog(true, consultatiiArrayList.get(position), position);
                } else {
                    deleteConsultatie(position);
                }
            }
        });
        builder.show();
    }

    /************************************************************************/
    /* Shows alert dialog with EditText options to enter / edit a consultatie.
     * when shouldUpdate=true, it automatically displays old note and changes the button text to UPDATE */
    // pentru FAB "+"
    public void showConsultatiiEditDialog(final boolean shouldUpdate, final Consultatii consultatie, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_consultatii, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Activity_Consultatii.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText input_id_pacient = view.findViewById(R.id.iddialog_consultatie_id_pacient);
        final EditText input_id_medicament = view.findViewById(R.id.iddialog_consultatie_id_medicament);
        final EditText input_data = view.findViewById(R.id.iddialog_consultatie_data);
        final EditText input_diagnostic = view.findViewById(R.id.iddialog_consultatie_diagnostic);
        final EditText input_doza = view.findViewById(R.id.iddialog_consultatie_doza);

        TextView dialogTitle = view.findViewById(R.id.iddialog_consultatii_title);
        dialogTitle.setText(!shouldUpdate ? "Adaugare consultatie noua" : "Editare consultatie");

        if (shouldUpdate && consultatie != null) {
            input_id_pacient.setText(Integer.toString((consultatie.getIdPacient())));
            input_id_medicament.setText(Integer.toString(consultatie.getIdMedicament()));
            input_data.setText(consultatie.getDataconsultatie());
            input_diagnostic.setText(consultatie.getDiagnostic());
            input_doza.setText(Float.toString(consultatie.getDozamedicament()));
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a toast message when no text is entered
                if (    TextUtils.isEmpty(input_id_pacient.getText().toString()) ||
                        TextUtils.isEmpty(input_id_medicament.getText().toString()) ||
                        TextUtils.isEmpty(input_diagnostic.getText().toString()) ||
                        TextUtils.isEmpty(input_doza.getText().toString()))
                {
                    Toast.makeText(Activity_Consultatii.this,
                            "Completeaza ids, doza(mg) si numele diagnosticului!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // VERIFICARE DACA EXISTA PACIENT SI CONSULTATIE CU Id-urile respective (introduse) !!!
                List<Integer> lista_ids_pacienti = db.getListaIds_Pacienti();
                List<Integer> lista_ids_medicamente = db.getListaIds_Medicamente();

                if (!(lista_ids_pacienti.contains(Integer.valueOf(input_id_pacient.getText().toString())) &&
                        lista_ids_medicamente.contains(Integer.valueOf(input_id_medicament.getText().toString())))) {

                    Toast.makeText(Activity_Consultatii.this,
                            "Id-urile introduse nu se regasesc !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                } //

                if (shouldUpdate && consultatie != null) {
                    // update consultatie by it's id
                    updateConsultatie(
                            Integer.valueOf(input_id_pacient.getText().toString()),
                            Integer.valueOf(input_id_medicament.getText().toString()),
                            input_data.getText().toString(),
                            input_diagnostic.getText().toString(),
                            Float.valueOf(input_doza.getText().toString()),
                            position);
                } else {
                    // create new consultatie
                    createConsultatie(
                            Integer.valueOf(input_id_pacient.getText().toString()),
                            Integer.valueOf(input_id_medicament.getText().toString()),
                            input_data.getText().toString(),
                            input_diagnostic.getText().toString(),
                            Float.valueOf(input_doza.getText().toString()));
                }
            }
        });
    }

    private void toggleEmptyConsultatii() {
        if (db.getConsultatiiCount() > 0) {
            noConsultatiiView.setVisibility(View.GONE);
        } else {
            noConsultatiiView.setVisibility(View.VISIBLE);
        }
    }

}
