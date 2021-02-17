/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.duosamples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.microsoft.device.display.samples.companionpane.CompanionPaneActivity
import com.microsoft.device.display.samples.draganddrop.DragAndDropActivity
import com.microsoft.device.display.samples.dualview.DualViewActivity
import com.microsoft.device.display.samples.extendcanvas.ExtendedCanvasActivity
import com.microsoft.device.display.samples.hingeangle.HingeAngleActivity
import com.microsoft.device.display.samples.intentsecondscreen.IntentToSecondScreenFirstActivity
import com.microsoft.device.display.samples.listdetail.ListDetailsActivity
import com.microsoft.device.display.samples.multipleinstances.MultipleInstancesMainActivity
import com.microsoft.device.display.samples.pen.PenEventsActivity
import com.microsoft.device.display.samples.qualifiers.QualifiersActivity
import com.microsoft.device.display.samples.twopage.TwoPageActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DuoSamplesListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_duo_samples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_1_drag_and_drop_try).setOnClickListener {
            val intent = Intent(requireActivity(), DragAndDropActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_1_dear_and_drop).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_DRAG_AND_DROP)
        }

        view.findViewById<Button>(R.id.button_2_dual_view).setOnClickListener {
            val intent = Intent(requireActivity(), DualViewActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_2_dual_view).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_DUAL_VIEW)
        }

        view.findViewById<Button>(R.id.button_3_hinge_angle).setOnClickListener {
            val intent = Intent(requireActivity(), HingeAngleActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_3_hinge_angle).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_HINGE_ANGLE)
        }

        view.findViewById<Button>(R.id.button_4_list_details).setOnClickListener {
            val intent = Intent(requireActivity(), ListDetailsActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_4_list_details).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_LIST_DETAILS)
        }

        view.findViewById<Button>(R.id.button_5_pen_events).setOnClickListener {
            val intent = Intent(requireActivity(), PenEventsActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_5_pen_events).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_PEN_EVENTS)
        }

        view.findViewById<Button>(R.id.button_6_two_page).setOnClickListener {
            val intent = Intent(requireActivity(), TwoPageActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_6_two_page).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_TWO_PAGE)
        }

        view.findViewById<Button>(R.id.button_7_companion_pane).setOnClickListener {
            val intent = Intent(requireActivity(), CompanionPaneActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_7_companion_pane).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_COMPANION_PANE)
        }

        view.findViewById<Button>(R.id.button_8_extended_canvas).setOnClickListener {
            val intent = Intent(requireActivity(), ExtendedCanvasActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_8_extended_canvas).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_EXTENDED_CANVAS)
        }

        view.findViewById<Button>(R.id.button_9_intent_to_second_screen).setOnClickListener {
            val intent = Intent(requireActivity(), IntentToSecondScreenFirstActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_9_intent_to_second_screen).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_SECOND_SCREEN)
        }

        view.findViewById<Button>(R.id.button_10_qualifiers).setOnClickListener {
            val intent = Intent(requireActivity(), QualifiersActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_10_qualifiers).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_QUALIFIERS)
        }

        view.findViewById<Button>(R.id.button_11_multiple_instances).setOnClickListener {
            val intent = Intent(requireActivity(), MultipleInstancesMainActivity::class.java)
            startActivity(intent)
        }
        view.findViewById<View>(R.id.link_11_multiple_instances).setOnClickListener {
            openGitLink(GithubRepoLinks.GIT_LINK_MULTIPLE_INSTANCES)
        }
    }

    private fun openGitLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(browserIntent)
    }
}