package com.vinayakgardi.dictionary__application.model

import com.google.gson.annotations.SerializedName


data class Word(
    @SerializedName("word") val word: String,
    @SerializedName("phonetics") val phonetics: List<Phonetic>,
    @SerializedName("meanings") val meanings: List<Meaning>
)

data class Phonetic(
    @SerializedName("audio") val audio: String,
    @SerializedName("text") val text: String?
)

data class Meaning(
    @SerializedName("partOfSpeech") val partOfSpeech: String,
    @SerializedName("definitions") val definitions: List<Definition>,
    @SerializedName("synonyms") val synonyms: List<String>,
    @SerializedName("antonyms") val antonyms: List<String>
)

data class Definition(
    @SerializedName("definition") val definition: String
)