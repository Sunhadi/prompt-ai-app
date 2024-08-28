package com.gemini.mypromptai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gemini.mypromptai.ui.theme.primaryBackground
import com.gemini.mypromptai.ui.theme.secondaryBackground
import com.gemini.mypromptai.ui.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    clearText: ()->Unit,
    text: String,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(64.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryBackground
        ),
        title = {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        },
        actions = {
            IconButton(
                onClick = clearText,
                modifier = Modifier
                    .padding(12.dp)
                    .size(50.dp)
                    .background(
                        color = secondaryBackground,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.size(28.dp),
                    tint = textColor
                )
            }
        }
    )
}