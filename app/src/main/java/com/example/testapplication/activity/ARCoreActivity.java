package com.example.testapplication.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testapplication.R;
import com.google.ar.core.*;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Iterator;
import java.util.Random;

public class ARCoreActivity extends AppCompatActivity {

    private static final String TAG = ARCoreActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable bananaRenderable, chocolateRenderable, whippedCreamRenderable, milkCartonRenderable, cookieRenderable;
    private Random randomGenerator;

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
                        if (arFrame != null) {

                            Iterator<Plane> planes = arFrame.getUpdatedTrackables(Plane.class).iterator();

                            while (planes.hasNext()) {
                                Plane plane = planes.next();
                                if (plane.getTrackingState() == TrackingState.TRACKING) {
                                    arFragment.getPlaneDiscoveryController().hide();
                                    createShoppingItem(1 + randomGenerator.nextInt(6), positionObjectOnPane(plane));
                                }
                            }


                        }
                    }
                }
        );
    }


    private AnchorNode positionObjectOnPane(Plane plane) {
        float maxX = plane.getExtentX() * 2;
        float randomX = (maxX * randomGenerator.nextFloat()) - plane.getExtentX();

        float maxZ = plane.getExtentZ() * 2;
        float randomZ = (maxZ * randomGenerator.nextFloat()) - plane.getExtentZ();

        Pose pose = plane.getCenterPose();
        float[] translation = pose.getTranslation();
        float[] rotation = pose.getRotationQuaternion();

        translation[0] += randomX;
        translation[2] += randomZ;
        pose = new Pose(translation, rotation);

        Anchor anchor = arFragment.getArSceneView().getSession().createAnchor(pose);
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        return anchorNode;
    }

    private void createShoppingItem(int selected, AnchorNode anchorNode) {
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        switch (selected) {
            case 1:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(bananaRenderable);
                transformableNode.select();
                break;

            case 2:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(cookieRenderable);
                transformableNode.select();
                break;

            case 3:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(milkCartonRenderable);
                transformableNode.select();
                break;
            case 4:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(whippedCreamRenderable);
                transformableNode.select();
                break;
            case 5:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(chocolateRenderable);
                transformableNode.select();
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
