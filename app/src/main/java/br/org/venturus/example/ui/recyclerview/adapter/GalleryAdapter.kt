package br.org.venturus.example.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import br.org.venturus.example.R
import br.org.venturus.example.databinding.GalleryItemBinding
import br.org.venturus.example.dto.binding.ImageDataBinding
import br.org.venturus.example.model.imgur.Image

class GalleryAdapter(
    private val context: Context,
    private val images: MutableList<Image> = mutableListOf(),
    var onItemClickListener: (image: Image) -> Unit = {}
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var _binding: GalleryItemBinding? = null
    private val viewBinding get() = _binding!!
    var recyclerView: RecyclerView? = null

    init {
        setHasStableIds(true);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        _binding = GalleryItemBinding.inflate(inflater, parent, false)
        viewSizeCalculation(parent)
        return ViewHolder(viewBinding).also {
            viewBinding.lifecycleOwner = it
        }
    }

    private fun viewSizeCalculation(parent: ViewGroup) {
        val layoutParams = viewBinding.root.layoutParams
        val spanCount: Int = parent.context.resources.getInteger(R.integer.span_count)
        layoutParams.width = parent.width / spanCount
        layoutParams.height = layoutParams.width * 3 / 2
        viewBinding.root.layoutParams = layoutParams
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun update(newImages: List<Image>) {
        notifyItemRangeRemoved(0, images.size)
        images.clear()
        images.addAll(newImages)
        notifyItemRangeInserted(0, newImages.size)
    }

    inner class ViewHolder(private val galleryBinding: GalleryItemBinding) :
        RecyclerView.ViewHolder(galleryBinding.root), LifecycleOwner {
        private val registry = LifecycleRegistry(this)

        private lateinit var image: Image

        init {
            galleryBinding.root.setOnClickListener {
                if (::image.isInitialized) {
                    onItemClickListener(image)
                }
            }
        }

        fun bind(image: Image) {
            this.image = image
            galleryBinding.image = ImageDataBinding(image)
        }

        override fun getLifecycle(): Lifecycle = registry

    }

}

