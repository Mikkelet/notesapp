package dk.example.notesapp.presentation.screens.notes

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun NotesScreen(
    navigation: NavController = rememberNavController(),
    viewModel: NotesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val notesState = viewModel.notesFlow.collectAsState(emptyList())
        val notes = notesState.value
        Text(text = "Notes")
        LazyColumn {
            items(notes) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navigation.navigate("note/${it.id}") {
                            }
                        }
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "Note ${it.id}"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = it.titleState
                    )
                }
            }
        }
    }
}