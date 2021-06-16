.BoardGameHandler
import com.example.boardgamecollector.database.DataBaseHandler
import com.example.boardgamecollector.database.RankingHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BoardGameCollectionAdapter.OnItemClickListener {
    private val orderByList = listOf("title", "year", "ranking")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayUserBoardGames(orderByList[0])

        val spinner = createOrderBySpinner()
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                val orderByElement = parent.getItemAtPosition(position) as? String ?: return
                displayUserBoardGames(orderByElement)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val buttonNewBoardGame: Button = findViewById(R.id.buttonNewBoardGameId)
        buttonNewBoardGame.setOnClickListener {
            showNewBoardGameActivity()
        }

        val buttonBggScreen: Button = findViewById(R.id.buttonBggScreenId)
        buttonBggScreen.setOnClickListener {
            showBoardGameGeekActivity()
        }

        val buttonLocations: Button = findViewById(R.id.buttonLocationsId)
        buttonLocations.setOnClickListener {
            showBoardGameLocationsActivity()
        }
    }

    override fun onRestart() {
        super.onRestart()
        displayUserBoardGames(orderByList[0])
    }

    private fun createOrderBySpinner(): Spinner {
        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, orderByList
        )
        spinner.adapter = adapter
        return spinner
    }

    private fun displayUserBoardGames(orderByElement: String) {
        val dataBaseHandler = DataBaseHandler(this, null, null, 1)
        val boardGameHandler = BoardGameHandler(dataBaseHandler)
        val boardGamesList: MutableList<BoardGame> = boardGameHandler.getAllBoardGames()
                as MutableList<BoardGame>

        val rankingHandler = RankingHandler(dataBaseHandler)
        for (boardGame in boardGamesList) {
            if (boardGame.bggId != 0) {
                boardGame.ranking = rankingHandler.getRankingPosition(boardGame.bggId)
            }
        }

        when (orderByElement) {
            orderByList[0] -> boardGamesList.sortBy { it.originalTitle }
            orderByList[1] -> boardGamesList.sortByDescending { it.yearPublished }
            orderByList[2] -> boardGamesList.sortBy { it.ranking }
        }

        val adapter =
            BoardGameCollectionAdapter(
                this,
                boardGamesList,
                this,
                this::deleteBoardGame
            )
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter
    }

    private fun showNewBoardGameActivity() {
        val intent = Intent(this, NewBoardGameSearchActivity::class.java)
        startActivity(intent)
    }

    private fun showBoardGameGeekActivity() {
        val intent = Intent(this, BoardGameGeekActivity::class.java)
        startActivity(intent)
    }

    private fun showBoardGameLocationsActivity() {
        val intent = Intent(this, BoardGameLocationListActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClick(game: BoardGame) {
        val intent = Intent(this, BoardGameDetailsActivity::class.java)
        intent.putExtra(BGG_MESSAGE, game.id)
        startActivity(intent)
    }

    private fun deleteBoardGame(boardGame: BoardGame) {
        val spinner: Spinner = findViewById(R.id.spinner)
        val order = spinner.selectedItem as String
        Thread {
            val dataBaseHandler = DataBaseHandler(this, null, null, 1)
            val boardGameHandler = BoardGameHandler(dataBaseHandler)

            boardGameHandler.deleteBoardGame(boardGame.id)
            displayUserBoardGames(order)
        }.run()
    }
}