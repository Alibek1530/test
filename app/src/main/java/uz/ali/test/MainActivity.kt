package uz.ali.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import uz.ali.test.app.App
import uz.ali.test.databese.DataModel

class MainActivity : AppCompatActivity() {
   // lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //   navController = findNavController(R.id.nav_host_fragment)
    }

}