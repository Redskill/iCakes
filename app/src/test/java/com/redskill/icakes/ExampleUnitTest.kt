package com.redskill.icakes

import com.redskill.icakes.model.Cake
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun list_is_sorted_without_duplicates() {
        val cakeList = listOf(
            Cake("Lemon", "", ""),
            Cake("Orange", "", ""),
            Cake("Chocolate", "", ""),
            Cake("Lemon", "", "")
        )
        val sortedListNoDuplicate = cakeList.distinct().sortedBy { it.desc }
        assertEquals(
            listOf(
                Cake("Chocolate", "", ""),
                Cake("Lemon", "", ""),
                Cake("Orange", "", ""),
            ), sortedListNoDuplicate
        )
    }
}