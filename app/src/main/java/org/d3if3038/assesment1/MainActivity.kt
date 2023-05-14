package org.d3if3038.assesment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.main_container)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

//    private fun changeToPersonalityTest() {
//        val fullName = binding.nameInput.text.toString()
//        if (fullName.isEmpty()) {
//            Toast.makeText(this, R.string.name_empty, Toast.LENGTH_LONG).show()
//            return
//        }
//
//        val age = binding.ageInput.text.toString()
//        if (age.isEmpty()) {
//            Toast.makeText(this, R.string.age_empty, Toast.LENGTH_LONG).show()
//            return
//        }
//
//        var personalityTestIntent = Intent(this, PersonalityTestActivity::class.java)
//        personalityTestIntent.putExtra("fullName", fullName)
//        personalityTestIntent.putExtra("age", age)
//
//        startActivity(personalityTestIntent)
//    }
}