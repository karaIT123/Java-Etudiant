package com.example.etudiant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialogue extends AppCompatDialogFragment {

    private EditText noteEditText;
    private TextView titreEditText;
    private DialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.note_layout, null);

        noteEditText = view.findViewById(R.id.indexDecimal);
        titreEditText = view.findViewById(R.id.index);

        //titreEditText.setText("Notation");

        /*final TextView finalTitreEditText = titreEditText;
        final EditText finalNoteEditText = noteEditText;*/
        builder.setView(view)
                .setTitle("Notez l'activite")
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Noter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String note = noteEditText.getText().toString();
                        Integer x = Integer.parseInt(note);
                        if (x < 0 || x > 100) {
                            Toast.makeText(getContext(),"Veuillez entrez une note valide",Toast.LENGTH_LONG).show();
                            //titreEditText.setText("Veuillez entrez une note valide");
                            //noteEditText.setText("Veuillez entrez une note valide");
                        }else {
                            listener.getText(note);
                        }
                        //String titre = titreEditText.getText().toString();
                    }
                });

        /*noteEditText = view.findViewById(R.id.indexDecimal);
        titreEditText = view.findViewById(R.id.index);*/


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogueListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implementer dialogueListener");
        }
    }

    public interface DialogueListener{
        void getText(String note);
    }
}
