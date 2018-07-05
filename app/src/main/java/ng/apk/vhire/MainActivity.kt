package ng.apk.vhire

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ng.apk.vhire.ui.bookings.BookingsFragment
import ng.apk.vhire.ui.profile.ProfileFragment
import ng.apk.vhire.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private var selectedFragment: Fragment = SearchFragment.newInstance()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search -> selectedFragment = SearchFragment.newInstance()
            R.id.navigation_bookings -> selectedFragment = BookingsFragment.newInstance("")
            R.id.navigation_profile -> selectedFragment = ProfileFragment.newInstance()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, selectedFragment)
        transaction.addToBackStack(selectedFragment.tag)
        transaction.commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras
        val navigation = findViewById<BottomNavigationView>(R.id.navigation) as BottomNavigationView

        if (bundle != null) {
            if (bundle.containsKey("bookings")) {
                selectedFragment = BookingsFragment.newInstance(bundle.getString("bookings", ""))
                navigation.selectedItemId = R.id.navigation_bookings
            } else if (bundle.containsKey("profile")) {
                selectedFragment = ProfileFragment.newInstance()
                navigation.selectedItemId = R.id.navigation_profile
            }
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, selectedFragment)
        transaction.commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


}
