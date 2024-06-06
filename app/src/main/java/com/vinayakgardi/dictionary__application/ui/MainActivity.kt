package com.vinayakgardi.dictionary__application.ui


import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinayakgardi.dictionary__application.R
import com.vinayakgardi.dictionary__application.adapter.MeaningAdapter
import com.vinayakgardi.dictionary__application.api.RetrofitObject
import com.vinayakgardi.dictionary__application.databinding.ActivityMainBinding
import com.vinayakgardi.dictionary__application.model.WordDataItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MeaningAdapter
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = MeaningAdapter(emptyList())

        binding.btnSearch.setOnClickListener {
            val word = binding.searchInput.text.toString().trim()
            getMeaning(word)
        }

        setAdapter()


    }


    fun setAdapter() {
        binding.recyclerViewMeaning.visibility = View.VISIBLE
        binding.recyclerViewMeaning.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMeaning.adapter = adapter
    }

    private fun prepareMediaPlayer(url: String) {
        binding.playPhonetics.visibility = View.VISIBLE
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                binding.playPhonetics.isEnabled = true
            }
            setOnCompletionListener {
                this@MainActivity.isPlaying = false
                binding.playPhonetics.setImageResource(R.drawable.ic_play_button)
            }
        }
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            mediaPlayer.pause()
            binding.playPhonetics.setImageResource(R.drawable.ic_play_button)
        } else {
            mediaPlayer.start()
            binding.playPhonetics.setImageResource(R.drawable.ic_pause_button)
        }
        isPlaying = !isPlaying
    }


    private fun getMeaning(word: String) {
        setOnProgress(true)
        GlobalScope.launch {
            try {
                val response = RetrofitObject.dictionaryApi.getMeaning(word)
                Log.i("Word Response", response.body().toString())
                if (response.body() == null) {
                    throw (Exception())
                }
                runOnUiThread {
                    setOnProgress(false)
                    response.body()?.first()?.let { it ->
                        setUi(it)
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    setOnProgress(false)
                    Toast.makeText(
                        this@MainActivity,
                        "No meaning for such word",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }


    }

    private fun setUi(response: WordDataItem) {

        binding.textWordTitle.text = response.word

        if (response.phonetics[0].text.isNullOrEmpty()) {
            binding.textWordPhonetics.text = ""
        } else {
            binding.textWordPhonetics.text = response.phonetics[0].text

        }
        binding.playPhonetics.setOnClickListener {
            togglePlayPause()
        }

        if (response.phonetics[0].audio.isNullOrBlank()) {
            binding.playPhonetics.visibility = View.GONE
            Toast.makeText(this, "audio phonetics not available for this word", Toast.LENGTH_SHORT)
                .show()
        } else {

            prepareMediaPlayer(response.phonetics[0].audio)

        }


        adapter.updateNewData(response.meanings)

    }

    private fun setOnProgress(progress: Boolean) {
        if (progress) {
            binding.btnSearch.visibility = View.GONE
            binding.progressBarSearch.visibility = View.VISIBLE
        } else {
            binding.btnSearch.visibility = View.VISIBLE
            binding.progressBarSearch.visibility = View.GONE
        }
    }
}