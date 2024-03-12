package pe.edu.idat.appcamera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import pe.edu.idat.appcamera.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var file: File
    private var currentPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCompartir.setOnClickListener(this)
        binding.btnTomarFoto.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTomarFoto -> takePhoto()
            R.id.btnCompartir -> sharePhoto()
        }
    }

    private fun sharePhoto() {
        TODO("Not yet implemented")
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            it.resolveActivity(packageManager).also {
                two ->
                savePhoto()
                val photoUri: Uri = getUriContent(file)
                it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            }
        }
        openCamera.launch(intent)
    }

    private fun getUriContent(file: File): Uri {
        return FileProvider.getUriForFile(applicationContext, "pe.edu.idat.appcamera.fileprovider", file)
    }

    private fun savePhoto() {
        val imgDirectory =getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_0001", ".png", imgDirectory)
        currentPath =file.absolutePath
    }

    private val openCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) binding.ivFoto.setImageBitmap(getBitmapImage())
    }

    private fun getBitmapImage(): Bitmap? {
        return BitmapFactory.decodeFile(file.toString())
    }


}