import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.model.MangaChapter

class ChaptersAdapter(
    private val onClickChapter: (MangaChapter) -> Unit
) : ListAdapter<MangaChapter, ChaptersAdapter.ViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = TextView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.textView.text = "Chap " + item.attributes.chapter + if(item.attributes.title == null) "" else ": "+ item.attributes.title
        holder.textView.setOnClickListener {
            onClickChapter(item)
        }
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaChapter>(){
            override fun areItemsTheSame(
                oldItem: MangaChapter,
                newItem: MangaChapter
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MangaChapter,
                newItem: MangaChapter
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}