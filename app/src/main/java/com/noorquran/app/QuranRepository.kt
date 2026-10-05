package com.noorquran.app

import android.content.Context
import org.json.JSONObject

/**
 * ایک آیت کا data — عربی اور Roman Urdu دونوں کے ساتھ
 */
data class Ayah(
    val surah: Int,
    val ayah: Int,
    val arabic: String,
    val romanUrdu: String?
)

/**
 * QuranRepository — assets سے عربی متن اور Roman Urdu ترجمہ پڑھتا ہے
 */
class QuranRepository(private val context: Context) {

    /**
     * سورۃ الفاتحہ کی تمام آیات واپس کرتا ہے
     */
    fun loadAlFatihah(): List<Ayah> {
        val arabicMap = readJsonAsset("arabic_quran.json")
        val romanMap = readJsonAsset("roman_urdu.json")

        val list = mutableListOf<Ayah>()
        for (key in arabicMap.keys) {
            val parts = key.split(":")
            if (parts.size != 2) continue
            val surah = parts[0].toIntOrNull() ?: continue
            val ayah = parts[1].toIntOrNull() ?: continue

            list.add(
                Ayah(
                    surah = surah,
                    ayah = ayah,
                    arabic = arabicMap[key] ?: "",
                    romanUrdu = romanMap[key]
                )
            )
        }
        return list.sortedWith(compareBy({ it.surah }, { it.ayah }))
    }

    /**
     * assets سے JSON فائل پڑھ کر Map میں تبدیل کرتا ہے
     */
    private fun readJsonAsset(fileName: String): Map<String, String> {
        return try {
            val jsonText = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
            val jsonObject = JSONObject(jsonText)
            val map = mutableMapOf<String, String>()
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val k = keys.next()
                map[k] = jsonObject.getString(k)
            }
            map
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
