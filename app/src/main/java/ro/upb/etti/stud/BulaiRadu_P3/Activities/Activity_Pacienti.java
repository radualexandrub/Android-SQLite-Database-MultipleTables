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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Activity_Adapters.Adapter_Pacienti;
import ro.upb.etti.stud.BulaiRadu_P3.DatabaseHelper;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Pacienti;
import ro.upb.etti.stud.BulaiRadu_P3.R;
import ro.upb.etti.stud.BulaiRadu_P3.utils.MyDividerItemDecoration;
import ro.upb.etti.stud.BulaiRadu_P3.utils.RecyclerTouchListener;

public class Activity_Pacienti extends AppCompatActivity{
    private Adapter_Pacienti mAdapter;
    private List<Pacienti> pacientiList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noPacientiView;

    private DatabaseHelper db;

    /* ~~~ onCreate ~~~ */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //buton de intoarcere <- din toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacienti);

        coordinatorLayout = findViewById(R.id.pacienti_coordinator_layout);
        recyclerView = findViewById(R.id.pacienti_recycler_view);
        noPacientiView = findViewById(R.id.empty_pacienti_view);
        db = new DatabaseHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.pacienti_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPacientiEditDialog(false, null, -1);
            }
        });

        pacientiList.addAll(db.getAllPacienti("nume"));
        mAdapter = new Adapter_Pacienti(this, pacientiList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyPacienti();

        // On long press on RecyclerView item, open alert dialog with options to choose: Edit / Delete
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    /* ~~~ Methods for onCreate ~~~ */
    /* Insert new pacient in db and refresh the pacientiList */
    private void createPacient(String cnp,
                               String nume,
                               String prenume,
                               String adresa,
                               String asigurare) {
        long id = db.insertPacient(cnp, nume, prenume, adresa, asigurare);

        Pacienti obj_pacient = db.getPacient(id);

        if (obj_pacient != null) {
            pacientiList.add(0, obj_pacient);
            mAdapter.notifyDataSetChanged();

            toggleEmptyPacienti();
        }
    }

    /* Update pacienti in db and update item in the pacientiList by its position */
    private void updatePacient(String cnp,
                             String nume,
                             String prenume,
                             String adresa,
                             String asigurare,
                             int position) {
        // update pacient as object
        Pacienti pacient_obj = pacientiList.get(position);
        pacient_obj.setCnp(cnp);
        pacient_obj.setNume(nume);
        pacient_obj.setPrenume(prenume);
        pacient_obj.setAdresa(adresa);
        pacient_obj.setAsigurare(asigurare);

        // update pacient object in db
        db.updatePacienti(pacient_obj);

        // refresh the list
        pacientiList.set(position, pacient_obj);
        mAdapter.notifyItemChanged(position);

        toggleEmptyPacienti();
    }

    /* Delete pacient from SQLite db and remove the item from the pacientiList by its position */
    private void deletePacient(int position) {
        db.deletePacienti(pacientiList.get(position)); // delete pacient from db

        pacientiList.remove(position); // delete pacient from pacientiList
        mAdapter.notifyItemRemoved(position);

        toggleEmptyPacienti();
    }

    /* Open dialog with Edit - Delete options
     * Edit - 0
     * Delete - 1 */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Editeaza", "Sterge"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected_option) {
                if (selected_option == 0) {
                    showPacientiEditDialog(true, pacientiList.get(position), position);
                } else {
                    deletePacient(position);
                }
            }
        });
        builder.show();
    }

    /* Shows alert dialog with EditText options to enter / edit a pacient.
     * when shouldUpdate=true, it automatically displays old note and changes the button text to UPDATE */
    public void showPacientiEditDialog(final boolean shouldUpdate, final Pacienti pacient, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_pacienti, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Activity_Pacienti.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputCnp = view.findViewById(R.id.iddialog_pacient_cnp);
        final EditText inputNume = view.findViewById(R.id.iddialog_pacient_nume);
        final EditText inputPrenume = view.findViewById(R.id.iddialog_pacient_prenume);
        final EditText inputAdresa = view.findViewById(R.id.iddialog_pacient_adresa);
        final EditText inputAsigurare = view.findViewById(R.id.iddialog_pacient_asigurare);

        TextView dialogTitle = view.findViewById(R.id.iddialog_pacient_title);
        dialogTitle.setText(!shouldUpdate ? "Adaugare pacient nou" : "Editare pacient");

        if (shouldUpdate && pacient != null) {
            inputCnp.setText(pacient.getCnp());
            inputNume.setText(pacient.getNume());
            inputPrenume.setText(pacient.getPrenume());
            inputAdresa.setText(pacient.getAdresa());
            inputAsigurare.setText(pacient.getAsigurare());
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
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputCnp.getText().toString()) ||
                        TextUtils.isEmpty(inputNume.getText().toString()) ||
                        TextUtils.isEmpty(inputPrenume.getText().toString())) {

                    Toast.makeText(Activity_Pacienti.this,
                            "Completeaza CNP, nume si prenume pacient!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && pacient != null) {
                    // update pacient by it's id
                    updatePacient(inputCnp.getText().toString(),
                            inputNume.getText().toString(),
                            inputPrenume.getText().toString(),
                            inputAdresa.getText().toString(),
                            inputAsigurare.getText().toString(),
                            position);
                } else {
                    // create new pacient
                    createPacient(inputCnp.getText().toString(),
                            inputNume.getText().toString(),
                            inputPrenume.getText().toString(),
                            inputAdresa.getText().toString(),
                            inputAsigurare.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyPacienti() {
        if (db.getPacientiCount() > 0) {
            noPacientiView.setVisibility(View.GONE);
        } else {
            noPacientiView.setVisibility(View.VISIBLE);
        }
    }
}
