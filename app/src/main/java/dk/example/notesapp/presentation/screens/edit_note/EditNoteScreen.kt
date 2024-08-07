package dk.example.notesapp.presentation.screens.edit_note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EditNoteScreen(
    navigation: NavController,
    viewModel: EditNoteViewModel = hiltViewModel(),
) {
    val uiStateFlow = viewModel.uiState.collectAsState()
    val titleState = viewModel.titleFlow.collectAsState()
    val uiState = uiStateFlow.value
    val title = titleState.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState == EditNoteViewModel.UiState.Loading) {
            Text(text = "Loading")
        } else if (uiState is EditNoteViewModel.UiState.OnNote) {
            val note = uiState.note
            Text(text = "Edit note ${note.id}")
            Spacer(modifier = Modifier.height(32.dp))
            TextField(value = title, onValueChange = {
                viewModel.onTitleUpdate(it)
            })
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                viewModel.applyChanges()
                navigation.popBackStack()
            }) {
                Text(text = "Apply")
            }
        }
    }
}