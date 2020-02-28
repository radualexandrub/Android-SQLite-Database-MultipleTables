package ro.upb.etti.stud.BulaiRadu_P3.Activities_Client;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters.Adapter_Sectii;
import ro.upb.etti.stud.BulaiRadu_P3.DatabaseHelper;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Sectii;
import ro.upb.etti.stud.BulaiRadu_P3.R;
import ro.upb.etti.stud.BulaiRadu_P3.utils.MyDividerItemDecoration;
import ro.upb.etti.stud.BulaiRadu_P3.utils.RecyclerTouchListener;

public class Activity_Sectii_Client extends AppCompatActivity {
    private Adapter_Sectii mAdapter;
    private List<Sectii> sectiiList  = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noSectiiView;
    private Spinner spinner;

    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //buton de intoarcere <- din toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sectii);

        coordinatorLayout = findViewById(R.id.sectii_coordinator_layout);
        recyclerView = findViewById(R.id.sectii_recycler_view);
        noSectiiView = findViewById(R.id.empty_sectii_view);
        db = new DatabaseHelper(this);

        // Actiune pentru butonul FAB ("+")
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sectii_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSectiiEditDialog(false, null, -1);
            }
        });

        sectiiList.addAll(db.getAllSectii());
        mAdapter = new Adapter_Sectii(this, sectiiList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptySectii();

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
    /* Insert new sectie in db and refresh sectiiList */
    private void createSectie(Integer id_pacient,
                              Integer id_medic,
                              String nume,
                              Float buget) {
        long id = db.insertSectie(id_pacient, id_medic, nume, buget);

        Sectii obj_sectie = db.getSectie(id);

        if (obj_sectie != null) {
            sectiiList.add(0, obj_sectie);
            mAdapter.notifyDataSetChanged();

            toggleEmptySectii();
        }
    }

    /* Update sectii in db and update item in the sectiiList by its position */
    private void updateSectie(Integer id_pacient,
                              Integer id_medic,
                              String nume,
                              Float buget,
                              int position) {
        // update sectie as object
        Sectii sectie_obj = sectiiList.get(position);
        sectie_obj.setIdPacient(id_pacient);
        sectie_obj.setIdMedic(id_medic);
        sectie_obj.setNume(nume);
        sectie_obj.setBuget(buget);

        // update sectie object in db
        db.updateSectii(sectie_obj);

        // refresh the list/adapter
        sectiiList.set(position, sectie_obj);
        mAdapter.notifyItemChanged(position);

        toggleEmptySectii();
    }

    /* Delete sectii from SQLite db and remove the item from the sectiiList by its position */
    private void deleteSectie(int position) {
        db.deleteSectii(sectiiList.get(position)); // delete sectie from db

        sectiiList.remove(position); // delete sectie from sectiiList adapter
        mAdapter.notifyItemRemoved(position);

        toggleEmptySectii();
    }

    /****************************************/
    /* Open dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1 */
    private void showActionDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Editeaza"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected_option) {
                if (selected_option == 0) {
                    showSectiiEditDialog(true, sectiiList.get(position), position);
                }
            }
        });
        builder.show();
    }

    /************************************************************************/
    /* Shows alert dialog with EditText options to enter / edit a sectie.
     * when shouldUpdate=true, it automatically displays old note and changes the button text to UPDATE */
    // pentru FAB "+"
    public void showSectiiEditDialog(final boolean shouldUpdate, final Sectii sectie, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_sectii, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Activity_Sectii_Client.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText input_id_pacient = view.findViewById(R.id.iddialog_sectie_id_pacient);
        final EditText input_id_medic = view.findViewById(R.id.iddialog_sectie_id_medic);
        final EditText inputNume = view.findViewById(R.id.iddialog_sectie_nume);
        final EditText inputBuget = view.findViewById(R.id.iddialog_sectie_buget);

        TextView dialogTitle = view.findViewById(R.id.iddialog_sectie_title);
        dialogTitle.setText(!shouldUpdate ? "Adaugare sectie noua" : "Editare sectie");

        if (shouldUpdate && sectie != null) {
            input_id_pacient.setText(Integer.toString((sectie.getIdPacient())));
            input_id_medic.setText(Integer.toString(sectie.getIdMedic()));
            inputNume.setText(sectie.getNume());
            inputBuget.setText(Float.toString(sectie.getBuget()));
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
                if (TextUtils.isEmpty(input_id_pacient.getText().toString()) ||
                    TextUtils.isEmpty(input_id_medic.getText().toString()) ||
                    TextUtils.isEmpty(inputNume.getText().toString()) ||
                    TextUtils.isEmpty(inputBuget.getText().toString()))
                {
                        Toast.makeText(Activity_Sectii_Client.this,
                                "Completeaza id-urile, numele si bugetul sectiei!", Toast.LENGTH_SHORT).show();
                        return;
                }

                // Verificare DACA EXISTA Pacienti si Medici cu Id-urile respective (introduse) !!!
                List<Integer> lista_ids_pacienti = db.getListaIds_Pacienti();
                List<Integer> lista_ids_medici = db.getListaIds_Medici();

                if (!(lista_ids_pacienti.contains(Integer.valueOf(input_id_pacient.getText().toString())) &&
                        lista_ids_medici.contains(Integer.valueOf(input_id_medic.getText().toString())))) {

                        Toast.makeText(Activity_Sectii_Client.this,
                                "Id-urile introduse nu se regasesc !", Toast.LENGTH_SHORT).show();
                        return;
                } else {
                    alertDialog.dismiss();
                } //

                if (shouldUpdate && sectie != null) {
                    // update sectie by it's id
                    updateSectie(
                            Integer.valueOf(input_id_pacient.getText().toString()),
                            Integer.valueOf(input_id_medic.getText().toString()),
                            inputNume.getText().toString(),
                            Float.valueOf(inputBuget.getText().toString()),
                            position);
                } else {
                    // create new sectie
                    createSectie(
                            Integer.valueOf(input_id_pacient.getText().toString()),
                            Integer.valueOf(input_id_medic.getText().toString()),
                            inputNume.getText().toString(),
                            Float.valueOf(inputBuget.getText().toString()));
                    }
            }
        });
    }

    private void toggleEmptySectii() {
        if (db.getSectiiCount() > 0) {
            noSectiiView.setVisibility(View.GONE);
        } else {
            noSectiiView.setVisibility(View.VISIBLE);
        }
    }

}
