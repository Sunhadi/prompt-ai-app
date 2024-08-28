package com.gemini.mypromptai.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gemini.mypromptai.ui.data.PromptMessage
import com.gemini.mypromptai.ui.data.ResultState
import com.gemini.mypromptai.ui.helper.generativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    private val _response = MutableStateFlow<ResultState<List<PromptMessage>>>(ResultState.Loading)
    val response:StateFlow<ResultState<List<PromptMessage>>> = _response

    init {
        _response.value = ResultState.Success(emptyList())
    }

    fun sendPrompt(prompt:String){
        viewModelScope.launch {
            try {
                val message = generativeModel.generateContent(prompt)

                _response.value = _response.value.let {
                    if (it is ResultState.Success){
                        ResultState.Success(it.data + PromptMessage(message.text,isUser = false))
                    }else{
                        ResultState.Success(listOf(PromptMessage(message.text,isUser = false)))
                    }
                }
            }catch (e:Exception){
                Log.e("MainViewModel", "sendPrompt: ${e.cause}")
            }
        }
    }

    fun storeUserPrompt(prompt: String){
        viewModelScope.launch {
            _response.value = _response.value.let {
                if (it is ResultState.Success){
                    ResultState.Success(it.data + PromptMessage(prompt, isUser = true))
                }else{
                    ResultState.Success(listOf(PromptMessage(prompt, isUser = true)))
                }
            }
        }
    }

    fun clearChat(){
        _response.value = ResultState.Success(emptyList())
    }

}