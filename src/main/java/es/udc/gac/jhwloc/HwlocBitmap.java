/*
 * Copyright (C) 2019 Universidade da Coruña
 * 
 * This file is part of jhwloc.
 * 
 * jhwloc is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * jhwloc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jhwloc. If not, see <http://www.gnu.org/licenses/>.
 */
package es.udc.gac.jhwloc;

public class HwlocBitmap implements Cloneable {

	private long handler;
	private String bitmap;

	protected HwlocBitmap() {
		this.handler = -1;
		this.bitmap = null;
	}

	HwlocBitmap(long handler) {
		this.handler = handler;
		this.bitmap = this.toString();
	}

	private void setHandler(long handler) {
		this.handler = handler;
	}

	/**
	 * Allocate a new empty bitmap.
	 * <p>
	 * The bitmap should be freed by a corresponding call to <tt>free()</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_alloc()</tt>.
	 *
	 * @return A valid bitmap or <tt>null</tt>.
	 */
	public static HwlocBitmap alloc() {
		long rc = jhwloc_bitmap_alloc();

		if(rc == -1)
			return null;

		return new HwlocBitmap(rc);
	}

	protected static HwlocBitmap alloc(boolean cpuset) {
		long rc = jhwloc_bitmap_alloc();

		if(rc == -1)
			return null;

		if (cpuset)
			return new HwlocCPUSet(rc);
		else
			return new HwlocNodeSet(rc);
	}

	/**
	 * Allocate a new full bitmap.
	 * <p>
	 * The bitmap should be freed by a corresponding call to <tt>free()</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_alloc_full()</tt>.
	 *
	 * @return A valid bitmap or <tt>null</tt>.
	 */
	public static HwlocBitmap alloc_full() {
		long rc = jhwloc_bitmap_alloc_full();

		if(rc == -1)
			return null;

		return new HwlocBitmap(rc);
	}

	protected static HwlocBitmap alloc_full(boolean cpuset) {
		long rc = jhwloc_bitmap_alloc_full();

		if(rc == -1)
			return null;

		if (cpuset)
			return new HwlocCPUSet(rc);
		else
			return new HwlocNodeSet(rc);
	}

	/**
	 * Copy the contents of bitmap <tt>src</tt> into the already allocated bitmap <tt>dst</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_copy()</tt>.
	 * 
	 * @param dst Destination bitmap.
	 * @param src Source bitmap.
	 * @return 0 on success, -1 on error.
	 */
	public static int copy(HwlocBitmap dst, HwlocBitmap src) {
		return jhwloc_bitmap_copy(dst, src);
	}

	/**
	 * Add index <tt>id</tt> in this bitmap.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_set()</tt>.
	 * 
	 * @param id Index
	 * @return 0 on success, -1 on error.
	 */
	public int set(int id) {
		return jhwloc_bitmap_set(id);
	}

	/**
	 * Add indexes from <tt>begin</tt> to <tt>end</tt> in this bitmap.
	 * <p>
	 * If <tt>end</tt> is -1, the range is infinite. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_set_range()</tt>.
	 * 
	 * @param begin Start index
	 * @param end End index
	 * @return 0 on success, -1 on error.
	 */
	public int set_range(int begin, int end) {
		return jhwloc_bitmap_set_range(begin, end);
	}

	/**
	 * Remove index <tt>id</tt> from this bitmap.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_clr()</tt>.
	 * 
	 * @param id Index
	 * @return 0 on success, -1 on error.
	 */
	public int clr(int id) {
		return jhwloc_bitmap_clr(id);
	}

	/**
	 * Remove indexes from <tt>begin</tt> to <tt>end</tt> in this bitmap.
	 * <p>
	 * If <tt>end</tt> is -1, the range is infinite. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_clr_range()</tt>.
	 * 
	 * @param begin Start index
	 * @param end End index
	 * @return 0 on success, -1 on error.
	 */
	public int clr_range(int begin, int end) {
		return jhwloc_bitmap_clr_range(begin, end);
	}

	/**
	 * Empty this bitmap bitmap and add bit <tt>id</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_only()</tt>.
	 * 
	 * @param id Index
	 * @return 0 on success, -1 on error.
	 */
	public int only(int id) {
		return jhwloc_bitmap_only(id);
	}

