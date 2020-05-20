package com.moviedb.network

data class TMDbVideos(
    val id: Int,
    val videos: Videos
){
    data class Videos(
        val id: Int,
        val key: String,
        val name: String,
        val site: String,
        val size: Int,
        val type: String
    )
}