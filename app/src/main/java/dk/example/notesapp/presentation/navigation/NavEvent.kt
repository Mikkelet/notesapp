package dk.example.notesapp.presentation.navigation

sealed class NavEvent {
    data object Back : NavEvent()
    data class Push(val screen: Screen) : NavEvent()
}