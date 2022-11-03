package com.e4ekta.dynamicuisample.src

sealed class UiType {
    object LABEL : UiType()
    object EDITTEXT: UiType()
    object BUTTON: UiType()
}
