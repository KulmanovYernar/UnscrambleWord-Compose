package com.example.unscramble.ui.test

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()


    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        assertFalse(currentGameUiState.isGuessedWordWrong)

        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)

    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value

        assertEquals(0, currentGameUiState.score)
        assertTrue(currentGameUiState.isGuessedWordWrong)


    }

    @Test
    fun gameViewModel_initialization_FirstWordLoaded(){
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
        assertEquals(1, gameUiState.currentWordCount)
        assertEquals(0, gameUiState.score)
    }


    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly(){
        var expectedScore = 0
        var gameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        repeat(MAX_NO_OF_WORDS){
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()

            gameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(gameUiState.currentScrambledWord)
            assertEquals(expectedScore,gameUiState.score)
        }

        assertTrue(gameUiState.isGameOver)
        assertTrue(gameUiState.currentWordCount == MAX_NO_OF_WORDS)

    }
    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}