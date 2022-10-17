package com.example.notesultimate.domain.util

sealed interface OrderType {
    object Ascending:OrderType
    object Descending:OrderType
}