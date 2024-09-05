import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEngine(navController: NavHostController, paddingValues: PaddingValues) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var query by remember { mutableStateOf("") }
        Column(modifier = Modifier
            .padding(paddingValues)
            .background(
                Color(0xFFF5F5EF)
            )
        ) {
            SearchBar(
                query = query,
                onQueryChanged = { query = it },
                onClearQuery = { query = "" },
            )
            TextRow(modifier = Modifier.padding(top = 5.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)){
                ScrollContent(navController = navController)
            }
        }
    }

@Composable
fun TextRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextWithDivider(text = "TAGS")
        Spacer(modifier = Modifier.width(16.dp))
        Divider(color = Color.Black, modifier = Modifier
            .width(0.dp))
        Spacer(modifier = Modifier.width(16.dp))
        TextWithDivider(text = "SORT")
        Spacer(modifier = Modifier.width(16.dp))
        Divider(color = Color.Black, modifier = Modifier
            .width(0.dp))
        Spacer(modifier = Modifier.width(16.dp))
        TextWithDivider(text = "FILTER")
    }
    HorizontalDivider(
        thickness = 2.dp,
        color = Color.Black,
    )
}

@Composable
fun TextWithDivider(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(vertical = 15.dp)
    )
}

@Composable
fun TextBox(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Input") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
            )
        }
    }
}



@Composable
fun CasosIndividuales(){
        Box(
            modifier = Modifier
                .padding(horizontal = 0.dp)
                .padding(vertical = 0.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = "Casos Individuales",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )
        }
}


@Composable
fun ScrollContent(navController: NavHostController) {
    val itemsList = listOf("Caso 1 - Colaboradores", "Caso 2 - Colaboradores", "Caso 3 - Colaboradores", "Caso 4 - Colaboradores", "Caso 5 - Colaboradores", "Caso 6 - Colaboradores", " Caso 7 - Colaboradores")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(itemsList) { item ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(Color(0xFFFAFEFF)),
                modifier = Modifier
                .padding(vertical = 6.dp),
                onClick = {navController.navigate("case_view")}
            ) {
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .padding(16.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQuery: () -> Unit,
    placeholderText: String = "Buscar archivo"
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(text = placeholderText) },
        trailingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = 30.dp),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge
    )
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    SearchEngine(navController = navController, paddingValues = PaddingValues(16.dp))
}
