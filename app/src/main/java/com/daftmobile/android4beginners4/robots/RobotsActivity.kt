package com.daftmobile.android4beginners4.robots

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daftmobile.android4beginners4.robots.viewmodel.ExternalSourceRobotsViewModel
import com.daftmobile.android4beginners4.robots.viewmodel.LiveDataRobotsViewModel
import com.daftmobile.android4beginners4.robots.viewmodel.RobotsViewModel
import com.daftmobile.android4beginners4.robots.viewmodel.SortingType
import kotlinx.android.synthetic.main.activity_robots.*

class RobotsActivity : AppCompatActivity() {
    private lateinit var viewModel: RobotsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robots)

        viewModel = ViewModelProviders.of(this).get(ExternalSourceRobotsViewModel::class.java)
        viewModel.getRobotList().observe(this, Observer {
            robotTextView.text=it
        })

        addRobotFab.setOnClickListener {
            addNewItem()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sorting_menu, menu)

        if((viewModel as ExternalSourceRobotsViewModel).getSortingType() == SortingType.ASCENDING)
            menu.getItem(1).setChecked(true)
        else
            menu.getItem(2).setChecked(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.setChecked(true)
        return when (item.itemId) {
            R.id.ascending -> {
                (viewModel as ExternalSourceRobotsViewModel).setSortingType(SortingType.ASCENDING)
                true
            }
            R.id.descending -> {
                (viewModel as ExternalSourceRobotsViewModel).setSortingType(SortingType.DESCENDING)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addNewItem() {
        viewModel.addRobot()
    }
}
