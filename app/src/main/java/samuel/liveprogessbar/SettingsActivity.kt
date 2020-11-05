package samuel.liveprogessbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*
import kotlin.math.roundToInt

class SettingsActivity : AppCompatActivity() {

    private var sex = 0
    var lifeTm = 83

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        val isFirstRun = pref.getBoolean("isFirstRun", true)
        if (!isFirstRun){
            HeightVal.setText(pref.getFloat("Height", 0F).roundToInt().toString())
            WeightVal.setText(pref.getInt("Weight", 0).toString())
            smokebar.progress = pref.getInt("Smoke", 0)
            smokebar2.progress = pref.getInt("Beer", 0)
            smokebar3.progress = pref.getInt("Drive", 0)
            smokebar4.progress = pref.getInt("Happy", 0)

            diabetes.isChecked = pref.getBoolean("Diabetes", false)
            hearthproblem.isChecked = pref.getBoolean("Corazon", false)
            yearVal.setText(pref.getString("BYear", "1/1999"))

            sex = pref.getInt("sex", 0)
            if (sex == 0){ ma_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent)); fe_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.normalBtn)) }
            if (sex == 1){ fe_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent)); ma_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.normalBtn)) }
        }

    }

    fun male(view: View) {
        sex = 0
        if (sex == 0){ ma_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent)); fe_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.normalBtn)) }
    }
    fun female(view: View) {
        sex = 1

        if (sex == 1){ fe_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent)); ma_btn.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.normalBtn)) }
    }

    fun exitSave (view: View) {

        lifeTm = liveExpenctacy()

        val pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); val editor = pref.edit()

        editor.putFloat("Height", Integer.parseInt(HeightVal.text.toString()).toFloat())
        editor.putInt("Weight", Integer.parseInt(WeightVal.text.toString()))
        editor.putInt("Smoke", smokebar.progress)
        editor.putInt("Beer", smokebar2.progress)
        editor.putInt("Drive", smokebar3.progress)
        editor.putInt("Happy", smokebar4.progress)
        editor.putBoolean("Diabetes", diabetes.isChecked)
        editor.putBoolean("Corazon", hearthproblem.isChecked)
        editor.putString("BYear", yearVal.text.toString())
        print(yearVal.text.toString())

        editor.putInt("lifeExp", lifeTm)
        editor.putInt("sex", sex)
        editor.putInt("age", calculateAge().roundToInt())

        editor.putBoolean("isFirstRun", false)

        editor.commit()

        val intent = Intent(this, MainActivity::class.java).apply{}
        startActivity(intent)    }

    fun calculateAge(): Float {

        var rawDate = yearVal.text.toString()
        var delimiter = "/"

        val date = rawDate.split(delimiter)
        print(date[0])
        print(date[1])

        val month = date[0].toFloat()
        val year = date[1].toFloat() * 12

        val birth = month + year
        val now = Calendar.getInstance().get(Calendar.MONTH) + (Calendar.getInstance().get(Calendar.YEAR) * 12)

        val age = (now - birth) / 12

        return age
    }

    override fun onBackPressed() {
        // Put your own code here which you want to run on back button click.
        finishAffinity()
        super.onBackPressed()
    }

    private fun liveExpenctacy(): Int {
        if (sex == 0){ lifeTm -= 3 }
        if (sex == 1) { lifeTm += 3 }

        lifeTm -= BMIclaculate()

        val smokebar = findViewById<SeekBar>(R.id.smokebar)
        if (smokebar.progress == 3){ lifeTm -= 8}
        if (smokebar.progress == 2){ lifeTm -= 6}
        if (smokebar.progress == 1){ lifeTm -= 2}
        if (smokebar.progress == 0){ lifeTm -= 0}

        val beerbar = findViewById<SeekBar>(R.id.smokebar2)
        if (beerbar.progress == 0){ lifeTm -= 0}
        if (beerbar.progress == 1){ lifeTm -= 1}
        if (beerbar.progress == 2){ lifeTm -= 2}
        if (beerbar.progress == 3){ lifeTm -= 3}

        val drivebar = findViewById<SeekBar>(R.id.smokebar3)
        if (drivebar.progress == 0){ lifeTm += 1}
        if (drivebar.progress == 2){ lifeTm -= 1}
        if (drivebar.progress == 3){ lifeTm -= 2}

        val happybar = findViewById<SeekBar>(R.id.smokebar4)
        if (happybar.progress == 0){ lifeTm -= 1}
        if (happybar.progress == 1){ lifeTm += 1}
        if (happybar.progress == 2){ lifeTm += 2}
        if (happybar.progress == 3){ lifeTm += 3}

        if (diabetes.isChecked) { lifeTm -= 7 }
        if (hearthproblem.isChecked) { lifeTm -= 8 }

        return lifeTm
    }

    private fun BMIclaculate(): Int {
        val height = Integer.parseInt(HeightVal.getText().toString()).toFloat() / 100
        val weight = Integer.parseInt(WeightVal.getText().toString())
        val BMI = weight / (height * height)

        var minuslife = 0

        if (BMI >= 0 ) { minuslife = 3 }
        if (BMI >= 19 ) { minuslife = 0 }
        if (BMI >= 24 ) { minuslife = 3 }
        if (BMI >= 29 ) { minuslife = 5 }
        if (BMI >= 39 ) { minuslife = 9 }

        return minuslife
    }

}

