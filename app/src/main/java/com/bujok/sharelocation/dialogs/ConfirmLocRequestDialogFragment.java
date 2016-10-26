package com.bujok.sharelocation.dialogs;

/**
 * Created by Buje on 26/10/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Pair;

import com.bujok.sharelocation.R;
import com.bujok.sharelocation.backendcomms.ServletPostAsyncTask;

public  class ConfirmLocRequestDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_sendLocationRequestToUser)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Pair<Context, String> senderData  = new Pair<Context, String>(getContext(), getArguments().getString("sender")) ;
                        Pair<Context, String> receiverData  = new Pair<Context, String>(getContext(), getArguments().getString("receiver")) ;

                        new ServletPostAsyncTask().execute(senderData,receiverData);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}