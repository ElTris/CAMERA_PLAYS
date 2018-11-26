package com.example.tr.camera_plays;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView picture;
    Button buttonView;
    TextView rutas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picture=findViewById(R.id.camara);
        buttonView=findViewById(R.id.vistaphoto);
        rutas=findViewById(R.id.ruta);



        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisoCamera();
                permisoRead();

                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Selecciona una imagen", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            picture.setImageBitmap(bitmap);
            picture.buildDrawingCache();//EXPERIMENTO DE ESTA LINEA
            bitmap = picture.getDrawingCache();
            Save savefile = new Save();
            savefile.SaveImage(getApplicationContext(),bitmap);
            rutas.setText(savefile.file_path);

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Selecciona una imagen", Toast.LENGTH_LONG).show();
        }
    }

    public void permisoCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1000);
        }
        else {

        }
    }


    private void permisoRead() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
    }
}
