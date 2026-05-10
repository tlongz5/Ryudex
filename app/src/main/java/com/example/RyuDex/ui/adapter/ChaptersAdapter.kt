import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO

class ChaptersAdapter(
    private val onClickChapter: (MangaChapterDTO) -> Unit
) : ListAdapter<MangaChapterDTO, ChaptersAdapter.ViewHolder>(DIFF_UTIL) {
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
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaChapterDTO>(){
            override fun areItemsTheSame(
                oldItem: MangaChapterDTO,
                newItem: MangaChapterDTO
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: MangaChapterDTO,
                newItem: MangaChapterDTO
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}