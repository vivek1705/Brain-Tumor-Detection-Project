package com.annie.brain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.brain.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Detect extends AppCompatActivity {

    ImageButton btn1;
    ImageButton btn2;
    ImageButton btnPredict;
    TextView output;
    ImageView imgMri;
    Bitmap img;
    private final int CAMERA_RES_CODE=100;
    private final int G_RES_CODE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        btn1 =findViewById(R.id.btn1);
        btn2 =findViewById(R.id.btn2);
        btnPredict = findViewById(R.id.btnPredict);
        imgMri =findViewById(R.id.imgMri);
        output =findViewById(R.id.output);

        btn1.setOnClickListener(view -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera,CAMERA_RES_CODE);
        });

        btn2.setOnClickListener(view -> {
            Intent gallery =new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery,G_RES_CODE);

        });

        btnPredict.setOnClickListener(view -> {

            if(img!=null) {
                img=Bitmap.createScaledBitmap(img, 64, 64, true);
            try {


                Model model = Model.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 64, 64, 3}, DataType.FLOAT32);
                TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                tensorImage.load(img);
                ByteBuffer byteBuffer = tensorImage.getBuffer();

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                Model.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                if(outputFeature0.getFloatArray()[0]==1.0){
                    Intent output = new Intent(getApplicationContext(),Result.class);
                    output.putExtra("name","0");
                    startActivity(output);
                }
                else{
                    Intent out = new Intent(getApplicationContext(),Result2.class);
                    startActivity(out);
                }

                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }
            }

            else{
                Toast.makeText(Detect.this, "Please Select Image First", Toast.LENGTH_SHORT).show();
            }


    });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==CAMERA_RES_CODE){

                assert data != null;
                img =(Bitmap) (data.getExtras().get("data"));
                imgMri.setImageBitmap(img);

            }

            if(requestCode==G_RES_CODE) {
                assert data != null;
                imgMri.setImageURI(data.getData());
                Uri uri = data.getData();

                try {
                    img=MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}