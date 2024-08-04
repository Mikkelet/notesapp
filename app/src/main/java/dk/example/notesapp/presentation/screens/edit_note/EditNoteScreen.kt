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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EditNoteScreen(
    viewModel: EditNoteViewModel = hiltViewModel(),
    navigation: NavController
) {
    val note = viewModel.noteState
    if (note == null) {
        Text(text = "loading")
    } else
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Edit note ${note.id}")
            Spacer(modifier = Modifier.height(32.dp))
            TextField(value = note.titleState, onValueChange = {
                note.titleState = it
            })
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = {
                viewModel.applyChanges()
                navigation.popBackStack()
                //navigation.popBackStack("notes", inclusive = false, saveState = true)
            }) {
                Text(text = "Apply")
            }

        }
}