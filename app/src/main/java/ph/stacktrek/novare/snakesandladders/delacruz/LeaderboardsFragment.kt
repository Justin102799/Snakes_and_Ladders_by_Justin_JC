package ph.stacktrek.novare.snakesandladders.delacruz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ph.stacktrek.novare.snakesandladders.delacruz.adapter.PlayersAdapter
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.FragmentLeaderboardsBinding
import ph.stacktrek.novare.snakesandladders.delacruz.model.Players
import ph.stacktrek.novare.snakesandladders.delacruz.model.PlayersDAOSQLLiteImplementation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardsFragment.newLeaderboard] factory method to
 * create an instance of this fragment.
 */
class LeaderboardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLeaderboardsBinding
    private lateinit var playersAdapter: PlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLeaderboardsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLeaderboardsPlayers()
    }

    fun getLeaderboardsPlayers() {
        val playersDAO = PlayersDAOSQLLiteImplementation(requireContext())
        playersAdapter = PlayersAdapter(requireContext(), playersDAO.getPlayers() as ArrayList<Players>)
        with(binding.leaderboardsList) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = playersAdapter
        }
    }

    fun addWinner(players: Players, context: Context) {

        val playersDAO = PlayersDAOSQLLiteImplementation(context)

        playersAdapter = PlayersAdapter(context, playersDAO.getPlayers() as ArrayList<Players>)
        playersDAO.addPlayers(players)
        playersAdapter.addPlayer(players)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}