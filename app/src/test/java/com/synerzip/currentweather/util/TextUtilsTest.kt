package com.synerzip.currentweather.util

import com.google.common.truth.Truth
import org.junit.Test

class TextUtilsTest {
    @Test
    fun toTitleCase() {
        Truth.assertThat("Clear sky".toTitleCase()).isEqualTo("Clear Sky")
    }

    @Test
    fun toWindFormat() {
        Truth.assertThat("21.2".toWindFormat()).isEqualTo("21.2 m/s")
    }

    @Test
    fun toDegreeFormat() {
        Truth.assertThat("21.2".toDegreeFormat()).isEqualTo("21.2\u00B0")
    }
}