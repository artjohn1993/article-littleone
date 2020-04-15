package com.rental.article.model


object Content {

    data class Result(
        var contents : List<Contents>,
        var timeline: List<Timeline>?,
        var table: List<List<String>>?,
        var tableWithColHeaders: List<List<String>>?,
        var tableWithRowHeaders: List<List<String>>?,
        var tableWithRowAndColHeaders: List<List<String>>?
    )

    data class Timeline(
        var section: String,
        var events : List<Events>
    )

    data class Events(
        var type: String?,
        var name: String?,
        var time: String?,
        var description: String?,
        var alert: String?
    )

    data class Contents(
        var h1: String?,
        var h2: String?,
        var p: String?,
        var accordions: List<Accordions>,
        var blockquote: String?,
        var img: String?,
        var video: String?,
        var download: Download?,
        var ol: Li?,
        var ul: Li?,
        var iconlist: Li?
    )

    data class Download(
        var filepath: String,
        var label: String,
        var filesizeKb: Float
    )

    data class Li(
        var li: List<LiContent>,
        var alert: String?
    )

    data class LiContent(
        var dot: String?,
        var contents: String,
        var icon: String?
    )

    data class Accordions(
        var title : String,
        var contents : List<Contents>
    )



}