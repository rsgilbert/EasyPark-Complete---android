package com.gilboot.easypark.visit



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragVisitBinding
import com.gilboot.easypark.util.withUpdateVisit


// Visit where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
class VisitFragment : Fragment() {
    private val visitViewModel: VisitViewModel by viewModels {
        VisitViewModelFactory(visitIdFromArgs)
    }

    lateinit var binding: FragVisitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_visit,
                container,
                false
            )

//        recheckLogin()
        binding.visitViewModel = visitViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            fabMinus.setOnClickListener { completeVisit() }
        }




        return binding.root


    }


}

val VisitFragment.visitIdFromArgs
get() = VisitFragmentArgs.fromBundle(arguments!!).visitId

fun VisitFragment.completeVisit() {
    withUpdateVisit(visitIdFromArgs) {
        findNavController().popBackStack()
    }
}


