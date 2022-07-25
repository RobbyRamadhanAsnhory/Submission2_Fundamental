package com.example.subsmission2_robbyramadhana_md_07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subsmission2_robbyramadhana_md_07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DescCallback<List<UserData>> {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var userQuery: String
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        userAdapter = Adapter()
        bindingMain.listUserMain.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        bindingMain.searchMain.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUser(userQuery).observe(this@MainActivity, {
                        when (it) {
                            is Desc.Error -> onFailed(it.message)
                            is Desc.Loading -> onLoading()
                            is Desc.Success -> it.data?.let { it1 -> onSuccess(it1) }
                        }
                    })
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onSuccess(data: List<UserData>) {
        userAdapter.setAllData(data)
        bindingMain.apply {
            searchIconMain.visibility = invisible
            messageSearch.visibility = invisible
            pbMain.visibility = invisible
            listUserMain.visibility = visible
        }
    }

    override fun onLoading() {
        bindingMain.apply {
            searchIconMain.visibility = invisible
            messageSearch.visibility = invisible
            pbMain.visibility = visible
            listUserMain.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        bindingMain.apply {
            //User yang dicari tidak ditemukan
            if (message == null) {
                searchIconMain.apply {
                    setImageResource(R.drawable.logo_search)
                    visibility = visible
                }
                messageSearch.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                }
                //Memunculkan error pada exception
            } else {
                searchIconMain.apply {
                    setImageResource(R.drawable.logo_search)
                    visibility = visible
                }
                messageSearch.apply {
                    text = message
                    visibility = visible
                }
            }
            pbMain.visibility = invisible
            listUserMain.visibility = invisible
        }
    }
}