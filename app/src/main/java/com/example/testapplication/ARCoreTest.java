package com.example.testapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.ar.core.*;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Iterator;
import java.util.List;

public class ARCoreTest extends AppCompatActivity {

    private static final String TAG = ARCoreTest.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_arcore_test2);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        ModelRenderable.builder()
                .setSource(this, R.raw.magikarpmodel)
                .build()
                .thenAccept(renderable -> andyRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });


        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (andyRenderable == null) {
                        return;
                    }

                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());

                    andy.getScaleController().setMinScale(0.05f);
                    andy.getScaleController().setMaxScale(0.1f);

                    andy.setParent(anchorNode);
                    andy.setRenderable(andyRenderable);
                    andy.select();
                });

        arFragment.getArSceneView().getScene().addOnUpdateListener(
                new Scene.OnUpdateListener() {
                    @Override
                    public void onUpdate(FrameTime frameTime) {
                        Frame arFrame = arFragment.getArSceneView().getArFrame();
                        if (arFrame != null) {
                            Iterator<Plane> updatedTrackablesIterator = arFrame.getUpdatedTrackables(Plane.class).iterator();
                            while (updatedTrackablesIterator.hasNext()) {
                                Plane plane = updatedTrackablesIterator.next();
                                if (plane.getTrackingState() == TrackingState.TRACKING) {
                                    arFragment.getPlaneDiscoveryController().hide();
                                    Iterator<Anchor> updatedAnchorsIterator = arFrame.getUpdatedAnchors().iterator();
                                    List<HitResult> hitTest = arFrame.hitTest(0,0);
                                    Iterator<HitResult> hitTestIterator = hitTest.iterator();
                                    while (hitTestIterator.hasNext()) {
                                        HitResult hitResult = hitTestIterator.next();
                                        Anchor modelAnchor = plane.createAnchor(hitResult.getHitPose());
                                        AnchorNode modelAnchorNode = new AnchorNode(modelAnchor);
                                        modelAnchorNode.setParent(arFragment.getArSceneView().getScene());

                                        TransformableNode modelTransformableNode = new TransformableNode(arFragment.getTransformationSystem());
                                        modelTransformableNode.setParent(modelAnchorNode);
                                        modelTransformableNode.setRenderable(andyRenderable);

                                            modelTransformableNode.getScaleController().setMinScale(0.08f);
                                            modelTransformableNode.getScaleController().setMaxScale(0.13f);

                                        //TODO: Set position on table top
                                    }
                                }
                            }
                        }
                    }
                }
        );
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
