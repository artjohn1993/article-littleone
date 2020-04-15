package com.rental.article.model

object Article {

    data class Result(
        var content : String,
        var created : Created,
        var lengthTime : Int,
        var lengthTimeText: String,
        var oldId: String,
        var programId: String,
        var tags: List<String>,
        var title: String,
        var type: String,
        var updated: Created,
        var sample: String?
    )

    data class Created(
        var nanoseconds : Int,
        var seconds : Int
    )
}