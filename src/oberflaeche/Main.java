package oberflaeche;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Main {

	public static void main(String[] args) {

		// Universum erschaffen
		GraphicsConfiguration g = SimpleUniverse.getPreferredConfiguration();
		Canvas3D leinwand = new Canvas3D(g);
		SimpleUniverse su = new SimpleUniverse(leinwand);

		// Viewposition verscheiben
		ViewingPlatform vp = su.getViewingPlatform();
		Transform3D changevp = new Transform3D();
		changevp.set(new Vector3d(0, 0, 180));
		vp.getViewPlatformTransform().setTransform(changevp);
		
		BranchGroup bg = new BranchGroup();
		
		createContent(bg);

		View view = su.getViewer().getView();
		view.setBackClipDistance(5000);
		
		ViewBehaviour behav = new ViewBehaviour(vp.getViewPlatformTransform());
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(5000);
		behav.setSchedulingBounds(bounds);
		bg.addChild(behav);
		
		
		
		su.addBranchGraph(bg);

		// Rahmen erzeugen
		JFrame frame = new JFrame("Baum");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(leinwand, "Center");
		frame.setSize(700, 700);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter()
		{@Override
		public void windowClosing(WindowEvent e){System.exit(0);}});
	}

	private static void createContent(BranchGroup bg) {
		createLight(bg);
		
	   	bg.addChild(new Oberflache());
	}
	
	private static void createLight(BranchGroup bg){
		BoundingSphere bounds2 =	new BoundingSphere (new Point3d (0, 0.0, 50), 100);
    	Color3f lightColor = new Color3f (0.0f, 0.5f, 0.5f);
    	Vector3f light1Direction = new Vector3f (0.0f, 0.0f, -1f);
    	DirectionalLight light1  = new DirectionalLight (lightColor, light1Direction);
    	light1.setInfluencingBounds (bounds2);
    	bg.addChild (light1);

    	AmbientLight ambientLightNode = new AmbientLight (lightColor);
    	ambientLightNode.setInfluencingBounds (bounds2);
		bg.addChild(ambientLightNode);
	}
}
