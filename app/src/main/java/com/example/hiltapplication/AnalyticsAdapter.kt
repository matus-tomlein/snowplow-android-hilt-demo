package com.example.hiltapplication

import android.content.Context
import com.snowplowanalytics.snowplow.Snowplow.createTracker
import com.snowplowanalytics.snowplow.configuration.NetworkConfiguration
import com.snowplowanalytics.snowplow.configuration.SubjectConfiguration
import com.snowplowanalytics.snowplow.configuration.TrackerConfiguration
import com.snowplowanalytics.snowplow.event.SelfDescribing
import com.snowplowanalytics.snowplow.tracker.DevicePlatform
import com.snowplowanalytics.snowplow.tracker.LogLevel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AnalyticsAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val subjectConfig = SubjectConfiguration().colorDepth(24)
    private val trackerConfig = TrackerConfiguration(context.packageName)
        .screenViewAutotracking(false)
        .screenContext(false)
        .installAutotracking(true)
        .platformContext(true)
        .logLevel(LogLevel.DEBUG)
        .devicePlatform(DevicePlatform.Mobile)
        .userAnonymisation(false)
    private val tracker = createTracker(
        context,
        "my-tracker",
        NetworkConfiguration("http://192.168.100.2:9090"),
        subjectConfig,
        trackerConfig
    )

    fun trackEvent() {
        tracker.track(
            SelfDescribing(
                "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
                mapOf("example_key" to "example_value")
            )
        )
    }
}
