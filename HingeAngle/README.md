# Hinge angle sample for Surface Duo

This sample contains code that [accompanies this blog post](https://devblogs.microsoft.com/surface-duo/hinge-angle-on-surface-duo/), and demonstrates how to listen and react to changes in the hinge angle.

![](Screenshots/)

```kotlin
private val hingeAngleSensorListener = object : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == hingeAngleSensor) {
            val angle = event.values[0].toInt()
            val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
            viewModel.setHingeAngleLiveData(angle)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}

private fun setupSensors() {
    sensorManager = requireActivity().getSystemService(Service.SENSOR_SERVICE) as SensorManager?
    sensorManager?.let {
        val sensorList: List = it.getSensorList(Sensor.TYPE_ALL)
        for (sensor in sensorList) {
            if (sensor.name.contains(HINGE_ANGLE_SENSOR_NAME)) {
                hingeAngleSensor = sensor
            }
        }
    }
}
```

## Related links

- [Surface Duo hinge sensor docs](https://docs.microsoft.com/dual-screen/android/api-reference/hinge-sensor/)
- [Hinge angle blog post](https://devblogs.microsoft.com/surface-duo/resource-configuration-for-microsoft-surface-duo/)
- [Get the Surface Duo emulator](https://docs.microsoft.com/dual-screen/android/emulator/)
