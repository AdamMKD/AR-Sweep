package com.example.testapplication.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testapplication.BlueberryMuffin;
import com.example.testapplication.PoisonBottle;
import com.example.testapplication.R;
import com.example.testapplication.Snowman;
import com.example.testapplication.shopping.Banana;
import com.example.testapplication.shopping.Chocolate;
import com.example.testapplication.shopping.Cookie;
import com.example.testapplication.shopping.MilkCarton;
import com.example.testapplication.shopping.ShoppingItem;
import com.example.testapplication.shopping.WhippedCream;
import com.google.ar.core.*;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import java.util.Iterator;
import java.util.Random;
import java.util.*;

public class ARCoreActivity extends AppCompatActivity {

    private static final String TAG = ARCoreActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable bananaRenderable, chocolateRenderable, whippedCreamRenderable, milkCartonRenderable, cookieRenderable,
                            poisonBottleRenderable, blueberryMuffinRenderable, snowmanRenderable, coffeecupRenderable;
    private ArrayList<ShoppingItem> userItemList;
    private ArrayList<ShoppingItem> spawnedList;
    private Random randomGenerator;

    private boolean readyToSpawnItems;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        spawnedList = new ArrayList<ShoppingItem>();

        //
        new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished % 2000 == 0) {
                    spawnedList.clear();
                } else if (millisUntilFinished % 1000 == 0) {
                    readyToSpawnItems = false;
                } else {
                    readyToSpawnItems = true;
                }
            }

            @Override
            public void onFinish() {
                cancel();
                start();
            }
        }.start();

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

                                    if (spawnedList.size() < 6 && readyToSpawnItems) {
                                        createShoppingItem(1 + randomGenerator.nextInt(10), positionObjectOnPane(plane));
                                    }
                                }
                            }


                        }
                    }
                }
        );


//        arFragment.setOnTapArPlaneListener(new Node.OnTapListener() {
//            @Override
//            public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
//                if (anchorNode.getAnchor() != null) {
//                    arFragment.getArSceneView().getScene().removeChild(anchorNode);
//                    anchorNode.getAnchor().detach();
//                    anchorNode.setParent(null);
//                }
//            }
//        });


    }

//    private void handleOnTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
//        Log.d(TAG, "handleOnTouch");
//        // First call ArFragment's listener to handle TransformableNodes.
//        arFragment.onPeekTouch(hitTestResult, motionEvent);
//
//        //We are only interested in the ACTION_UP events - anything else just return
//        if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
//            return;
//        }
//
//        // Check for touching a Sceneform node
//        if (hitTestResult.getNode() != null) {
//            Log.d(TAG, "handleOnTouch hitTestResult.getNode() != null");
//            Node hitNode1 = hitTestResult.getNode();
//
//            Toast.makeText(ARCoreTest.this, "We've hit Andy!!", Toast.LENGTH_SHORT).show();
//            arFragment.getArSceneView().getScene().removeChild(hitNode1);
//            hitNode1.getAnchor().detach();
//            hitNode1.setParent(null);
//            hitNode1 = null;
//
//        }
//    }


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

                spawnedList.add(new Banana("Banana", 0, R.raw.banana));
                break;

            case 2:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(cookieRenderable);
                transformableNode.select();

                spawnedList.add(new Cookie("Cookie", 0, R.raw.banana));
                break;

            case 3:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(milkCartonRenderable);
                transformableNode.select();

                spawnedList.add(new MilkCarton("Milk Carton", 0, R.raw.banana));
                break;
            case 4:
                transformableNode.getScaleController().setMinScale(0.08f);
                transformableNode.getScaleController().setMaxScale(0.13f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(whippedCreamRenderable);
                transformableNode.select();

                spawnedList.add(new WhippedCream("Whipped Cream", 0, R.raw.banana));
                break;
            case 5:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(chocolateRenderable);
                transformableNode.select();

                spawnedList.add(new Chocolate("Chocolate", 0, R.raw.banana));
                break;
            case 6:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(poisonBottleRenderable);
                transformableNode.select();

                spawnedList.add(new PoisonBottle("Poison Bottle", 0, R.raw.poisonbottle));
                break;
            case 7:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(blueberryMuffinRenderable);
                transformableNode.select();

                spawnedList.add(new BlueberryMuffin("Blueberry Muffin", 0, R.raw.blueberrymuffin));
                break;
            case 8:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(snowmanRenderable);
                transformableNode.select();

                spawnedList.add(new Snowman("Snowman", 0, R.raw.snowman));
                break;
            case 9:
                transformableNode.getScaleController().setMinScale(0.20f);
                transformableNode.getScaleController().setMaxScale(0.25f);

                transformableNode.setParent(anchorNode);
                transformableNode.setRenderable(coffeecupRenderable);
                transformableNode.select();

                spawnedList.add(new Banana("Coffee Cup", 0, R.raw.banana));
                break;
        }

        transformableNode.setOnTapListener(new Node.OnTapListener() {
            @Override
            public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                if (anchorNode.getAnchor() != null) {
                    arFragment.getArSceneView().getScene().removeChild(anchorNode);
                    anchorNode.getAnchor().detach();
                    anchorNode.setParent(null);
                }
            }
        });
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
        ModelRenderable.builder()
                .setSource(this, R.raw.poisonbottle)
                .build()
                .thenAccept(renderable -> poisonBottleRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.blueberrymuffin)
                .build()
                .thenAccept(renderable -> blueberryMuffinRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.snowman)
                .build()
                .thenAccept(renderable -> snowmanRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load the shop item", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.coffeecup)
                .build()
                .thenAccept(renderable -> coffeecupRenderable = renderable)
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
