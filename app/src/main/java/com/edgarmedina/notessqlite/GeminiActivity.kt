package com.edgarmedina.notessqlite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edgarmedina.notessqlite.databinding.ActivityGeminiBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GeminiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeminiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeminiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.generateBtn.setOnClickListener {
            modelCall()
            binding.progressBar.visibility = View.VISIBLE
        }

        //Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

    }

    fun modelCall() {
        var textView = binding.textView
        textView.setTextIsSelectable(true)
        val generativeModel = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = "",
        )

        val prompt = binding.promptTiet.text.toString()
        MainScope().launch {
            val response = generativeModel.generateContent(prompt)
            textView.setText(response.text)
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
