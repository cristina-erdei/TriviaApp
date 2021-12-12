package model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

object QuestionList {

    private val list: ArrayList<Question> = ArrayList()
    fun getQuestionList(): ArrayList<Question> {
        return list;
    }

    fun addQuestion(q: Question) {
        list.add(q)
    }

    fun removeQuestion(q: Question) {
        list.remove(q)
    }

    fun removeQuestionByIndex(i: Int) {
        list.removeAt(i)
    }

    fun resetList() {
        list.clear()
    }

    fun getTestList(): ArrayList<Question> {
        val testList: ArrayList<Question> = ArrayList()
        val date: LocalDateTime = LocalDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        testList.add(
            Question(
                1,
                "Test Question?",
                "Yes, test question",
                60,
                date.format(formatter),
                Category( "Istorie")
            )
        )
        testList.add(
            Question(
                2,
                "Test Question?",
                "Yes, test question",
                60,
                date.format(formatter),
                Category( "Istorie")
            )
        )
        testList.add(
            Question(
                3,
                "Test Question?",
                "Yes, test question",
                60,
                date.format(formatter),
                Category( "Istorie")
            )
        )

        return testList
    }

}
