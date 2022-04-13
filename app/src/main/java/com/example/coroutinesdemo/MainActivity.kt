package com.example.coroutinesdemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var customDialog: Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExecute:Button=findViewById(R.id.btn_execute)
        btnExecute.setOnClickListener {
            showProgressDialog()
            lifecycleScope.launch{
                execute("Task executed successfully")
            }

        }
    }
    private suspend fun execute(result:String){
        //withContext is a method created to move an operation into different thread until
        //complete it's process and then move into the original thread
        //IO means to run in Input output
        withContext(Dispatchers.IO){
            for (i in 1..100000){
                Log.e("delay : ",""+i)
            }
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(this@MainActivity,result,Toast.LENGTH_LONG).show()
            }
        }

    }
    // cancel the custom dialog when the toast printed
    private fun cancelProgressDialog() {
        if (customDialog!=null){
            customDialog?.dismiss()
            customDialog=null
        }
    }

    private fun showProgressDialog(){
        customDialog= Dialog(this)
        customDialog?.setContentView(R.layout.dialog_custom)
        customDialog?.show()

    }


}