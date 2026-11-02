
package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.shape.RoundedCornerShape

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                NumberGridApp()
            }
        }
    }
}

@Composable
private fun NumberGridApp() {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val columnCount = remember(configuration.orientation) {
        context.resources.getInteger(R.integer.grid_columns)
    }

    var rectangleCount by rememberSaveable {
        mutableIntStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = colorResource(R.color.screen_background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(
                        R.dimen.screen_horizontal_padding
                    ),
                    vertical = dimensionResource(
                        R.dimen.screen_vertical_padding
                    )
                )
        ) {

            Text(
                text = stringResource(R.string.screen_title),
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(R.color.screen_title)
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.header_spacing)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(
                        R.string.items_count,
                        rectangleCount
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.secondary_text)
                )
            }

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.list_top_spacing)
                )
            )

            if (rectangleCount == 0) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(
                            R.string.empty_list_message
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colorResource(R.color.secondary_text)
                    )
                }

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnCount),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),

                    contentPadding = PaddingValues(
                        bottom = dimensionResource(
                            R.dimen.grid_bottom_padding
                        )
                    ),

                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.grid_spacing)
                    ),

                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.grid_spacing)
                    )

                ) {

                    items(
                        count = rectangleCount,
                        key = { index -> index }
                    ) { index ->

                        NumberSquare(index = index)

                    }
                }
            }

            Button(
                onClick = {
                    rectangleCount += 1
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(
                            R.dimen.button_top_spacing
                        )
                    ),

                contentPadding = PaddingValues(
                    vertical = dimensionResource(
                        R.dimen.button_vertical_padding
                    )
                )

            ) {
                Text(
                    text = stringResource(
                        R.string.add_rectangle_button
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun NumberSquare(index: Int) {

    val squareColor = if (index % 2 == 0) {

        colorResource(R.color.even_item_background)

    } else {

        colorResource(R.color.odd_item_background)

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),

        shape = RoundedCornerShape(
            dimensionResource(R.dimen.square_corner_radius)
        ),

        colors = CardDefaults.cardColors(
            containerColor = squareColor
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                R.dimen.square_elevation
            )
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = index.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = colorResource(R.color.item_number_text)
            )

        }
    }
}
