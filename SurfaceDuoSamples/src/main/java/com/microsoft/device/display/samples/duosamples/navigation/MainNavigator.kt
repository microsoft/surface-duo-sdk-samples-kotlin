package com.microsoft.device.display.samples.duosamples.navigation

import androidx.navigation.FoldableNavController
import com.microsoft.device.display.samples.duosamples.R
import com.microsoft.device.display.samples.duosamples.samples.Sample

/**
 * Wrapper for the Navigation Controller.
 * Contains logic to navigate from the sample list to each sample.
 */
class MainNavigator {
    private var navController: FoldableNavController? = null

    fun bind(navController: FoldableNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun navigateToSampleDescription() {
        navController?.navigate(R.id.action_sample_list_to_sample_details)
    }

    fun navigateUp() {
        navController?.navigateUp()
    }

    fun navigateFromListToSample(sample: Sample) {
        val action = when (sample) {
            Sample.DRAG_AND_DROP -> R.id.sample_list_to_drag_and_drop
            Sample.DUAL_VIEW -> R.id.sample_list_to_dual_view
            Sample.HINGE_ANGLE -> R.id.sample_list_to_hinge_angle
            Sample.LIST_DETAILS -> R.id.sample_list_to_list_details
            Sample.EXTENDED_CANVAS -> R.id.sample_list_to_extended_canvas
            Sample.TWO_PAGE -> R.id.sample_list_to_two_page
            Sample.COMPANION_PANE -> R.id.sample_list_to_companion_pane
            Sample.PEN_EVENTS -> R.id.sample_list_to_pen_events
            Sample.INTENT_TO_SECOND_SCREEN -> R.id.sample_list_to_intent_to_second_screen
            Sample.QUALIFIERS -> R.id.sample_list_to_qualifiers
            Sample.MULTIPLE_INSTANCES -> R.id.sample_list_to_multiple_instances
        }
        navController?.navigate(action)
    }

    fun navigateFromDetailsToSample(sample: Sample) {
        val action = when (sample) {
            Sample.DRAG_AND_DROP -> R.id.sample_details_to_drag_and_drop
            Sample.DUAL_VIEW -> R.id.sample_details_to_dual_view
            Sample.HINGE_ANGLE -> R.id.sample_details_to_hinge_angle
            Sample.LIST_DETAILS -> R.id.sample_details_to_list_details
            Sample.EXTENDED_CANVAS -> R.id.sample_details_to_extended_canvas
            Sample.TWO_PAGE -> R.id.sample_details_to_two_page
            Sample.COMPANION_PANE -> R.id.sample_details_to_companion_pane
            Sample.PEN_EVENTS -> R.id.sample_details_to_pen_events
            Sample.INTENT_TO_SECOND_SCREEN -> R.id.sample_details_to_intent_to_second_screen
            Sample.QUALIFIERS -> R.id.sample_details_to_qualifiers
            Sample.MULTIPLE_INSTANCES -> R.id.sample_details_to_multiple_instances
        }
        navController?.navigate(action)
    }
}