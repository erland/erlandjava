package erland.game.racer;

/**
 * Implements a level editor to edit the start block of a track
 */
public class RacerMapStartBlockEditor extends RacerMapBlockEditor {

    protected int getFirstSelectBlock()
    {
        return 64;
    }

    protected int getMaxLevel() {
        return 4;
    }

    protected String getLevelFileGroupLabel() {
        return "mapstartblock";
    }

    protected String getLevelFileLabel() {
        return "mapstartblocks";
    }
}
