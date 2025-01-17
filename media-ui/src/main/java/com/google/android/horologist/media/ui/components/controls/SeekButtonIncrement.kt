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

package com.google.android.horologist.media.ui.components.controls

import com.google.android.horologist.media.ui.ExperimentalHorologistMediaUiApi

@ExperimentalHorologistMediaUiApi
public sealed class SeekButtonIncrement(
    public open val seconds: Int
) {

    public object Unknown : SeekButtonIncrement(-1)

    public object Five : SeekButtonIncrement(5)

    public object Ten : SeekButtonIncrement(10)

    public object Thirty : SeekButtonIncrement(30)

    public data class Other(override val seconds: Int) : SeekButtonIncrement(seconds)
}
