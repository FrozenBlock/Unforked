package net.frozenblock.unforked.util

import java.util.function.Consumer

// Based on IteratorCallbackList from ModsMod
class IteratorCallbackList<T>(private val delegate: List<T>, private val reset: Consumer<List<T>>, private val callback: Runnable) : List<T> by delegate {

    private var modified: Boolean = false

    override fun iterator(): Iterator<T> {
        if (!modified) {
            callback.run()
            modified = true
        }
        reset.accept(delegate)
        return delegate.iterator()
    }
}
