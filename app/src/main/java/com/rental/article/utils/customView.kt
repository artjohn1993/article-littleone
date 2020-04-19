package com.rental.article.utils


import android.R.attr.*
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import com.rental.article.enum.ListType
import com.rental.article.model.Content
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.support.design.button.MaterialButton
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.util.DisplayMetrics
import android.view.Display
import android.view.Gravity
import org.jetbrains.anko.*
import android.view.animation.TranslateAnimation
import android.webkit.*
import android.widget.*
import com.rental.article.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.custom.style
import kotlin.math.absoluteValue
import org.jetbrains.anko.support.v4.contentLoadingProgressBar

val colorLightGray = "#4C4C4C"
val colorBlack = "#2C2C2C"
val colorPink = "#BC8590"

fun paragraph(context: Context,layout: LinearLayout, data: String) {

    val text = TextView(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text.text = Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT)
    } else {
        text.text = Html.fromHtml(data)
    }

    text.setPadding(0, 0,0,25)
    text.textSize = 15f
    text.movementMethod = LinkMovementMethod.getInstance()
    text.textColor = Color.parseColor(colorLightGray)
    text.setLineSpacing(1.3f,1.3f)
    layout.addView(text)
}

fun h1(context: Context, layout: LinearLayout, data: String?) {

    val text = TextView(context)
    text.text = data
    text.textSize = 25f
    text.typeface = Typeface.DEFAULT_BOLD
    text.setPadding(0, 0,0,25)
    text.textColor = Color.parseColor(colorBlack)

    layout.addView(text)
}

fun h2(context: Context,layout: LinearLayout, data: String) {

    val text = TextView(context)
    text.text = data
    text.textSize = 18f
    text.typeface = Typeface.DEFAULT_BOLD
    text.setPadding(0, 0,0,15)
    text.textColor = Color.parseColor(colorBlack)
    layout.addView(text)
}

fun articleList(context: Context,layout: LinearLayout, icon : String, number: String, paragraph: String, type : ListType) {
    val color = Color.parseColor("#444B78")

    val content = TextView(context)
    content.text = paragraph
    content.setPadding(30, 0,0,0)
    content.textColor = Color.parseColor(colorLightGray)
    content.setLineSpacing(1.1f, 1.1f)

    val container = LinearLayout(context)
    container.orientation = LinearLayout.HORIZONTAL

    when(type) {
        ListType.NUMBER -> {
            val numberText = TextView(context)
            numberText.text = number.plus(".")
            numberText.textColor = color
            container.addView(numberText)
            container.setPadding(0, 0,0,20)
        }
        ListType.BULLETED -> {

            val numberText = TextView(context)
            numberText.text = "\u25CF"
            numberText.textColor = color
            container.addView(numberText)
            container.setPadding(0, 0,0,20)
        }
        ListType.ICON -> {
            val icon = ImageView(context)
            icon.image = context.resources.getDrawable(com.rental.article.R.drawable.home_icon)
            icon.layoutParams = ViewGroup.LayoutParams(80,80)
            icon.setPadding(0, 10,0,0)

            container.addView(icon)
            container.setPadding(0, 0,0,30)
        }
    }

    container.addView(content)
    layout.addView(container)
}

fun iconAlert(context: Context,layout: LinearLayout, alertContent: String) {

    val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    param.bottomMargin = 25
    val container = LinearLayout(context)
    container.orientation = LinearLayout.HORIZONTAL
    container.setPadding(50,25,50,25)
    container.setBackgroundResource(com.rental.article.R.drawable.alert_background)
    container.layoutParams = param

    val icon = ImageView(context)
    icon.image = context.resources.getDrawable(com.rental.article.R.mipmap.ic_information)
    icon.layoutParams = LinearLayout.LayoutParams(80,80)
    icon.setPadding(0, 0,10,0)

    val content = TextView(context)
    content.text = alertContent
    content.setPadding(10, 0,0,0)
    content.setTextColor(Color.WHITE)


    container.addView(icon)
    container.addView(content)

    layout.addView(container)
}

