
class BoardGameRankingActivity : AppCompatActivity(), BoardGameRankingAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_ranking)

        val extras = intent.extras ?: return
        val boardGameId = extras.getInt(BGG_MESSAGE)

        val rankingList = getAllRankingPositions(boardGameId)
        displayRankingList(rankingList)
    }

    private fun getAllRankingPositions(boardGameId: Int): List<Ranking> {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val rankingHandler = RankingHandler(dataBaseHandler)

        return rankingHandler.getAllRankingPositions(boardGameId)
    }

    private fun displayRankingList(rankingList: List<Ranking>) {
        val adapter =
            BoardGameRankingAdapter(
                this,
                rankingList,
                this
            )
        recyclerViewRankingPositions.layoutManager = LinearLayoutManager(this)
        recyclerViewRankingPositions.adapter = adapter
    }

    override fun onItemClick(ranking: Ranking) {
    }
}