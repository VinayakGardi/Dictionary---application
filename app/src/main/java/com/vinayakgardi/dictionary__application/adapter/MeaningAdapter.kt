package com.vinayakgardi.dictionary__application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.dictionary__application.databinding.RecyclerMeaningRowBinding
import com.vinayakgardi.dictionary__application.model.Meaning
import com.vinayakgardi.dictionary__application.model.WordDataItem

class MeaningAdapter(var meaningList: List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    inner class MeaningViewHolder(val binding: RecyclerMeaningRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(meaning: Meaning) {
            binding.partOfSpeechTextview.text = meaning.partOfSpeech
            binding.definitionsTextview.text = meaning.definitions.joinToString("\n\n") {
                var currentIndex = meaning.definitions.indexOf(it)
                (currentIndex+1).toString() +". "+ it.definition.toString()
            }

            if(meaning.synonyms.isEmpty()){
                binding.synonymsTextview.visibility = View.GONE
                binding.synonymsTitleTextview.visibility = View.GONE
            }
            else{
                binding.synonymsTextview.visibility = View.VISIBLE
                binding.synonymsTitleTextview.visibility = View.VISIBLE
                binding.synonymsTextview.text = meaning.synonyms.joinToString(", ")
            }

            if(meaning.antonyms.isEmpty()){
                binding.antonymsTextview.visibility = View.GONE
                binding.antonymsTitleTextview.visibility = View.GONE
            }
            else{
                binding.antonymsTextview.visibility = View.VISIBLE
                binding.antonymsTitleTextview.visibility = View.VISIBLE
                binding.antonymsTextview.text = meaning.antonyms.joinToString(", ")
            }
        }
    }

    fun updateNewData(newList: List<Meaning>) {
        meaningList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding =
            RecyclerMeaningRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}