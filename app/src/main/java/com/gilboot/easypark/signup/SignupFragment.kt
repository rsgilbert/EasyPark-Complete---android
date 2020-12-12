package com.gilboot.easypark.signup

import androidx.fragment.app.Fragment


class SignupFragment : Fragment() {
//    val signupViewModel: SignupViewModel by viewModels()
//    lateinit var binding: FragSignupBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//        binding = DataBindingUtil.inflate<FragSignupBinding>(
//            inflater, R.layout.frag_signup, container,
//            false
//        )
//
//        binding.signupViewModel = signupViewModel
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//
//        }
//
//        ensureNoActiveUser()
//
//
//        return binding.root
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.sign_up_options_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.signup -> {
//                beginSignup()
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//
//        return true
//    }
//}
//
//fun SignupFragment.beginSignup() {
//    val email = binding.editEmail.text.toString()
//    val password = binding.editPassword.text.toString()
//    when {
//        email.isBlank() ->
//            toast("Email cannot be blank")
//        password.isBlank() ->
//            toast("Password cannot be blank")
//
//        else ->
//            when {
//                binding.driverCheckbox.isChecked -> {
//                    val driver = Driver(email = email, password = password)
//                    navigateToDriverInfoFragment(driver)
//                }
//                binding.parkCheckbox.isChecked -> {
//                    val park = Park(email = email, password = password)
//                    navigateToParkInfoFragment(park)
//                }
//            }
//    }
}

//fun SignupFragment.navigateToParkInfoFragment(park: Park) {
//    val action = SignupFragmentDirections.actionSignupFragmentToInfoFragment(park)
//    findNavController().navigate(action)
//}
//
//fun SignupFragment.navigateToDriverInfoFragment(driver: Driver) {
//    val action = SignupFragmentDirections.actionSignupFragmentToDriverInfoFragment(driver)
//    findNavController().navigate(action)
//}
//
//
//// ensure there is no active user and run checkLogin again
//fun SignupFragment.ensureNoActiveUser() {
//    context?.removeUserFromPrefs()
//    (requireActivity() as MainActivity).checkLogin()
//}