package table;

import lombok.*;


/**
 * Represents the class of the winner data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Winner {

    /**
     * Name of the winner player.
     */
    private String winnerName;
    /**
     * Color of the winner player.
     */
    private String winnerColor;
    /**
     * Count of the moves which belongs to the winner player.
     */
    private int winnerMoves;
    /**
     * Date when tha game was played.
     */
    private String date;
}

