package net.ibtikar.task.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import net.ibtikar.task.R
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentsFactory: FragmentsFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentsFactory
        setContentView(R.layout.activity_main)
        //setupActionBarWithNavController(findNavController(R.id.notesFragment2))
    }
    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.notesFragment2)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
}