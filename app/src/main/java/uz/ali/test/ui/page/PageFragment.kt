package uz.ali.test.ui.page

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.ali.test.R
import uz.ali.test.databinding.FragmentPageBinding
import uz.ali.test.utils.FilterStatus
import java.lang.Exception

class PageFragment(var key: String, var episod0: String) : Fragment() {

    private lateinit var binding: FragmentPageBinding
    private lateinit var viewModel: PageViewModel
    lateinit var filterStatus: FilterStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterStatus = FilterStatus()
        binding = FragmentPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        viewModel.key.value = key
        viewModel.episod0.value = episod0
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setPage()

        viewModel.getNetworkError.observe(viewLifecycleOwner, Observer {

        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressPage.visibility = View.VISIBLE
            } else {
                binding.progressPage.visibility = View.GONE
            }
        })

        viewModel.name.observe(viewLifecycleOwner, Observer {
            binding.name.text = it
        })
        viewModel.statuss.observe(viewLifecycleOwner, Observer {
            binding.onlinee.text = it
            binding.online.setCardBackgroundColor(Color.parseColor(filterStatus.getStatus(it)))
        })
        viewModel.human.observe(viewLifecycleOwner, Observer {
            binding.numan.text = "- " + it
        })
        viewModel.location.observe(viewLifecycleOwner, Observer {
            binding.location.text = it
        })
        viewModel.locationEnd.observe(viewLifecycleOwner, Observer {
            binding.locationEnd.text = it
        })
        viewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            Picasso.get()
                .load(it)
                .into(binding.image, object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        binding.image.setImageResource(R.drawable.error)
                    }

                })
        })
    }

}