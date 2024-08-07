package dk.example.notesapp.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.example.notesapp.presentation.navigation.NavigationState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigationState: NavigationState
) : ViewModel() {

    val navFlow = navigationState.navFlow
}