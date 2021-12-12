package adaptors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia_app.databinding.QuestionItemBinding
import model.Question
import model.QuestionList

class QuestionRecycleViewAdaptor(
) : RecyclerView.Adapter<QuestionRecycleViewAdaptor.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return QuestionList.questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {

        with(holder) {
            with(QuestionList.questions[position]) {
                binding.questionQuestion.text = question
                binding.questionAnswer.text = answer
                binding.questionCategory.text = category?.title
                binding.questionValue.text = value.toString()
                binding.questionDate.text = airdate
            }
        }
    }

    inner class QuestionViewHolder(val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}