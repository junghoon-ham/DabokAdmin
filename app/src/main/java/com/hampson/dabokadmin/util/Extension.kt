package com.hampson.dabokadmin.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> List<T>.toSnapshotStateList(): SnapshotStateList<T> {
    return mutableStateListOf<T>().apply { addAll(this@toSnapshotStateList) }
}