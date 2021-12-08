/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *  *
 *
 */

package com.microsoft.device.display.samples.duosamples.samples

import android.content.Context
import com.microsoft.device.display.samples.companionpane.CompanionPaneActivity
import com.microsoft.device.display.samples.draganddrop.DragAndDropActivity
import com.microsoft.device.display.samples.dualview.DualViewActivity
import com.microsoft.device.display.samples.duosamples.GithubRepoLinks
import com.microsoft.device.display.samples.duosamples.R
import com.microsoft.device.display.samples.extendcanvas.ExtendedCanvasActivity
import com.microsoft.device.display.samples.hingeangle.HingeAngleActivity
import com.microsoft.device.display.samples.intentsecondscreen.IntentToSecondScreenFirstActivity
import com.microsoft.device.display.samples.listdetail.ListDetailsActivity
import com.microsoft.device.display.samples.multipleinstances.MultipleInstancesMainActivity
import com.microsoft.device.display.samples.pen.PenEventsActivity
import com.microsoft.device.display.samples.qualifiers.QualifiersActivity
import com.microsoft.device.display.samples.twopage.TwoPageActivity

/**
 * Utility class that return string and image resources to display the samples list and details.
 */
class SampleBuilder {
    companion object {
        fun getTitle(sample: Sample, context: Context) = context.getString(
            when (sample) {
                Sample.DRAG_AND_DROP -> R.string.drag_and_drop_app_name
                Sample.DUAL_VIEW -> R.string.dual_view_app_name
                Sample.HINGE_ANGLE -> R.string.hinge_angle_app_name
                Sample.LIST_DETAILS -> R.string.list_details_app_name
                Sample.PEN_EVENTS -> R.string.pen_events_app_name
                Sample.TWO_PAGE -> R.string.two_page_app_name
                Sample.COMPANION_PANE -> R.string.companion_pane_app_name
                Sample.EXTENDED_CANVAS -> R.string.extend_canvas_app_name
                Sample.INTENT_TO_SECOND_SCREEN -> R.string.intent_app_name
                Sample.QUALIFIERS -> R.string.qualifiers_app_name
                Sample.MULTIPLE_INSTANCES -> R.string.multiple_instances_app_name
            }
        )

        fun getThumbnail(sample: Sample) =
            when (sample) {
                Sample.DRAG_AND_DROP -> R.drawable.thumbnail_drag_and_drop
                Sample.DUAL_VIEW -> R.drawable.thumbnail_dual_view
                Sample.HINGE_ANGLE -> R.drawable.thumbnail_hinge_angle
                Sample.LIST_DETAILS -> R.drawable.thumbnail_list_details
                Sample.PEN_EVENTS -> R.drawable.thumbnail_pen_events
                Sample.TWO_PAGE -> R.drawable.thumbnail_two_page
                Sample.COMPANION_PANE -> R.drawable.thumbnail_companion_pane
                Sample.EXTENDED_CANVAS -> R.drawable.thumbnail_extended_canvas
                Sample.INTENT_TO_SECOND_SCREEN -> R.drawable.thumbnail_intent_to_second_screen
                Sample.QUALIFIERS -> R.drawable.thumbnail_qualifiers
                Sample.MULTIPLE_INSTANCES -> R.drawable.thumbnails_multiple_instances
            }

        fun getSimpleDescription(sample: Sample, context: Context) = context.getString(
            when (sample) {
                Sample.DRAG_AND_DROP -> R.string.drag_and_drop_info
                Sample.DUAL_VIEW -> R.string.dual_view_info
                Sample.HINGE_ANGLE -> R.string.hinge_angle_info
                Sample.LIST_DETAILS -> R.string.list_details_info
                Sample.PEN_EVENTS -> R.string.pen_events_info
                Sample.TWO_PAGE -> R.string.two_page_info
                Sample.COMPANION_PANE -> R.string.companion_pane_info
                Sample.EXTENDED_CANVAS -> R.string.extend_canvas_info
                Sample.INTENT_TO_SECOND_SCREEN -> R.string.intent_to_second_screen_info
                Sample.QUALIFIERS -> R.string.qualifiers_info
                Sample.MULTIPLE_INSTANCES -> R.string.multiple_instances_info
            }
        )

        fun getDetailedImage(sample: Sample) =
            when (sample) {
                Sample.DRAG_AND_DROP -> R.drawable.details_drag_and_drop
                Sample.DUAL_VIEW -> R.drawable.detailed_dual_view
                Sample.HINGE_ANGLE -> R.drawable.detailed_hinge_angle
                Sample.LIST_DETAILS -> R.drawable.detailed_list_detail
                Sample.PEN_EVENTS -> R.drawable.detailed_pen_events
                Sample.TWO_PAGE -> R.drawable.detailed_two_page
                Sample.COMPANION_PANE -> R.drawable.detailed_companion_pane
                Sample.EXTENDED_CANVAS -> R.drawable.detailed_extended_canvas
                Sample.INTENT_TO_SECOND_SCREEN -> R.drawable.detailed_intet_to_second_screen
                Sample.QUALIFIERS -> R.drawable.detailed_qualifiers
                Sample.MULTIPLE_INSTANCES -> R.drawable.detailed_multiple_instances
            }

        fun getDescription(sample: Sample, context: Context) = context.getString(
            when (sample) {
                Sample.DRAG_AND_DROP -> R.string.drag_and_drop_description
                Sample.DUAL_VIEW -> R.string.dual_view_description
                Sample.HINGE_ANGLE -> R.string.hinge_angle_description
                Sample.LIST_DETAILS -> R.string.list_details_description
                Sample.PEN_EVENTS -> R.string.pen_events_description
                Sample.TWO_PAGE -> R.string.two_page_description
                Sample.COMPANION_PANE -> R.string.companion_pane_description
                Sample.EXTENDED_CANVAS -> R.string.extend_canvas_description
                Sample.INTENT_TO_SECOND_SCREEN -> R.string.intent_to_second_screen_description
                Sample.QUALIFIERS -> R.string.qualifiers_description
                Sample.MULTIPLE_INSTANCES -> R.string.multiple_instances_description
            }
        )

        fun getGithubUrl(sample: Sample) =
            when (sample) {
                Sample.DRAG_AND_DROP -> GithubRepoLinks.GIT_LINK_DRAG_AND_DROP
                Sample.DUAL_VIEW -> GithubRepoLinks.GIT_LINK_DUAL_VIEW
                Sample.HINGE_ANGLE -> GithubRepoLinks.GIT_LINK_HINGE_ANGLE
                Sample.LIST_DETAILS -> GithubRepoLinks.GIT_LINK_LIST_DETAILS
                Sample.PEN_EVENTS -> GithubRepoLinks.GIT_LINK_PEN_EVENTS
                Sample.TWO_PAGE -> GithubRepoLinks.GIT_LINK_TWO_PAGE
                Sample.COMPANION_PANE -> GithubRepoLinks.GIT_LINK_COMPANION_PANE
                Sample.EXTENDED_CANVAS -> GithubRepoLinks.GIT_LINK_EXTENDED_CANVAS
                Sample.INTENT_TO_SECOND_SCREEN -> GithubRepoLinks.GIT_LINK_SECOND_SCREEN
                Sample.QUALIFIERS -> GithubRepoLinks.GIT_LINK_QUALIFIERS
                Sample.MULTIPLE_INSTANCES -> GithubRepoLinks.GIT_LINK_MULTIPLE_INSTANCES
            }

        fun getSampleActivity(sample: Sample) =
            when (sample) {
                Sample.DRAG_AND_DROP -> DragAndDropActivity::class.java
                Sample.DUAL_VIEW -> DualViewActivity::class.java
                Sample.HINGE_ANGLE -> HingeAngleActivity::class.java
                Sample.LIST_DETAILS -> ListDetailsActivity::class.java
                Sample.PEN_EVENTS -> PenEventsActivity::class.java
                Sample.TWO_PAGE -> TwoPageActivity::class.java
                Sample.COMPANION_PANE -> CompanionPaneActivity::class.java
                Sample.EXTENDED_CANVAS -> ExtendedCanvasActivity::class.java
                Sample.INTENT_TO_SECOND_SCREEN -> IntentToSecondScreenFirstActivity::class.java
                Sample.QUALIFIERS -> QualifiersActivity::class.java
                Sample.MULTIPLE_INSTANCES -> MultipleInstancesMainActivity::class.java
            }
    }
}