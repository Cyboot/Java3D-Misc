/*
 *      @(#)EarthApp.java 1.1 01/06/21
 *
 * Copyright (c) 1996-2001 Sun Microsystems, Inc. All Rights Reserved.
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
 * Getting Started with the Java 3D API
 * written in Java 3D
 *
 * EarthApp.java demonstrates line texturing.
 * A 'twisted strip' is a continuous surface with a two twists in it.
 * (A Mobius strip has one twist)
 * The Class Strip creates a surface using a TriangleStripArray.
 * A twist strip is placed in a scene graph with a RotationInterpolator
 * so the strip spins.  As the strip spins, when the back faces of the
 * individual triangles face the image plate, they disappear.
 *
 * One twisted strip is created using the inner class (Twist).
 * This visual object is rendered as filled polygons.
 * A second twist visual object is made from the same
 * Geometry with a different Appearance to render as lines (only).
 * The line-only strip helps to see where the filled polygon strip
 * is.
 */
package tutorial;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
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
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class EarthApp extends Applet {

                          
    Appearance createTwistAppearance(){

        Appearance twistAppear = new Appearance();

        String filename = "earth.jpg";
        System.out.println("attempt to load texture from file: "+filename);
        TextureLoader loader = new TextureLoader(filename, this);
        ImageComponent2D image = loader.getImage();

        if(image == null) {
            System.out.println("load failed for texture: "+filename);
        }

        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                      image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);

        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

        twistAppear.setTexture(texture);

        return twistAppear;
    }

    /////////////////////////////////////////////////
    //
    // create scene graph branch group
    //
    public BranchGroup createSceneGraph() {

        BranchGroup contentRoot = new BranchGroup();

        // Create the transform group node and initialize it to the
        // identity. Add it to the root of the subgraph.
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        contentRoot.addChild(objSpin);

        Appearance appear = createTwistAppearance();
        objSpin.addChild(new Sphere(1.0f, Primitive.GENERATE_TEXTURE_COORDS, appear));
        
        Alpha rotationAlpha = new Alpha(-1, 16000);
  
        RotationInterpolator rotator =
                 new RotationInterpolator(rotationAlpha, objSpin);
 
        // a bounding sphere specifies a region a behavior is active
        // create a sphere centered at the origin with radius of 1
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);

        // make background white
        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        contentRoot.addChild(background);

        // Let Java 3D perform optimizations on this scene graph.
        contentRoot.compile();

        return contentRoot;
    } // end of CreateSceneGraph method of EarthApp

    // Create a simple scene and attach it to the virtual universe

    public EarthApp() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        canvas3D.setStereoEnable(false);

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);

    } // end of EarthApp constructor

    //  The following method allows this to be run as an application

    public static void main(String[] args) {
        System.out.println("EarthApp - Java 3D API version 1.2");
        System.out.print("See \"Getting Started with the Java 3D API\"");
        System.out.println(" (Chapter 7)");

        Frame frame = new MainFrame(new EarthApp(), 256, 256);
    } // end of main method of EarthApp

} // end of class EarthApp
