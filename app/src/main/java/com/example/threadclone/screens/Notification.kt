package com.example.threadclone.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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

    val dao: Dao = Databse.getDatabaseInstance(context).getDao()

    val hourlyCheckInCounts = dao.getHourlyCheckInCounts()

    val hours = mutableListOf<Int>()
    val checkInCounts = mutableListOf<Int>()

    val customColorOthers = Color(0xFF74C5CA)

    for (hour in 0..23) {
        hours.add(hour)
        checkInCounts.add(0)
    }

    for (hourlyCheckInCount in hourlyCheckInCounts) {
        val hour = hourlyCheckInCount.checkInHour.toInt()
        val count = hourlyCheckInCount.checkInCount
        if (hour in 0..23) {
            checkInCounts[hour] = count
        }
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
    val customColor = Color(0xFF014A6F)


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (PeakText,PeakChart) = createRefs()
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .height(25.dp)
                .constrainAs(PeakText){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 10.dp)
                }
                .background(customColor, RoundedCornerShape(14.dp)),
            color = Color.Transparent,
        ) {
            Text(text = "Peak Facility Usage ",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
            )
        }

        ComposeChart1(modelProducer, modifier= Modifier
            .wrapContentSize()
            .padding(horizontal = 20.dp)
            .constrainAs(PeakChart){
                top.linkTo(PeakText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

    }



}
@Composable
private fun ComposeChart1(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
) {
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberLineCartesianLayer(listOf(rememberLineSpec(DynamicShaders.color(Color(0xFF74C5CA))))),
            startAxis = rememberStartAxis(label= rememberAxisLabelComponent(color = Color.Black)),
            bottomAxis = rememberBottomAxis(label= rememberAxisLabelComponent(color = Color.Black),guideline = null),

        ),
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}