package dk.example.notesapp.presentation.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationState @Inject constructor() {
    private val _navigationFlow = MutableSharedFlow<NavEvent>()
    val navFlow: Flow<NavEvent> = _navigationFlow

    suspend fun push(screen: Screen) {
        _navigationFlow.emit(NavEvent.Push(screen))
    }

    suspend fun pop() {
        _navigationFlow.emit(NavEvent.Back)
    }
}