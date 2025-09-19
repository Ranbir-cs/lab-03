package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";
    private static final String ARG_POSITION = "position";

    private EditText editTextCityName;
    private EditText editTextProvinceName;
    private City cityToEdit;
    private int position;

    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onCityEdited(City city, int position);
    }

    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_city, null);
        editTextCityName = view.findViewById(R.id.edit_text_city_name);
        editTextProvinceName = view.findViewById(R.id.edit_text_province_name);
        Button saveButton = view.findViewById(R.id.button_save_city);

        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable(ARG_CITY);
            position = getArguments().getInt(ARG_POSITION);
            if (cityToEdit != null) {
                editTextCityName.setText(cityToEdit.getName());
                editTextProvinceName.setText(cityToEdit.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    // This is overridden below to prevent dialog from closing on invalid input
                });
        
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String cityName = editTextCityName.getText().toString().trim();
                String provinceName = editTextProvinceName.getText().toString().trim();

                if (cityName.isEmpty()) {
                    editTextCityName.setError("City name cannot be empty");
                    return;
                }
                if (provinceName.isEmpty()) {
                    editTextProvinceName.setError("Province name cannot be empty");
                    return;
                }

                if (cityToEdit != null) {
                    cityToEdit.setName(cityName);
                    cityToEdit.setProvince(provinceName);
                    listener.onCityEdited(cityToEdit, position);
                    dialog.dismiss();
                }
            });
        });


        return dialog;
    }
}
