package es.udc.gac.jhwloc.examples;

import es.udc.gac.jhwloc.*;

public class Bitmaps {
	public static void main(String[] args) {
		HwlocTopology topo = new HwlocTopology();

		/* initialize a topology context */
		topo.init();
		/* build the topology created and configured above */		
		topo.load();

		int nbpus = topo.get_nbobjs_by_type(HWLOC.OBJ_PU);
		HwlocObject firstpu = topo.get_obj_by_type(HWLOC.OBJ_PU, 0);
		HwlocObject lastpu = topo.get_obj_by_type(HWLOC.OBJ_PU, nbpus-1);

		HwlocBitmap set = (HwlocBitmap) firstpu.getCPUSet().clone();
		HwlocBitmap res = set.or(lastpu.getCPUSet());
		System.out.println("bitmap is "+res);
		set.free();
		res.free();

		/* terminate this topology context */
		topo.destroy();
	}
}