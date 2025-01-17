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

package com.google.android.horologist.networks.ui

import android.content.Context
import android.text.format.Formatter
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.CurvedAlignment
import androidx.wear.compose.foundation.CurvedScope
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.curvedText
import com.google.android.horologist.networks.data.DataUsageReport
import com.google.android.horologist.networks.data.NetworkStatus
import com.google.android.horologist.networks.data.NetworkType
import com.google.android.horologist.networks.data.Networks
import com.google.android.horologist.networks.data.Status

internal fun CurvedScope.curveDataUsage(
    modifier: Modifier = Modifier,
    networkStatus: Networks,
    networkUsage: DataUsageReport?,
    style: CurvedTextStyle,
    context: Context,
) {
    val activeNetwork = networkStatus.activeNetwork

    networkStatus.networks.filterNot { it.id == activeNetwork?.id }.forEach {
        curvedComposable(radialAlignment = CurvedAlignment.Radial.Outer) {
            Icon(
                modifier = modifier
                    .size(12.dp),
                imageVector = it.type.icon,
                contentDescription = null,
                tint = it.tint(active = false)
            )
        }
        val usage = networkUsage?.dataByType?.get(it.type.typeName)
        if (usage != null) {
            curvedText(text = usage.toSize(context), style = style)
        }
    }
    activeNetwork?.let {
        curvedComposable(radialAlignment = CurvedAlignment.Radial.Outer) {
            Icon(
                modifier = modifier
                    .size(16.dp),
                imageVector = it.type.icon,
                contentDescription = null,
                tint = it.tint(active = true)
            )
        }
        val usage = networkUsage?.dataByType?.get(activeNetwork.type.typeName)
        if (usage != null) {
            curvedText(
                text = usage.toSize(context), style = style
            )
        }
    }
}

@Composable
internal fun LinearDataUsage(
    networkStatus: Networks,
    networkUsage: DataUsageReport?,
    style: TextStyle,
    context: Context,
) {
    val activeNetwork = networkStatus.activeNetwork

    networkStatus.networks.filterNot { it.id == activeNetwork?.id }.forEach {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = it.type.icon,
            contentDescription = null,
            tint = it.tint(active = false)
        )
        val usage = networkUsage?.dataByType?.get(it.type.typeName)
        if (usage != null) {
            Text(text = usage.toSize(context), style = style)
        }
    }
    activeNetwork?.let {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = it.type.icon,
            contentDescription = null,
            tint = it.tint(active = true)
        )
        val usage = networkUsage?.dataByType?.get(activeNetwork.type.typeName)
        if (usage != null) {
            Text(
                text = usage.toSize(context), style = style
            )
        }
    }
}

internal fun Long.toSize(context: Context): String {
    return Formatter.formatShortFileSize(context, this)
}

private fun NetworkStatus.tint(active: Boolean): Color {
    return if (!active && this.status == com.google.android.horologist.networks.data.Status.Available)
        Color.Blue
    else when (this.status) {
        is Status.Available -> Color.Green
        is Status.Losing -> Color.Yellow
        is Status.Lost -> Color.Gray
        is Status.Unknown -> Color.LightGray
    }
}

internal val NetworkType.icon
    get() = when (this) {
        is NetworkType.Wifi -> Icons.Filled.Wifi
        is NetworkType.Cellular -> Icons.Filled.SignalCellularAlt
        is NetworkType.Bluetooth -> Icons.Filled.Bluetooth
        else -> Icons.Filled.HelpOutline
    }
