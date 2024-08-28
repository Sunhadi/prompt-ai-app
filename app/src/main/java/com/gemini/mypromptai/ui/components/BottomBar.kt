package com.gemini.mypromptai.ui.components

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.gemini.mypromptai.R
import com.gemini.mypromptai.ui.helper.getSpeech
import com.gemini.mypromptai.ui.theme.primaryBackground
import com.gemini.mypromptai.ui.theme.secondaryBackground
import com.gemini.mypromptai.ui.theme.tertiaryBackground
import com.gemini.mypromptai.ui.theme.textColor


@Composable
fun BottomBar(
    onSend: (String) -> Unit
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var prompt by remember { mutableStateOf("") }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            prompt = results?.get(0).toString()
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getSpeech(speechRecognizerLauncher)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MySendInput(
            modifier = Modifier
                .padding(12.dp)
                .background(
                    color = secondaryBackground,
                    shape = RoundedCornerShape(8.dp)
                ),
            text = prompt,
            onTextChange = {
                prompt = it
            },
            placeHolder = "Type a message",
            onClick = {
                if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                    Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
                } else {
                    if (
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        getSpeech(speechRecognizerLauncher)
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                onSend(prompt)
                prompt = ""
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = secondaryBackground,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Favorite",
                modifier = Modifier.size(28.dp),
                tint = textColor
            )
        }
    }
}


@Composable
fun MySendInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeHolder: String,
    onClick: () -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f),
        value = text,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                text = placeHolder,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF798488)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = secondaryBackground,
            unfocusedContainerColor = secondaryBackground,
            cursorColor = textColor,
            focusedBorderColor = tertiaryBackground,
            unfocusedBorderColor = primaryBackground,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        ),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()
                onClick()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_mic_none_24),
                    contentDescription = "attachment",
                    tint = textColor
                )
            }
        },
        maxLines = 4,
    )
}

