package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemsclicked {

    private lateinit var madapter: NewsAapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)
        data()
        madapter = NewsAapter(this)
        recyclerview.adapter = madapter
    }

    private fun data() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=120fead7bbb04a779388c1e623943c85"
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            Listener {
                val newsofJSONArray = it.getJSONArray("articles")
                val newsArray = ArrayList<newsdata>()
                for(i in 0 until newsofJSONArray.length()){
                    val newsjsonobject = newsofJSONArray.getJSONObject(i)
                    val news = newsdata(
                        newsjsonobject.getString("title"),
                        newsjsonobject.getString("author"),
                        newsjsonobject.getString("url"),
                        newsjsonobject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                madapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    override fun onitemclicked(item: newsdata) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}