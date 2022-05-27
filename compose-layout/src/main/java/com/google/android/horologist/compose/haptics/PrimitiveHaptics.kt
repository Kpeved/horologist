/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.compose.haptics

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
public class PrimitiveHaptics(
    private val vibrator: Vibrator,
    private val disableScrollTick: Boolean
) :
    HapticAction {
    private val tick = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)

    private val click = VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)

    private val heavyClick = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)

    override fun performScrollTick() {
        Log.i("HapticsAction", "Primitive: ScrollTick, enabled: ${!disableScrollTick}")

        if (!disableScrollTick) {
            vibrator.vibrate(tick)
        }
    }

    override fun performClick() {
        Log.i("HapticsAction", "Primitive: Click")

        vibrator.vibrate(click)
    }

    override fun performOverscroll() {
        Log.i("HapticsAction", "Primitive: Overscroll")

        vibrator.vibrate(heavyClick)
    }
}