fun accordionSection(context: Context,layout: LinearLayout, titleVal: String, contentVal: List<Content.Contents>) {

    var contentHeight = 0

    val display = context.windowManager.defaultDisplay
    var customWidth = display.width - 90

    var param = LinearLayout.LayoutParams(customWidth,LinearLayout.LayoutParams.WRAP_CONTENT)
    //param.height = 200

    var container = LinearLayout(context)
    container.orientation = LinearLayout.VERTICAL
    container.layoutParams = param
    container.setPadding(10,10,10,10)
    container.setBackgroundResource(com.rental.article.R.drawable.border)

    var headerContainer = LinearLayout(context)
    headerContainer.orientation = LinearLayout.HORIZONTAL
    headerContainer.weightSum = 1f


    val titleParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    titleParam.weight = 1f
    titleParam.gravity = Gravity.CENTER_VERTICAL
    val title = TextView(context)
    title.text = titleVal
    title.textSize = 15f
    title.setTypeface(title.typeface, Typeface.BOLD)
    title.layoutParams = titleParam
    title.textColor = Color.parseColor(colorBlack)


    val arrowParam = LinearLayout.LayoutParams(80,80)
    arrowParam.gravity = Gravity.RIGHT
    val arrow = ImageButton(context)
    arrow.image = context.resources.getDrawable(com.rental.article.R.mipmap.ic_arrow_down)
    //arrow.layoutParams = arrowParam
    arrow.backgroundColor = Color.TRANSPARENT
    arrow.setColorFilter(Color.parseColor("#D81B60"))


    headerContainer.addView(title)
    headerContainer.addView(arrow)
    container.addView(headerContainer)

    val content = TextView(context)
    content.textColor = Color.parseColor(colorBlack)
    content.setLineSpacing(1.3f,1.3f)

    var contentHtmlString = ""
    for (item in contentVal) {
        if (item.p != null) {
            val perParagraph = "<p>".plus(item.p).plus("</p><br>")
            contentHtmlString = contentHtmlString.plus(perParagraph)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.text = Html.fromHtml(contentHtmlString, Html.FROM_HTML_MODE_COMPACT)
            } else {
                content.text = Html.fromHtml(contentHtmlString)
            }

            container.addView(content)
        }
        if (item.h1 != null) {
            h1(context, container, item.h1)
        }
        if (item.h2 != null) {
            h2(context, container, item.h2!!)
        }
        if(item.ol != null) {
            item.ol!!.li.forEach { liItem ->
                if (liItem.dot != null) {
                    articleList(context, container,"", liItem.dot!!, liItem.contents, ListType.NUMBER)
                }
                else {

                }
            }
        }
        if(item.ul != null) {
            item.ul!!.li.forEach { liItem ->
                articleList(context, container,"", "", liItem.contents, ListType.BULLETED)
            }
        }
        if(item.iconlist != null) {

            if (item.iconlist!!.alert != null) {
                iconAlert(context, container, item.iconlist!!.alert!!)
            }

            item.iconlist!!.li.forEach {iconItem ->
                articleList(context, container,iconItem.icon!!, "", iconItem.contents, ListType.ICON)
            }
        }
    }


    headerContainer.setOnClickListener {
        if(container.height == 200) {
            val newHeight = contentHeight + 20
            slideView(container,200,newHeight)
            arrow.image = context.resources.getDrawable(com.rental.article.R.mipmap.ic_arrow_up)
        }
        else {
            val newHeight = contentHeight + 20
                slideView(container,newHeight,200)
            arrow.image = context.resources.getDrawable(com.rental.article.R.mipmap.ic_arrow_down)
        }
    }


    layout.addView(container)

    layout.post {
        contentHeight = container.height
        container.layoutParams.height = 200
        container.requestLayout()
    }
}

fun slideView(view: View, currentHeight: Int, newHeight: Int) {
    val slideAnimator = ValueAnimator.ofInt(currentHeight, newHeight)
    slideAnimator.duration = 300

    slideAnimator.addUpdateListener {animation1 ->
        val value: Int = animation1.getAnimatedValue() as Int
        view.layoutParams.height = value.absoluteValue
        view.requestLayout()
    }

    val animationSet = AnimatorSet()
    animationSet.play(slideAnimator)
    animationSet.start()
}

