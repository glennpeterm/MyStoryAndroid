
package android.media.videoeditor;

/**
 * This listener interface is used by
 * {@link MediaVideoItem#extractAudioWaveform(ExtractAudioWaveformProgressListener listener)}
 * or
 * {@link AudioTrack#extractAudioWaveform(ExtractAudioWaveformProgressListener listener)}
 * {@hide}
 */
public interface ExtractAudioWaveformProgressListener {
    /**
     * This method notifies the listener of the progress status of
     * an extractAudioWaveform operation.
     * This method may be called maximum 100 times for one operation.
     *
     * @param progress The progress in %. At the beginning of the operation,
     *          this value is set to 0; at the end, the value is set to 100.
     */
    public void onProgress(int progress);
}

