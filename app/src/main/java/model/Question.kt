package model

import com.beust.klaxon.*

class Question(
    val id: Long?,
    val answer: String?,
    val question: String?,
    val value: Long?,
    val airdate: String?,


    val category: Category?
) {
    companion object {
        fun fromJson(json: String) = klaxon.parseArray<Question>(json)
    }
}

private val klaxon = Klaxon()

data class Category(
    val title: String?,
)