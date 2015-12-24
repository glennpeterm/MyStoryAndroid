
package android.media.videoeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.tabview.TabViewActivity;

import android.media.videoeditor.VideoEditor.MediaProcessingProgressListener;

/**
 * The VideoEditorFactory class must be used to instantiate VideoEditor objects
 * by creating a new project {@link #create(String)} or by loading an
 * existing project {@link #load(String)}.
 * {@hide}
 */
public class VideoEditorFactory {
    /**
     * This is the factory method for creating a new VideoEditor instance.
     *
     * @param projectPath The path where all VideoEditor internal
     *            files are stored. When a project is deleted the application is
     *            responsible for deleting the path and its contents.
     *
     * @return The VideoEditor instance
     *
     * @throws IOException if path does not exist or if the path can
     *             not be accessed in read/write mode
     */
    public static VideoEditor create(String projectPath) throws IOException {
    	
        /*
         *  If the project path does not exist create it
         */
    	projectPath = TabViewActivity.getProjectPath();
        final File dir = new File(projectPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new FileNotFoundException("Cannot create project path: "
                                                                 + projectPath);
            } else {
                /*
                 * Create the file which hides the media files
                 * from the media scanner
                 */
                if (!new File(dir, ".nomedia").createNewFile()) {
                    throw new FileNotFoundException("Cannot create file .nomedia");
                }
            }
        }

        return new VideoEditorImpl(projectPath);
    }

    /**
     * This is the factory method for instantiating a VideoEditor from the
     * internal state previously saved with the
     * {@link VideoEditor#save(String)} method.
     *
     * @param projectPath The path where all VideoEditor internal files
     *            are stored. When a project is deleted the application is
     *            responsible for deleting the path and its contents.
     * @param generatePreview if set to true the
     *      {@link MediaEditor#generatePreview(MediaProcessingProgressListener
     *             listener)}
     *      will be called internally to generate any needed transitions.
     *
     * @return The VideoEditor instance
     *
     * @throws IOException if path does not exist or if the path can
     *             not be accessed in read/write mode or if one of the resource
     *             media files cannot be retrieved
     */
    public static VideoEditor load(String projectPath, boolean generatePreview)
        throws IOException {
        final VideoEditor videoEditor = new VideoEditorImpl(projectPath);
        if (generatePreview) {
            videoEditor.generatePreview(null);
        }
        return videoEditor;
    }
}
