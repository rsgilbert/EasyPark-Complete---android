

// Dashboard where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
//class DashboardFragment2 : Fragment() {
//    private val dashboardViewModel: DashboardViewModel by activityViewModels {
//        DashboardViewModelFactory(repository)
//    }
//
//    val dashboardAdapter = DashboardAdapter(dashboardOnClickListener)
//
//
//    lateinit var binding: FragDashboardParkBinding
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//
//        setCorrectDrawerMenu()
//        binding =
//            DataBindingUtil.inflate(
//                inflater,
//                R.layout.frag_dashboard_park,
//                container,
//                false
//            )
//
//
////        recheckLogin()
//        binding.dashboardViewModel = dashboardViewModel
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//            visitList.adapter = dashboardAdapter
//            fabAdd.setOnClickListener {
//                showNumberplateDialog()
//                dashboardViewModel?.getVisits()
//            }
//        }
//
//
//        return binding.root
//
//
//    }
//
//
//}
//
//fun Fragment.recheckLogin() {
//    (activity as MainActivity).setCorrectDrawerMenu()
//}
//
//
//val DashboardFragment.dashboardOnClickListener: DashboardAdapter.OnClickListener
//    get() = DashboardAdapter.OnClickListener {
//        navigateToVisit(it._id)
//    }
//
//fun DashboardFragment.navigateToVisit(visitId: String) {
//    findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToVisitFragment(visitId))
//}
//
//fun Fragment.showNumberplateDialog() {
//    NumberplateDialog().show(childFragmentManager, "numberplate dialog")
//}
//
