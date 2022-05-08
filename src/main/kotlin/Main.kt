package prog
import java.lang.Math.sqrt
import java.util.*









fun main(args: Array<String>) {
    //////////////////
    //The beforemath//
    //////////////////

    // Welcome message
    println("Welcome to Blackjack!")
    println()

    // Create table object
    val table : Table = Table()

    // Add players
    fun getInteger(prompt : String): Int? {
        print(prompt)
        val num = readLine()
        return try {
            val n = num?.toInt()
            n
        } catch (e : NumberFormatException) {
            (getInteger(prompt))
        }
    }

    val n = getInteger("How many people are playing?\n\t> ")

    if (n != null) {
        for (i in (1 .. n)) {
            print("Enter player $i name:\n\t> ")
            table.initializePlayer()
            println()
        }
    }

    // Deal 2 cards to all players
    table.dealer.hit(table.dealer)
    table.dealer.hit(table.dealer)
    for (player in table.players) {
        table.dealer.hit(player)
        table.dealer.hit(player)
    }

    //////////////////
    //The duringmath//
    //////////////////
    var playersIn : Boolean = true
    while (playersIn) {
        for (player in table.players) {

            if (player.hitting){

                // Show player their own hand
                println()
                println()
                println(player.name + ", here is your hand:")
                player.printFullHand()

                // Show dealer's and opponents' hands
                println()
                println("Here are your opponents hands:")

                println("Dealer:")
                table.dealer.printVisibleHand()
                println()

                for (opponent in table.players) {
                    if (opponent != player) {
                        println(opponent.name + ":")
                        opponent.printVisibleHand()
                        println()
                    }
                }

                // Ask player if they'd like to hit
                println("Would you like to hit (h), or stay (s)?")
                print("\t> ")
                player.hitting = (readLine()?.uppercase() == "H") // Staying by default

                // Hit player, if they deserve it
                if (player.hitting) {
                    table.dealer.hit(player)
                }

                // Show them their hand one more time
                println("Here is your hand now:")
                player.printFullHand()
                println()

                // Let them know if they've busted
                if (player.countHand() > 21) {
                    println("You've busted")
                    println()
                    player.hitting = false
                }
            }
        }

        // Check if any players still love you
        // I have no idea if it's efficient, but I'm pretty proud of this logic construct
        playersIn = false
        for (player in table.players) {
            playersIn = (player.hitting || playersIn)
        }
    }

    /////////////////
    //The aftermath//
    /////////////////

    // The dealer deals to themselves
    table.dealer.finish()

    // Show the dealer's final hand
    println("The dealer's final hand:")
    table.dealer.printFullHand()
    println()

    // Announce winners
    if (table.dealer.countHand() > 21) {
        for (player in table.players) {
            if (player.countHand() >= 21) {
                println("$player wins!")
            }
        }
    } else {
        for (player in table.players) {
            if (player.countHand() <= 21) {
                if (player.countHand() > table.dealer.countHand()){
                    println(player.name + " wins!")
                } else if (player.countHand() == table.dealer.countHand()) {
                    println(player.name + " ties")
                }
            }
        }
    }
}