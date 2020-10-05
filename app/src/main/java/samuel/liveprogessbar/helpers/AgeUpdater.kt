package samuel.liveprogessbar.helpers

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*

class AgeUpdater {

    fun sendData(context: Context) : Int{
        val pref = PreferenceManager.getDefaultSharedPreferences(context); val editor = pref.edit()

        editor.putInt("age", liveExpenctacy(context))

        println(liveExpenctacy(context))

        return liveExpenctacy(context)
    }

    private fun liveExpenctacy(context: Context): Int {
        val pref = PreferenceManager.getDefaultSharedPreferences(context);

        val smoke = pref.getInt("Smoke", 0)
        val beer = pref.getInt("Beer", 0)
        val drive = pref.getInt("Drive", 0)
        val happy = pref.getInt("Happy", 0)

        val diabetes = pref.getBoolean("Diabetes", false)
        val hearthproblem = pref.getBoolean("Corazon", false)
        val sex = pref.getInt("sex", 0)

        var lifeTm = 83

        if (sex == 0){ lifeTm -= 3 }
        if (sex == 1) { lifeTm += 3 }

        lifeTm -= BMIclaculate(context)

        if (smoke == 0){ lifeTm -= 8 }
        if (smoke== 1){ lifeTm -= 6}
        if (smoke== 2){ lifeTm -= 2}
        if (smoke== 3){ lifeTm -= 0}

        if (beer == 0){ lifeTm -= 0}
        if (beer == 1){ lifeTm -= 1}
        if (beer == 2){ lifeTm -= 2}
        if (beer == 3){ lifeTm -= 3}

        if (drive == 0){ lifeTm += 1}
        if (drive == 2){ lifeTm -= 1}
        if (drive == 3){ lifeTm -= 2}

        if (happy == 0){ lifeTm -= 1}
        if (happy == 1){ lifeTm += 1}
        if (happy == 2){ lifeTm += 2}
        if (happy == 3){ lifeTm += 3}

        if (diabetes == true) { lifeTm -= 7 }
        if (hearthproblem == true) { lifeTm -= 8 }

        val age = lifeTm - calculateAge(context)

        return age
    }

    private fun BMIclaculate(context: Context): Int {
        val pref = PreferenceManager.getDefaultSharedPreferences(context);

        val height = pref.getFloat("Height", 0F) / 100
        val weight = pref.getInt("Weight", 0)

        val BMI = weight / (height * height)

        var minuslife = 0

        if (BMI >= 0 ) { minuslife = 3 }
        if (BMI >= 19 ) { minuslife = 0 }
        if (BMI >= 24 ) { minuslife = 3 }
        if (BMI >= 29 ) { minuslife = 5 }
        if (BMI >= 39 ) { minuslife = 9 }

        return minuslife
    }

    fun calculateAge(context: Context): Int {
        val pref = PreferenceManager.getDefaultSharedPreferences(context);
        val rawDate = pref.getString("BYear", "9/2020").toString()

        val delimiter = "/"

        val date = rawDate.split(delimiter)

        val month = date[0].toInt()
        val year = date[1].toInt() * 12

        val birth = month + year
        val now = Calendar.getInstance().get(Calendar.MONTH) + (Calendar.getInstance().get(Calendar.YEAR) * 12)

        val age = (now - birth) / 12
        println(age)

        return age
    }

}