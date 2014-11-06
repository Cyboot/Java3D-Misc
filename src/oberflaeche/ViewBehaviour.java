package oberflaeche;

import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.media.j3d.Behavior;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnAWTEvent;
import javax.vecmath.Vector3d;

public class ViewBehaviour extends Behavior{
	private TransformGroup TGtarget;
	private Transform3D T3Drot = new Transform3D();
	double angle =0;
	final double STEP = 0.000001;
	
	public ViewBehaviour(TransformGroup target) {
		this.TGtarget = target;
	}
	
	@Override
	public void initialize() {
		this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
	}

	@Override
	public void processStimulus(@SuppressWarnings("rawtypes") Enumeration criteria) {
		angle += STEP;
		T3Drot.set(new Vector3d(0,0,-0.001));
		TGtarget.setTransform(T3Drot);
		this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
	}

}
