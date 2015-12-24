

package android.media.videoeditor;


/**
 * This class allows to render a crossfade (dissolve) effect transition between
 * two videos
 * {@hide}
 */
public class TransitionCrossfade extends Transition {
    /**
     * An object of this type cannot be instantiated by using the default
     * constructor
     */
    @SuppressWarnings("unused")
    private TransitionCrossfade() {
        this(null, null, null, 0, 0);
    }

    /**
     * Constructor
     *
     * @param transitionId The transition id
     * @param afterMediaItem The transition is applied to the end of this
     *      media item
     * @param beforeMediaItem The transition is applied to the beginning of
     *      this media item
     * @param durationMs duration of the transition in milliseconds
     * @param behavior behavior is one of the behavior defined in Transition
     *      class
     *
     * @throws IllegalArgumentException if behavior is not supported.
     */
    public TransitionCrossfade(String transitionId, MediaItem afterMediaItem,
            MediaItem beforeMediaItem, long durationMs, int behavior) {
        super(transitionId, afterMediaItem, beforeMediaItem, durationMs, behavior);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    void generate() {
        super.generate();
    }
}
