package checkitems.dialog.org.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import checkitems.dialog.org.activities.CheckListActivity;
import checkitems.dialog.org.checklistdialog.R;
import checkitems.dialog.org.interfaces.DialogResponse;

/**
 * Cuadro de diálogo con lista de selección múltiple
 */
public class CheckListDialog extends DialogFragment {
    private boolean[] mSelectedItems = {false,false,false,false,false,false};
    private DialogResponse response;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        response = (CheckListActivity)getActivity();
        //se establece el título del cuadro de diálogo
        builder.setTitle(R.string.select_start)
                // se asigna la lista de elementos que se pueden seleccionar (null para ninguno),
                //y se asocia el método de evento que recibirá el callbacks cuando los elementos sean seleccionados
                .setMultiChoiceItems(R.array.starts_position, mSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                mSelectedItems[which]=isChecked;
                            }
                        })
                // se definen los botones de acción
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //se tratan los elementos seleccionados
                        response.onResponse(mSelectedItems);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //se descartan los elementos seleccionados
                        response.onCancel();
                    }
                });

        return builder.create();
    }
}
