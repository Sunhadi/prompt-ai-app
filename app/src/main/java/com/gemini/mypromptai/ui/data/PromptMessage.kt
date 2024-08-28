package com.gemini.mypromptai.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PromptMessage(
    val message: String? = "Im Sorry I dont understand that",
    val isUser: Boolean = true
): Parcelable
