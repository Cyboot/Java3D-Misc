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
public class EarthApp extends Applet {

	BranchGroup createScene() {
		BranchGroup objRoot = new BranchGroup();

		Transform3D t3d = new Transform3D();
		t3d.rotX(Math.toRadians(23));

		TransformGroup objRot = new TransformGroup(t3d);

		objRot.addChild(new Planet("earth-big", 22, 0, 0.1f));


		objRoot.addChild(objRot);
		return objRoot;
	}

	public EarthApp() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		canvas3D.setStereoEnable(false);

		SimpleUniverse u = new SimpleUniverse(canvas3D);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed
		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3d(0, 0, 100));
		u.getViewingPlatform().getViewPlatformTransform().setTransform(t3d);
		View view = u.getViewer().getView();
		view.setBackClipDistance(5000);


		u.addBranchGraph(createScene());
	}

	public static void main(String argv[]) {
		new MainFrame(new EarthApp(), 1280, 720);
	}
}
