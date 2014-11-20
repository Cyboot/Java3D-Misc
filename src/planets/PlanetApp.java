package planets;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * PrimitiveTextureApp creates a single plane with texture mapping.
 */
public class PlanetApp extends Applet {

	BranchGroup createScene() {
		BranchGroup objRoot = new BranchGroup();

		Transform3D t3d = new Transform3D();
		t3d.rotX(Math.toRadians(30));

		TransformGroup objRot = new TransformGroup(t3d);

		float distanceFactor = 1.5f;

		objRot.addChild(new Planet("sun-small", 10, 0));

		objRot.addChild(new Planet("merkury-small", 2, 15 * distanceFactor));
		objRot.addChild(new Planet("line", 15 * distanceFactor, 0));

		objRot.addChild(new Planet("venus-small", 2, 20 * distanceFactor));
		objRot.addChild(new Planet("line", 20 * distanceFactor, 0));

		objRot.addChild(new Planet("earth-k", 3, 30 * distanceFactor));
		objRot.addChild(new Planet("line", 30 * distanceFactor, 0));

		objRot.addChild(new Planet("mars-small", 3, 40 * distanceFactor));
		objRot.addChild(new Planet("line", 40 * distanceFactor, 0));

		objRot.addChild(new Planet("jupiter-small", 8, 60 * distanceFactor));
		objRot.addChild(new Planet("line", 60 * distanceFactor, 0));

		objRot.addChild(new Planet("saturn-small", 6, 80 * distanceFactor));
		objRot.addChild(new Planet("line", 80 * distanceFactor, 0));

		objRot.addChild(new Planet("uranus-small", 5, 95 * distanceFactor));
		objRot.addChild(new Planet("line", 95 * distanceFactor, 0));

		objRot.addChild(new Planet("neptune-small", 5, 110 * distanceFactor));
		objRot.addChild(new Planet("line", 110 * distanceFactor, 0));


		objRoot.addChild(objRot);
		return objRoot;
	}

	public PlanetApp() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		canvas3D.setStereoEnable(false);

		SimpleUniverse u = new SimpleUniverse(canvas3D);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed
		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3d(0, 0, 500));
		u.getViewingPlatform().getViewPlatformTransform().setTransform(t3d);
		View view = u.getViewer().getView();
		view.setBackClipDistance(5000);


		u.addBranchGraph(createScene());
	}

	public static void main(String argv[]) {
		new MainFrame(new PlanetApp(), 1920, 1080);
	}
}
