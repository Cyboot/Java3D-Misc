package planets;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import tutorial.NewTextureLoader;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class Planet extends TransformGroup {

	public Planet(String name, float size, float distance) {
		this(name, size, distance, 1);
	}

	public Planet(String name, float size, float distance, float moonSpeed) {
		name += ".png";
		Appearance appear = new Appearance();
		TextureLoader loader = new NewTextureLoader("images/" + name);
		ImageComponent2D image = loader.getImage();

		// can't use parameterless constuctor
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(),
				image.getHeight());
		texture.setImage(0, image);

		if (name.contains("line")) {
			TransparencyAttributes ta = new TransparencyAttributes();
			ta.setTransparencyMode(TransparencyAttributes.BLENDED);
			ta.setTransparency(0.5f);
			appear.setTransparencyAttributes(ta);
		}

		appear.setTexture(texture);

		PolygonAttributes pa = new PolygonAttributes();
		pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);

		if (name.contains("line")) {
			pa.setCullFace(PolygonAttributes.CULL_NONE);
		}
		appear.setPolygonAttributes(pa);


		TransformGroup rotT = new TransformGroup();

		Transform3D t3d = new Transform3D();
		t3d.set(new Vector3d(0, 0, distance));

		TransformGroup transT = new TransformGroup(t3d);
		transT.addChild(new Sphere(size, Primitive.GENERATE_TEXTURE_COORDS, 1000, appear));

		if (name.contains("earth")) {
			Transform3D t3dmoon = new Transform3D();
			t3dmoon.set(new Vector3d(0, 0, 3 * size));
			TransformGroup moonTransT = new TransformGroup(t3dmoon);

			Appearance appMoon = new Appearance();
			TextureLoader loaderM = new NewTextureLoader("images/moon.png");
			ImageComponent2D imageM = loaderM.getImage();
			Texture2D textureMoon = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
					imageM.getWidth(), imageM.getHeight());
			textureMoon.setImage(0, imageM);

			appMoon.setTexture(textureMoon);

			moonTransT.addChild(new Sphere(size / 4f, Primitive.GENERATE_TEXTURE_COORDS, 100,
					appMoon));

			TransformGroup moonRot = new TransformGroup();
			moonRot.setCapability(ALLOW_TRANSFORM_WRITE);
			RotationInterpolator rotInt = new RotationInterpolator(new Alpha(-1,
					(long) (3000 / moonSpeed)), moonRot);
			rotInt.setSchedulingBounds(new BoundingSphere(new Point3d(), 90000));

			moonRot.addChild(rotInt);
			moonRot.addChild(moonTransT);

			transT.addChild(moonRot);
		}


		rotT.addChild(transT);

		if (distance == 0)
			distance = 300;
		RotationInterpolator rotInt = new RotationInterpolator(new Alpha(-1, (long) (Math.pow(
				distance * 0.6, 1.3) * 200)), rotT);
		rotInt.setSchedulingBounds(new BoundingSphere(new Point3d(), 90000));
		rotT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotT.addChild(rotInt);

		this.addChild(rotT);
	}

}
