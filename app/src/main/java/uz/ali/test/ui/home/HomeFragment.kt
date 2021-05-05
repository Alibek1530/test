package uz.ali.test.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.ali.test.R
import uz.ali.test.app.App
import uz.ali.test.databese.DataDao
import uz.ali.test.databinding.FragmentHomeBinding
import uz.ali.test.models.characters.Result
import uz.ali.test.ui.adapters.EndlessRecyclerViewScrollListener
import uz.ali.test.ui.adapters.MyAdapter
import uz.ali.test.ui.page.PageFragment


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var roomDao: DataDao
    var b = true

    init {
        roomDao = App.getAppDB().getDataDao()
    }

    var itemCliked: MyAdapter.OnItemClickedListener? = null

    var adapter: MyAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.start()

        itemCliked = object : MyAdapter.OnItemClickedListener {
            override fun itemClicked(position: String,episod0:String) {
                fragmentManager!!.beginTransaction().replace(R.id.nav_host_fragment, PageFragment(position,episod0)).addToBackStack("").commit()
            }

        }

        var layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewHome.layoutManager = layoutManager
        adapter = MyAdapter(ArrayList(), itemCliked as MyAdapter.OnItemClickedListener)
        binding.recyclerViewHome.adapter = adapter

        binding.recyclerViewHome.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.next()
            }

        })


        viewModel.getCharacterList.observe(viewLifecycleOwner, Observer {
            adapter!!.addList(it)
            b = false
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressHome.visibility = View.VISIBLE
            } else {
                binding.progressHome.visibility = View.GONE
            }
        })
        viewModel.getNetworkError.observe(viewLifecycleOwner, Observer {
            if (b) {
                adapter!!.addListOffline(roomDao.getAllData())
                b = false
            }
        })
    }

}