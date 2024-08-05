package dk.example.notesapp.presentation

import androidx.lifecycle.SavedStateHandle

object SavedStateHandleValues {

    var SavedStateHandle.editTitle: String
        get() = get<String>(KEY_TITLE) ?: ""
        set(value) {
            set(KEY_TITLE, value)
        }

    fun SavedStateHandle.editTitleFlow() = getStateFlow(KEY_TITLE, null)
    fun SavedStateHandle.idFlow() = getStateFlow("id", "")


    private const val KEY_ID = "id"
    private const val KEY_TITLE = "title"
}