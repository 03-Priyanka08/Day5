package com.example.day5

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.day5.ui.theme.Day5Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Day5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String) {
    var phn by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var uPhn by remember { mutableStateOf("") }
    var cpass by remember { mutableStateOf("") }
    var cpass2 by remember { mutableStateOf("") }
    var idChg by remember { mutableStateOf("") }
    var remPhn by remember { mutableStateOf("") }
    var idDel by remember { mutableStateOf("") }
    val db = DetailDB.getInstance(LocalContext.current).getDao()
    val scope = rememberCoroutineScope()
    var f by remember { mutableStateOf(false) }
    var u by remember { mutableStateOf(false) }
    var d by remember { mutableStateOf(false) }
    var da by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        OutlinedTextField(value = phn,
            onValueChange = {
                phn= it },
            label = { Text(text = "Phone No.")},
            modifier = Modifier.padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(value = pass,
            onValueChange = {
                pass= it },
            label = { Text(text = "Password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Button(
            onClick = {
                scope.launch {
                    try {
                        db.addData(DetailEntity(pNo = phn, pwd = pass))
                        phn = ""
                        pass = ""
                    }catch (ex: Exception){
                        println("Data Not added")
                    }
                }
            },
            Modifier.padding(top = 10.dp, bottom = 20.dp),
            ) {
            Text(text = "Save")
            }

        OutlinedTextField(value = uPhn,
            onValueChange = {
                uPhn= it },
            label = { Text(text = "Find User By Phone No")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Button(
            onClick = { f = !f },
            Modifier.padding(top = 10.dp, bottom = 20.dp),
        ) {
            Text(text = "Find")
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = cpass,
                onValueChange = {
                    cpass = it
                },
                label = { Text(text = "Old Password") },
                modifier = Modifier
                    .requiredWidth(170.dp)
                    .padding(start = 5.dp)
            )

            OutlinedTextField(
                value = cpass2,
                onValueChange = {
                    cpass2 = it
                },
                modifier = Modifier
                    .requiredWidth(170.dp)
                    .padding(start = 5.dp),
                label = { Text(text = "New Password")})
        }
        if(f){
            val itm by remember {
                derivedStateOf {
                    runBlocking {
                        withContext(Dispatchers.IO){
                            db.getPhnNo(uPhn)
                        }
                    }
                }
            }
            Text(text = "Password of User: ${itm.pwd}")
            cpass = itm.pwd
            idChg = itm.uID.toString()
            remPhn = itm.pNo
        }
        if(u){
            scope.launch {
                try {
                    db.updatePass(DetailEntity(uID = idChg.toInt(), pNo = remPhn, pwd = cpass2))

                } catch (ex: Exception) {
                    println("cancelled")
                }
            }
        }
        if(d){

            scope.launch {
                try {
                    db.delUser(DetailEntity(uID = idDel.toInt(), pNo = "", pwd = ""))

                } catch (ex: Exception) {
                    println("cancelled")
                }
            }
        }
        Button(
            onClick = { u = !u},
            Modifier.padding(top = 10.dp, bottom = 20.dp),
        ) {
            Text(text = "Update Password")
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = idDel,
                onValueChange = {
                    idDel = it
                },
                label = { Text(text = "ID") },
                modifier = Modifier.requiredWidth(200.dp)
            )
            Button(
                onClick = {d= !d},
                Modifier.padding(top = 10.dp),
            ) {
                Text(text = "Drop")
            }
        }

        if(da){
            val scope = rememberCoroutineScope()
            scope.launch {
                try {
                    db.delAll()

                } catch (ex: Exception) {
                    println("cancelled")
                }
            }
        }
        Button(
            onClick = {
                da = !da
            },
            Modifier.padding(top = 20.dp),
        ) {
            Text(text = "Delete All")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Day5Theme {
        Greeting("Android")
    }
}