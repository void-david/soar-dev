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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
fun SearchEngine(navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Search Engine")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* User icon action */ }) {
                        Icon(Icons.Filled.Person, contentDescription = "User")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            TextRow(modifier = Modifier.padding(top = 28.dp))
            Spacer(modifier = Modifier.height(16.dp))
            TextBox(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            CasosIndividuales()
            ScrollContent()

        }
    }
}

@Composable
fun TextRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
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
}

@Composable
fun TextWithDivider(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun TextBox(modifier: Modifier = Modifier) {
    LazyRow (modifier= Modifier.fillMaxWidth()){
        item {
            Box(
                modifier = Modifier

                    .fillMaxWidth()
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
        item {
            Box(
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .padding(vertical = 0.dp)
                    .background(Color.Black)
                    .padding(horizontal = 16.dp, vertical = 17.dp)
                    .border(1.dp, Color.Black)

            ) {
                Text(
                    text = "SEARCH",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
            }
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
fun ScrollContent() {
    val itemsList = listOf("Caso 1 - Colaboradores", "Caso 2 - Colaboradores", "Caso 3 - Colaboradores", "Caso 4 - Colaboradores", "Caso 5 - Colaboradores", "Caso 6 - Colaboradores", " Caso 7 - Colaboradores")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(itemsList) { item ->
            Card(modifier = Modifier
                .padding(vertical = 12.dp)) {
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

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    SearchEngine(navController = navController)
}
