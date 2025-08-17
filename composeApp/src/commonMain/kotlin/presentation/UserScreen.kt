@file:OptIn(ExperimentalMaterial3Api::class)

package presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import components.BottomSheetContent
import components.UserList
import domain.User
import domain.GameRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun UserScreen(repository: GameRepository) {
    val scope = rememberCoroutineScope()
    GlobalScope.launch {
        println("UserScreen ${repository.getGamePacks()}")
    }
    UserScreenContent(
        users = emptyList(),
        addUser = {  },
        updateUser = { },
        deleteUser = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenContent(
    users: List<User>,
    addUser: (User) -> Unit,
    updateUser: (User) -> Unit,
    deleteUser: (User) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        UserList(users = users, modifier = Modifier.padding(innerPadding)) { user ->
            selectedUser = user
            showBottomSheet = true
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    user = selectedUser,
                    onSave = { user ->
                        scope.launch {
                            if (selectedUser == null) {
                                addUser(user)
                            } else {
                                updateUser(user)
                            }
                            sheetState.hide()
                        }.invokeOnCompletion {
                            showBottomSheet = false
                            selectedUser = null
                        }
                    },
                    onDelete = { user ->
                        scope.launch {
                            user?.let { deleteUser(it) }
                            sheetState.hide()
                        }.invokeOnCompletion {
                            showBottomSheet = false
                            selectedUser = null
                        }
                    }
                )
            }
        }
    }
}
