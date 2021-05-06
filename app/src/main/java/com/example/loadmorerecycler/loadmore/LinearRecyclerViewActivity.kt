package com.example.loadmorerecycler.loadmore

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmorerecycler.R
import kotlinx.android.synthetic.main.activity_linear_recycler_view.*

@Suppress("DEPRECATION")
class LinearRecyclerViewActivity : AppCompatActivity() {

    lateinit var itemCells: ArrayList<String?>
    lateinit var loadMoreItemsCells: ArrayList<String?>
    lateinit var myAdapter: RecyclerviewAdapter
    lateinit var scrollListener: RecyclerViewLoadMoreScroll
    lateinit var layoutManager: RecyclerView.LayoutManager

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_recycler_view)

        setData()
        setAdapter()
        setLayoutManager()
        setScrollListener()

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(this.resources.getDrawable(R.drawable.divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }

    private fun setData() {
        itemCells = ArrayList()
        for (i in 0..50) {
            itemCells.add("Item $i")
        }
    }

    private fun setAdapter() {
        myAdapter = RecyclerviewAdapter(itemCells)
        recyclerView.adapter = myAdapter
    }

    private fun setLayoutManager() {
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    private fun setScrollListener() {
//        layoutManager = LinearLayoutManager(this)
        scrollListener = RecyclerViewLoadMoreScroll(layoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        myAdapter.addLoadingView()
        loadMoreItemsCells = ArrayList()

        val start = myAdapter.itemCount
        //Load 16 more items
        val end = start + 16
        Handler().postDelayed({
            for (i in start..end) {
                loadMoreItemsCells.add("Item $i")
            }
            myAdapter.removeLoadingView()
            myAdapter.addData(loadMoreItemsCells)
            scrollListener.setLoaded()
            myAdapter.notifyDataSetChanged()
        }, 3000)
    }
}
