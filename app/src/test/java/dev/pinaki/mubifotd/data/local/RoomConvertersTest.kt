package dev.pinaki.mubifotd.data.local

import com.google.common.truth.Truth
import org.junit.Test

class RoomConvertersTest {

    private val converters = RoomConverters()
    private val expectedList = listOf("Martin Scorsese", "Joachim Trier", "Satyajit Ray")
    private val expectedString = "Martin Scorsese, Joachim Trier, Satyajit Ray"

    @Test
    fun `should convert a string list`() {
        val actual = converters.fromStringList(expectedList)
        Truth.assertThat(actual).isEqualTo(expectedString)
    }

    @Test
    fun `should parse to a string list`() {
        val actual = converters.toStringList(expectedString)
        Truth.assertThat(actual).isEqualTo(expectedList)
    }
}