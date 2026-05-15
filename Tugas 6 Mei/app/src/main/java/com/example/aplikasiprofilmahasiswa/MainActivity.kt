package com.example.aplikasiprofilmahasiswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import com.example.aplikasiprofilmahasiswa.ui.theme.AplikasiProfilMahasiswaTheme

class MainActivity : ComponentActivity() {

    companion object {

        const val TAG = "LifecycleMain"

        val lifecycleStatus =
            mutableStateOf("onCreate")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleStatus.value = "onCreate"

        Log.d(TAG, "onCreate dipanggil")

        Toast.makeText(
            this,
            "onCreate",
            Toast.LENGTH_SHORT
        ).show()

        enableEdgeToEdge()

        setContent {

            AplikasiProfilMahasiswaTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    MainScreen(
                        modifier = Modifier.padding(innerPadding),

                        openDetail = {

                            val intent = Intent(
                                this,
                                DetailActivity::class.java
                            )

                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleStatus.value = "onStart"

        Log.d(TAG, "onStart dipanggil")

        Toast.makeText(
            this,
            "onStart",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()

        lifecycleStatus.value = "onResume"

        Log.d(TAG, "onResume dipanggil")

        Toast.makeText(
            this,
            "onResume",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPause() {
        super.onPause()

        lifecycleStatus.value = "onPause"

        Log.d(TAG, "onPause dipanggil")

        Toast.makeText(
            this,
            "onPause",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()

        lifecycleStatus.value = "onStop"

        Log.d(TAG, "onStop dipanggil")

        Toast.makeText(
            this,
            "onStop",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycleStatus.value = "onDestroy"

        Log.d(TAG, "onDestroy dipanggil")

        Toast.makeText(
            this,
            "onDestroy",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onRestart() {
        super.onRestart()

        lifecycleStatus.value = "onRestart"

        Log.d(TAG, "onRestart dipanggil")

        Toast.makeText(
            this,
            "onRestart",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,

    openDetail: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor = colorResource(
                    id = R.color.white
                )
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.foto_mahasiswa
                    ),

                    contentDescription = stringResource(
                        id = R.string.detail_description
                    ),

                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),

                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(
                        id = R.string.nama
                    ),

                    fontSize = 26.sp,

                    fontWeight = FontWeight.Bold,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        "${stringResource(id = R.string.label_nim)} " +
                                stringResource(id = R.string.nim),

                    fontSize = 18.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(
                        id = R.string.prodi
                    ),

                    fontSize = 18.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(
                        id = R.string.deskripsi
                    ),

                    fontSize = 16.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text =
                        "${stringResource(id = R.string.lifecycle_text)} " +
                                MainActivity.lifecycleStatus.value,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold,

                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        openDetail()
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(
                            id = R.color.primary_color
                        )
                    )
                ) {

                    Text(
                        text = stringResource(
                            id = R.string.btn_detail
                        ),

                        color = colorResource(
                            id = R.color.white
                        )
                    )
                }
            }
        }
    }
}