package com.rental.article

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.rental.article.enum.ListType
import com.rental.article.model.Article
import com.rental.article.model.Content
import com.rental.article.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userListType = object : TypeToken<ArrayList<Article.Result>>() {}.type

        val jsonFileString = getJsonDataFromAsset(applicationContext, "sample.json")
        var gson = Gson()
        var data : List<Article.Result> = gson.fromJson(jsonFileString, userListType)

        data.forEach { item ->

            var content = gson.fromJson(item.content,Content.Result::class.java)


            if (content.contents != null) {
                content.contents.forEach { contentItem ->
                    if (contentItem.p != null) {
                        paragraph(this, mainActivity, contentItem.p!!)
                    }
                    else if (contentItem.h1 != null) {
                        h1(this, mainActivity, contentItem.h1!!)
                    }
                    else if (contentItem.h2 != null) {
                        h2(this, mainActivity, contentItem.h2!!)
                    }
                    else if(contentItem.ol != null) {
                        contentItem.ol!!.li.forEach { liItem ->
                            println(liItem.contents)
                            if (liItem.dot != null) {
                                articleList(this, mainActivity,"", liItem.dot!!, liItem.contents, ListType.NUMBER)
                            }
                            else {

                            }
                        }
                    }
                    else if(contentItem.ul != null) {
                        contentItem.ul!!.li.forEach { liItem ->
                            articleList(this, mainActivity,"", "", liItem.contents, ListType.BULLETED)
                        }
                    }
                    else if(contentItem.iconlist != null) {

                        if (contentItem.iconlist!!.alert != null) {
                            iconAlert(this, mainActivity, contentItem.iconlist!!.alert!!)
                        }

                        contentItem.iconlist!!.li.forEach {iconItem ->
                            articleList(this, mainActivity,iconItem.icon!!, "", iconItem.contents, ListType.ICON)
                        }
                    }
                    else if(contentItem.accordions != null) {
                        contentItem.accordions.forEach { accordionItem ->
                            accordionSection(this, mainActivity, accordionItem.title, accordionItem.contents)
                        }
                    }
                    else if (contentItem.blockquote != null) {
                        qoute(this, mainActivity, contentItem.blockquote!!)
                    }
                    else if(contentItem.img != null) {
                        image(this, mainActivity, contentItem.img!!)
                    }
                    else if(contentItem.video != null) {
                        video(this, mainActivity, contentItem.video!!)
                    }
                    else if(contentItem.download != null) {
                        download(this, mainActivity, contentItem.download!!)
                    }
                }
            }

            if(content.timeline != null) {
                content.timeline!!.forEach {timeLineItem  ->
                    timelineTitle(this, mainActivity,timeLineItem.section)
                    timeLineItem.events.forEach { eventItem ->
                        timeline(this, mainActivity, eventItem)
                    }
                }
            }
            if (content.table != null) {
                table(this, mainActivity, content.table!!, content.table!![0].count(), false, false)
            }
            if(content.tableWithColHeaders != null) {
                table(this, mainActivity, content.tableWithColHeaders!!, content.tableWithColHeaders!![0].count(), false, true)
            }
            if(content.tableWithRowHeaders != null) {
                table(this, mainActivity, content.tableWithRowHeaders!!, content.tableWithRowHeaders!![0].count(), true, false)
            }
            if(content.tableWithRowAndColHeaders != null) {
                table(this, mainActivity, content.tableWithRowAndColHeaders!!, content.tableWithRowAndColHeaders!![0].count(), true, true)
            }
        }
    }
}
