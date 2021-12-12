package adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia_app.databinding.QuestionItemBinding
import model.Question

class QuestionRecycleViewAdaptor(
    private val dataSource: ArrayList<Question>
) : RecyclerView.Adapter<QuestionRecycleViewAdaptor.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun updateData(qs: List<Question>) {
//        dataSource.clear()
//        notifyDataSetChanged()

        dataSource.addAll(qs)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        with(holder) {
            with(dataSource[position]) {
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