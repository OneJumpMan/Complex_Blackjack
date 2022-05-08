package prog

open class Player(val name : String) {

    // This list represents the player's hand
    var hand : MutableList<Card> = mutableListOf<Card>()

    // This bool indicates that the player is still in the round
    // It will be set to false when they choose to stay, or when they bust
    var hitting : Boolean = true


    fun printFullHand() {
        for (card in hand) {
            println(card.symbol + " " + card.suit)
        }
        println("Total: " + String.format("%.4f", this.countHand()) )
    }

    open fun printVisibleHand() {
        for (i in (0 until hand.count())) {
            if (i < 2) { println("facedown") }
            else { println(hand[i].symbol + " " + hand[i].suit) }
        }
    }

    fun countHand() : Double {
        var total: Double = 0.0 // The eventual return
        val aces: MutableList<Card> = mutableListOf()// A list to keep track of the aces, which we have to count differently

        var real : Double = 0.0
        var imaginary : Double = 0.0

        // This for loop passes thru the list once, adding up what it can
        for (card in this.hand) {
            if (card.symbol != "A") {
                val cardValue = when (card.symbol) {
                    "2" -> 2
                    "3" -> 3
                    "4" -> 4
                    "5" -> 5
                    "6" -> 6
                    "7" -> 7
                    "8" -> 8
                    "9" -> 9
                    else -> 10
                }

                if (card.suit == "spades" || card.suit == "clubs") {
                    real += cardValue
                } else {
                    imaginary += cardValue
                }

            } else {
                aces.add(card)
            }
        }

        // Then we have to add the aces
        // The complication comes when we have aces in more than one dimension, and we can't make all of them 11
        // For example, consider if the totals before counting aces were 9 real, 0 imaginary, and we had one of each kind of ace.
        // Either one, but not both of the aces can be counted as 11, and the absolute total is very different depending on which one we choose.
        // So, how do we choose?
        // Well, basically, in order to maximize the absolute total, we want to give the currently biggest dimension the first chance for its aces to be 11.
        // So that's what all this messy code below is doing.
        val biggestDimension : Array<String> = if (real > imaginary) {arrayOf("spade", "club")} else {arrayOf("heart", "diamond")}
        // This for loop takes care of adding the aces to the total
        for (card in aces) {
            if (card.suit in biggestDimension) {
                if ("spades" in biggestDimension) {
                    real += if (kotlin.math.sqrt((real + 11) * (real + 11) + imaginary * imaginary) <= 21) {
                        11
                    } else {
                        1
                    }
                } else {
                    imaginary += if (kotlin.math.sqrt(real * real + (imaginary + 11) * (imaginary + 11)) <= 21) {
                        11
                    } else {
                        1
                    }
                }
            }
        }
        return kotlin.math.sqrt(real * real + imaginary * imaginary)
    }
}
