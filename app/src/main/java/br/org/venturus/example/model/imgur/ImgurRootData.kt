package br.org.venturus.example.model.imgur

data class ImgurRootData(
    val data: MutableList<Imgur> = mutableListOf(),
    val success: Boolean = false,
    val status: Int = 0
    )