fun qoute(context: Context,layout: LinearLayout, qouteValue : String) {
    val container = LinearLayout(context)
    container.gravity = Gravity.CENTER
    container.orientation = LinearLayout.VERTICAL

    val image = ImageView(context)
    image.setImageResource(com.rental.article.R.mipmap.ic_qoute)
    val imageParam = LinearLayout.LayoutParams(100, 100)
    imageParam.topMargin = 20
    imageParam.bottomMargin = 20
    image.layoutParams = imageParam

    var content = TextView(context)
    content.text = qouteValue
    content.textColor = Color.parseColor(colorPink)
    content.gravity = Gravity.CENTER
    val contentParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    contentParam.bottomMargin = 40
    content.setLineSpacing(1.3f,1.3f)


    container.addView(image)
    container.addView(content)

    layout.addView(container)
}

fun image(context: Context, layout: LinearLayout, url: String) {

    val displayMetrics = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(displayMetrics)

    val image = ImageView(context)
    val imageParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    imageParam.bottomMargin = 25
    image.layoutParams = imageParam
    image.backgroundColor = Color.RED


    Picasso.with(context)
        .load(com.rental.article.R.drawable.sample_image)
        .resize(displayMetrics.widthPixels - 100,0)
        .into(image)


    layout.addView(image)
}

fun video(context: Context, layout: LinearLayout, url: String) {
    val displayMetrics = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(displayMetrics)
    val param = LinearLayout.LayoutParams(displayMetrics.widthPixels - 30, displayMetrics.widthPixels/2)

    val webView = WebView(context)
    webView.layoutParams = param
    webView.settings.javaScriptEnabled = true
    webView.settings.defaultTextEncodingName = "utf-8"
    webView.webViewClient = WebViewClient()
    webView.setInitialScale(1)
    webView.settings.defaultZoom = WebSettings.ZoomDensity.FAR
    webView.settings.loadWithOverviewMode = true
    webView.settings.useWideViewPort = true
    webView.loadUrl(url)

    layout.addView(webView)
}


fun download(context: Context, layout: LinearLayout, download : Content.Download) {

    //    val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45)
    //    param.height = 45
    val button = MaterialButton(context)
    val data = download.filesizeKb /1000
    val title = "  ".plus(download.label).plus(" ").plus(data)
    button.text = title
    button.icon = (context.resources.getDrawable(R.mipmap.ic_download))
    button.iconSize = 50
    button.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
    button.backgroundTintList = context.resources.getColorStateList(R.color.colorPink)
    button.transformationMethod = null
    button.setPadding(10,60,10,60)

    layout.addView(button)
}

fun timeline(context: Context, layout: LinearLayout , data: Content.Events) {
    val displayMetrics = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(displayMetrics)

    val container = LinearLayout(context)
    container.orientation = LinearLayout.HORIZONTAL
    //container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    val line = View(context)
    val lineParam = LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT)
    lineParam.marginStart = 50
    line.layoutParams = lineParam
    line.backgroundColor = Color.RED

    val iconParam = LinearLayout.LayoutParams(80,80)
    iconParam.gravity = Gravity.CENTER_VERTICAL
    iconParam.marginStart = -40
    val icon = ImageView(context)
    icon.image = context.resources.getDrawable(com.rental.article.R.drawable.home_icon)
    icon.layoutParams = iconParam

    val arrowParam = LinearLayout.LayoutParams(80,80)
    arrowParam.gravity = Gravity.CENTER_VERTICAL
    arrowParam.setMargins(40,0,-15,0)
    val arrow = ImageView(context)
    arrow.image = context.resources.getDrawable(com.rental.article.R.drawable.ic_triangle)
        arrow.setColorFilter(Color.parseColor("#f9fafd"))
    arrow.layoutParams = arrowParam


    val contentConParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    contentConParam.setMargins(0,25,0,25)
    val contentContainer = RelativeLayout(context)
    contentContainer.setPadding(20, 20,20,20)
    contentContainer.layoutParams = contentConParam
    contentContainer.setBackgroundResource(R.drawable.backgound_round_gray)


    container.addView(line)
    container.addView(icon)
    container.addView(arrow)
    if (data.alert == null) {
        timelineContent(context, contentContainer, data)
        container.addView(contentContainer)
    }
    else {
        icon.image = null
        arrow.image = null
        iconAlert(context,container, data.alert!!)
    }

    layout.addView(container)

}

