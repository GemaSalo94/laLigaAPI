package pmdm.laligaapi.equipos.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pmdm.laligaapi.equipos.ui.model.Jugador
import pmdm.laligaapi.equipos.ui.model.Equipo
import pmdm.laligaapi.equipos.ui.uiState.EquipoUiState
import pmdm.laligaapi.equipos.ui.viewmodel.EquipoViewModel
import pmdm.laligaapi.ui.theme.LaLigaAPITheme
import pmdm.laligaapi.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipoScreen( idEquipo: Equipo, onBack: () -> Unit,
                  equipoViewModel: EquipoViewModel = hiltViewModel()
) {
    val uiStateEquipo by equipoViewModel.uiStateEquipo.collectAsStateWithLifecycle()

    val message by equipoViewModel.message.collectAsState()

    var isRefreshing by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(uiStateEquipo) {
        isRefreshing = uiStateEquipo is EquipoUiState.Loading
    }
    LaunchedEffect(idEquipo) {
        equipoViewModel.cargarEquipo(idEquipo = idEquipo.id)
    }

    var show by rememberSaveable { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    LaLigaAPITheme {
        Scaffold(modifier = Modifier.fillMaxWidth(),
            topBar = {
                TopAppBar(
                    title = { Text("Equipo") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple40,
                        titleContentColor = Color.White,
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Volver al atrás"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { show = true }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                tint = Color.White,
                                contentDescription = "Eliminar equipo"
                            )
                        }
                        IconButton(onClick = { equipoViewModel.descargarDatosEnRoom()  }) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                tint = Color.White,
                                contentDescription = "Guardar en Room"
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = { }
        ) {paddingValues ->
            // Renderizamos la UI en base a si es Error, Loading o Success
            when (val state = uiStateEquipo) {
                is EquipoUiState.Error -> {
                    // En caso de error, envolvemos la pantalla en un PullToRefresh
                    // para que el usuario pueda deslizar hacia abajo y reintentar.
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            equipoViewModel.cargarEquipo(idEquipo = idEquipo.id)
                        }) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues) // Aplicamos el padding del Scaffold
                                .verticalScroll(rememberScrollState()),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Mostramos el mensaje de error devuelto por el estado
                                Text(
                                    text = state.mensaje,
                                    color = Color.Red, style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.size(4.dp))
                                // Botón explícito para volver a intentar la llamada de red
                                Button(onClick = { equipoViewModel.cargarEquipo(idEquipo = idEquipo.id) }) {
                                    Text(text = "Reintentar")
                                }
                            }
                        }
                    }
                }

                EquipoUiState.Loading -> {
                    // Si está cargando y no estamos refrescando manualmente, mostramos un spinner
                    if (!isRefreshing) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(paddingValues) // Aplicamos el padding del Scaffold
                        ) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }

                is EquipoUiState.Success -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true
                            equipoViewModel.cargarEquipo(idEquipo = idEquipo.id)
                        }) {

                        EquipoDetail(state.post, Modifier.padding(paddingValues), equipoViewModel)
                    }
                }
            }
        }
        LaunchedEffect(message) {
            message?.let {
                snackbarHostState.showSnackbar(it)
            }
        }
}


    MyDialog(show, title = "Advertencia",
        description = "¿Está seguro de que desea eliminar el equipo?",
        onDismiss = { show = false },
        onConfirm = { show = false })
}


@Composable
fun EquipoDetail(equipo: Equipo, modifier: Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Parte superior: Información del equipo
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Escudo a la izquierda
            Image(
                painter = painterResource(id = equipo.generarIcono()),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )

            // Datos del equipo a la derecha
            Column {
                Text(
                    text = equipo.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fundado: ${equipo.fechaFundacion}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Socios: ${equipo.numeroSocios}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Parte inferior: Lista de jugadores
        Text(
            text = "Plantilla:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(equipo.jugadores) { jugador ->
                JugadorItem(jugador = jugador)
            }
        }
    }
}

@Composable
fun JugadorItem(jugador: Jugador) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono pequeño a la izquierda
        Image(
            painter = painterResource(id = jugador.generarIcono()),
            contentDescription = jugador.nombre,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 12.dp),
            contentScale = ContentScale.Fit
        )

        // Información del jugador a la derecha
        Column {
            Text(
                text = jugador.nombre,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Dorsal: ${jugador.dorsal}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = jugador.posicion,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}


@Composable
fun MyDialog(
    show: Boolean,
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = description) },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancelar")
                }
            })
    }
}