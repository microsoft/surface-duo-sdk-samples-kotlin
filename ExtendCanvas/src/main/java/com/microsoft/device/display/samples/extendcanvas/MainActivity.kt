/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *   
 */
package com.microsoft.device.display.samples.extendcanvas

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.extend.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu);
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_bar, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        if (searchManager.getSearchableInfo(componentName) != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.isIconifiedByDefault = false
        searchView.isFocusable = true
        searchView.requestFocusFromTouch()
        searchView.onActionViewExpanded()
        return true
    }
}