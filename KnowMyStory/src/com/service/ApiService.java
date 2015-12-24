/*
 * Know My Story 
 */
package com.service;

import java.util.HashMap;
import java.util.Map;

import com.videoeditor.util.StringUtils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class ApiService extends Service{
	 private static final IntentPool mIntentPool = new IntentPool(8);
	 // Parameters
	    private static final String PARAM_OP = "op";
	    private static final String PARAM_PROJECT_PATH = "project";
	    private static final String PARAM_STORYBOARD_ITEM_ID = "item_id";
	    private static final String PARAM_RELATIVE_STORYBOARD_ITEM_ID = "r_item_id";
	    private static final String PARAM_FILENAME = "filename";
	    private static final String PARAM_MEDIA_ITEM_RENDERING_MODE = "rm";
	    private static final String PARAM_THEME = "theme";
	    private static final String PARAM_REQUEST_ID = "rid";
	    
	    private static final int OP_MEDIA_ITEM_ADD_VIDEO_URI = 100;
	    
	    // Static member variables
	    private static final Map<String, Intent> mPendingIntents = new HashMap<String, Intent>();

	    private static final int OP_VIDEO_EDITOR_EXPORT = 4;
	    private static final String PARAM_HEIGHT = "height";
	    private static final String PARAM_BITRATE = "bitrate";
	    
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	  /**
     * @return A unique id
     */
    public static String generateId() {
        return StringUtils.randomString(6);
    }
    /**
     * Add a new video media item after the specified media item id
     *
     * @param context The context
     * @param projectPath The project path
     * @param mediaItemId The mediaItem id
     * @param afterMediaItemId The id of the media item preceding the media item
     * @param uri The media item URI
     * @param renderingMode The rendering mode
     * @param themeId The theme id
     */
    public static void addMediaItemVideoUri(Context context, String projectPath,
            String mediaItemId, String afterMediaItemId, Uri uri, int renderingMode,
            String themeId) {
        final Intent intent = mIntentPool.get(context, ApiService.class);
        intent.putExtra(PARAM_OP, OP_MEDIA_ITEM_ADD_VIDEO_URI);
        intent.putExtra(PARAM_PROJECT_PATH, projectPath);
        intent.putExtra(PARAM_STORYBOARD_ITEM_ID, mediaItemId);
        intent.putExtra(PARAM_RELATIVE_STORYBOARD_ITEM_ID, afterMediaItemId);
        intent.putExtra(PARAM_FILENAME, uri);
        intent.putExtra(PARAM_MEDIA_ITEM_RENDERING_MODE, renderingMode);
        intent.putExtra(PARAM_THEME, themeId);

        startCommand(context, intent);
    }
    /**
     * Start the service (if it is not running) with the specified Intent
     *
     * @param context The context
     * @param intent The intent
     *
     * @return The request id of the pending request
     */
    private static String startCommand(Context context, Intent intent) {
        final String requestId = StringUtils.randomString(8);
        intent.putExtra(PARAM_REQUEST_ID, requestId);
        mPendingIntents.put(requestId, intent);

        System.out.println("mPendingIntents: "+mPendingIntents);
        context.startService(intent);

       /* final String projectPath = intent.getStringExtra(PARAM_PROJECT_PATH);
        if (projectPath != null) {
            final boolean projectEdited = isProjectBeingEdited(projectPath);
            if (projectEdited) {
                for (ApiServiceListener listener : mListeners) {
                    listener.onProjectEditState(projectPath, projectEdited);
                }
            }
        }*/

        return requestId;
    }
    /**
     * Export the VideoEditor movie
     *
     * @param context The context
     * @param projectPath The project path
     * @param filename The export filename
     * @param height The output movie height
     * @param bitrate The output movie bitrate
     */
    public static void exportVideoEditor(Context context, String projectPath, String filename,
            int height, int bitrate) {
    	System.out.println("export path: "+projectPath);
    	System.out.println("export file: "+filename);
        final Intent intent = mIntentPool.get(context, ApiService.class);
        intent.putExtra(PARAM_OP, OP_VIDEO_EDITOR_EXPORT);
        intent.putExtra(PARAM_PROJECT_PATH, projectPath);
        intent.putExtra(PARAM_FILENAME, filename);
        intent.putExtra(PARAM_HEIGHT, height);
        intent.putExtra(PARAM_BITRATE, bitrate);

        startCommand(context, intent);
    }
    /**
     * Checks if the service is busy modifying the timeline. While
     * the video editor is busy the application should not attempt
     * to preview the movie.
     *
     * @param projectPath The project path
     *
     * @return {@code true} if the video editor is modifying the timeline
     */
    /*public static boolean isProjectBeingEdited(String projectPath) {
        for (Intent intent : mPendingIntents.values()) {
            final int op = intent.getIntExtra(PARAM_OP, -1);
            switch (op) {
                // When these operations are pending the video editor is not busy.
                case OP_VIDEO_EDITOR_LOAD_PROJECTS:
                case OP_VIDEO_EDITOR_SAVE:
                case OP_MEDIA_ITEM_SET_VOLUME:
                case OP_MEDIA_ITEM_SET_MUTE:
                case OP_MEDIA_ITEM_GET_THUMBNAILS:
                case OP_MEDIA_ITEM_LOAD:
                case OP_TRANSITION_GET_THUMBNAIL:
                case OP_AUDIO_TRACK_SET_VOLUME:
                case OP_AUDIO_TRACK_SET_MUTE: {
                    break;
                }

                default: {
                    final String pp = intent.getStringExtra(PARAM_PROJECT_PATH);
                    if (pp != null && pp.equals(projectPath)) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }*/
}
