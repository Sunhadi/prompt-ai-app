package com.gemini.mypromptai.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gemini.mypromptai.R
import com.gemini.mypromptai.ui.theme.secondaryBackground
import com.gemini.mypromptai.ui.theme.textColor


@Composable
fun CardTemplate(
    text:String,
    icon:Int,
    onSend: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(8.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(
            contentColor = textColor,
            containerColor = secondaryBackground
        ),
        onClick = onSend
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, fontSize = 20.sp)
        }
    }


}