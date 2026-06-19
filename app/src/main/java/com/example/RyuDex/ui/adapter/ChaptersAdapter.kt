import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemDetailChapterBinding
import com.example.RyuDex.model.dto.chapter.MangaChapterDTO

class ChaptersAdapter(
    private val onClickChapter: (MangaChapterDTO) -> Unit
) : ListAdapter<MangaChapterDTO, ChaptersAdapter.ViewHolder>(DIFF_UTIL) {

    class ViewHolder(val binding: ItemDetailChapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailChapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        // 3. Tận hưởng sức mạnh của ViewBinding: Gọi thẳng tên ID trong XML
        // Không cần khai báo biến, gọi qua holder.binding.[id_cua_view]
        val chapterNum = item.attributes.chapter ?: "0"
        holder.binding.tvChapterNumber.text = "Chapter $chapterNum"

        if (!item.attributes.title.isNullOrBlank()) {
            holder.binding.tvChapterTitle.text = item.attributes.title
            holder.binding.tvChapterTitle.visibility = View.VISIBLE
        } else {
            holder.binding.tvChapterTitle.visibility = View.GONE
        }

        // 4. Bắt sự kiện Click vào toàn bộ Item thông qua `binding.root`
        holder.binding.root.setOnClickListener {
            onClickChapter(item)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MangaChapterDTO>() {
            override fun areItemsTheSame(oldItem: MangaChapterDTO, newItem: MangaChapterDTO) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MangaChapterDTO, newItem: MangaChapterDTO) = oldItem == newItem
        }
    }
}
