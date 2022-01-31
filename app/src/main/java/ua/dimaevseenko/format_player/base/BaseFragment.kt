package ua.dimaevseenko.format_player.base

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    internal open fun tag(): String = "tag"
}