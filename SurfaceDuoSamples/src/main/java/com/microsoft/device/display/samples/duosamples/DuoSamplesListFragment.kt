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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_duo_samples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindSampleButton(R.id.button_1_drag_and_drop_try, DragAndDropActivity::class.java)
        bindGithubButton(R.id.link_1_dear_and_drop, GithubRepoLinks.GIT_LINK_DRAG_AND_DROP)

        bindSampleButton(R.id.button_2_dual_view, DualViewActivity::class.java)
        bindGithubButton(R.id.link_2_dual_view, GithubRepoLinks.GIT_LINK_DUAL_VIEW)

        bindSampleButton(R.id.button_3_hinge_angle, HingeAngleActivity::class.java)
        bindGithubButton(R.id.link_3_hinge_angle, GithubRepoLinks.GIT_LINK_HINGE_ANGLE)

        bindSampleButton(R.id.button_4_list_detail, ListDetailsActivity::class.java)
        bindGithubButton(R.id.link_4_list_detail, GithubRepoLinks.GIT_LINK_LIST_DETAILS)

        bindSampleButton(R.id.button_5_pen_events, PenEventsActivity::class.java)
        bindGithubButton(R.id.link_5_pen_events, GithubRepoLinks.GIT_LINK_PEN_EVENTS)

        bindSampleButton(R.id.button_6_two_page, TwoPageActivity::class.java)
        bindGithubButton(R.id.link_6_two_page, GithubRepoLinks.GIT_LINK_TWO_PAGE)

        bindSampleButton(R.id.button_7_companion_pane, CompanionPaneActivity::class.java)
        bindGithubButton(R.id.link_7_companion_pane, GithubRepoLinks.GIT_LINK_COMPANION_PANE)

        bindSampleButton(R.id.button_8_extended_canvas, ExtendedCanvasActivity::class.java)
        bindGithubButton(R.id.link_8_extended_canvas, GithubRepoLinks.GIT_LINK_EXTENDED_CANVAS)

        bindSampleButton(R.id.button_9_intent_to_second_screen, IntentToSecondScreenFirstActivity::class.java)
        bindGithubButton(R.id.link_9_intent_to_second_screen, GithubRepoLinks.GIT_LINK_SECOND_SCREEN)

        bindSampleButton(R.id.button_10_qualifiers, QualifiersActivity::class.java)
        bindGithubButton(R.id.link_10_qualifiers, GithubRepoLinks.GIT_LINK_QUALIFIERS)

        bindSampleButton(R.id.button_11_multiple_instances, MultipleInstancesMainActivity::class.java)
        bindGithubButton(R.id.link_11_multiple_instances, GithubRepoLinks.GIT_LINK_MULTIPLE_INSTANCES)
    }

    private fun bindSampleButton(buttonId: Int, activityToOpen: Class<*>) {
        view?.findViewById<Button>(buttonId)?.setOnClickListener {
            val intent = Intent(requireActivity(), activityToOpen)
            startActivity(intent)
        }
    }

    private fun bindGithubButton(buttonId: Int, link: String) {
        view?.findViewById<Button>(buttonId)?.setOnClickListener {
            openGitLink(link)
        }
    }

    private fun openGitLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(browserIntent)
    }
}