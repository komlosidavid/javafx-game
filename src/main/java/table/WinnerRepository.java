package table;

import java.util.Comparator;
import java.util.List;

/**
 * Repository of the winner player.
 */
public class WinnerRepository extends gSonRepository<Winner> {

    /**
     * Constructor of the WinnerRepository.
     */
    public WinnerRepository() {
        super(Winner.class);
    }

    /**
     * Get the winner player data.
     * @return the list of the winner player data
     */
    public List<Winner> getWinners(){
        return listOfElements.stream()
                .sorted(Comparator.comparing(Winner::getWinnerMoves))
                .toList();
    }

}
