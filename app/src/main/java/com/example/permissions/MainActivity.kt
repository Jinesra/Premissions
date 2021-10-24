package com.example.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    val FINE_LOCATION_RQ = 101
    val CAMERA_RQ = 102
    val STORAGE_RQ = 103
    val INTERNET_RQ = 104
    val CONTACTS_RQ = 105
    val SYNC_SETTINGS_RQ = 106

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonTaps()

    }
    private val valuesOfPermissions:Array<String?> = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_SYNC_SETTINGS
    )
    @RequiresApi(Build.VERSION_CODES.M)
    private val multiplePermissionsRegistration =  registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "READ_EXTERNAL_STORAGE permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "READ_EXTERNAL_STORAGE permission refused",Toast.LENGTH_SHORT).show()
        }
        if (checkSelfPermission(Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "CAMERA permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "CAMERA permission refused",Toast.LENGTH_SHORT).show()
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "ACCESS_FINE_LOCATION permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "ACCESS_FINE_LOCATION permission refused",Toast.LENGTH_SHORT).show()
        }

        if (checkSelfPermission(Manifest.permission.INTERNET)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "INTERNET permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "INTERNET permission refused",Toast.LENGTH_SHORT).show()
        }

        if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "READ_CONTACTS permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "READ_CONTACTS permission refused",Toast.LENGTH_SHORT).show()
        }

        if (checkSelfPermission(Manifest.permission.WRITE_SYNC_SETTINGS)
            == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(applicationContext,  "WRITE_SYNC_SETTINGS permission granted",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,  "WRITE_SYNC_SETTINGS permission refused",Toast.LENGTH_SHORT).show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun buttonTaps(){
        val button_camera: Button = findViewById(R.id.btn_camera)
        button_camera.setOnClickListener{
            checkForPermissions(Manifest.permission.CAMERA, "camera", CAMERA_RQ)
        }
        val button_location: Button = findViewById(R.id.btn_location)
        button_location.setOnClickListener{
            checkForPermissions(Manifest.permission.ACCESS_FINE_LOCATION, "location", FINE_LOCATION_RQ)
        }
        val button_storage: Button = findViewById(R.id.button2)
        button_storage.setOnClickListener{
            checkForPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, "storage", STORAGE_RQ)
        }
        val button_internet: Button = findViewById(R.id.button3)
        button_internet.setOnClickListener{
            checkForPermissions(Manifest.permission.INTERNET, "internet", INTERNET_RQ)
        }

        val button_contacts: Button = findViewById(R.id.button4)
        button_contacts.setOnClickListener{
            checkForPermissions(Manifest.permission.READ_CONTACTS, "contacts", CONTACTS_RQ)
        }

        val button_sync: Button = findViewById(R.id.button5)
        button_sync.setOnClickListener{
            checkForPermissions(Manifest.permission.WRITE_SYNC_SETTINGS, "write-sync-settings", SYNC_SETTINGS_RQ)
        }
        val button_all: Button = findViewById(R.id.button8)
        button_all.setOnClickListener{
            multiplePermissionsRegistration.launch(valuesOfPermissions)
        }
    }

    private fun checkForPermissions(permission:String, name: String, requestCode: Int){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            when{
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext,  "$name permission granted",Toast.LENGTH_SHORT).show()

                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name,requestCode)
                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        fun innerCheck(name: String){
            if(grantResults.isEmpty()||grantResults[0]!= PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,  "$name permission refused",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,  "$name permission granted",Toast.LENGTH_SHORT).show()

            }
        }

        when (requestCode){
            FINE_LOCATION_RQ -> innerCheck("location")
            CAMERA_RQ -> innerCheck("camera")
            STORAGE_RQ -> innerCheck("storage")
            INTERNET_RQ -> innerCheck("internet")
            CONTACTS_RQ -> innerCheck("contacts")
            SYNC_SETTINGS_RQ -> innerCheck("write-sync-settings")
        }
    }

    private fun showDialog(permission: String, name: String, requestCode: Int){
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK"){ dialog, which ->
                ActivityCompat.requestPermissions(this@MainActivity,arrayOf(permission),requestCode)
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}