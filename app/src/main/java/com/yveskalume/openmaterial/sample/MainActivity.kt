package com.yveskalume.openmaterial.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {}
                        ) {
                            Text(text = "My Custom button")
                        }


                        var selectedNavItemIndex by remember {
                            mutableIntStateOf(0)
                        }
                        NavigationBar {

                            NavigationBarItem(
                                selected = selectedNavItemIndex == 0,
                                onClick = { selectedNavItemIndex = 0 },
                                label = {
                                    Text(text = "Tab 1")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home Tab"
                                    )
                                }
                            )

                            NavigationBarItem(
                                selected = selectedNavItemIndex == 1,
                                onClick = { selectedNavItemIndex = 1 },
                                label = {
                                    Text(text = "Tab 2")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorite Tab"
                                    )
                                },
                            )

                            NavigationBarItem(
                                selected = selectedNavItemIndex == 2,
                                onClick = { selectedNavItemIndex = 2 },
                                label = {
                                    Text(text = "Tab 3")
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings Tab"
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
