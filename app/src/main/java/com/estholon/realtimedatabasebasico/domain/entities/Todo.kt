package com.estholon.realtimedatabasebasico.domain.entities

data class Todo (
    val title: String? = "",
    val description: String? = "",
    val done: Boolean? = false
)