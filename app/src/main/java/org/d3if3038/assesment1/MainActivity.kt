package org.d3if3038.assesment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import org.d3if3038.assesment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener { changeToPersonalityTest() }
    }

    private fun changeToPersonalityTest() {
        val fullName = binding.nameInput.text.toString()
        if (fullName.isEmpty()) {
            Toast.makeText(this, R.string.name_empty, Toast.LENGTH_LONG).show()
            return
        }

        val age = binding.ageInput.text.toString()
        if (age.isEmpty()) {
            Toast.makeText(this, R.string.age_empty, Toast.LENGTH_LONG).show()
            return
        }

        var personalityTestIntent = Intent(this, PersonalityTestActivity::class.java)
        personalityTestIntent.putExtra("fullName", fullName)
        personalityTestIntent.putExtra("age", age)

        startActivity(personalityTestIntent)
    }
}