/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.complementarycontext.fragment

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.complementarycontext.R
import com.microsoft.device.display.samples.complementarycontext.Slide

class SlideFragment : Fragment() {

    private var content: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_slide_layout, container, false)
        val textView = view.findViewById<TextView>(R.id.text_view)
        if (arguments != null) {
            content = arguments?.getString(CONTENT)
            textView.text = content
        }

        return view
    }

    override fun toString(): String {
        return content ?: ""
    }

    companion object {
        private const val CONTENT = "content"
        private fun newInstance(slide: Slide): SlideFragment {
            return SlideFragment().apply {
                this.arguments = Bundle().apply {
                    putString(CONTENT, slide.content)
                }
            }
        }

        // Init fragments for ViewPager
        fun getFragments(slides: ArrayList<Slide>?): SparseArray<SlideFragment> {
            val fragments = SparseArray<SlideFragment>()
            for ((i, slide) in slides!!.withIndex()) {
                fragments.put(i, newInstance(slide))
            }
            return fragments
        }
    }
}
