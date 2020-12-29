package com.gilboot.easypark.reservelist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.model.Reserve
import com.gilboot.easypark.databinding.ReserveListBinding
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.repository


// Fragment to show nearby reserves
// operator at that reserve
class ReserveListFragment : Fragment() {
    private val reserveListViewModel: ReserveListViewModel by viewModels {
        ReserveListViewModelFactory(
            repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding =
            DataBindingUtil.inflate<ReserveListBinding>(
                inflater,
                R.layout.reserve_list,
                container,
                false
            )

        binding.reserveListViewModel = reserveListViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            reserveList.adapter = ReserveListAdapter(reserveOnClickListener)
        }

        return binding.root
    }
}

val ReserveListFragment.reserveOnClickListener: ReserveListAdapter.OnClickListener
    get() = ReserveListAdapter.OnClickListener {
        navigateToReserve(it)
    }

fun ReserveListFragment.navigateToReserve(reserve: Reserve) {
    findNavController().navigate(
        ReserveListFragmentDirections.actionReserveListFragmentToReserveFragment(
            reserve
        )
    )
}

