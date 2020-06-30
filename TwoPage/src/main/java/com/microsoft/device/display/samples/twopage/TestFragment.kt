/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.twopage

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_layout.view.*

class TestFragment : Fragment() {
    private lateinit var text: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        val mTextView = view.text_view
        arguments?.apply {
            text = this.getString("text") ?: ""
            mTextView?.text = text
        }
        return view
    }

    override fun toString(): String {
        return text
    }

    companion object {
        private fun newInstance(text: String) = TestFragment().apply {
            arguments = Bundle().apply {
                putString("text", text)
            }
        }

        // Init fragments for ViewPager
        val fragments: SparseArray<TestFragment>
            get() {
                val fragments = SparseArray<TestFragment>()
                for (i in 0..9) {
                    fragments.put(i, newInstance("Page " + (i + 1)))
                }
                return fragments
            }
    }
}