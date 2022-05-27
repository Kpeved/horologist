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

@RequiresApi(Build.VERSION_CODES.S)
public class PremiumHaptics(private val vibrator: Vibrator) : HapticAction {

    private var tick: VibrationEffect = VibrationEffect.startComposition().apply {
        addPrimitive(VibrationEffect.Composition.PRIMITIVE_TICK)
    }.compose()

    private var click: VibrationEffect = VibrationEffect.startComposition().apply {
        addPrimitive(VibrationEffect.Composition.PRIMITIVE_CLICK)
    }.compose()

    private var overscrollClick: VibrationEffect = VibrationEffect.startComposition().apply {
        addPrimitive(VibrationEffect.Composition.PRIMITIVE_THUD)
    }.compose()

    override fun performScrollTick() {
        Log.i("HapticsAction", "Premium: ScrollTick")
        vibrator.vibrate(tick)
    }

    override fun performClick() {
        Log.i("HapticsAction", "Premium: Click")
        vibrator.vibrate(click)
    }

    override fun performOverscroll() {
        Log.i("HapticsAction", "Premium: Overscroll")
        vibrator.vibrate(overscrollClick)
    }
}
