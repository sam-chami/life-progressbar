package samuel.liveprogessbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onBackPressed() {
        // Put your own code here which you want to run on back button click.
        finishAffinity()
        super.onBackPressed()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_settings -> {
            val intent = Intent(this, SettingsActivity::class.java).apply{}
            startActivity(intent)
            true
        }
        R.id.menu_info -> {
            val intent = Intent(this, About::class.java).apply{}
            startActivity(intent)
            true
        }
        R.id.menu_exit -> {
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