fun timelineContent(context: Context, layout: RelativeLayout , data: Content.Events) {
    val nameParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
    nameParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
    nameParam.addRule(RelativeLayout.ALIGN_PARENT_TOP)
    val contentName = TextView(context)
    contentName.text = data.name
    contentName.layoutParams = nameParam
    contentName.textColor = Color.parseColor(colorBlack)
    contentName.typeface = Typeface.DEFAULT_BOLD
    contentName.setPadding(0,10,0,0)

    val timeParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,50)
    timeParam.addRule(RelativeLayout.ALIGN_PARENT_END)
    timeParam.addRule(RelativeLayout.ALIGN_PARENT_TOP)
    val contentTime = TextView(context)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        contentTime.text = Html.fromHtml(data.time, Html.FROM_HTML_MODE_COMPACT)
    } else {
        contentTime.text = Html.fromHtml(data.time)
    }
    contentTime.layoutParams = timeParam
    contentTime.textColor = Color.parseColor(colorBlack)
    contentTime.setPadding(0,10,0,0)


    val descriptionParam = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
    descriptionParam.addRule(RelativeLayout.ALIGN_BOTTOM,contentName.id)
    descriptionParam.topMargin = 80

    val contentDescription = TextView(context)
    contentDescription.text = data.description
    contentDescription.layoutParams = descriptionParam
    contentDescription.textColor = Color.parseColor(colorLightGray)

    layout.addView(contentName)
    layout.addView(contentTime)
    layout.addView(contentDescription)
}

fun timelineTitle(context: Context, layout: LinearLayout, title: String) {
    val containerParam = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    val container = RelativeLayout(context)
    container.setPadding(0,0,0,0)
    container.layoutParams = containerParam

    val bulletParam = RelativeLayout.LayoutParams(30,30)
    bulletParam.setMargins(37,0,0,0)
    bulletParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    val bullet = ImageView(context)
    bullet.setImageResource(R.mipmap.ic_circle)
    bullet.layoutParams = bulletParam
    bullet.setColorFilter(Color.RED)

    val titleParam = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    titleParam.setMargins(100,0,0,0)
    titleParam.addRule(RelativeLayout.ALIGN_PARENT_TOP)
    val titleText = TextView(context)
    titleText.text = title
    titleText.setTextColor(Color.RED)
    titleText.layoutParams = titleParam
    titleText.typeface = Typeface.DEFAULT_BOLD
    titleText.setPadding(0,-10,0,-10)

    container.addView(bullet)
    container.addView(titleText)
    layout.addView(container)
}

