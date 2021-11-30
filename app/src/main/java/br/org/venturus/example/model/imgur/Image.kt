package br.org.venturus.example.model.imgur

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "images")
data class Image (
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
    @Transient @Ignore val id: String? = null,
    @Transient @Ignore val title: String? = null,
    @Transient @Ignore val description: String? = null,
    @Transient @Ignore val datetime: Int? = null,
    @Ignore val type: String = "",
    @Transient @Ignore val animated: Boolean? = null,
    @Transient @Ignore val width: Int? = null,
    @Transient @Ignore val height: Int? = null,
    @Transient @Ignore val size: Int? = null,
    @Transient @Ignore val views: Int? = null,
    @Transient @Ignore val vote: String? = null,
    @Transient @Ignore val favorite: Boolean? = null,
    @Transient @Ignore val nfsw: String? = null,
    @Transient @Ignore val section: String? = null,
    @Transient @Ignore val account_url: String? = null,
    @Transient @Ignore val account_id: String? = null,
    @Transient @Ignore val is_ad: Boolean? = null,
    @Transient @Ignore val is_most_viral: Boolean? = null,
    @Transient @Ignore val has_sound: Boolean? = null,
    @Transient @Ignore val tags: MutableList<String>? = null,
    @Transient @Ignore val ad_type: Int? = null,
    @Transient @Ignore val ad_url: String? = null,
    @Transient @Ignore val edited: String? = null,
    @Transient @Ignore val in_gallery: Boolean? = null,
    var link: String = "",
    @Transient @Ignore val mp4_size: Int? = null,
    @Transient @Ignore val mp4: String? = null,
    @Transient @Ignore val gifv: String? = null,
    @Transient @Ignore val hls: String? = null,
    @Transient @Ignore val processing: Any? = null,
    @Transient @Ignore val comment_count: Int? = null,
    @Transient @Ignore val favorite_count: Int? = null,
    @Transient @Ignore val ups: Int? = null,
    @Transient @Ignore val downs: Int? = null,
    @Transient @Ignore val points: Int? = null,
    @Transient @Ignore val score: Int? = null
)