	/**
	 * Fill this bitmap and clear the index <tt>id</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_allbut()</tt>.
	 * 
	 * @param id Index
	 * @return 0 on success, -1 on error.
	 */
	public int allbut(int id) {
		return jhwloc_bitmap_allbut(id);
	}

	/**
	 * Test whether bitmap <tt>sub_bitmap</tt> is part of bitmap <tt>super_bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_isincluded()</tt>.
	 * <p>
	 * Note
	 * <ul>
	 * <li>The empty bitmap is considered included in any other bitmap.
	 * </ul>
	 * 
	 * @param sub_bitmap Sub bitmap.
	 * @param super_bitmap Super bitmap.
	 * @return <code>true</code> if <tt>sub_bitmap</tt> is part of <tt>super_bitmap</tt>,
	 * <code>false</code> instead.
	 */
	public static boolean isincluded(HwlocBitmap sub_bitmap, HwlocBitmap super_bitmap) {
		int rc = jhwloc_bitmap_isincluded(sub_bitmap, super_bitmap);

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Free the C memory allocated by this bitmap.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_free()</tt>.
	 */
	public void free() {
		jhwloc_bitmap_free();
	}

	/**
	 * Duplicate this bitmap by allocating a new bitmap and copying bitmap contents.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_dup()</tt>.
	 *
	 * @return A copy of this bitmap object or <tt>null</tt>.
	 */
	@Override
	public Object clone() {
		Object obj = null;

		long handler = jhwloc_bitmap_dup();

		if (handler == -1)
			return null;

		try {
			obj = super.clone();
		} catch(CloneNotSupportedException ex) {
			System.out.println("This should never happen! (CloneNotSupportedException)");
		}

		((HwlocBitmap) obj).setHandler(handler);

		return obj;
	}

	/**
	 * Stringify this bitmap into a newly allocated string.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_asprintf()</tt>.
	 *
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		this.bitmap = jhwloc_bitmap_asprintf();
		return this.bitmap;
	}

	/**
	 * Stringify a bitmap into a newly allocated list string.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_list_asprintf()</tt>.
	 *
	 * @return The list string representation.
	 */
	public String toStringList() {
		return jhwloc_bitmap_list_asprintf();
	}
	
	/**
	 * Stringify a bitmap into a newly allocated taskset-specific string.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_taskset_asprintf()</tt>.
	 *
	 * @return The taskset-specific string representation.
	 */
	public String toStringTasket() {
		return jhwloc_bitmap_taskset_asprintf();
	}

	/**
	 * <tt>OR</tt> this bitmap object and <tt>bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_or()</tt>.
	 *
	 * @param bitmap Bitmap with which to perform the <tt>OR</tt> operation.
	 * @return The bitmap result of the <tt>OR</tt> operation.
	 */
	public HwlocBitmap or(HwlocBitmap bitmap) {
		long handler = jhwloc_bitmap_or(bitmap);
		return new HwlocBitmap(handler);
	}

	/**
	 * <tt>AND</tt> this bitmap object and <tt>bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_and()</tt>.
	 *
	 * @param bitmap Bitmap with which to perform the <tt>AND</tt> operation.
	 * @return The bitmap result of the <tt>AND</tt> operation.
	 */
	public HwlocBitmap and(HwlocBitmap bitmap) {
		long handler = jhwloc_bitmap_and(bitmap);
		return new HwlocBitmap(handler);
	}

	/**
	 * <tt>AND</tt> this bitmap object and the NEGATION of <tt>bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_andnot()</tt>.
	 *
	 * @param bitmap Bitmap with which to perform the <tt>ANDNOT</tt> operation.
	 * @return The bitmap result of the <tt>ANDNOT</tt> operation.
	 */
	public HwlocBitmap andnot(HwlocBitmap bitmap) {
		long handler = jhwloc_bitmap_andnot(bitmap);
		return new HwlocBitmap(handler);
	}

	/**
	 * <tt>XOR</tt> this bitmap object and <tt>bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_xor()</tt>.
	 *
	 * @param bitmap Bitmap with which to perform the <tt>XOR</tt> operation.
	 * @return The bitmap result of the <tt>XOR</tt> operation.
	 */
	public HwlocBitmap xor(HwlocBitmap bitmap) {
		long handler = jhwloc_bitmap_xor(bitmap);
		return new HwlocBitmap(handler);
	}

	/**
	 * <tt>NEGATE</tt> this bitmap object.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_not()</tt>.
	 *
	 * @return The bitmap result of the <tt>NOT</tt> operation.
	 */
	public HwlocBitmap not() {
		long handler = jhwloc_bitmap_not();
		return new HwlocBitmap(handler);
	}

	/**
	 * Keep a single index among those set in this bitmap.
	 * <p>
	 * May be useful before binding so that the process does not have a 
	 * chance of migrating between multiple logical CPUs in the original mask.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_singlify()</tt>.
	 * 
	 * @return 0 on success, -1 on error.
	 */
	public int singlify() {
		return jhwloc_bitmap_singlify();
	}

	/**
	 * Test whether this bitmap object and <tt>bitmap</tt> intersects.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_intersects()</tt>.
	 *
	 * @param bitmap Bitmap with which to test intersection.
	 * @return <code>true</code> if this bitmap intersect with <tt>bitmap</tt>,
	 * <code>false</code> instead.
	 */
	public boolean intersects(HwlocBitmap bitmap) {

		int rc = jhwloc_bitmap_intersects(bitmap);

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Test whether this bitmap object is equal to <tt>bitmap</tt>.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_isequal()</tt>.
	 *
	 * @param bitmap Bitmap with which to test equality.
	 * @return <code>true</code> if this bitmap is equals to <tt>bitmap</tt>,
	 * <code>false</code> instead.
	 */
	@Override
	public boolean equals(Object bitmap) {

		if(bitmap == null)
			return false;

		if(!(bitmap instanceof HwlocBitmap))
			return false;

		if (bitmap == this)
			return true;

		int rc = jhwloc_bitmap_isequal((HwlocBitmap)bitmap);

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Compare this bitmap object and <tt>bitmap</tt> in lexicographic order.
	 * <p>
	 * Lexicographic comparison of bitmaps, starting for their highest indexes.  Compare
	 * last indexes first, then second, etc. The empty bitmap is considered lower than
	 * anything.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_compare()</tt>.
	 * 
	 * @param bitmap Bitmap with which to perform the comparison.
	 * @return Result of the comparison.
	 */
	public int compare(HwlocBitmap bitmap) {
		return jhwloc_bitmap_compare(bitmap);
	}

	/**
	 * Compare this bitmap object and <tt>bitmap</tt> using their lowest index.
	 * <p>
	 * Smaller least significant bit is smaller. The empty bitmap is considered higher than anything.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_compare_first()</tt>.
	 * 
	 * @param bitmap Bitmap with which to perform the comparison.
	 * @return Result of the comparison.
	 */
	public int compare_first(HwlocBitmap bitmap) {
		return jhwloc_bitmap_compare_first(bitmap);
	}

	/**
	 * Empty this bitmap.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_zero()</tt>.
	 */
	public void zero() {
		jhwloc_bitmap_zero();
	}

	/**
	 * Fill this bitmap with all possible indexes (even if those objects don’t exist or are otherwise unavailable).
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_fill()</tt>.
	 */
	public void fill() {
		jhwloc_bitmap_fill();
	}

	/**
	 * Test whether index <tt>id</tt> is part of this bitmap.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_isset()</tt>.
	 * 
	 * @param id Index
	 * @return <code>true</code> if <tt>id</tt> is part of this bitmap,
	 * <code>false</code> instead.
	 */
	public boolean isset(int id) {
		int rc = jhwloc_bitmap_isset(id);

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Test whether this bitmap is empty.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_iszero()</tt>.
	 *
	 * @return <code>true</code> if this bitmap is empty, <code>false</code> instead.
	 */
	public boolean iszero() {
		int rc = jhwloc_bitmap_iszero();

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Test whether this bitmap is completely full.
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_isfull()</tt>.
	 *
	 * @return <code>true</code> if this bitmap is completely full, <code>false</code> instead.
	 */
	public boolean isfull() {
		int rc = jhwloc_bitmap_isfull();

		if(rc == 1)
			return true;

		return false;
	}

	/**
	 * Compute the first index (least significant bit) in this bitmap. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_first()</tt>.
	 * 
	 * @return -1 if no index is set.
	 */
	public int first() {
		return jhwloc_bitmap_first();
	}

	/**
	 * Compute the first unset index (least significant bit) in this bitmap. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_first_unset()</tt>.
	 * 
	 * @return -1 if no index is unset.
	 */
	public int first_unset() {
		return jhwloc_bitmap_first_unset();
	}

	/**
	 * Compute the last index (most significant bit) in this bitmap. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_last()</tt>.
	 * 
	 * @return -1 if no index is set or if this bitmap is infinitely set.
	 */
	public int last() {
		return jhwloc_bitmap_last();
	}

	/**
	 * Compute the last unset index (most significant bit) in this bitmap. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_last_unset()</tt>.
	 * 
	 * @return -1 if no index is unset or if this bitmap is infinitely set.
	 */
	public int last_unset() {
		return jhwloc_bitmap_last_unset();
	}

	/**
	 * Compute the next index in this bitmap which is after index <tt>prev</tt>.
	 * <p>
	 * If <tt>prev</tt> is -1, the first index is returned. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_next()</tt>.
	 * 
	 * @param prev Previous index
	 * @return -1 no index with higher index is set.
	 */
	public int next(int prev) {
		return jhwloc_bitmap_next(prev);
	}

	/**
	 * Compute the next unset index in this bitmap which is after index <tt>prev</tt>.
	 * <p>
	 * If <tt>prev</tt> is -1, the first unset index is returned. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_next_unset()</tt>.
	 * 
	 * @param prev Previous index
	 * @return -1 no index with higher index is unset.
	 */
	public int next_unset(int prev) {
		return jhwloc_bitmap_next_unset(prev);
	}

	/**
	 * Compute the "weight" of this bitmap. 
	 * <p>
	 * Java binding of the hwloc operation <tt>hwloc_bitmap_weight()</tt>.
	 * 
	 * @return The number of indexes that are in this bitmap or -1 if this bitmap
	 * is infinitely set.
	 */
	public int weight() {
		return jhwloc_bitmap_weight();
	}

	/**
	 * Compute the hash code of this bitmap. 
	 * 
	 * @return hash code.
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + this.bitmap.hashCode();
		return hash;
	}

	/********************** PRIVATE NATIVE METHODS 	**********************/
	private static native long jhwloc_bitmap_alloc();
	private static native long jhwloc_bitmap_alloc_full();
	private native void jhwloc_bitmap_free();
	private native long jhwloc_bitmap_dup();
	private static native int jhwloc_bitmap_copy(HwlocBitmap dst, HwlocBitmap src);
	private native int jhwloc_bitmap_set(int id);
	private native int jhwloc_bitmap_set_range(int begin, int end);
	private native int jhwloc_bitmap_clr(int id);
	private native int jhwloc_bitmap_clr_range(int begin, int end);
	private native int jhwloc_bitmap_only(int id);
	private native int jhwloc_bitmap_allbut(int id);
	private native long jhwloc_bitmap_or(HwlocBitmap bitmap);
	private native long jhwloc_bitmap_and(HwlocBitmap bitmap);
	private native long jhwloc_bitmap_andnot(HwlocBitmap bitmap);
	private native long jhwloc_bitmap_xor(HwlocBitmap bitmap);
	private native long jhwloc_bitmap_not();
	private native int jhwloc_bitmap_singlify();
	private native int jhwloc_bitmap_intersects(HwlocBitmap bitmap);
	private static native int jhwloc_bitmap_isincluded(HwlocBitmap sub_bitmap, HwlocBitmap super_bitmap);
	private native int jhwloc_bitmap_isequal(HwlocBitmap bitmap);
	private native int jhwloc_bitmap_compare(HwlocBitmap bitmap);
	private native int jhwloc_bitmap_compare_first(HwlocBitmap bitmap);
	private native void jhwloc_bitmap_zero();
	private native void jhwloc_bitmap_fill();
	private native int jhwloc_bitmap_isset(int id);
	private native int jhwloc_bitmap_iszero();
	private native int jhwloc_bitmap_isfull();
	private native String jhwloc_bitmap_asprintf();
	private native String jhwloc_bitmap_list_asprintf();
	private native String jhwloc_bitmap_taskset_asprintf();
	private native int jhwloc_bitmap_first();
	private native int jhwloc_bitmap_first_unset();
	private native int jhwloc_bitmap_last();
	private native int jhwloc_bitmap_last_unset();
	private native int jhwloc_bitmap_next(int prev);
	private native int jhwloc_bitmap_next_unset(int prev);
	private native int jhwloc_bitmap_weight();
}
