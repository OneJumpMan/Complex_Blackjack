package prog

// The Deck object represents a deck of 52 playing card
class Deck {

    // This variable and init block create an array of 52 cards, with the appropriate suits and symbols
    private var allcards : Array<Card> = Array(52) { i -> Card(null, null)}

    init {
        var i : Int = 0
        for(suit in listOf("spade", "heart", "club", "diamond")){
            for(symbol in listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")){
                this.allcards[i] = (Card(symbol, suit))
                i += 1
            }
        }
    }

    // Sequence is a random ordering of the numbers 0-51
    // Each element in it corresponds to an index in the allcards array
    // Sequence effectively serves as a shuffling of the deck
    private var sequence = Array(52) { i -> i }

    // Curr is a counter
    // Its purpose is to move through the sequence array, keeping track of where in the deck we are
    private var curr : Int = 0

    // This function performs the act of drawing a new card off the top of the randomly shuffled deck
    // It does this by returning the card in allcards that corresponds to the element in sequence that corresponds to curr, and then incrementing curr
    // If curr is already at the end of sequence, this function returns null.
    fun draw() : Card? {
        if (curr != 52){
            curr += 1
            return allcards[sequence[curr-1]]
        } else {
            return null
        }
    }

    // This function represents shuffling the deck
    // It re-randomizes sequence and sets curr back to 0
    fun newShuffle(){
        sequence.shuffle() // An exceedingly convenient builtin function
        curr = 0
    }
}
