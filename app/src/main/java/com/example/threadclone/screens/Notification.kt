package com.example.threadclone.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.threadclone.roomdb.Dao
import com.example.threadclone.roomdb.Databse
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.zoom.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.math.log
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Notification(){
    val context= LocalContext.current

    val x: List<Int> = (1..24).toList()


    val dao: Dao = Databse.getDatabaseInstance(context).getDao()

    val hourlyCheckInCounts = dao.getHourlyCheckInCounts()

// Store the powers (hours) and corresponding check-in counts in separate lists
    val hours = mutableListOf<Int>()
    val checkInCounts = mutableListOf<Int>()

// Iterate through the result and populate the lists
    for (hourlyCheckInCount in hourlyCheckInCounts) {
        hours.add(hourlyCheckInCount.checkInHour)
        checkInCounts.add(hourlyCheckInCount.checkInCount)
    }
    val modelProducer = remember { CartesianChartModelProducer.build() }

    LaunchedEffect(Unit) {
            withContext(Dispatchers.Default) {
                modelProducer.tryRunTransaction {
                    lineSeries {
                        series(hours,checkInCounts)
                    }
                }
            }
    }
    ComposeChart1(modelProducer, modifier= Modifier
        .wrapContentSize()
        .padding(horizontal = 20.dp))

}
@Composable
private fun ComposeChart1(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
) {
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(listOf(rememberLineSpec(DynamicShaders.color(Color(0xffa485e0))))),
            startAxis = rememberStartAxis(label= rememberAxisLabelComponent(color = Color.Black)),
            bottomAxis = rememberBottomAxis(label= rememberAxisLabelComponent(color = Color.Black),guideline = null),

        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}