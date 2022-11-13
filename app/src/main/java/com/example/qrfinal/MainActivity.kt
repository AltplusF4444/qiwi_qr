package com.example.qrfinal

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    var im: ImageView? = null
    var bGenerate: Button? = null
    var bScanner: Button? = null
    var ecryptedToken: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ecryptedToken = "qqqq"
        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bScanner = findViewById(R.id.bScan)
        bScanner?.setOnClickListener{
            CheckCameraPermission()
        }
        CreateQr();
    }

    private fun CreateQr(){

        bGenerate?.setOnClickListener {
            try {
                val barcodeEncode: BarcodeEncoder = BarcodeEncoder()
                val bitmap : Bitmap = barcodeEncode.encodeBitmap(ecryptedToken!!, BarcodeFormat.QR_CODE, 1000, 1000)
                im?.setImageBitmap(bitmap)
            } catch (e: WriterException){}

        }
    }

    private fun CheckCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 12)
        }
        else
        {
            startActivity(Intent(this, ScannerActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 12 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            startActivity(Intent(this, ScannerActivity::class.java))
        }
    }



}