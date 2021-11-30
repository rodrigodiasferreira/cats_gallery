package br.org.venturus.example.dto.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.org.venturus.example.R
import br.org.venturus.example.model.imgur.Image
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

data class ImageDataBinding(
    var image: Image = Image(),
    val url: MutableLiveData<String> = MutableLiveData<String>().also{it.value = image.link}
): ViewModel()

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .thumbnail(0.25f)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(600,900)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.broken_image)
        .into(view)
}