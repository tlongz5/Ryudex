import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RyuDex.databinding.ItemExploreFeatureBinding
import com.google.android.material.chip.Chip

class ExploreFeatureAdapter(
    private val genres: List<Pair<String?, String>>,
    private val callbackShowMore: () -> Unit,
    private val callbackClickTag: (List<String>) -> Unit,
    private val callbackDownload: () -> Unit
) : RecyclerView.Adapter<ExploreFeatureAdapter.ViewHolder>() {

    private val tagsPicked = mutableListOf<String>()

    inner class ViewHolder(
        private val binding: ItemExploreFeatureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            renderGenres()
            binding.tvShowMore.setOnClickListener {
                callbackShowMore()
            }

            binding.tvDownload.setOnClickListener {
                callbackDownload()
            }
        }

        private fun renderGenres() {
            binding.chipGroupGenre.removeAllViews()

            val data = genres.take(10)
            data.forEach { genre ->
                val chip = Chip(binding.root.context).apply {
                    text = genre.second
                    isCheckable = true
                    isClickable = true
                    chipStrokeWidth = 0f

                    isChecked = tagsPicked.contains(genre.first)

                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            tagsPicked.add(genre.first!!)
                        } else {
                            tagsPicked.remove(genre.first)
                        }
                        callbackClickTag(tagsPicked)
                    }

                    chipBackgroundColor = ColorStateList(
                        arrayOf(
                            intArrayOf(R.attr.state_checked),
                            intArrayOf()
                        ),
                        intArrayOf(
                            Color.parseColor("#2196F3"), // checked color
                            Color.parseColor("#EEEEEE")  // normal color
                        )
                    )

                    setTextColor(
                        ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_checked),
                                intArrayOf()
                            ),
                            intArrayOf(
                                Color.WHITE,
                                Color.BLACK
                            )
                        )
                    )
                }
                binding.chipGroupGenre.addView(chip)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemExploreFeatureBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}