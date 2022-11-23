package com.example.proyecto3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int TOMA_VIDEO = 1;
    private VideoView vv1;
    private Spinner sp1;
    private String[] lista;
    private Object Spinner1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vv1=findViewById(R.id.videoView);
        sp1=findViewById(R.id.spinner);
        lista=fileList();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this , android.R.layout.simple_spinner_item,lista);
        sp1.setAdapter(adapter);


    }
    public void tomarVideo(View v)
    {
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,TOMA_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TOMA_VIDEO && resultCode==RESULT_OK )
        {
            Uri videoUri=data.getData();
            vv1.setVideoURI(videoUri);
            vv1.start();

            try {
                String mode;
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(),"r");
                FileInputStream in = videoAsset.createInputStream();
                FileOutputStream archivo = openFileOutput(crearNombreArchivoMP4(), Context.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0){
                    archivo.write(buf,0,len);

                }


            }catch (IOException e)
            {
                Toast.makeText(this,"problemas en la grabacion",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private String crearNombreArchivoMP4(){

        String fecha = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String nombre = fecha + "mp4";
        return nombre;

    }
      public void verVideo(View v)
    {
      int pos=sp1.getSelectedItemPosition();
      vv1.setVideoPath(getFilesDir()+"/"+lista[pos]);
      vv1.start();

    }


}