package com.aa.android.common;

import android.net.Uri;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.aa.android.common.data.SharedDataEvent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains common utility methods.
 */
public final class CommonUtils {
    private CommonUtils() {}

    /**
     * Closes a {@code Closeable} object and ignores the exception.
     *
     * @param closeable
     *         the closeable
     *
     * @throws RuntimeException
     *         if a runtime exception occurs while throwing. Any other exception is ignored
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (RuntimeException rethrown) {
            throw rethrown;
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * Asserts this thread IS the UI thread.
     */
    public static void assertUiThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must be called on main thread");
        }
    }

    /**
     * Asserts this thread is NOT the UI thread.
     */
    public static void assertNotUiThread() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called on main thread");
        }
    }

    /**
     * Creats a uri for a {@link com.google.android.gms.wearable.PutDataRequest} with specified path segments. For
     * example, to create a uri with nodeId='12345' and  path='/trunk/branch', you can use:
     * <pre>
     *     <code>
     *     Uri uri = CommonUtils.getUriForDataItem("12345", "trunk", "branch");
     *     </code>
     * </pre>
     *
     * @param nodeId
     *         the node id
     * @param pathSegments
     *         the path segemnts
     *
     * @return the uri for creating a put data request
     */
    public static Uri getUriForDataItem(String nodeId, String... pathSegments) {
        Uri.Builder builder = new Uri.Builder()
                .scheme(PutDataRequest.WEAR_URI_SCHEME)
                .authority(nodeId);

        if (pathSegments != null) {
            for (String pathSegment : pathSegments) {
                builder.appendPath(pathSegment);
            }
        }

        return builder.build();
    }

    /**
     * Creates a uri for a {@link com.google.android.gms.wearable.PutDataRequest} with a specified path.
     *
     * @param nodeId
     *         the node id
     * @param path
     *         the path for the uri
     *
     * @return the uri for creating a put data request
     */
    public static Uri getUriForDataItem(String nodeId, String path) {
        return new Uri.Builder()
                .scheme(PutDataRequest.WEAR_URI_SCHEME)
                .authority(nodeId)
                .path(path)
                .build();
    }

    /**
     * Gets the local node id synchronously. Must NOT be called on the UI thread.
     *
     * @param client
     *         the GoogleApiClient
     *
     * @return the local node id
     */
    public static String getLocalNodeId(GoogleApiClient client) {
        assertNotUiThread();
        NodeApi.GetLocalNodeResult nodeResult
                = Wearable.NodeApi.getLocalNode(client).await();
        return nodeResult.getNode().getId();
    }

    /**
     * Gets the connected node ids synchronously. Must NOT be called on tne UI thread.
     *
     * @param client
     *         the GoogleApiClient
     *
     * @return the list of node ids or an empty list if none are available
     */
    public static List<String> getConnectedNodeIds(GoogleApiClient client) {
        assertNotUiThread();
        NodeApi.GetConnectedNodesResult nodesResult = Wearable.NodeApi.getConnectedNodes(client)
                .await();
        List<Node> nodes = nodesResult.getNodes();
        List<String> results = new ArrayList<>(nodes.size());
        for (Node node : nodes) {
            results.add(node.getId());
        }
        return results;
    }

    /**
     * Gets the node id from this event.
     *
     * @param event
     *         the event
     *
     * @return the node id from which this event was sent
     */
    @Nullable
    public static String getNodeId(SharedDataEvent event) {
        if (event != null) {
            return getNodeId(event.getUri());
        }
        return null;
    }

    /**
     * Gets the node id from this uri.
     *
     * @param uri
     *         the uri
     *
     * @return the node id
     */
    @Nullable
    public static String getNodeId(Uri uri) {
        if (uri != null) {
            return uri.getAuthority();
        }
        return null;
    }
}
