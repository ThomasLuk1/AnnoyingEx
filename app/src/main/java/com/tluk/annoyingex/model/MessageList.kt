package com.tluk.annoyingex.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class MessageList (
    val messages: ArrayList<String>
) : Parcelable