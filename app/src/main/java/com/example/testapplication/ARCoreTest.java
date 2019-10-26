package com.example.testapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.ar.core.*;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ARCoreTest extends AppCompatActivity {

    private static final String TAG = ARCoreTest.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private Random randomGenerator;

    private ArFragment arFragment;
    private ModelRenderable bananaRenderable, chocolateRenderable, whippedCreamRenderable, milkCartonRenderable, cookieRenderable;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        randomGenerator = new Random();

        setContentView(R.layout.activity_arcore_test2);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        setUpShoppingModels();


        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    // Create the transformable andy and add it to the anchor.
                    createShoppingItem(1 + randomGenerator.nextInt(6), anchorNode);
                });

        arFragment.getArSceneView().getScene().addOnUpdateListener(
                new Scene.OnUpdateListener() {
                    @Override
                    public void onUpdate(FrameTime frameTime) {
                        Frame arFrame = arFragment.getArSceneView().getArFrame();
                        ArSceneView arSceneView = arFragment.getArSceneView();
                        if (arFrame != null) {
                            Iterator<Plane> planes = arFrame.getUpdatedTrackables(Plane.class).iterator();
                            while (planes.hasNext()) {
                                Plane plane = planes.next();
                                if (plane.getTrackingState() == TrackingState.TRACKING) {
                                    arFragment.getPlaneDiscoveryController().hide();
                                    Collection<Anchor> updatedAnchors = arFrame.getUpdatedAnchors();

                                    plane.getCenterPose();

                                    Iterator<HitResult> hitResultIterator = arFrame.hitTest(0,0).iterator();
                                    while (hitResultIterator.hasNext()) {
                                        HitResult hitResult = hitResultIterator.next();

                                        Anchor anchor = hitResult.createAnchor();
                                        AnchorNode anchorNode = new AnchorNode(anchor);
                                        anchorNode.setParent(arFragment.getArSceneView().getScene());

                                        createShoppingItem(1 + randomGenerator.nextInt(6), anchorNode);

                                    }
                                }
                            }
                        }
                    }
                }
        );
    }

    private void createShoppingItem(int selected, AnchorNode anchorNode) {
        switch (selected) {
            case 1 : TransformableNode banana = new TransformableNode(arFragment.getTransformationSystem());

                banana.getScaleController().setMinScale(0.08f);
                banana.getScaleController().setMaxScale(0.13f);

                banana.setParent(anchorNode);
                banana.setRenderable(bananaRenderable);
                banana.select();
                break;
            case 2 : TransformableNode cookie = new TransformableNode(arFragment.getTransformationSystem());

                cookie.getScaleController().setMinScale(0.08f);
                cookie.getScaleController().setMaxScale(0.13f);

                cookie.setParent(anchorNode);
                cookie.setRenderable(cookieRenderable);
                cookie.select();
                break;
            case 3 : TransformableNode milkCarton = new TransformableNode(arFragment.getTransformationSystem());

                milkCarton.getScaleController().setMinScale(0.08f);
                milkCarton.getScaleController().setMaxScale(0.13f);

                milkCarton.setParent(anchorNode);
                milkCarton.setRenderable(milkCartonRenderable);
                milkCarton.select();
                break;
            case 4 : TransformableNode whippedCream = new TransformableNode(arFragment.getTransformationSystem());

                whippedCream.getScaleController().setMinScale(0.08f);
                whippedCream.getScaleController().setMaxScale(0.13f);

                whippedCream.setParent(anchorNode);
                whippedCream.setRenderable(whippedCreamRenderable);
                whippedCream.select();
                break;
            case 5 : TransformableNode chocolate = new TransformableNode(arFragment.getTransformationSystem());

                chocolate.getScaleController().setMinScale(0.08f);
                chocolate.getScaleController().setMaxScale(0.13f);

                chocolate.setParent(anchorNode);
                chocolate.setRenderable(chocolateRenderable);
                chocolate.select();
                break;
        }
    }

    private void setUpShoppingModels() {
        ModelRenderable.builder()
                .setSource(this, R.raw.banana)
                .build()
                .thenAccept(renderable -> bananaRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.cannedwhipcream)
                .build()
                .thenAccept(renderable -> whippedCreamRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.chocolatebar)
                .build()
                .thenAccept(renderable -> chocolateRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.cookie)
                .build()
                .thenAccept(renderable -> cookieRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.milkcarton)
                .build()
                .thenAccept(renderable -> milkCartonRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
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