fun table(context: Context,layout: LinearLayout,data : List<List<String>>, numberColumn: Int, isRowHeader:Boolean, isColHeader:Boolean) {
    val displayMetrics = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(displayMetrics)

    val scrollViewParam = LinearLayout.LayoutParams(displayMetrics.widthPixels - 32, LinearLayout.LayoutParams.WRAP_CONTENT)
    scrollViewParam.bottomMargin = 30
    val scrollView = ScrollView(context)
    scrollView.scrollBarStyle = ScrollView.SCROLLBARS_OUTSIDE_INSET
    scrollView.layoutParams = scrollViewParam

    val horizontalViewParam = LinearLayout.LayoutParams(displayMetrics.widthPixels - 35, LinearLayout.LayoutParams.WRAP_CONTENT)
    val horizontalScrollView = HorizontalScrollView(context)
    horizontalScrollView.layoutParams = horizontalViewParam

    val tableViewParam = LinearLayout.LayoutParams(displayMetrics.widthPixels - 35, LinearLayout.LayoutParams.MATCH_PARENT)
    tableViewParam.setMargins(10,0,10,0)
    val tableView = TableLayout(context)
    tableView.layoutParams = tableViewParam

    val swipeParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    swipeParam.setMargins(0,20,0,50)
    val swipeContainer = LinearLayout(context)
    swipeContainer.orientation = LinearLayout.HORIZONTAL
    swipeContainer.layoutParams = swipeParam

    val prevParam = LinearLayout.LayoutParams(70,70)
    val prevButton = ImageButton(context)
    prevButton.setImageResource(R.mipmap.ic_prev)
    prevButton.layoutParams = prevParam
    prevButton.backgroundColor = Color.TRANSPARENT
    prevButton.setPadding(20,20,20,20)

    val nextParam = LinearLayout.LayoutParams(70,70)
    val nextButton = ImageButton(context)
    nextButton.setImageResource(R.mipmap.ic_next)
    nextButton.layoutParams = nextParam
    nextButton.backgroundColor = Color.TRANSPARENT
    nextButton.setPadding(20,20,20,20)

    val textParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
    textParam.weight = 1f
    textParam.gravity = Gravity.CENTER
    val textView = TextView(context)
    textView.text = "Swipe or tap the  arrows to see more"
    textView.textColor = Color.BLACK
    textView.layoutParams = textParam
    textView.textAlignment = LinearLayout.TEXT_ALIGNMENT_CENTER

    var firstIndex = 0
    data.forEach { item ->
        val rowParam = TableRow.LayoutParams(displayMetrics.widthPixels - 35, 150)
        val row = TableRow(context)
        row.layoutParams = rowParam
        row.gravity = Gravity.CENTER

        var secondIndex = 0
        item.forEach { text ->

            val contentParam = TableRow.LayoutParams((displayMetrics.widthPixels - 100) / 2,150)
            contentParam.setMargins(5,0,5,0)
            val content = TextView(context)
            content.layoutParams = contentParam
            content.text = text
            content.width = (displayMetrics.widthPixels - 100) / 2
            content.height = 150
            content.gravity = Gravity.CENTER
            content.textColor = Color.BLACK

            content.setPadding(30,10,30,10)

            if (isColHeader && isRowHeader) {
                if (firstIndex == 0) {
                    if(text != "") {
                        content.backgroundColor = Color.parseColor("#eb5eaa")
                        content.textColor = Color.WHITE
                    }
                }
                else if (secondIndex == 0) {
                    content.backgroundResource = R.drawable.border_gray_background
                }
                else {
                    content.backgroundResource = R.drawable.border_background
                }
            }
            else if(isColHeader) {
                if (firstIndex == 0) {
                    content.backgroundColor = Color.parseColor("#eb5eaa")
                    content.textColor = Color.WHITE
                }
                else {
                    content.backgroundResource = R.drawable.border_background
                }
            }
            else if (isRowHeader) {
                if (secondIndex == 0) {
                    content.backgroundResource = R.drawable.border_gray_background
                }
                else {
                    content.backgroundResource = R.drawable.border_background
                }
            }
            else {
                content.backgroundResource = R.drawable.border_background
            }

            row.addView(content)
            secondIndex++
        }
        tableView.addView(row)
        firstIndex++
    }



    horizontalScrollView.addView(tableView)
    scrollView.addView(horizontalScrollView)
    layout.addView(scrollView)

    if (numberColumn > 2) {

        swipeContainer.addView(prevButton)
        swipeContainer.addView(textView)
        swipeContainer.addView(nextButton)
        layout.addView(swipeContainer)
    }

    layout.post {
        nextButton.setOnClickListener {
            horizontalScrollView.fullScroll(View.FOCUS_RIGHT)
        }
        prevButton.setOnClickListener {
            horizontalScrollView.fullScroll(View.FOCUS_LEFT)
        }
    }
}





