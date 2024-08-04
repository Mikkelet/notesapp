package dk.example.notesapp.presentation.screens.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel(),
    navigation: NavController
) {
    val noteFlow = viewModel.noteFlow.collectAsState(initial = null)
    val note = noteFlow.value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Note ${note?.id}")
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "${note?.titleState}")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            navigation.navigate("editnote/${note?.id}")
        }) {
            Text(text = "Edit")
        }
    }
}