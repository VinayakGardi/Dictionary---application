package com.vinayakgardi.dictionary__application.model

data class WordDataItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)