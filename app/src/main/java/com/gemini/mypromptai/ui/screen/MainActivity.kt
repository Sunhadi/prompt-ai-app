package com.gemini.mypromptai.ui.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gemini.mypromptai.ui.components.BottomBar
import com.gemini.mypromptai.ui.components.CardMessage
import com.gemini.mypromptai.ui.components.CardTemplate
import com.gemini.mypromptai.ui.components.TopBar
import com.gemini.mypromptai.ui.data.ResultState
import com.gemini.mypromptai.ui.data.promptTemplate
import com.gemini.mypromptai.ui.theme.MyPromptAITheme
import com.gemini.mypromptai.ui.theme.primaryBackground
import com.gemini.mypromptai.ui.theme.textColor

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPromptAITheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBar(
                            onSend = { prompt ->
                                viewModel.storeUserPrompt(prompt)
                                viewModel.sendPrompt(prompt)
                            }
                        )
                    },
                    topBar = {
                        TopBar(
                            clearText = {
                                viewModel.clearChat()
                            },
                            text = "MyPromptAI"
                        )
                    },
                    contentColor = textColor,
                    containerColor = primaryBackground
                ) { innerPadding ->

                    viewModel.response.collectAsState(
                        initial = ResultState.Success(emptyList())
                    ).value.let {
                        data ->
                        when (data) {
                            is ResultState.Success -> {
                                if(data.data.isEmpty()){
                                    LazyVerticalGrid(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding)
                                            .padding(horizontal = 14.dp),
                                        verticalArrangement = Arrangement.Bottom,
                                        columns = GridCells.Fixed(2)
                                    ) {
                                        items(promptTemplate){
                                            template ->
                                            CardTemplate(
                                                text = template.text,
                                                icon = template.icon,
                                                onSend={
                                                    viewModel.sendPrompt(template.prompt)
                                                    viewModel.storeUserPrompt(template.prompt)
                                                }
                                            )
                                        }
                                    }
                                }else{
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding)
                                            .padding(horizontal = 12.dp),
                                        verticalArrangement = Arrangement.Bottom

                                    ) {
                                        items(data.data){
                                            CardMessage(text = it.message!!, isUser = it.isUser)
                                        }
                                    }
                                }

                            }

                            is ResultState.Loading -> {
                                Log.i("MainActivity", "Loading")
                            }

                            is ResultState.Error -> {
                                Log.e("MainActivity", "Error: $data")
                            }
                        }
                    }
                }
            }
        }
    }
}



