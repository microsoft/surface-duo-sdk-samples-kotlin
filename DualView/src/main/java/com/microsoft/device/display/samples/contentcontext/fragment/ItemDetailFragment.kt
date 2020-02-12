/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.contentcontext.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.contentcontext.Item
import com.microsoft.device.display.samples.contentcontext.R
import kotlinx.android.synthetic.main.fragment_item_detail.view.*

class ItemDetailFragment : Fragment() {
    private lateinit var mWebView: WebView
    @get:JavascriptInterface
    var lat = 0.0
        private set
    @get:JavascriptInterface
    var lng = 0.0
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (arguments?.getParcelable(Item.KEY) as Item?)?.apply {
            lat = location?.x?.toDouble() ?: 0.0
            lng = location?.y?.toDouble() ?: 0.0
            activity?.title = toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail,
                container, false)
        mWebView = view.web_view
        setupWebView()
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        mWebView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(this@ItemDetailFragment, "AndroidFunction")
            webViewClient = WebViewClient()
            loadUrl("file:///android_asset/googlemap.html")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(item: Item): ItemDetailFragment {
            return ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Item.KEY, item)
                }
            }
        }
    }
}