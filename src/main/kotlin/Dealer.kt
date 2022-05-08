package prog

class Dealer (name : String) : Player("Dealer") {

    // create a deck object and shuffle it
    private val deck: Deck = Deck()
    init{
        this.deck.newShuffle()
    }

    override fun printVisibleHand() {
        for (i in (0 until hand.count())) {
            if (i < 1) { println("facedown") }
            else { println(hand[i].symbol + " " + hand[i].suit) }
        }
    }

    fun hit(player: Player) {
        this.deck.draw()?.let { player.hand.add(it) }
    }

    fun finish() {
        while (this.countHand() < 17) {
            this.hit(this)
        }
    }

}
