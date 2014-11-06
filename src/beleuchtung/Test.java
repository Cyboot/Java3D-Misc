package beleuchtung;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Test extends Applet {

	SimpleUniverse	simpleU;
	static boolean	application	= false;

	public BranchGroup createSceneGraph() {

		BranchGroup objRoot = new BranchGroup();

		Sphere sphere = new Sphere();
		Appearance sphereAppearance = new Appearance();

		sphereAppearance.setColoringAttributes(new ColoringAttributes(
				new Color3f(0.0f, 0.0f, 1.0f), 1));

		Material mat = new Material();
		mat.setAmbientColor(new Color3f(0.0f, 0.0f, 1.0f));
		mat.setDiffuseColor(new Color3f(0.7f, 0.7f, 0.7f));
		mat.setSpecularColor(new Color3f(0.7f, 0.7f, 0.7f));
		sphereAppearance.setMaterial(mat);
		
		Shape3D shape = new Shape3D();
		int polygonCount = 10;
		TriangleStripArray tri = new TriangleStripArray(polygonCount , GeometryArray.COORDINATES, new int[]{polygonCount});
	    
	    
	    
	    tri.setCoordinate(0, new Point3f(0,1,0));
	    tri.setCoordinate(1, new Point3f(0,0,0));
	    tri.setCoordinate(2, new Point3f(1,1,0));
	    tri.setCoordinate(3, new Point3f(1,0,0));
	    
	    shape.setGeometry(tri);
	    shape.setAppearance(sphereAppearance);
	    
		shape.setCapability( Shape3D.ALLOW_APPEARANCE_READ);
	   
		sphere.setAppearance(sphereAppearance);
//		objRoot.addChild(sphere);
		objRoot.addChild(shape);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0.0, 5), 5.0);
		Color3f lightColor = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f light1Direction = new Vector3f(0.0f, 0.0f, -1f);
		DirectionalLight light1 = new DirectionalLight(lightColor,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		AmbientLight ambientLightNode = new AmbientLight(lightColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);
		return objRoot;

	}

	public Test() {

	}

	@Override
	public void init() {

		setLayout(new BorderLayout());

		Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		add("Center", c);

		simpleU = new SimpleUniverse(c);
		BranchGroup scene = createSceneGraph();

		TransformGroup tg = simpleU.getViewingPlatform()
				.getViewPlatformTransform();

		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3f(0f, 0f, 5f));
		tg.setTransform(t3d);

		scene.compile();

		simpleU.addBranchGraph(scene);

	}

	@Override
	public void destroy() {
		simpleU.removeAllLocales();
	}

	public static void main(String[] args) {
		application = true;

		Frame frame = new MainFrame(new Test(), 300, 300);

	}

}
