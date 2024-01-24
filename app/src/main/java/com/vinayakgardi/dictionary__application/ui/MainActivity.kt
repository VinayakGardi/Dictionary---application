package com.vinayakgardi.dictionary__application.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinayakgardi.dictionary__application.adapter.MeaningAdapter
import com.vinayakgardi.dictionary__application.api.RetrofitObject
import com.vinayakgardi.dictionary__application.databinding.ActivityMainBinding
import com.vinayakgardi.dictionary__application.model.Word
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: MeaningAdapter
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


    fun setAdapter(){
        binding.recyclerViewMeaning.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMeaning.adapter = adapter
    }

    private fun getMeaning(word: String){
        setOnProgress(true)
        GlobalScope.launch {
            try{
                val response =  RetrofitObject.dictionaryApi.getMeaning(word)
                Log.i("Word Response" , "${response.body().toString()}")
                if(response.body() == null){
                    throw (Exception())
                }
                runOnUiThread{
                    setOnProgress(false)
                    response.body()?.first()?.let {it->
                        setUi(it)
                    }
                }

            }catch (e: Exception){
                runOnUiThread{
                    setOnProgress(false)
                    Toast.makeText(this@MainActivity, "No meaning for such word", Toast.LENGTH_SHORT).show()
                }

            }

        }


    }
    private fun setUi(response : Word){
        binding.textWordTitle.text = response.word
        binding.textWordPhonetics.text = response.phonetics[0].text.toString()
        adapter.updateNewData(response.meanings)

    }

    private fun setOnProgress(progress: Boolean) {
         if(progress){
             binding.btnSearch.visibility = View.GONE
             binding.progressBarSearch.visibility = View.VISIBLE
         }
        else{
             binding.btnSearch.visibility = View.VISIBLE
             binding.progressBarSearch.visibility = View.GONE
         }
    }
}