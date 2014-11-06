package oberflaeche;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class Oberflache extends TransformGroup {
	public Oberflache() {
		Shape3D shape = new Shape3D();

		float MAX_X = 5f;
		int WIDTH = 50;
		int HEIGHT =50;

		int polygonCount = (WIDTH * 2 + 1) * (HEIGHT * 2 + 1) * 2;

		TriangleStripArray tri = new TriangleStripArray(polygonCount,
				GeometryArray.COORDINATES, new int[] { polygonCount });

		for (int k = -HEIGHT, j = 0; k < HEIGHT; k += 2) {
			for (int i = -WIDTH; i <= WIDTH; i++) {
				tri.setCoordinate(
						j++,
						new Point3f(i, 1 + k, (float) (MAX_X * Math.cos(Math
								.sqrt(i * i + (k + 1) * (k + 1))))));
				tri.setCoordinate(j++, new Point3f(i, 0 + k,
						(float) (MAX_X * Math.cos(Math.sqrt(i * i + k * k)))));
			}
			for (int i = WIDTH; i >= -WIDTH; i--) {
				tri.setCoordinate(
						j++,
						new Point3f(i, 1 + k, (float) (MAX_X * Math.cos(Math
								.sqrt(i * i + (k + 1) * (k + 1))))));
				tri.setCoordinate(
						j++,
						new Point3f(i, 2 + k, (float) (MAX_X * Math.cos(Math
								.sqrt(i * i + (k + 2) * (k + 2))))));
			}
		}

		shape.setGeometry(tri);
		shape.setAppearance(getAppearance());

		this.addChild(getRotationTransformGroup(shape));
	}
	
	{
	// tri.setCoordinate(0, new Point3f(0,1,0));
	// tri.setCoordinate(1, new Point3f(0,0,0));
	// tri.setCoordinate(2, new Point3f(1,1,0));
	// tri.setCoordinate(3, new Point3f(1,0,0));
	//
	// tri.setCoordinate(4, new Point3f(2,1,0));
	// tri.setCoordinate(5, new Point3f(2,0,0));
	// tri.setCoordinate(6, new Point3f(3,1,0));
	// tri.setCoordinate(7, new Point3f(3,0,0));
	//
	// tri.setCoordinate(8, new Point3f(3,-1,0));
	// tri.setCoordinate(9, new Point3f(2,0,0));
	// tri.setCoordinate(10, new Point3f(2,-1,0));
	// tri.setCoordinate(11, new Point3f(1,0,0));
	// tri.setCoordinate(12, new Point3f(1,-1,0));
	// tri.setCoordinate(13, new Point3f(0,0,0));
	// tri.setCoordinate(14, new Point3f(0,-1,0));
	// for(int k=0,j=0;k<HEIGHT;k+=2){
	// for(int i=0;i<=WIDTH;i++){
	// tri.setCoordinate(j++, new Point3f(i,1+k,0));
	// tri.setCoordinate(j++, new Point3f(i,0+k,0));
	// }
	// for(int i=WIDTH;i>=0;i--){
	// tri.setCoordinate(j++, new Point3f(i,1+k,0));
	// tri.setCoordinate(j++, new Point3f(i,2+k,0));
	// }
	// }
	}
		
	private Appearance getAppearance() {
		Appearance appear = new Appearance();
		appear.setColoringAttributes(new ColoringAttributes(new Color3f(1,
				0.3f, 0), 1));
		PolygonAttributes pa = new PolygonAttributes();
		pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
		appear.setPolygonAttributes(pa);

		Material mat = new Material();
		mat.setAmbientColor(new Color3f(0.0f, 0.0f, 1.0f));
		mat.setDiffuseColor(new Color3f(0.7f, 0.7f, 0.7f));
		mat.setSpecularColor(new Color3f(0.7f, 0.7f, 0.7f));
		appear.setMaterial(mat);

		return appear;
	}

	private TransformGroup getRotationTransformGroup(Shape3D shape) {
		TransformGroup tf_rotate = new TransformGroup();
		tf_rotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Alpha rotAlpha = new Alpha(-1, 5000);
		RotationInterpolator rotInterpol = new RotationInterpolator(rotAlpha,
				tf_rotate);
		rotInterpol.setMaximumAngle((float) Math.toRadians(40));
		rotInterpol.setMinimumAngle((float) Math.toRadians(-40));
		BoundingSphere bounds = new BoundingSphere();
		bounds.setRadius(5000);
		rotInterpol.setSchedulingBounds(bounds);

//		tf_rotate.addChild(rotInterpol);
		tf_rotate.addChild(shape);
		return tf_rotate;
	}
}
