package prog

class Table() {
    var players : MutableList<Player> = mutableListOf()

    // When a game starts, this function creates a player by taking user input.
    fun initializePlayer() {
        var nameGotten : Boolean = false
        while (!nameGotten) {
            // Get name
            val name = readLine()

            // Validate name
            // Create player if name is valid
            if (name != null) {
                var unique: Boolean = true
                for (p in this.players) {
                    if (p.name == name) {
                        unique = false
                    }
                }
                if (name == "") {
                    println("You forgot the part where you were supposed to enter a name. Try again.")
                } else if (unique) {
                    this.players.add(Player(name))
                    nameGotten = true
                } else {
                    println("Sorry, that's already $name's name. Try again.")
                }
            }
        }
    }
    var dealer : Dealer = Dealer("Dealer")
}
