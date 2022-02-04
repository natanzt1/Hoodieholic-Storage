package com.example.natan.storagehoodieholic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.adapter.BarangAdapter;
import com.example.natan.storagehoodieholic.helper.preferenceHelper;

public class AccountFragment extends Fragment {
    View view;
    TextView tvProfilNama, tvProfilUsername;
    Button editBtn, logoutBtn;

//    Sistem berjalan dari sini
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);
        editBtn = view.findViewById(R.id.btn_profil_edit);
        logoutBtn = view.findViewById(R.id.btn_profil_logout);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        setProfile();
        return view;
    }

//    Kodingan ini untuk mengatur nama, gambar profil, dan username akun yang sedang login
    public void setProfile(){
        tvProfilNama = view.findViewById(R.id.tv_profil_nama);
        tvProfilUsername = view.findViewById(R.id.tv_profil_username);

        preferenceHelper preference = new preferenceHelper(getActivity());
        String profileNama = preference.getPreferenceNama();
        String profileUsername = preference.getPreferenceUsername();

        tvProfilNama.setText(profileNama);
        tvProfilUsername.setText(profileUsername);
    }

//    Saat tombol edit profil ditekan, maka sistem akan mengarah ke EditProfileActivity
    public void editProfile(){
        Intent intent = new Intent(getActivity(), DetectionActivity.class);
        startActivity(intent);
    }

    public void logout(){
        preferenceHelper preference = new preferenceHelper(getActivity());
        preference.setLogout();
        Intent intent = new Intent(getActivity(), Activity_login.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Apakah anda yakin ingin Logout?")
                .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                    }
                })
                .setNegativeButton("Batal", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
