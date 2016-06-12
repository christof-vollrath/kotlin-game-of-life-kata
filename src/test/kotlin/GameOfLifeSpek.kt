import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class GameOfLifeSpek: Spek({

    given("Game of Life rules") {
        val gameOfLife = GameOfLife()
        on("living cell") {
            val currentState = true
            on("lonely cell") {
                val nrOfNeighbors = 1
                it("should die") {
                    assertFalse(gameOfLife.shouldBeAlive(currentState, nrOfNeighbors))
                }
            }
            on("cell with 2 neighbours") {
                val nrOfNeighbors = 2
                it("should survive") {
                    assert(gameOfLife.shouldBeAlive(currentState, nrOfNeighbors))
                }
            }
            on("cell with 4 neighbours") {
                val nrOfNeighbors = 4
                it("should die") {
                    assertFalse(gameOfLife.shouldBeAlive(currentState, nrOfNeighbors))
                }
            }
        }
        on("dead cell") {
            val currentState = false
            on("cell with 2 neighbours") {
                val nrOfNeighbors = 2
                it("stay dead") {
                    assertFalse(gameOfLife.shouldBeAlive(currentState, nrOfNeighbors))
                }
            }
            on("cell with 3 neighbours") {
                val nrOfNeighbors = 3
                it("should be born") {
                    assert(gameOfLife.shouldBeAlive(currentState, nrOfNeighbors))
                }
            }
        }
    }

    given("Game of Life neighbours") {
        val gameOfLife = GameOfLife()
        on("no neighbours") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            it("should be 0") {
                assertEquals(0, gameOfLife.countLivingNeighbours(GameOfLife.Cell(1, 1)))
            }
        }
        on("one neighbour") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            gameOfLife.setAlive(0,1)
            it("should be 1") {
                assertEquals(1, gameOfLife.countLivingNeighbours(GameOfLife.Cell(1, 1)))
            }
        }
        on("Eight neighbours") {
            val gameOfLife = GameOfLife()
            for(x in 0..2)
                for (y in 2..4)
                    gameOfLife.setAlive(x,y)
            it("should be 8") {
                assertEquals(8, gameOfLife.countLivingNeighbours(GameOfLife.Cell(1, 3)))
            }
        }
    }

    given("Game of Life board") {
        on("empty board") {
            val gameOfLife = GameOfLife()
            it("should be empty") {
                assertFalse(gameOfLife.isAlive(1, 1))
                assertFalse(gameOfLife.isAlive(0, 1))
            }
        }
        on("board with one living cell") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            it("should have this cell") {
                assert(gameOfLife.isAlive(1, 1))
                assertFalse(gameOfLife.isAlive(0, 1))
            }
        }
    }

    given("Game of Life board and rules") {
        on("board with one living cell") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            it("should die") {
                gameOfLife.nextIteration()
                assertFalse(gameOfLife.isAlive(1, 1))
            }
        }
        on("cell with two neighbours") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            gameOfLife.setAlive(0,0)
            gameOfLife.setAlive(2,0)
            it("should survive") {
                gameOfLife.nextIteration()
                assert(gameOfLife.isAlive(1, 1))
                assertFalse(gameOfLife.isAlive(0, 0))
                assertFalse(gameOfLife.isAlive(2, 0))
            }
        }
        on("board with three living cell in a row") {
            val gameOfLife = GameOfLife()
            gameOfLife.setAlive(1,1)
            gameOfLife.setAlive(1,2)
            gameOfLife.setAlive(1,3)
            it("should die two cells, two cells be born and one stay") {
                gameOfLife.nextIteration()
                assert(gameOfLife.isAlive(0, 2))
                assert(gameOfLife.isAlive(1, 2))
                assert(gameOfLife.isAlive(2, 2))
                assertFalse(gameOfLife.isAlive(1, 1))
                assertFalse(gameOfLife.isAlive(1, 3))
            }
        }
    }

})

