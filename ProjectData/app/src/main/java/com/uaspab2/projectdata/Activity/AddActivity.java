package com.uaspab2.projectdata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uaspab2.projectdata.API.APIRequestData;
import com.uaspab2.projectdata.API.RetroServer;
import com.uaspab2.projectdata.Model.ResponseModel;
import com.uaspab2.projectdata.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private EditText etNama, etNpm, etNilai;
    private Button btnSimpan;
    private String nama, npm, nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etNama = findViewById(R.id.et_nama);
        etNpm = findViewById(R.id.et_npm);
        etNilai = findViewById(R.id.et_nilai);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                npm = etNpm.getText().toString();
                nilai = etNilai.getText().toString();

                if (nama.trim().equals("")) {
                    etNama.setError("Nama Harus Diisi!");
                }
                else if (npm.trim().equals("")) {
                    etNpm.setError("Npm Harus Diisi!");
                }
                else if (nilai.trim().equals("")) {
                    etNilai.setError("Nilai Harus Diisi!");
                }
                else {
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, npm, nilai);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(AddActivity.this, "Kode : " + kode + "| Pesan : " + pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal Menghubungi Server | " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}