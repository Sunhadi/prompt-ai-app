package com.gemini.mypromptai.ui.data

import com.gemini.mypromptai.R


data class PromptTemplate(
    val icon: Int,
    val prompt: String,
    val text: String
)


val promptTemplate = listOf<PromptTemplate>(
    PromptTemplate(
        icon = R.drawable.baseline_lightbulb_outline_24,
        prompt = "Can you help me create a personalized morning routine that would help increase my productivity throughout the day? Start by asking me about my current habits and what activities energize me in the morning.",
        text = "Morning Routine For Productivity"
    ),
    // vacation
    PromptTemplate(
        icon = R.drawable.baseline_beach_access_24,
        prompt = "Are you planning a vacation? If so, where would you like to go and what activities are you interested in doing? I can help you brainstorm ideas and find the perfect getaway!",
        text = "Plan Your Dream Vacation"
    ),
    // foods
    PromptTemplate(
        icon = R.drawable.baseline_fastfood_24,
        prompt = "Do you ever struggle with meal planning? Tell me about your dietary preferences and what kind of dishes you enjoy. I can help you create delicious and healthy recipes that fit your needs!",
        text = "Get Creative in the Kitchen"
    ),
    // commute
    PromptTemplate(
        icon = R.drawable.baseline_commute_24,
        prompt = "How do you typically get to work or school?  Is your commute stressful?  I can help you find ways to make your commute more enjoyable and productive.",
        text = "Optimize Your Commute"
    ),
)