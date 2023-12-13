package com.example.examenmanuelcf

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examenmanuelcf.data.DataSource.productos
import com.example.examenmanuelcf.data.Producto
import com.example.examenmanuelcf.ui.theme.ExamenManuelCFTheme
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenManuelCFTheme {
                //Scaffold (topBar = {TopBar()}, content = MyApp())

                TopBar()
                MyApp()
            }
        }
    }

    @Composable
    private fun MyApp() {
        val nombreDefault = "";
        val precioTextoDefault = "";
        val precioDefault = 0;

        val nombreState = remember {
            mutableStateOf(nombreDefault)
        }

        val precioTextoState = remember {
            mutableStateOf(precioTextoDefault);
        }

        val precioState = remember {
            mutableStateOf(precioDefault);
        }

        val mensajeState = remember {
            mutableStateOf("")
        }

        val listaProductos = remember {
            mutableStateOf(productos)
        }

        val firstTime = remember {
            mutableStateOf(true)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar()
                Spacer(modifier = Modifier.size(10.dp))
                Row() {
                    Column(modifier = Modifier
                        .width(180.dp)
                        .padding(horizontal = 8.dp)
                    ) {
                        TextField(
                            value = "${nombreState.value}",
                            label = {Text(text = "Nombre")},
                            onValueChange = {
                                nombre ->
                                nombreState.value = nombre.trim()
                            }
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        TextField(value = "${precioTextoState.value}",
                            label = {Text(text = "Precio")},
                            onValueChange = {
                                precio ->
                                precioTextoState.value = precio.trim();
                                precioState.value = precioTextoState.value.toInt()
                            }
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Button(onClick = {
                            val producto: Producto = Producto(nombreState.value, precioState.value)
                            var indexOfProduct = -1;
                            var index = 0;

                            productos.forEach {p ->
                                if (p.nombre == producto.nombre && p.precio == producto.precio) {
                                    indexOfProduct == index
                                    Log.d("TAG", "MyApp: " + index)
                                }
                                index++
                            }

                            if(indexOfProduct == -1) {
                                productos.add(producto)
                                listaProductos.value = productos
                                mensajeState.value = "Se ha añadido el producto ${producto.nombre} con precio ${producto.precio}"
                            } else if (productos[indexOfProduct].precio != producto.precio) {
                                mensajeState.value = "Del producto ${producto.nombre} se ha modificado el precio de ${productos[indexOfProduct].precio} euros a ${producto.precio} euros"
                                productos[indexOfProduct] = producto
                                listaProductos.value = productos
                            } else {
                                mensajeState.value = "NO se ha modificado nada del producto ${producto.nombre}, el precio es el mismo"
                            }

                            // reiniciamos los valores
                            nombreState.value = nombreDefault
                            precioState.value = precioDefault
                            precioTextoState.value = precioTextoDefault
                            // indicamos que ya no es la primera vez que accedemos a la app
                            firstTime.value = false

                        }) {
                            Text(text = "Add/Update producto")
                        }
                    }
                    LazyColumn {
                        items(listaProductos.value) { producto ->
                            Spacer(modifier = Modifier.size(8.dp))

                            Surface(shape = RoundedCornerShape(10.dp)) {
                                Column {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color.Yellow)) {
                                        Text(//modifier = Modifier.padding(20.dp),
                                            text = "Nombre: " + producto.nombre)
                                    }
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color.Cyan)
                                    ) {
                                        Text(//modifier = Modifier.padding(20.dp),
                                            text = "Precio: " + producto.precio.toString())
                                    }
                                }
                            }
                        }
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = Color.LightGray
                ) {
                    if (nombreState.value == "" && precioState.value == 0 && firstTime.value){
                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp),
                            text = "Todavia no han añadido ningún valor"
                        )
                    } else {
                        Column {
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                text = nombreState.value
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                text = precioState.value.toString())
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                modifier = Modifier
                                    .padding(start = 20.dp),
                                text = mensajeState.value)
                        }
                    }


                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            color = Color.LightGray
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 50.dp, end = 50.dp),
                text = "Hola soy alumno Manuel Cruz"
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExamenManuelCFTheme {
        Greeting("Android")
    }
}