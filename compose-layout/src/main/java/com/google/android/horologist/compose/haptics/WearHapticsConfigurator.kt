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

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class WearHapticsConfigurator(private val context: Context) : HapticsConfigurator {

    override val action: HapticAction
        @SuppressLint("NewApi")
        get() {
            val vibrator = getVibrator(context)
            return if (Build.PRODUCT.startsWith("sdk_gwear_")) {
                EmulatedHaptics
            } else if (isTouchExplorationEnabled(context)) {
                HapticAction.None
            } else {
                if (supportsCompositionVibration(vibrator)) {
                    PremiumHaptics(vibrator)
                } else if (supportsPrimitiveVibration(vibrator)) {
                    PrimitiveHaptics(vibrator, isGW4Classic())
                } else {
                    LegacyHaptics(vibrator)
                }
            }
        }

    private fun isGW4Classic(): Boolean = Build.MODEL.matches("SM-R8[89]5.".toRegex())

    private fun isTouchExplorationEnabled(context: Context) =
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE)
            as android.view.accessibility.AccessibilityManager).isTouchExplorationEnabled

    private fun supportsCompositionVibration(vibrator: Vibrator) =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && vibrator.areAllPrimitivesSupported(
            VibrationEffect.Composition.PRIMITIVE_CLICK
        )

    private fun supportsPrimitiveVibration(vibrator: Vibrator) =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && vibrator.areAllEffectsSupported(
            VibrationEffect.EFFECT_CLICK
        ) == Vibrator.VIBRATION_EFFECT_SUPPORT_YES

    private fun getVibrator(context: Context): Vibrator =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

}