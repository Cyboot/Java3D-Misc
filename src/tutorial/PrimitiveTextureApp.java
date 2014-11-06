/*
 *      @(#)PrimitiveTextureApp.java 1.1 00/09/25 01:58
 *
 * Copyright (c) 1996-2000 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

/*

 */
package tutorial;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * PrimitiveTextureApp creates a single plane with texture mapping.
 */
public class PrimitiveTextureApp extends Applet {

	BranchGroup createScene() {
		BranchGroup objRoot = new BranchGroup();

		Transform3D transform = new Transform3D();

		Appearance appear = new Appearance();

		String filename = "earth_big.jpg";
		TextureLoader loader = new TextureLoader(filename, this);
		ImageComponent2D image = loader.getImage();

		if (image == null) {
			System.out.println("load failed for texture: " + filename);
		}

		// can't use parameterless constuctor
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
				image.getWidth(), image.getHeight());
		texture.setImage(0, image);

		appear.setTexture(texture);

		TransformGroup rotT = new TransformGroup();

		rotT.addChild(new Sphere(0.7f, Primitive.GENERATE_TEXTURE_COORDS, 100,
				appear));

		RotationInterpolator rotInt = new RotationInterpolator(new Alpha(-1,
				10 * 1000), rotT);
		rotInt.setSchedulingBounds(new BoundingSphere());
		rotT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotT.addChild(rotInt);
		
		Background background = new Background();
		background.setColor(1.0f, 1.0f, 1.0f);
		background.setApplicationBounds(new BoundingSphere());
		objRoot.addChild(rotT);

		return objRoot;
	}

	public PrimitiveTextureApp() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		Canvas3D canvas3D = new Canvas3D(config);
		add("Center", canvas3D);

		canvas3D.setStereoEnable(false);

		SimpleUniverse u = new SimpleUniverse(canvas3D);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		u.getViewingPlatform().setNominalViewingTransform();

		u.addBranchGraph(createScene());
	}

	public static void main(String argv[]) {
		System.out.print("PrimitiveTextureApp.java \n- ");
		System.out
				.println("The simpliest example of using texture mapping on a geometic primitive.\n");
		System.out
				.println("This is a simple example progam from The Java 3D API Tutorial.");
		System.out.println("The Java 3D Tutorial is available on the web at:");
		System.out
				.println("http://java.sun.com/products/java-media/3D/collateral ");

		new MainFrame(new PrimitiveTextureApp(), 256, 256);
	}
}
