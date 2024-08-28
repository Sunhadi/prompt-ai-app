package com.gemini.mypromptai.ui.helper

import com.gemini.mypromptai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

val generativeModel = GenerativeModel(
    // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
    modelName = "gemini-1.5-flash",
    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
    apiKey = BuildConfig.GEMINI_API_KEY
)