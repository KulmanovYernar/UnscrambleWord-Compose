package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(): ViewModel() {
    var userGuess by mutableStateOf("")
        private set

    private lateinit var currentWord:String
    private var usedWords:MutableSet<String> = mutableSetOf()

    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()




    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle():String{
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        }
        else{
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }

    }

    private fun shuffleCurrentWord(word:String):String{
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        while(String(tempWord).equals(word)){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

     fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUIState(currentScrambledWord = pickRandomWordAndShuffle())
    }

}