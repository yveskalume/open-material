package com.yveskalume.openmaterial.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.yveskalume.openmaterial.button.Button
import com.yveskalume.openmaterial.navigationbar.NavigationBar
import com.yveskalume.openmaterial.navigationbar.NavigationBarItem
import com.yveskalume.openmaterial.sample.ui.theme.OpenMaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenMaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Button(
                            onClick = {}
                        ) {
                            Text(text = "My Custom button")
                        }

                        NavigationBar {

                            NavigationBarItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = false,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null
                                    )
                                },
                            )

                            NavigationBarItem(
                                selected = false,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
