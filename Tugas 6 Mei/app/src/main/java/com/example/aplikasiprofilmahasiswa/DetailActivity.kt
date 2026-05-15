package com.example.aplikasiprofilmahasiswa

import android.os.Bundle
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            AplikasiProfilMahasiswaTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    DetailScreen(
                        modifier = Modifier.padding(innerPadding),

                        onBackClick = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,

    onBackClick: () -> Unit
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
                        id = R.string.detail_title
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
                        id = R.string.detail_title
                    ),

                    fontSize = 26.sp,

                    fontWeight = FontWeight.Bold,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text =
                        "${stringResource(id = R.string.label_nama)} " +
                                stringResource(id = R.string.nama),

                    fontSize = 18.sp,

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
                    text =
                        "${stringResource(id = R.string.label_prodi)} " +
                                stringResource(id = R.string.prodi),

                    fontSize = 18.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(
                        id = R.string.hobi
                    ),

                    fontSize = 18.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(
                        id = R.string.email
                    ),

                    fontSize = 18.sp,

                    color = colorResource(
                        id = R.color.black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(
                        id = R.string.detail_description
                    ),

                    fontSize = 16.sp,

                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        onBackClick()
                    }
                ) {

                    Text(
                        text = stringResource(
                            id = R.string.btn_back
                        )
                    )
                }
            }
        }
    }
}