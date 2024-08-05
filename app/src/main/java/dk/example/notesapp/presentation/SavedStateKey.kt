package dk.example.notesapp.presentation

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SavedStateKey {
    const val KEY_ID = "id"
    const val KEY_TITLE = "title"
}

class SavedStateVarDelegate<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return savedStateHandle[key] ?: defaultValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        savedStateHandle[key] = value
    }
}

class SavedStateValDelegate<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
) : ReadOnlyProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return savedStateHandle[key] ?: throw Exception("Property not found")
    }
}