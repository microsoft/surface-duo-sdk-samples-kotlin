/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.pen

import android.util.Log
import android.view.MotionEvent

/**
 * Utility class used to extract the [MotionEvent] action name
 */
class PenEventsHandler {
    companion object {
        private const val DEBUG_TAG = "PenEventsDebug"
    }

    /**
     * @return the corresponding action name resource id for the given [MotionEvent]
     */
    fun getActionValue(event: MotionEvent): Int {
        return when (val action = event.actionMasked) {
            MotionEvent.ACTION_DOWN -> R.string.action_down
            MotionEvent.ACTION_MOVE -> R.string.action_move
            MotionEvent.ACTION_UP -> R.string.action_up
            MotionEvent.ACTION_CANCEL -> R.string.action_cancel
            MotionEvent.ACTION_HOVER_ENTER -> R.string.action_hover_enter
            MotionEvent.ACTION_HOVER_EXIT -> R.string.action_hover_exit
            MotionEvent.ACTION_HOVER_MOVE -> R.string.action_hover_move
            MotionEvent.ACTION_BUTTON_PRESS -> R.string.action_button_press
            MotionEvent.ACTION_BUTTON_RELEASE -> R.string.action_button_release
            else -> action
        }
    }

    /**
     * Checks if the drawing surface can handle the given [MotionEvent]
     *
     * @return [true] if the drawing surface can handle the event, [false] otherwise
     */
    fun canHandleEvent(event: MotionEvent): Boolean {
        return when (val action = event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(DEBUG_TAG, " Action was DOWN ")
                true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i(DEBUG_TAG, " Action was MOVE ")
                true
            }
            MotionEvent.ACTION_UP -> {
                Log.i(DEBUG_TAG, " Action was UP ")
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.i(DEBUG_TAG, " Action was CANCEL ")
                true
            }
            MotionEvent.ACTION_HOVER_ENTER -> {
                Log.i(DEBUG_TAG, " Action was HOVER_ENTER")
                true
            }
            MotionEvent.ACTION_HOVER_EXIT -> {
                Log.i(DEBUG_TAG, " Action was HOVER_EXIT")
                true
            }
            MotionEvent.ACTION_HOVER_MOVE -> {
                Log.i(DEBUG_TAG, " Action was HOVER_MOVE")
                true
            }
            MotionEvent.ACTION_BUTTON_PRESS -> {
                Log.i(DEBUG_TAG, " Action was Button Press")
                true
            }
            MotionEvent.ACTION_BUTTON_RELEASE -> {
                Log.i(DEBUG_TAG, " Action was BUTTON_RELEASE")
                true
            }
            else -> {
                Log.i(DEBUG_TAG, "DEFAULT!!! $action")
                false
            }
        }
    }
}