package samuel.liveprogessbar

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import samuel.liveprogessbar.R.id
import samuel.liveprogessbar.R.layout
import samuel.liveprogessbar.R.string.*
import samuel.liveprogessbar.helpers.Notifications
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var lifeTm = 83
    var lifeRt = 0
    lateinit var mAdView : AdView

    //declaring variables
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "samuel.liveprogressbar"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lifeTm = (pref.getInt("lifeExp", 83))
        lifeRt = (pref.getInt("age", 0))

        val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)
        if (isFirstRun) {
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("¡Bienvenido! Vamos a configurar la aplicación.")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Continuar", DialogInterface.OnClickListener {
                    dialog, id -> val intent = Intent(this, SettingsActivity::class.java).apply{}
                   startActivity(intent)
                })
                // negative button text and action
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Primera Vez")
            // show alert dialog
            alert.show()
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).commit()


        lfExpectancy.text = lifeTm.toString()
        deathProgress.progress = lifeRt
        deathProgress.max = lifeTm
        val percentage = lifeRt * 100 / lifeTm
        textView3.text =  percentage.toString() + "%"

        recommendations()

        val Notifications = Notifications()
        Notifications.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(app_name), "Update settings")
        //Initialize Ads
         MobileAds.initialize(this) {}
         mAdView = findViewById(id.adView)
         val adRequest = AdRequest.Builder().build()
         mAdView.loadAd(adRequest)
     }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    fun nextR (view: View){
        recommendations()
    }
    fun recommendations(){
        val Rv = Random.nextInt(1, 19)
        if (Rv == 1) Recomendations_healh.text = getString(R1)
        if (Rv == 2) Recomendations_healh.text = getString(R2)
        if (Rv == 3) Recomendations_healh.text = getString(R3)
        if (Rv == 4) Recomendations_healh.text = getString(R4)
        if (Rv == 5) Recomendations_healh.text = getString(R5)
        if (Rv == 6) Recomendations_healh.text = getString(R6)
        if (Rv == 7) Recomendations_healh.text = getString(R7)
        if (Rv == 8) Recomendations_healh.text = getString(R8)
        if (Rv == 9) Recomendations_healh.text = getString(R9)
        if (Rv == 10) Recomendations_healh.text = getString(R10)
        if (Rv == 11) Recomendations_healh.text = getString(R11)
        if (Rv == 12) Recomendations_healh.text = getString(R12)
        if (Rv == 13) Recomendations_healh.text = getString(R13)
        if (Rv == 14) Recomendations_healh.text = getString(R14)
        if (Rv == 15) Recomendations_healh.text = getString(R15)
        if (Rv == 16) Recomendations_healh.text = getString(R16)
        if (Rv == 17) Recomendations_healh.text = getString(P17)
        if (Rv == 18) Recomendations_healh.text = getString(P18)
        if (Rv == 19) Recomendations_healh.text = getString(P19)

    }

    fun shareApp(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(share_text))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        id.menu_settings -> {
            val intent = Intent(this, SettingsActivity::class.java).apply{}
            startActivity(intent)
            true
        }
        id.menu_info -> {
            val intent = Intent(this, About::class.java).apply{}
            startActivity(intent)
            true
        }
        id.menu_share -> {
            shareApp()
            true
        }
        id.menu_exit -> {
            finish()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}